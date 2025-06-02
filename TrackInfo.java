public class TrackInfo {
    private String name;
    private double recommendedSpeed;
    private String segmentType;       // "corner", "straight", etc.
    private double segmentStart;      // normalized 0.0 to 1.0
    private double segmentEnd;
    private boolean driftOptimal;

    public TrackInfo(String name, double recommendedSpeed, String segmentType,
                     double segmentStart, double segmentEnd, boolean driftOptimal) {
        this.name = name;
        this.recommendedSpeed = recommendedSpeed;
        this.segmentType = segmentType;
        this.segmentStart = segmentStart;
        this.segmentEnd = segmentEnd;
        this.driftOptimal = driftOptimal;
    }

    public String getName() {
        return name;
    }

    public double getRecommendedSpeed() {
        return recommendedSpeed;
    }

    public String getSegmentType() {
        return segmentType;
    }

    public double getSegmentStart() {
        return segmentStart;
    }

    public double getSegmentEnd() {
        return segmentEnd;
    }

    public boolean isDriftOptimal() {
        return driftOptimal;
    }

    public boolean isCorner() {
        return segmentType.equalsIgnoreCase("corner");
    }

    public boolean isStraight() {
        return segmentType.equalsIgnoreCase("straight");
    }

    public void printTrackInfo() {
        System.out.printf("Track: %s | Type: %s | Speed: %.1f | Zone: %.2f–%.2f | Drift Line: %s%n",
                name, segmentType, recommendedSpeed, segmentStart, segmentEnd,
                driftOptimal ? "Optimal" : "Standard");
    }
}

