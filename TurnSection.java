public class TurnSection {
    private int id;
    private double length;
    private double angleDegrees;
    private double entrySpeed;
    private double exitSpeed;
    private int brakeZoneIntensity;
    private String color;
    private String name;

    public TurnSection(int id, double length, double angleDegrees, double entrySpeed, double exitSpeed, int brakeZoneIntensity, String color, String name) {
        this.id = id;
        this.length = length;
        this.angleDegrees = angleDegrees;
        this.entrySpeed = entrySpeed;
        this.exitSpeed = exitSpeed;
        this.brakeZoneIntensity = brakeZoneIntensity;
        this.color = color;
        this.name = name;
    }

    public double getEntrySpeed() { return entrySpeed; }
    public double getExitSpeed() { return exitSpeed; }
    public double getAngleDegrees() { return angleDegrees; }
    public int getBrakeZoneIntensity() { return brakeZoneIntensity; }
    public String getName() { return name; }
}