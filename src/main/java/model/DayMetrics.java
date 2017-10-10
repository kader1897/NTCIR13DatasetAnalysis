package model;

/**
 * Created by kader.belli on 21.08.2017.
 */
public class DayMetrics {

    public static int COUNTER = 0;

    private int id;
    private double calories = 0;
    private int steps = 0;
    private int sleepDuration = 0;
    private int sleepScore = 0;
    private int numberOfInterruptions = 0;
    private int numberOfTossAndTurns = 0;
    private int interruptionDuration = 0;
    private int restingHeartRate = 0;
    private double bikeCalories = 0;
    private int bikeDuration = 0;
    private double runCalories = 0;
    private int runDuration = 0;
    private int runSteps = 0;
    private double walkCalories = 0;
    private int walkDuration = 0;
    private int walkSteps = 0;

    public DayMetrics(){
        id = ++COUNTER;
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

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getSleepDuration() {
        return sleepDuration;
    }

    public void setSleepDuration(int sleepDuration) {
        this.sleepDuration = sleepDuration;
    }

    public int getSleepScore() {
        return sleepScore;
    }

    public void setSleepScore(int sleepScore) {
        this.sleepScore = sleepScore;
    }

    public int getNumberOfInterruptions() {
        return numberOfInterruptions;
    }

    public void setNumberOfInterruptions(int numberOfInterruptions) {
        this.numberOfInterruptions = numberOfInterruptions;
    }

    public int getNumberOfTossAndTurns() {
        return numberOfTossAndTurns;
    }

    public void setNumberOfTossAndTurns(int numberOfTossAndTurns) {
        this.numberOfTossAndTurns = numberOfTossAndTurns;
    }

    public int getInterruptionDuration() {
        return interruptionDuration;
    }

    public void setInterruptionDuration(int interruptionDuration) {
        this.interruptionDuration = interruptionDuration;
    }

    public int getRestingHeartRate() {
        return restingHeartRate;
    }

    public void setRestingHeartRate(int restingHeartRate) {
        this.restingHeartRate = restingHeartRate;
    }

    public double getBikeCalories() {
        return bikeCalories;
    }

    public void setBikeCalories(double bikeCalories) {
        this.bikeCalories = bikeCalories;
    }

    public int getBikeDuration() {
        return bikeDuration;
    }

    public void setBikeDuration(int bikeDuration) {
        this.bikeDuration = bikeDuration;
    }

    public double getRunCalories() {
        return runCalories;
    }

    public void setRunCalories(double runCalories) {
        this.runCalories = runCalories;
    }

    public int getRunDuration() {
        return runDuration;
    }

    public void setRunDuration(int runDuration) {
        this.runDuration = runDuration;
    }

    public int getRunSteps() {
        return runSteps;
    }

    public void setRunSteps(int runSteps) {
        this.runSteps = runSteps;
    }

    public double getWalkCalories() {
        return walkCalories;
    }

    public void setWalkCalories(double walkCalories) {
        this.walkCalories = walkCalories;
    }

    public int getWalkDuration() {
        return walkDuration;
    }

    public void setWalkDuration(int walkDuration) {
        this.walkDuration = walkDuration;
    }

    public int getWalkSteps() {
        return walkSteps;
    }

    public void setWalkSteps(int walkSteps) {
        this.walkSteps = walkSteps;
    }
}
