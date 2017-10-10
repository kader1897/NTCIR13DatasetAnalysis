package model;

/**
 * Created by kader.belli on 21.08.2017.
 */
public class DayActivities {

    public static int COUNTER = 0;

    private int id;
    private int steps = 0;
    private double distance = 0;
    private int elevation = 0;
    private double calories = 0;

    public DayActivities() {
        id = ++COUNTER;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }
}
