public class RaceData {
    private String driverName;
    private double lapTime;
    private double tireTemp;
    private double tireWear;

    private double speed;
    private double throttle;
    private double brakeForce;
    private double cornerSpeed;
    private double suspensionLoad;
    private double gapToNextDriver;
    private double gapToLeadDriver;

    public RaceData(String driverName, double lapTime, double tireTemp, double tireWear,
                    double speed, double throttle, double brakeForce,
                    double cornerSpeed, double suspensionLoad,
                    double gapToNextDriver, double gapToLeadDriver) {
        this.driverName = driverName;
        this.lapTime = lapTime;
        this.tireTemp = tireTemp;
        this.tireWear = tireWear;
        this.speed = speed;
        this.throttle = throttle;
        this.brakeForce = brakeForce;
        this.cornerSpeed = cornerSpeed;
        this.suspensionLoad = suspensionLoad;
        this.gapToNextDriver = gapToNextDriver;
        this.gapToLeadDriver = gapToLeadDriver;
    }

    public String getDriverName() { return driverName; }
    public double getLapTime() { return lapTime; }
    public double getSectionTime() { return lapTime; } // <--- Added for compatibility
    public double getTireTemp() { return tireTemp; }
    public double getTireWear() { return tireWear; }

    public double getSpeed() { return speed; }
    public double getThrottle() { return throttle; }
    public double getBrakeForce() { return brakeForce; }
    public double getCornerSpeed() { return cornerSpeed; }
    public double getSuspensionLoad() { return suspensionLoad; }
    public double getGapToNextDriver() { return gapToNextDriver; }
    public double getGapToLeadDriver() { return gapToLeadDriver; }
}
