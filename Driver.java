import java.util.ArrayList;
import java.util.List;

public class Driver {
    private String name;
    private DriverProfile profile;
    private DriverHealth health = new DriverHealth();
    private GhostVoice ghostVoice;
    private List<Double> lapTimes = new ArrayList<>();

    public Driver(String name) {
        this.name = name;
        this.profile = new DriverProfile(name); // Adjust if you have another constructor
    }

    public String getName() {
        return name;
    }

    public DriverProfile getProfile() {
        return profile;
    }

    public DriverHealth getHealth() {
        return health;
    }

    public void setGhostVoice(GhostVoice voice) {
        this.ghostVoice = voice;
    }

    public void updateForEvent(boolean closeRace, boolean overtaking, boolean mistake) {
        health.update(closeRace, overtaking, mistake);
        if (ghostVoice != null) {
            ghostVoice.respondToDriverHealth(health);
        }
    }

    public void logLapTime(double lapTime) {
        lapTimes.add(lapTime);
    }

    public List<Double> getLapTimes() {
        return lapTimes;
    }

    @Override
    public String toString() {
        return "Driver{name='" + name + "', heartRate=" + health.getHeartRate() +
                ", stressLevel=" + health.getStressLevel() + "}";
    }
}
