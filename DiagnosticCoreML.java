import java.util.*;

public class DiagnosticCoreML {
    private Map<String, Double> partHealth; // partName -> condition %
    private Map<String, String> issueCauses; // partName -> cause
    private List<String> faultHistory;
    private boolean followFIA;

    public DiagnosticCoreML(boolean followFIA) {
        this.partHealth = new HashMap<>();
        this.issueCauses = new HashMap<>();
        this.faultHistory = new ArrayList<>();
        this.followFIA = followFIA;
    }

    public void monitorPart(String partName, double temp, double wear, String drivingStyle) {
        double stressFactor = calcStress(temp, wear, drivingStyle);
        double condition = 100.0 - stressFactor;
        partHealth.put(partName, condition);

        if (condition < 70.0) {
            String cause = detectCause(partName, temp, wear, drivingStyle);
            issueCauses.put(partName, cause);
            faultHistory.add("Detected issue: " + partName + " — Cause: " + cause);
        }
    }

    public void evaluateSystemIntegrity() {
        for (String part : partHealth.keySet()) {
            if (partHealth.get(part) < 70.0) {
                System.out.println("GHOST: [" + part + "] needs attention. " + explainIssue(part));
                checkConnectedFailures(part);
            }
        }
    }

    public void suggestFixes() {
        for (String part : partHealth.keySet()) {
            if (partHealth.get(part) < 70.0) {
                String suggestion = "Upgrade or tune " + part + " to handle " + issueCauses.get(part);
                if (followFIA) {
                    suggestion += " (FIA Approved)";
                } else {
                    suggestion += " (May push FIA edge)";
                }
                System.out.println("GHOST: " + suggestion);
            }
        }
    }

    private double calcStress(double temp, double wear, String style) {
        double styleFactor = style.equals("aggressive") ? 1.5 : style.equals("balanced") ? 1.0 : 0.5;
        return (temp * 0.3 + wear * 0.7) * styleFactor;
    }

    private String detectCause(String part, double temp, double wear, String style) {
        if (temp > 90 && wear > 60) return "Overheating and wear due to aggressive driving";
        if (wear > 80) return "Extended use without pit";
        if (temp > 95) return "Cooling failure or overuse";
        return "Unknown pattern — logging for analysis";
    }

    private void checkConnectedFailures(String part) {
        if (part.contains("Suspension") && partHealth.get("Tires") < 70) {
            System.out.println("GHOST: Tire wear may be caused by faulty suspension — recommend checking both.");
        }
        if (part.contains("Turbo") && partHealth.get("Engine") < 70) {
            System.out.println("GHOST: Engine performance may be affected by turbo heat stress.");
        }
    }

    private String explainIssue(String part) {
        return "Root cause: " + issueCauses.getOrDefault(part, "Unknown") + ".";
    }

    public void teachDriver() {
        for (String fault : faultHistory) {
            System.out.println("GHOST MEMORY: " + fault);
        }
        System.out.println("GHOST: Suggesting smoother throttle and earlier pit next run.");
    }
}

