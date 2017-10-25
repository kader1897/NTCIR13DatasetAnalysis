import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kader.belli on 21.08.2017.
 */

public class DatabaseController {
    private static String driver, url, dbName, username, password;
    private static Connection conn;

    private static boolean isDatabaseActive = false;

    public static void createController(String driver, String url, String dbName, String username, String password){
        DatabaseController.driver = driver;
        DatabaseController.url = url;
        DatabaseController.dbName = dbName;
        DatabaseController.username = username;
        DatabaseController.password = password;
    }

    public static void connectDatabase() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        conn = DriverManager.getConnection(url, username, password);

        setDatabaseActive(true);
        createDatabaseSchema();
    }

    public static void createDatabaseSchema() throws SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS " + dbName;

        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();

        conn.setCatalog(dbName);
    }

    public static void createDatabaseTables() throws SQLException {
        StringBuilder sql1 = new StringBuilder(" CREATE TABLE IF NOT EXISTS day_metrics (" +
                " id int not null, " +
                " calories double, " +
                " steps int, " +
                " sleep_duration int, " +
                " sleep_score int, " +
                " number_of_interruptions int, " +
                " number_of_toss_and_turns int, " +
                " interruption_duration int, " +
                " resting_heart_rate int, " +
                " bike_calories double, " +
                " bike_duration int, " +
                " run_calories double, " +
                " run_duration int, " +
                " run_steps int, " +
                " walk_calories double, " +
                " walk_duration int, " +
                " walk_steps int, " +
                " primary key(id) " +
                " ) " );

        StringBuilder sql2 = new StringBuilder("CREATE TABLE  IF NOT EXISTS day_activities " +
                "( " +
                "id int not null, " +
                "steps int, " +
                "distance double, " +
                "elevation int, " +
                "calories double, " +
                "primary key(id) " +
                ") ");

        StringBuilder sql3 = new StringBuilder("CREATE TABLE IF NOT EXISTS health_logs " +
                "( " +
                "id int not null, " +
                "log_time varchar(5), " +
                "mood varchar(20), " +
                "GLU double, " +
                "UA double, " +
                "CHOL double, " +
                "BP_Low int, " +
                "BP_High int, " +
                "HR double, " +
                "weight double, " +
                "primary key(id) " +
                ") ");


        StringBuilder sql4 = new StringBuilder("CREATE TABLE IF NOT EXISTS users " +
                "( " +
                "id int not null, " +
                "user_name char(2) not null, " +
                "primary key(id) " +
                ") ");

        StringBuilder sql5 = new StringBuilder("CREATE TABLE IF NOT EXISTS days " +
                "( " +
                "id int not null, " +
                "user_id int not null, " +
                "date_of_day char(10), " +
                "image_directory varchar(50), " +
                "day_metrics_id int, " +
                "day_activities_id int, " +
                "health_logs_id int, " +
                "primary key(id), " +
                "foreign key(user_id) references users(id), " +
                "foreign key(day_metrics_id) references day_metrics(id), " +
                "foreign key(day_activities_id) references day_activities(id), " +
                "foreign key(health_logs_id) references health_logs(id) " +
                ") ");

        StringBuilder sql6 = new StringBuilder("CREATE TABLE IF NOT EXISTS food_and_drink_logs " +
                "( " +
                "id int not null, " +
                "day_id int not null, " +
                "log_time varchar(5), " +
                "content varchar(100), " +
                "log_type int(1), " +
                "primary key(id), " +
                "foreign key(day_id) references days(id) " +
                ") ");


        StringBuilder sql7 = new StringBuilder("CREATE TABLE IF NOT EXISTS location " +
                "( " +
                "id int not null, " +
                "location_name varchar(100), " +
                "link varchar(100), " +
                "latitude double, " +
                "longitude double, " +
                "primary key(id) " +
                ") ");

        StringBuilder sql8 = new StringBuilder("CREATE TABLE IF NOT EXISTS body_metrics " +
                "( " +
                "id int not null, " +
                "calories double, " +
                "gsr double, " +
                "heart_rate int, " +
                "skin_temp double, " +
                "steps int, " +
                "primary key(id) " +
                ") ");

        StringBuilder sql9 = new StringBuilder("CREATE TABLE IF NOT EXISTS music " +
                "( " +
                "id int not null, " +
                "song varchar(100),  " +
                "song_mbid varchar(50), " +
                "artist varchar(100), " +
                "artist_mbid varchar(50),  " +
                "album varchar(100),  " +
                "album_mbid varchar(50), " +
                "primary key(id) " +
                ") ");

        StringBuilder sql10 = new StringBuilder("CREATE TABLE IF NOT EXISTS minutes " +
                "( " +
                "id int not null, " +
                "day_id int not null, " +
                "location_id int, " +
                "activity varchar(20), " +
                "body_metrics_id int, " +
                "music_id int, " +
                "primary key(id, day_id), " +
                "foreign key(day_id) references days(id), " +
                "foreign key(location_id) references location(id), " +
                "foreign key(body_metrics_id) references body_metrics(id), " +
                "foreign key(music_id) references music(id) " +
                ") ");

        StringBuilder sql11 = new StringBuilder("CREATE TABLE IF NOT EXISTS image " +
                "( " +
                "id int not null, " +
                "image_id varchar(50) not null, " +
                "minute_id int not null, " +
                "day_id int not null, " +
                "path varchar(50), " +
                "isPhoneImage bool, " +
                "primary key(id), " +
                "foreign key(minute_id, day_id) references minutes(id, day_id) " +
                ") ");


        conn.setAutoCommit(false);

        Statement stmt = conn.createStatement();

        stmt.addBatch(sql1.toString());
        stmt.addBatch(sql2.toString());
        stmt.addBatch(sql3.toString());
        stmt.addBatch(sql4.toString());
        stmt.addBatch(sql5.toString());
        stmt.addBatch(sql6.toString());
        stmt.addBatch(sql7.toString());
        stmt.addBatch(sql8.toString());
        stmt.addBatch(sql9.toString());
        stmt.addBatch(sql10.toString());
        stmt.addBatch(sql11.toString());

        stmt.executeBatch();
        conn.commit();

        stmt.close();
        conn.setAutoCommit(true);

    }

    private static void executeSQLStatement(Statement stmt, String s) throws SQLException {

//        boolean result = false;
        if(isDatabaseActive) {
            {
                stmt.executeUpdate(s);
                conn.commit();
            }
//                ResultSet generatedKeys = stmt.getGeneratedKeys();
//                if (generatedKeys.next()) {
//                    result = generatedKeys.getInt(1);
//                }

        }
//        return result;
    }

    private static void executeSQLBatch(Statement stmt) throws SQLException{
        if(isDatabaseActive)
            stmt.executeBatch();
    }

    public static void populateDatabase(List<User> users) throws SQLException {
        StringBuilder sb = null;
        Statement stmt = null;
        Integer userId, dayId, minuteId, dayMetricsId, dayActivitiesId, healthLogsId, locationId, bodyMetricsId, musicId;

        System.out.println("Populating database...");

        try {

            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            for (User user : users) {
                System.out.println("Parsing data for user: " + user.getUserId());

                sb = new StringBuilder("INSERT INTO users(id, user_name) VALUES (" + user.getId() + ", '" + user.getUserId() + "') ");

                DatabaseController.executeSQLStatement(stmt, sb.toString());
                userId = user.getId();

                List<Day> days = user.getDays();
                for (Day day : days) {
                    dayId = day.getId();

                    System.out.println("\tDate: " + day.getDate());
                    DayMetrics dayMetrics = day.getDayMetrics();
                    if (dayMetrics != null) {
                        sb = new StringBuilder(
                                "INSERT INTO day_metrics" +
                                        "(id, calories, steps, sleep_duration, sleep_score, number_of_interruptions, " +
                                        " number_of_toss_and_turns, interruption_duration, resting_heart_rate, " +
                                        " bike_calories, bike_duration, run_calories, run_duration, run_steps, " +
                                        " walk_calories, walk_duration, walk_steps) " +
                                        " VALUES (" + dayMetrics.getId() + ", " +
                                        dayMetrics.getCalories() + ", " +
                                        dayMetrics.getSteps() + ", " +
                                        dayMetrics.getSleepDuration() + ", " +
                                        dayMetrics.getSleepScore() + ", " +
                                        dayMetrics.getNumberOfInterruptions() + ", " +
                                        dayMetrics.getNumberOfTossAndTurns() + ", " +
                                        dayMetrics.getInterruptionDuration() + ", " +
                                        dayMetrics.getRestingHeartRate() + ", " +
                                        dayMetrics.getBikeCalories() + ", " +
                                        dayMetrics.getBikeDuration() + ", " +
                                        dayMetrics.getRunCalories() + ", " +
                                        dayMetrics.getRunDuration() + ", " +
                                        dayMetrics.getRunSteps() + ", " +
                                        dayMetrics.getWalkCalories() + ", " +
                                        dayMetrics.getWalkDuration() + ", " +
                                        dayMetrics.getWalkSteps() + ") ");

                        stmt.addBatch(sb.toString());
                        //DatabaseController.executeSQLStatement(stmt, sb.toString());
                        dayMetricsId = dayMetrics.getId();

                    } else {
                        dayMetricsId = null;
                    }

                    DayActivities dayActivities = day.getDayActivities();
                    if (dayActivities != null) {
                        sb = new StringBuilder(
                                "INSERT INTO day_activities" +
                                        "(id, steps, distance, elevation, calories) " +
                                        " VALUES (" + dayActivities.getId() + ", " +
                                        dayActivities.getSteps() + ", " +
                                        dayActivities.getDistance() + ", " +
                                        dayActivities.getElevation() + ", " +
                                        dayActivities.getCalories() + ") ");

                        stmt.addBatch(sb.toString());
                        //DatabaseController.executeSQLStatement(stmt, sb.toString());
                        dayActivitiesId = dayActivities.getId();

                    } else {
                        dayActivitiesId = null;
                    }

                    HealthLog healthLog = day.getHealthLogs();
                    if (healthLog != null) {
                        sb = new StringBuilder(
                                "INSERT INTO health_logs" +
                                        "(id, log_time, mood, GLU, UA, CHOL, BP_Low, BP_High, HR, weight) " +
                                        " VALUES (" + healthLog.getId() + ", " +
                                        formatStringForQuery(healthLog.getTime()) + ", " +
                                        formatStringForQuery(healthLog.getContent()) + ", " +
                                        healthLog.getGLU() + ", " +
                                        healthLog.getUA() + ", " +
                                        healthLog.getCHOL() + ", " +
                                        (healthLog.getBP() == null ? 0 : healthLog.getBP().getLow()) + ", " +
                                        (healthLog.getBP() == null ? 0 : healthLog.getBP().getHigh()) + ", " +
                                        healthLog.getHR() + ", " +
                                        healthLog.getWeight() + ") ");

                        stmt.addBatch(sb.toString());
                        //DatabaseController.executeSQLStatement(stmt, sb.toString());
                        healthLogsId = healthLog.getId();

                    } else {
                        healthLogsId = null;
                    }

                    sb = new StringBuilder("INSERT INTO days(id, user_id, date_of_day, image_directory, day_metrics_id, day_activities_id, health_logs_id) " +
                            " VALUES (" + dayId + ", " + userId + ", " +
                            formatStringForQuery(day.getDate()) + ", " +
                            formatStringForQuery(day.getImageDirectory()) + ", " +
                            dayMetricsId + ", " + dayActivitiesId + ", " + healthLogsId + ") ");

                    stmt.addBatch(sb.toString());
                    // DatabaseController.executeSQLStatement(stmt, sb.toString());


                    List<Log> foodLogs = day.getFoodLogs();
                    if (foodLogs.size() > 0) {
                        for (Log log : foodLogs) {
                            sb = new StringBuilder("INSERT INTO food_and_drink_logs" +
                                    "(id, day_id, log_time, content, log_type) VALUES (" +
                                    log.getId() + ", " + dayId + ", " +
                                    formatStringForQuery(log.getTime()) + ", " +
                                    formatStringForQuery(log.getContentForQuery()) + ", 1) ");

                            stmt.addBatch(sb.toString());

                    //        DatabaseController.executeSQLStatement(stmt, sb.toString());
                        }
                    }

                    List<Log> drinkLogs = day.getDrinkLogs();
                    if (drinkLogs.size() > 0) {
                        for (Log log : drinkLogs) {
                            sb = new StringBuilder("INSERT INTO food_and_drink_logs" +
                                    "(id, day_id, log_time, content, log_type) VALUES (" +
                                    log.getId() + ", " + dayId + ", " +
                                    formatStringForQuery(log.getTime()) + ", " +
                                    formatStringForQuery(log.getContentForQuery())  + ", 2) ");

                            stmt.addBatch(sb.toString());
                            //       DatabaseController.executeSQLStatement(stmt, sb.toString());
                        }
                    }

                    executeSQLBatch(stmt);


                    List<Minute> minutes = day.getMinutes();
                    for (Minute minute : minutes) {
                        minuteId = minute.getId();

                        if (minuteId % 100 == 0)
                            System.out.println("\t\tMinute: " + minuteId);

                        Location location = minute.getLocation();
                        if (location != null) {
                            sb = new StringBuilder(
                                    "INSERT INTO location" +
                                            "(id, location_name, link, latitude, longitude) " +
                                            " VALUES (" + location.getId() + ", " +
                                            formatStringForQuery(location.getNameForQuery()) + ", " +
                                            formatStringForQuery(location.getLink()) + ", " +
                                            location.getLatitude() + ", " +
                                            location.getLongitude() + ") ");


                            stmt.addBatch(sb.toString());
                            //DatabaseController.executeSQLStatement(stmt, sb.toString());
                            locationId = location.getId();

                        } else {
                            locationId = null;
                        }

                        BodyMetrics bodyMetrics = minute.getBodyMetrics();
                        if (bodyMetrics != null) {
                            sb = new StringBuilder(
                                    "INSERT INTO body_metrics" +
                                            "(id, calories, gsr, heart_rate, skin_temp, steps) " +
                                            " VALUES (" + bodyMetrics.getId() + ", " +
                                            bodyMetrics.getCalories() + ", " +
                                            bodyMetrics.getGsr() + ", " +
                                            bodyMetrics.getHeartRate() + ", " +
                                            bodyMetrics.getSkinTemp() + ", " +
                                            bodyMetrics.getSteps() + ") ");

                            stmt.addBatch(sb.toString());

                            //DatabaseController.executeSQLStatement(stmt, sb.toString());
                            bodyMetricsId = bodyMetrics.getId();

                        } else {
                            bodyMetricsId = null;
                        }

                        Music music = minute.getMusic();
                        if (music != null) {
                            sb = new StringBuilder(
                                    "INSERT INTO music" +
                                            "(id, song, song_mbid, artist, artist_mbid, album, album_mbid) " +
                                            " VALUES (" + music.getId() + ", " +
                                            formatStringForQuery(music.getSongForQuery()) + ", " +
                                            formatStringForQuery(music.getSongMbid()) + ", " +
                                            formatStringForQuery(music.getArtistForQuery()) + ", " +
                                            formatStringForQuery(music.getArtistMbid()) + ", " +
                                            formatStringForQuery(music.getAlbumForQuery()) + ", " +
                                            formatStringForQuery(music.getAlbumMbid()) + " )");

                            stmt.addBatch(sb.toString());
                            //DatabaseController.executeSQLStatement(stmt, sb.toString());
                            musicId = music.getId();

                        } else {
                            musicId = null;
                        }

                        sb = new StringBuilder("INSERT INTO minutes(id, day_id, location_id, activity, body_metrics_id, music_id) " +
                                " VALUES (" + minuteId + ", " + dayId + ", " + locationId + ", " +
                                formatStringForQuery(minute.getActivity()) + ", " +
                                bodyMetricsId + ", " + musicId + ") ");

                        stmt.addBatch(sb.toString());
                        //DatabaseController.executeSQLStatement(stmt, sb.toString());

                        List<Image> images = minute.getImages();
                        for (Image image : images) {
                            sb = new StringBuilder(
                                    "INSERT INTO image" +
                                            "(id, image_id, minute_id, day_id, path, isPhoneImage) " +
                                            " VALUES (" + image.getId() + ", " +
                                            formatStringForQuery(image.getImageId()) + ", " +
                                            minuteId + ", " +
                                            dayId + ", " +
                                            formatStringForQuery(image.getPath()) + ", false) ");

                            stmt.addBatch(sb.toString());
                            //DatabaseController.executeSQLStatement(stmt, sb.toString());
                        }

                        List<PhoneImage> phoneImages = minute.getPhoneImages();
                        for (PhoneImage phoneImage : phoneImages) {
                            sb = new StringBuilder(
                                    "INSERT INTO image" +
                                            "(id, image_id, minute_id, day_id, path, isPhoneImage) " +
                                            " VALUES (" + phoneImage.getId() + ", " +
                                            formatStringForQuery(phoneImage.getImageId()) + ", " +
                                            minuteId + ", " +
                                            dayId + ", " +
                                            formatStringForQuery(phoneImage.getPath()) + ", true) ");

                            stmt.addBatch(sb.toString());
                            //DatabaseController.executeSQLStatement(stmt, sb.toString());

                        }

                        executeSQLBatch(stmt);
                    }


                }
            }
        }
        finally {

                try {
                    if(stmt != null)
                        stmt.close();
                    conn.setAutoCommit(true);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

        }
    }

    public static void insertImageLabels(HashMap<String, ArrayList<ImageLabel> > imageLabelMap) throws SQLException {
        List< ArrayList<ImageLabel> > imageLabelList = (List<ArrayList<ImageLabel>>) imageLabelMap.values();
        System.out.println("Inserting image labels to database...");
        Statement stmt = null;

        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            for(ArrayList<ImageLabel> imageLabels : imageLabelList)
            {
                for(ImageLabel imageLabel : imageLabels) {
                    StringBuilder sb = new StringBuilder("INSERT INTO image_labels(id, path, label) " +
                            " VALUES (" + imageLabel.getId() + ", '" +
                            imageLabel.getPath() + "', '" + imageLabel.getLabel() + "') ");

                    stmt.addBatch(sb.toString());

                    if (imageLabel.getId() % 1000 == 0) {
                        executeSQLBatch(stmt);
                        System.out.println("Image label " + imageLabel.getId() + "inserted.");

                    }
                }

            }
        }
        finally {

                try {
                    if(stmt != null) {
                        stmt.executeBatch();
                        stmt.close();
                    }
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

    }
    private static void setDatabaseActive(boolean databaseActive) {
        isDatabaseActive = databaseActive;
        if(isDatabaseActive == false)
        {
            try {
                if(conn != null) {conn.close();}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static String formatStringForQuery(String str)
    {
        String result = (str == null ? null : ("'" + str + "'"));
        return result;
    }

}
