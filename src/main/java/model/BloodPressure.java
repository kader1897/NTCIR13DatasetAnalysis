package model;

public class BloodPressure {

    public static int COUNTER = 0;

    int high = 0, low = 0;

    public BloodPressure() {
        COUNTER++;
    }
    public BloodPressure(int high, int low) {
        COUNTER++;
        this.high = high;
        this.low = low;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }
}
