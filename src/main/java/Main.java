import model.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {



    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        List<User> users = null;
        String xmlFile = "NTCIR13_stage2.xml";
        String imageLabelsFile = "NTCIR13_lifelog_concepts.csv";

        try {
            users = parseXmlFile(xmlFile);
            databaseOperations(users);
            printDataSummary(users);
            //parseImageLabels(imageLabelsFile);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static List<User> parseXmlFile(String filename) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        NTCIRParser handler = new NTCIRParser();

        parser.parse(ClassLoader.getSystemResourceAsStream(filename),
                handler);

        System.out.println("End of execution...");

        return handler.getUsers();
    }

    private static void databaseOperations(List<User> users) throws SQLException, ClassNotFoundException {

        // JDBC driver name and main.java.database URL
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String URL = "jdbc:mysql://localhost?serverTimezone=UTC";
        final String DB_NAME = "NTCIR13";

        //  Database credentials
        final String USERNAME = "root";
        final String PASSWORD = "elveda";

        if(users == null || users.size() <= 0) {
            System.out.println("No user data!");
            return;
        }

        DatabaseController.createController(JDBC_DRIVER, URL, DB_NAME, USERNAME, PASSWORD);
        DatabaseController.connectDatabase();
        System.out.println("Database conection established.");

        DatabaseController.createDatabaseTables();
        DatabaseController.populateDatabase(users);


    }


    private static void parseImageLabels(String filename) throws IOException {

        Scanner scanner = new Scanner(new FileInputStream(new File(filename)));
        FileWriter writer = new FileWriter("res/labels.txt");
        HashMap<String , Integer> labelMap = new HashMap<>();
        String label = null;
        while(scanner.hasNext())
        {
            label = scanner.nextLine().split(",")[1];
            Integer cnt = labelMap.get(label);
            if(cnt == null)
                labelMap.put(label, 1);
            else
                labelMap.put(label, cnt + 1);
        }

        int i = 0;
        Set<String> keys = labelMap.keySet();
        for(String key : keys)
        {
            writer.write(++i + "-" + key + "-" + labelMap.get(key) + "\n");

            if(i % 100 == 0)
                writer.flush();
        }

        writer.flush();
        writer.close();
        scanner.close();

    }

    private static void printDataSummary(List<User> users)
    {
        for(User user : users)
        {
            System.out.println("User ID: " + user.getUserId());
            System.out.println("---------------");
            List<Day> days = user.getDays();
            System.out.println("---" +days.size() + " days.---");

            int dayMetricsCnt = 0, dayActivitiesCnt = 0, healthLogCnt = 0, foodLogCnt = 0, drinkLogCnt = 0;
            int totalLocationCnt = 0, totalActivityCnt = 0, totalBodyMatricsCnt = 0, totalMusicCnt = 0;
            int totalImageCnt = 0, totalActivityImageCnt = 0;

            for(Day day : days)
            {
                if(day.getDayMetrics() != null && day.getDayMetrics().getCalories() > 0)
                    dayMetricsCnt ++;

                if(day.getDayActivities() != null && day.getDayActivities().getSteps() > 0)
                    dayActivitiesCnt ++;

                if(day.getHealthLogs() != null && day.getHealthLogs().getTime() != null)
                    healthLogCnt ++;

                if(day.getFoodLogs().size() > 0)
                    foodLogCnt ++;

                if(day.getDrinkLogs().size() > 0)
                    drinkLogCnt ++;

                int locationCnt = 0, activityCnt = 0, bodyMatricsCnt = 0, musicCnt = 0, imageCnt = 0, activityImageCnt = 0;
                List<Minute> minutes = day.getMinutes();
                for(Minute minute : minutes)
                {
                    if(minute.getLocation() != null)
                        locationCnt ++;

                    if(minute.getActivity() != null)
                        activityCnt ++;

                    if(minute.getBodyMetrics() != null && minute.getBodyMetrics().getCalories() > 0)
                        bodyMatricsCnt ++;

                    if(minute.getMusic() != null)
                        musicCnt ++;

                    if(minute.getImages().size() > 0 || minute.getPhoneImages().size() > 0)
                    {
                        imageCnt ++;
                        if(minute.getActivity() != null)
                            activityImageCnt ++;
                    }
                }

                totalLocationCnt += locationCnt;
                totalActivityCnt += activityCnt;
                totalBodyMatricsCnt += bodyMatricsCnt;
                totalMusicCnt += musicCnt;
                totalImageCnt += imageCnt;
                totalActivityImageCnt += activityImageCnt;

            }
            System.out.println("\t-Day Metrics: " + dayMetricsCnt);
            System.out.println("\t-Day Activities: " + dayActivitiesCnt);
            System.out.println("\t-Health Logs: " + healthLogCnt);
            System.out.println("\t-Food Logs: " + foodLogCnt);
            System.out.println("\t-Drink Logs: " + drinkLogCnt);
            System.out.println("\t-Minutes with Location: " + totalLocationCnt);
            System.out.println("\t-Minutes with Activity: " + totalActivityCnt);
            System.out.println("\t-Minutes with Body Metrics: " + totalBodyMatricsCnt);
            System.out.println("\t-Minutes with Music: " + totalMusicCnt);
            System.out.println("\t-Minutes with Image(s): " + totalImageCnt);
            System.out.println("\t-Minutes with Image(s) and Activity: " + totalActivityImageCnt);
            System.out.println();

        }
    }

}
