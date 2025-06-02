public class GhostHUD {
    public void displayDriverHealth(DriverHealth health) {
        System.out.println("Heart Rate: " + health.getHeartRate() + " bpm");
        System.out.println("Stress: " + Math.round(health.getStressLevel() * 100) + "%");
    }

    public void updateDisplay(RaceData data, ECUSettings ecu) {
        System.out.println("Lap Section: " + data.getSectionTime() + "s | Speed: " + data.getSpeed() + " | Tire Temp: " + data.getTireTemp());
        // Add any other data you want to show here
        // You can access ECU settings here if needed, e.g.:
        // System.out.println("ABS: " + ecu.isAbsOn());
    }
}

