public class CornerZone {
    private final String name;
    private final double recommendedEntrySpeed;   // in km/h
    private final int brakeZoneIntensity;         // 0 = green (light), 1 = yellow (medium), 2 = red (hard)
    private final boolean requiresHandBrake;

    public CornerZone(String name, double recommendedEntrySpeed, int brakeZoneIntensity, boolean requiresHandBrake) {
        this.name = name;
        this.recommendedEntrySpeed = recommendedEntrySpeed;
        this.brakeZoneIntensity = brakeZoneIntensity;
        this.requiresHandBrake = requiresHandBrake;
    }

    public String getName() {
        return name;
    }

    public double getRecommendedEntrySpeed() {
        return recommendedEntrySpeed;
    }

    public int getBrakeZoneIntensity() {
        return brakeZoneIntensity;
    }

    public boolean requiresHandBrake() {
        return requiresHandBrake;
    }

    public String getBrakeColor() {
        switch (brakeZoneIntensity) {
            case 1: return "YELLOW";
            case 2: return "RED";
            default: return "GREEN";
        }
    }
}

