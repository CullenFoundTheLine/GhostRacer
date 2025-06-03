package Ghost;

import Core.DriverHealth;

public class GhostVoice {
    public void respondToDriverHealth(DriverHealth health) {
        if (health.getHeartRate() > 140) {
            System.out.println("Calm down, focus on breathing, trust your training.");
        } else if (health.getHeartRate() > 120) {
            System.out.println("You’re in the heat of the moment. Eyes up, stay cool!");
        } else {
            System.out.println("Nice and steady, keep your rhythm.");
        }
    }
}
