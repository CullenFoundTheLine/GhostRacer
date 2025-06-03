package Core;

public class DriverHealth {
    private int heartRate; // in BPM
    private double stressLevel; // 0.0 to 1.0

    public DriverHealth() {
        this.heartRate = 80; // baseline
        this.stressLevel = 0.2;
    }

    public void update(boolean closeRace, boolean overtaking, boolean crashOrMistake) {
        // Simulate stress spikes based on race events
        if (crashOrMistake) heartRate += 15;
        else if (overtaking) heartRate += 10;
        else if (closeRace) heartRate += 5;
        else heartRate = Math.max(75, heartRate - 2); // recovery

        // Cap the heart rate to reasonable limits for demo
        heartRate = Math.max(60, Math.min(heartRate, 180));
        // Stress increases with heart rate
        stressLevel = Math.min(1.0, (heartRate - 60) / 120.0);
    }

    public int getHeartRate() { return heartRate; }
    public double getStressLevel() { return stressLevel; }
}