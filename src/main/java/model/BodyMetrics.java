package model;

/**
 * Created by kader.belli on 21.08.2017.
 */
public class BodyMetrics {

    public static int COUNTER = 0;

    private int id;
    private double calories = 0;
    private double gsr = 0;
    private int heartRate = 0;
    private double skinTemp = 0;
    private int steps = 0;

    public BodyMetrics() {
        this.id = ++COUNTER;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getGsr() {
        return gsr;
    }

    public void setGsr(double gsr) {
        this.gsr = gsr;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public double getSkinTemp() {
        return skinTemp;
    }

    public void setSkinTemp(double skinTemp) {
        this.skinTemp = skinTemp;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
