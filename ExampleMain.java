import Core.Driver;
import Ghost.GhostHUD;
import Ghost.GhostVoice;
import model.LapMetrics;

public class ExampleMain {
    public static void main(String[] args) {
        Driver driver = new Driver("Cullen");
        GhostVoice ghost = new GhostVoice();
        GhostHUD hud = new GhostHUD();
        LapMetrics lapMetrics = new LapMetrics();
        driver.setGhostVoice(ghost);

        // Simulate a lap with various events
        boolean[] closeRaces = {false, true, false};
        boolean[] overtakes = {false, false, true};
        boolean[] mistakes = {false, false, false};

        for (int i = 0; i < 3; i++) {
            System.out.println("\nLap " + (i+1));
            driver.updateForEvent(closeRaces[i], overtakes[i], mistakes[i]);
            hud.displayDriverHealth(driver.getHealth());
            lapMetrics.recordDriverHealth(driver.getHealth());
        }

        System.out.println("\nFinal Recorded Metrics:");
        System.out.println("Average Heart Rate: " + lapMetrics.getAverageHeartRate() + " bpm");
        System.out.println("Average Stress Level: " + Math.round(lapMetrics.getAverageStressLevel() * 100) + "%");
    }
}
