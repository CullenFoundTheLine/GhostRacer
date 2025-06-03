package Ghost;

import java.util.List;
import java.util.Map;

import model.LapMetrics;
import model.TurnSection;

public class DriftBattleRegulations {

    // Analyze a lap for drift battle rules using existing LapMetrics and TurnSection data
    public DriftResult analyzeDriftLap(List<TurnSection> turns, Map<Integer, LapMetrics> lapData) {
        double totalScore = 0;
        boolean allValid = true;

        for (TurnSection turn : turns) {
            LapMetrics metrics = lapData.get(turn.getId());
            if (metrics == null) continue;

            // Example drift rules using existing metrics
            boolean valid = true;
            double score = 0;

            // Rule: Entry speed must be above 60 km/h
            if (turn.getEntrySpeed() < 60) valid = false;

            // Rule: Drift angle (using angleDegrees as a proxy) must be above 15 degrees
            if (turn.getAngleDegrees() < 15) valid = false;

            // Rule: Tire wear must not exceed 10 (arbitrary example)
            if (metrics.getTireWear() > 10) valid = false;

            // Example scoring
            score += metrics.getBrakePressure() * 0.2;
            score += turn.getAngleDegrees() * 0.5;

            if (!valid) allValid = false;
            totalScore += score;
        }

        return new DriftResult(allValid, totalScore);
    }

    // Result class for drift analysis
    public static class DriftResult {
        public final boolean isValid;
        public final double score;

        public DriftResult(boolean isValid, double score) {
            this.isValid = isValid;
            this.score = score;
        }
    }
}