package model;

/**
 * Created by kader.belli on 21.08.2017.
 */
public class HealthLog extends Log {
    public static int COUNTER = 0;

    private double GLU = 0;
    private double UA = 0;
    private double CHOL = 0;
    private BloodPressure BP = null;
    private double HR = 0;
    private double weight = 0;

    public HealthLog() {
        super(++HealthLog.COUNTER);
    }

    public double getGLU() {
        return GLU;
    }

    public void setGLU(double GLU) {
        this.GLU = GLU;
    }

    public double getUA() {
        return UA;
    }

    public void setUA(double UA) {
        this.UA = UA;
    }

    public double getCHOL() {
        return CHOL;
    }

    public void setCHOL(double CHOL) {
        this.CHOL = CHOL;
    }

    public BloodPressure getBP() {
        return BP;
    }

    public void setBP(BloodPressure BP) {
        this.BP = BP;
    }

    public double getHR() {
        return HR;
    }

    public void setHR(double HR) {
        this.HR = HR;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
