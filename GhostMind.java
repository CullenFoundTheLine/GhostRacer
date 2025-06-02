
import java.util.*;

public class GhostMind {
    private GhostMemory memory;
    private FIARegulations regulations;

    public GhostMind(GhostMemory memory, FIARegulations regulations) {
        this.memory = memory;
        this.regulations = regulations;
    }

    public String decideAction(double cornerAngle, double speed, boolean driftPossible, String drivingStyle, Map<String, Double> partHealth, double tireTemp) {
        String action;

        if (tireTemp > 95) {
            memory.recordIssue("Tire overheating", "High temp > 95C", "Suggest softer compound or earlier braking.");
            return "COOL_DOWN";
        }

        if (partHealth.getOrDefault("Suspension", 100.0) < 60 && partHealth.getOrDefault("Tires", 100.0) < 70) {
            memory.recordIssue("Compound wear issue", "Linked: Suspension → Tires", "Upgrade suspension or change driving style.");
        }

        if (driftPossible && drivingStyle.equals("aggressive")) {
            action = "DRIFT";
        } else if (cornerAngle > 45) {
            action = "BRAKE";
        } else {
            action = "MAINTAIN";
        }

        memory.recordDecision(cornerAngle, speed, driftPossible, drivingStyle, action);
        return action;
    }

    public void analyzeLapPerformance(Map<String, Double> partHealth, double lapTime, double tireTemp) {
        for (String part : partHealth.keySet()) {
            if (partHealth.get(part) < 70.0) {
                String suggestion = "Upgrade or tune " + part;
                if (!regulations.isLegal(part)) {
                    suggestion += " (May violate FIA)";
                } else {
                    suggestion += " (FIA Legal)";
                }
                memory.recordFix(part, suggestion);
            }
        }

        if (lapTime > memory.getAverageLapTime() * 1.1) {
            memory.recordInsight("Lap time dropped", "Evaluate tire pressure, brake zones, and driver fatigue.");
        }
    }
}
