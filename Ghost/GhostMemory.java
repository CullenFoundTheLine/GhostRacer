package Ghost;
import java.util.*;
import Core.CarCatalog;
import model.Feedback; // <-- Add this import

public class GhostMemory {
    private CarCatalog carCatalog; // Now used!
    private List<String> issueLog;
    private List<String> decisionHistory;
    private Map<String, String> fixSuggestions;
    private List<Double> lapTimes;
    private Map<String, List<Double>> driverLapHistory; // Driver -> Lap times
    private Map<String, String> driverHabits; // Driver -> Driving behavior notes
    private List<Feedback> feedbackList; // List to store feedback

    public GhostMemory() {
        carCatalog = new CarCatalog(); // Now used!
        issueLog = new ArrayList<>();
        decisionHistory = new ArrayList<>();
        fixSuggestions = new HashMap<>();
        lapTimes = new ArrayList<>();
        driverLapHistory = new HashMap<>();
        driverHabits = new HashMap<>();
        feedbackList = new ArrayList<>(); // Initialize feedback list
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
        driverLapHistory.computeIfAbsent(driverName, _ -> new ArrayList<>()).add(time);
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

    public void printAllIssues() {
        System.out.println("-- GHOST ISSUES LOG --");
        for (String issue : issueLog) {
            System.out.println(issue);
        }
    }

    public void printAllDecisions() {
        System.out.println("-- GHOST DECISION HISTORY --");
        for (String decision : decisionHistory) {
            System.out.println(decision);
        }
    }

    public void printAllFixSuggestions() {
        System.out.println("-- GHOST FIX SUGGESTIONS --");
        for (Map.Entry<String, String> entry : fixSuggestions.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // Example method to use carCatalog
    public void printCarCatalogSummary() {
        System.out.println("-- GHOST CAR CATALOG --");
        for (Core.Car car : carCatalog.getAllCars()) {
            System.out.println(car.getModel());
        }
    }

    // Method to record feedback
    public void recordFeedback(String lapId, String driverName, String role, String message, String suggestedAction) {
        Feedback fb = new Feedback();
        fb.lapId = lapId;
        fb.driverName = driverName;
        fb.role = role;
        fb.message = message;
        fb.suggestedAction = suggestedAction;

        feedbackList.add(fb); // Store feedback in the list
    }

    // Method to print all feedback
    public void printAllFeedback() {
        System.out.println("-- GHOST FEEDBACK --");
        for (Feedback fb : feedbackList) {
            System.out.println("Lap: " + fb.lapId + ", Driver: " + fb.driverName + ", Role: " + fb.role);
            System.out.println("Message: " + fb.message);
            System.out.println("Suggested Action: " + fb.suggestedAction);
        }
    }

    public void printFeedbackForDriver(String driverName) {
        for (Feedback fb : feedbackList) {
            if (fb.driverName.equals(driverName)) {
                System.out.println("[" + fb.role + "] " + fb.message + " | Suggestion: " + fb.suggestedAction);
            }
        }
    }

    public void printFeedbackForRole(String role) {
        System.out.println("-- FEEDBACK FOR ROLE: " + role.toUpperCase() + " --");
        for (Feedback fb : feedbackList) {
            if (fb.role.equalsIgnoreCase(role)) {
                System.out.println("Lap: " + fb.lapId + ", Driver: " + fb.driverName);
                System.out.println("Message: " + fb.message);
                System.out.println("Suggested Action: " + fb.suggestedAction);
            }
        }
    }

    public static void main(String[] args) {
        GhostMemory memory = new GhostMemory();

        // Simulate recording some data
        memory.recordIssue("Tire Wear", "High speed cornering", "Consider softer tires");
        memory.recordDecision(45.0, 120.0, true, "Aggressive", "Drift through the corner");
        memory.recordFix("Brake Pads", "Replace with high-performance pads");
        memory.recordLapTime("Driver1", 75.5);
        memory.recordDriverHabit("Driver1", "Late braking, aggressive cornering");

        // Simulate recording feedback
        memory.recordFeedback("Lap 2", "max", "coach", "Late braking in Turn 2", "Brake earlier next lap.");

        // Print all logs and suggestions
        memory.printAllIssues();
        memory.printAllDecisions();
        memory.printAllFixSuggestions();
        memory.printAllFeedback(); // Print all feedback

        // Print car catalog summary
        memory.printCarCatalogSummary();

        // Print feedback for specific roles
        memory.printFeedbackForRole("coach");
        memory.printFeedbackForRole("engineer");
        memory.printFeedbackForRole("driver");
    }
}

