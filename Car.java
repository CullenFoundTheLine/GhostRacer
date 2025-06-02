import java.util.*;

public class Car {
    private final String model, type;
    private double weight, baseGrip, crankHP, wheelHP, intensity, lastLap;
    private int laps;
    private final List<Part> parts = new ArrayList<>();
    private Tire tire = new Tire("Default", 1.0, 100.0, 5.0);

    // Telemetry for GHOST
    private double currentSpeed;
    private double tireTemperature;
    private double cornerSpeed;
    private double throttle;
    private double brakeForce;
    private double suspensionLoad;
    private double gapToNextDriver;
    private double gapToLeadDriver;
    private boolean overrideOn;
    private double lapTime;

    public Car(String model, String type, double weight, double baseGrip, double crankHP, double wheelHP, double intensity) {
        this.model = model;
        this.type = type;
        this.weight = weight;
        this.baseGrip = baseGrip;
        this.crankHP = crankHP;
        this.wheelHP = wheelHP;
        this.intensity = intensity;
    }

    public Car(String model, String type) {
        this(model, type, 1200, 1.0, 300, 250, 1.0); // default values
    }

    public void addPart(Part part) {
        parts.add(part);
    }

    public void applyTire(Tire t) {
        this.tire = t;
    }

    public String getModel() { return model; }
    public String getType() { return type; }
    public double getWeight() { return weight; }
    public double getCrankHP() { return crankHP; }
    public double getWheelHP() { return wheelHP; }
    public int getLaps() { return laps; }
    public double getLastLap() { return lastLap; }

    // Ghost-related telemetry accessors
    public double getThrottle() { return throttle; }
    public double getBrakeForce() { return brakeForce; }
    public double getLapTime() { return lapTime; }
    public double getSpeed() { return currentSpeed; }
    public double getTireTemp() { return tireTemperature; }
    public double getCornerSpeed() { return cornerSpeed; }
    public double getSuspensionLoad() { return suspensionLoad; }
    public double getGapToNextDriver() { return gapToNextDriver; }
    public double getGapToLeadDriver() { return gapToLeadDriver; }
    public boolean isOverrideOn() { return overrideOn; }

    // Update values from simulator
    public void updateTelemetry(double speed, double throttle, double brake, double tireTemp, double cornerSpeed, double suspensionLoad) {
        this.currentSpeed = speed;
        this.throttle = throttle;
        this.brakeForce = brake;
        this.tireTemperature = tireTemp;
        this.cornerSpeed = cornerSpeed;
        this.suspensionLoad = suspensionLoad;
        this.gapToNextDriver = Math.random() * 2;
        this.gapToLeadDriver = Math.random() * 2;
        this.overrideOn = Math.random() > 0.8;
        this.lapTime = 90 + Math.random() * 5;
    }

    public void simulateLapData() {
        this.currentSpeed = 120 + Math.random() * 10;
        this.tireTemperature = 90 + Math.random() * 10;
        this.cornerSpeed = 80 + Math.random() * 5;
        this.throttle = Math.random();
        this.brakeForce = Math.random();
        this.suspensionLoad = 0.8 + Math.random() * 0.2;
        this.gapToNextDriver = Math.random() * 2;
        this.gapToLeadDriver = Math.random() * 2;
        this.overrideOn = Math.random() > 0.8;
        this.lapTime = 90 + Math.random() * 5;
    }

    public void printDetails() {
        System.out.println("Model: " + model);
        System.out.println("Type: " + type);
        System.out.printf("Weight: %.1f kg | Base Grip: %.2f%n", weight, baseGrip);
        System.out.printf("Crank HP: %.0f | Wheel HP: %.0f | Intensity: %.2f%n", crankHP, wheelHP, intensity);
        System.out.printf("Tire: %s | Tire Rating: %.2f%n", tire.getName(), tire.getGripModifier());
    }
}

