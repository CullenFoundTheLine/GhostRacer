package model;

public class Feedback {
    public String lapId;
    public String driverName;
    public String role; // "coach", "engineer", "driver"
    public String message;
    public String suggestedAction;

    // Optionally, add a constructor for easier creation
    public Feedback() {}

    public Feedback(String lapId, String driverName, String role, String message, String suggestedAction) {
        this.lapId = lapId;
        this.driverName = driverName;
        this.role = role;
        this.message = message;
        this.suggestedAction = suggestedAction;
    }
}