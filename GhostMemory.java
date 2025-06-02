
import java.util.*;

public class GhostMemory {
    private List<String> issueLog;
    private List<String> decisionHistory;
    private Map<String, String> fixSuggestions;
    private List<Double> lapTimes;
    private Map<String, List<Double>> driverLapHistory; // Driver -> Lap times
    private Map<String, String> driverHabits; // Driver -> Driving behavior notes

    public GhostMemory() {
        issueLog = new ArrayList<>();
        decisionHistory = new ArrayList<>();
        fixSuggestions = new HashMap<>();
        lapTimes = new ArrayList<>();
        driverLapHistory = new HashMap<>();
        driverHabits = new HashMap<>();
    }

    public void recordIssue(String title, String cause, String recommendation) {
        issueLog.add(title + " | Cause: " + cause + " | Suggest: " + recommendation);
    }

    public void recordDecision(double cornerAngle, double speed, boolean drift, String style, String action) {
        decisionHistory.add("Corner: " + cornerAngle + ", Speed: " + speed + ", Drift: " + drift + ", Style: " + style + " => Action: " + action);
    }

    public void recordFix(String part, String fix) {
        fixSuggestions.put(part, fix);
    }

    public void recordLapTime(String driverName, double time) {
        lapTimes.add(time);
        driverLapHistory.computeIfAbsent(driverName, k -> new ArrayList<>()).add(time);
    }

    public double getAverageLapTime() {
        return lapTimes.stream().mapToDouble(t -> t).average().orElse(0.0);
    }

    public void recordDriverHabit(String driverName, String behaviorNote) {
        driverHabits.put(driverName, behaviorNote);
    }

    public List<Double> getDriverLaps(String driverName) {
        return driverLapHistory.getOrDefault(driverName, new ArrayList<>());
    }

    public String getDriverHabit(String driverName) {
        return driverHabits.getOrDefault(driverName, "No behavior data available.");
    }

    public void printInsights(String driverName) {
        System.out.println("-- GHOST DRIVER INSIGHTS FOR: " + driverName + " --");
        System.out.println("Behavior: " + getDriverHabit(driverName));
        System.out.println("Lap History: " + getDriverLaps(driverName));
    }

    public void recordInsight(String title, String note) {
        issueLog.add("Insight: " + title + " | " + note);
    }
}
