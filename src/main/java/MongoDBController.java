import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.*;
import org.bson.Document;

import java.util.*;

public class MongoDBController {

    private static String dbName;
    private static MongoCollection<Document> collectionUsers, collectionDays, collectionMinutes;

    private static Gson gson = new Gson();

    public static void createController(String dbName)
    {
        MongoDBController.dbName = dbName;
    }

    public static void connectDatabase()
    {
        System.out.println("Accessing MongoDB...");
        MongoClient mongoClient = new MongoClient();

        List<String> collList = new ArrayList<>();
        MongoDatabase database = mongoClient.getDatabase(dbName);
        database.listCollectionNames().into(collList);
        if(collList.size()>0)
        {
            database.drop();
            database = mongoClient.getDatabase(dbName);
        }

        collectionUsers = database.getCollection("Users");
        collectionDays = database.getCollection("Days");
        collectionMinutes = database.getCollection("Minutes");

        System.out.println("MongoDB connection established.");
    }

    public static void populateDatabase(List<User> users, HashMap<String, ArrayList<ImageLabel>> imageLabelMap) {

        for(User user : users)
        {
            Document newUser = new Document("_id", user.getId()).
                    append("user_name", user.getUserId());
            collectionUsers.insertOne(newUser);

            List<Day> days = user.getDays();
            List<Document> docDayList = new ArrayList<>();
            for(Day day : days)
            {
                Document newDay = new Document("_id", day.getId());
                newDay.append("user_id", user.getId());
                newDay.append("date", day.getDate());
                newDay.append("image_directory", day.getImageDirectory());
                newDay.append("day_metrics", formatObjectAsDocument(day.getDayMetrics()));
                newDay.append("day_activities", formatObjectAsDocument(day.getDayActivities()));
                newDay.append("health_logs", formatObjectAsDocument(day.getHealthLogs()));
                List<Document> docFoodLogs = new ArrayList<>();
                day.getFoodLogs().stream().forEach((log)->docFoodLogs.add(formatObjectAsDocument(log)));
                newDay.append("food_logs", docFoodLogs);

                List<Document> docDrinkLogs = new ArrayList<>();
                day.getFoodLogs().stream().forEach((log)->docDrinkLogs.add(formatObjectAsDocument(log)));
                newDay.append("drink_logs", docDrinkLogs);

                docDayList.add(newDay);

                List<Minute> minutes = day.getMinutes();
                List<Document> docMinuteList = new ArrayList<>();
                for(Minute minute : minutes)
                {
                    Document newMinute = formatObjectAsDocument(minute);
                    newMinute.append("day_id", day.getId());
                    newMinute.append("user_id", user.getId());

                    List<Image> images = minute.getImages();
                    Set<String> imageLabelSet = new HashSet<>();
                    images.forEach(image -> {
                        if(imageLabelMap.containsKey(image.getPath()))
                        {
                            imageLabelMap.get(image.getPath()).forEach(label -> imageLabelSet.add(label.getLabel()));
                        }
                    });
                    newMinute.append("image_labels", imageLabelSet);

                    docMinuteList.add(newMinute);
                }

                collectionMinutes.insertMany(docMinuteList);

            }

            collectionDays.insertMany(docDayList);

        }

        //String jsonString = gson.toJson(users.get(0).getDays().get(0).getMinutes().get(0));

        //System.out.println(jsonString);


    }

    private static Document formatObjectAsDocument(Object obj)
    {
        if(obj == null)
            return null;

        Document result = Document.parse(gson.toJson(obj));
        return result;
    }
}
