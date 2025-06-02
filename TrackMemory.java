import java.util.*;

public class TrackMemory {
    private String trackName;
    private double trackLengthKm;
    private double averageWidthMeters;
    private int maxCarsSideBySide;

    private List<TurnSection> turns = new ArrayList<>();
    private Map<Integer, LapMetrics> lapData;

    public TrackMemory(String trackName, double trackLengthKm, double averageWidthMeters, int maxCarsSideBySide) {
        this.trackName = trackName;
        this.trackLengthKm = trackLengthKm;
        this.averageWidthMeters = averageWidthMeters;
        this.maxCarsSideBySide = maxCarsSideBySide;
        this.lapData = new HashMap<>();
    }

    // Update addTurn to include name
    public void addTurn(int id, double length, double angleDegrees, double entrySpeed, double exitSpeed, int brakeZoneIntensity, String color, String name) {
        turns.add(new TurnSection(id, length, angleDegrees, entrySpeed, exitSpeed, brakeZoneIntensity, color, name));
    }

    public void recordLapData(int lapNumber, double lapTimeSeconds, double tireWearPercent, double avgBrakePressure, int pitStopLap) {
        lapData.put(lapNumber, new LapMetrics(lapTimeSeconds, tireWearPercent, avgBrakePressure, pitStopLap));
    }

    public List<TurnSection> getTurns() {
        return turns;
    }
    public Map<Integer, LapMetrics> getLapData() { return lapData; }
}