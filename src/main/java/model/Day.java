package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kader.belli on 21.08.2017.
 */
public class Day {

    public static int COUNTER = 0;

    private int id;
    private String date = null;
    private String imageDirectory = null;
    private DayMetrics dayMetrics = null;
    private DayActivities dayActivities = null;
    private HealthLog healthLogs = null;
    private List<Log> foodLogs;
    private List<Log> drinkLogs;
    private List<Minute> minutes;

    public Day() {
        id = ++COUNTER;
        foodLogs = new ArrayList<>();
        drinkLogs = new ArrayList<>();
        minutes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageDirectory() {
        return imageDirectory;
    }

    public void setImageDirectory(String imageDirectory) {
        this.imageDirectory = imageDirectory;

    }

    public DayMetrics getDayMetrics() {
        return dayMetrics;
    }

    public void setDayMetrics(DayMetrics dayMetrics) {
        this.dayMetrics = dayMetrics;
    }

    public DayActivities getDayActivities() {
        return dayActivities;
    }

    public void setDayActivities(DayActivities dayActivities) {
        this.dayActivities = dayActivities;
    }

    public HealthLog getHealthLogs() {
        return healthLogs;
    }

    public void setHealthLogs(HealthLog healthLogs) {
        this.healthLogs = healthLogs;
    }

    public List<Log> getFoodLogs() {
        return foodLogs;
    }

    public void setFoodLogs(List<Log> foodLogs) {
        this.foodLogs = foodLogs;
    }

    public void addFoodLog(Log foodLog)
    {
        this.foodLogs.add(foodLog);
    }

    public List<Log> getDrinkLogs() {
        return drinkLogs;
    }

    public void setDrinkLogs(List<Log> drinkLogs) {
        this.drinkLogs = drinkLogs;
    }

    public void addDrinkLog(Log drinkLog) {
        this.drinkLogs.add(drinkLog);
    }

    public List<Minute> getMinutes() {
        return minutes;
    }

    public void setMinutes(List<Minute> minutes) {
        this.minutes = minutes;
    }

    public void addMinute(Minute minute){
        this.minutes.add(minute);
    }
}
