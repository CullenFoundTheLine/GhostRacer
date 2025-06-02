public class RacePredictor {

    public Decision predictMove(
            Car playerCar,
            Car frontCar,
            TrackInfo trackInfo,
            double trackPosition,
            double spaceToFront,
            int currentGear,
            DriverHealth driverHealth,
            int brakeZoneIntensity, // 0=green, 1=yellow, 2=red
            boolean severeContact,
            boolean carDamaged
    ) {
        // Input validation
        if (playerCar == null || trackInfo == null) {
            logDecision("INVALID_INPUT", "Null car or track info", Decision.BRAKE);
            return Decision.BRAKE;
        }

        // Red zone: severe crash, spin, wall bang, or major damage
        if (brakeZoneIntensity == 2 || severeContact || carDamaged) {
            logDecision("RED_ZONE", "Severe danger detected", Decision.FULL_SAFETY_MODE);
            return Decision.FULL_SAFETY_MODE;
        }

        // Yellow zone: moderate risk (e.g., light wall tap, minor contact, high stress)
        if (brakeZoneIntensity == 1 || (driverHealth != null && driverHealth.getStressLevel() > 0.7)) {
            logDecision("YELLOW_ZONE", "Moderate risk, coaching driver", Decision.COACH);
            return Decision.COACH;
        }

        // Green zone: normal racing, no intervention
        double recommendedSpeed = trackInfo.getRecommendedSpeed();
        boolean isCorner = trackInfo.isCorner();
        boolean isStraight = !isCorner;
        boolean closeGap = spaceToFront < 1.5;

        if (isCorner && playerCar.getSpeed() > recommendedSpeed + 5) {
            logDecision("CORNER_OVERSPEED", "Player too fast for corner", Decision.BRAKE);
            return Decision.BRAKE;
        }

        if (closeGap && isStraight && frontCar != null && playerCar.getSpeed() > frontCar.getSpeed()) {
            logDecision("STRAIGHT_OVERTAKE", "Opportunity to overtake", Decision.OVERTAKE);
            return Decision.OVERTAKE;
        }

        logDecision("GREEN_ZONE", "Normal racing", Decision.MAINTAIN);
        return Decision.MAINTAIN;
    }

    private void logDecision(String context, String reason, Decision decision) {
        System.out.println("[RacePredictor] Context: " + context + " | Reason: " + reason + " | Decision: " + decision);
    }

    public enum Decision {
        FULL_SAFETY_MODE, // Restrict, slow, or stop
        COACH,            // Give advice, no restriction
        BRAKE,
        MAINTAIN,
        OVERTAKE
    }
}
