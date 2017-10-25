import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.*;
import org.bson.Document;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        List<User> users = null;
        HashMap<String, ArrayList<ImageLabel> > imageLabelMap = null;

        String xmlFile = "NTCIR13_stage2.xml";
        String imageLabelsFile = "src/main/resources/NTCIR13_lifelog_concepts.csv";
        String labelSummaryOutFile = null;
        //labelSummaryOutFile = "labels.txt";

        try {
            users = parseXmlFile(xmlFile);
            imageLabelMap = parseImageLabels(imageLabelsFile, labelSummaryOutFile);
            databaseOperations(users, imageLabelMap);
            mongoDBOperations(users, imageLabelMap);

            //printDataSummary(users);



            //  db.Minutes.find({"images" : { $elemMatch : {"path" : "u2/2016-09-09/20160909_074643_000.jpg"}}}).toArray()

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void mongoDBOperations(List<User> users, HashMap<String, ArrayList<ImageLabel> > imageLabelMap)
    {

        final String DB_NAME = "NTCIR13LifelogDataset";

        MongoDBController.createController(DB_NAME);
        MongoDBController.connectDatabase();
        MongoDBController.populateDatabase(users, imageLabelMap);


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

    private static void databaseOperations(List<User> users, HashMap<String, ArrayList<ImageLabel> > imageLabelMap) throws SQLException, ClassNotFoundException {

        // JDBC driver name and database URL
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
        System.out.println("MySQL database conection established.");


        DatabaseController.createDatabaseTables();

        try {
            DatabaseController.populateDatabase(users);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("WARNING: User data is already stored in the database.");
        }

        try {
            DatabaseController.insertImageLabels(imageLabelMap);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("WARNING: Image labels are already stored in the database.");
        }

    }


    private static HashMap<String, ArrayList<ImageLabel> > parseImageLabels(String filename, String outFile) throws IOException {

        Scanner scanner = new Scanner(new FileInputStream(filename));

        HashMap<String , Integer> labelMap = new HashMap<>();
        HashMap< String, ArrayList<ImageLabel> > imageLabelMap = new HashMap<>();

        // Read label file
        String label = null;
        String imageId = null;
        String prevImageId = null;

        ArrayList<ImageLabel> labelList = null;

        System.out.println("Parsing image labels...");
        while(scanner.hasNext())
        {
            String[] tokens = scanner.nextLine().split(",");
            if(!tokens[0].contains("jpg"))
                continue;

            imageId = parseImageId(tokens[0]);
            ImageLabel imageLabel = new ImageLabel(imageId, tokens[1]);

            if(!imageId.equals(prevImageId))
            {
                if(prevImageId != null)
                {
                    imageLabelMap.put(prevImageId, labelList);
                }
                labelList = new ArrayList<>();
                prevImageId = imageId;
            }

            labelList.add(imageLabel);

            Integer cnt = labelMap.get(label);
            if(cnt == null)
                labelMap.put(label, 1);
            else
                labelMap.put(label, cnt + 1);
        }
        scanner.close();

        // Print labels summary
        if(outFile != null) {
            int i = 0;
            Set<String> keys = labelMap.keySet();
            FileWriter writer = new FileWriter(outFile);
            for (String key : keys) {
                writer.write(++i + "-" + key + "-" + labelMap.get(key) + "\n");

                if (i % 100 == 0)
                    writer.flush();
            }

            writer.flush();
            writer.close();
        }


        return imageLabelMap;

    }

    private static String parseImageId(String str)
    {
        String[] idTokens = str.split("/");
        String user = idTokens[0];
        String img = idTokens[1];

        String year = img.substring(0,4);
        String month = img.substring(4,6);
        String day = img.substring(6,8);

        StringBuilder result = new StringBuilder(user + "/" + year + "-" + month + "-" + day + "/" + img);

        return result.toString();

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
