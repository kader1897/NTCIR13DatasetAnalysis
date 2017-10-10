
import model.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kader.belli on 21.08.2017.
 */
public class NTCIRParser extends DefaultHandler {

    final String PERSONAL_LOGS = "personal";
    final String HEALTH_LOG = "health";
    final String FOOD_LOG = "food";
    final String DRINK_LOG = "drink";

    List<User> users;
    String parentTag = null;
    String currentTag = null;
    String logMode = null;
    String content = null;

    BodyMetrics bodyMetrics = null;
    Day day = null;
    DayActivities dayActivities = null;
    DayMetrics dayMetrics = null;
    HealthLog healthLog = null;
    Image image = null;
    PhoneImage phoneImage = null;
    Location location = null;
    Log log = null;
    Minute minute = null;
    Music music = null;
    User user = null;



    public List<User> getUsers() {
        return users;
    }

    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes)
            throws SAXException {

        switch(qName){
            case "NTCIR-lifelog-dataset":
                System.out.println("NTCIR-lifelog-dataset xml file parser starts execution...");
                break;
            case "users":
                users = new ArrayList<>();
                break;
            case "user":
                user = new User();
                user.setUserId(attributes.getValue("id"));
                System.out.println("Parsing data for user " + user.getUserId() + "...");
                break;
            case "day":
                day = new Day();
                break;
            case "day-metrics":
                dayMetrics = new DayMetrics();
                parentTag = "day-metrics";
                break;
            case "day-activities":
                dayActivities = new DayActivities();
                parentTag = "day-activities";
                break;
            case "personal-logs":
                logMode = PERSONAL_LOGS;
                break;
            case "health-logs":
                healthLog = new HealthLog();
                logMode = HEALTH_LOG;
                break;
            case "food-logs":
                logMode = FOOD_LOG;
                break;
            case "drink-logs":
                logMode = DRINK_LOG;
                break;
            case "log":
                log = new Log();
                break;
            case "minute":
                minute = new Minute();
                minute.setId(Integer.parseInt(attributes.getValue("id")));
                break;
            case "location":
                location = new Location();
                parentTag = "location";
                break;
            case "bodymetrics":
                bodyMetrics = new BodyMetrics();
                parentTag = "bodymetrics";
                break;
            case "image":
                image = new Image();
                parentTag = "image";
                break;
            case "phone-image":
                phoneImage = new PhoneImage();
                parentTag = "phone-image";
                break;
            case "music":
                music = new Music();
                break;
            default:
                currentTag = qName;
        }
    }

    @Override
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {

        StringBuilder sb = null;

        switch(qName){
            case "user":
                users.add(user);
                user = null;
                break;
            case "day":
                user.addDay(day);
                day = null;
                break;
            case "day-metrics":
                day.setDayMetrics(dayMetrics);
                dayMetrics = null;
                break;
            case "day-activities":
                day.setDayActivities(dayActivities);
                dayActivities = null;
                break;
            case "personal-logs":
                logMode = null;
                break;
            case "health-logs":
                day.setHealthLogs(healthLog);
                healthLog = null;
                break;
            case "log":
                if(logMode.equals("food"))
                {
                    day.addFoodLog(log);
                }
                else
                {
                    day.addDrinkLog(log);
                }
                log = null;
                break;
            case "minute":
                day.addMinute(minute);
                minute = null;
                break;
            case "location":
                minute.setLocation(location);
                location = null;
                break;
            case "bodymetrics":
                minute.setBodyMetrics(bodyMetrics);
                bodyMetrics = null;
                break;
            case "image":
                minute.addImage(image, false);
                image = null;
                break;
            case "phone-image":
                minute.addImage(phoneImage, true);
                phoneImage = null;
                break;
            case "music":
                minute.setMusic(music);
                music = null;
                break;
            default:
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        content = String.copyValueOf(ch, start, length).trim();

        if(currentTag != null && content != null && !content.isEmpty()) {
            switch (currentTag) {
                case "date":
                    day.setDate(content);
                    System.out.println("Current date: " + day.getDate());
                    break;
                case "image-directory":
                    day.setImageDirectory(content);
                    break;
                case "Calories":
                    if (parentTag.equals("day-metrics"))
                        dayMetrics.setCalories(Double.parseDouble(content));
                    else // if(parentTag.equals("day-activities"))
                        dayActivities.setCalories(Double.parseDouble(content));
                    break;
                case "Steps":
                    if (parentTag.equals("day-metrics"))
                        dayMetrics.setSteps(Integer.parseInt(content));
                    else // if(parentTag.equals("day-activities"))
                        dayActivities.setSteps(Integer.parseInt(content));
                    break;
                case "Sleep-Duration":
                    dayMetrics.setSleepDuration(Integer.parseInt(content));
                    break;
                case "Sleep-Score":
                    dayMetrics.setSleepScore(Integer.parseInt(content));
                    break;
                case "Number-of-Interruptions":
                    dayMetrics.setNumberOfInterruptions(Integer.parseInt(content));
                    break;
                case "Number-of-Toss-and-Turns":
                    dayMetrics.setNumberOfTossAndTurns(Integer.parseInt(content));
                    break;
                case "Interruption-Duration":
                    dayMetrics.setInterruptionDuration(Integer.parseInt(content));
                    break;
                case "Resting-Heart-Rate":
                    dayMetrics.setRestingHeartRate(Integer.parseInt(content));
                    break;
                case "Bike-Calories":
                    dayMetrics.setBikeCalories(Double.parseDouble(content));
                    break;
                case "Bike-Duration":
                    dayMetrics.setBikeDuration(Integer.parseInt(content));
                    break;
                case "Run-Calories":
                    dayMetrics.setRunCalories(Double.parseDouble(content));
                    break;
                case "Run-Duration":
                    dayMetrics.setRunDuration(Integer.parseInt(content));
                    break;
                case "Run-Steps":
                    dayMetrics.setRunSteps(Integer.parseInt(content));
                    break;
                case "Walk-Calories":
                    dayMetrics.setWalkCalories(Double.parseDouble(content));
                    break;
                case "Walk-Duration":
                    dayMetrics.setWalkDuration(Integer.parseInt(content));
                    break;
                case "Walk-Steps":
                    dayMetrics.setWalkSteps(Integer.parseInt(content));
                    break;
                case "Distance":
                    dayActivities.setDistance(Double.parseDouble(content));
                    break;
                case "Elevation":
                    dayActivities.setElevation(Integer.parseInt(content));
                    break;
                case "Time":
                    if (logMode.equals(HEALTH_LOG))
                        healthLog.setTime(content);
                    else // if(logMode.equals(FOOD_LOG) || logMode.equals(DRINK_LOG))
                        log.setTime(content);
                    break;
                case "GLU":
                    healthLog.setGLU(Double.parseDouble(content));
                    break;
                case "UA":
                    healthLog.setUA(Double.parseDouble(content));
                    break;
                case "CHOL":
                    try {
                        healthLog.setCHOL(Double.parseDouble(content));
                    } catch (Exception e) {
                    }
                    break;
                case "BP":
                    String[] bp = content.split("/");
                    if (bp.length == 2) {
                        healthLog.setBP(new BloodPressure(Integer.parseInt(bp[0]), Integer.parseInt(bp[1])));
                    }
                    break;
                case "HR":
                    healthLog.setHR(Double.parseDouble(content));
                    break;
                case "Mood":
                    healthLog.setContent(content);
                    break;
                case "Weight":
                    healthLog.setWeight(Double.parseDouble(content));
                    break;
                case "Food":
                case "Drink":
                    log.setContent(content);
                    break;
                case "name":
                    location.setName(content);
                    break;
                case "latitude":
                    location.setLatitude(Double.parseDouble(content));
                    break;
                case "longitude":
                    location.setLongitude(Double.parseDouble(content));
                    break;
                case "link":
                    location.setLink(content);
                    break;
                case "activity":
                    minute.setActivity(content);
                    break;
                case "calories":
                    bodyMetrics.setCalories(Double.parseDouble(content));
                    break;
                case "gsr":
                    bodyMetrics.setGsr(Double.parseDouble(content));
                    break;
                case "heart-rate":
                    bodyMetrics.setHeartRate(Integer.parseInt(content));
                    break;
                case "skin-temp":
                    bodyMetrics.setSkinTemp(Double.parseDouble(content));
                    break;
                case "steps":
                    bodyMetrics.setSteps(Integer.parseInt(content));
                    break;
                case "image-id":
                    image.setImageId(content);
                    break;
                case "image-path":
                    image.setPath(content);
                    break;
                case "phone-image-id":
                    phoneImage.setImageId(content);
                    break;
                case "phone-image-path":
                    phoneImage.setPath(content);
                    break;
                case "song":
                    music.setSong(content);
                    break;
                case "song-mbid":
                    music.setSongMbid(content);
                    break;
                case "artist":
                    music.setArtist(content);
                    break;
                case "artist-mbid":
                    music.setArtistMbid(content);
                    break;
                case "album":
                    music.setAlbum(content);
                    break;
                case "album-mbid":
                    music.setAlbumMbid(content);
                    break;
                default:
                    if (content.isEmpty())
                        break;
                    else
                        throw new SAXException("Unknown tag: " + currentTag + "!");

            }
        }
        else
        {
            if(content == null || content.isEmpty())
                return;
            else
                throw new SAXException("Current tag is NULL!");
        }

    }


}
