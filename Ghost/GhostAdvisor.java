package Ghost;

import Core.Car;
import Core.Driver;
import Core.FIARegulations;
import Core.Part;

public class GhostAdvisor {

    public String explainUpgradeForDriver(Car car, Part newPart) {
        double oldHP = car.getCrankHP();
        double newHP = oldHP + newPart.getPowerGain();
        return String.format(
            "Installing the %s will boost your car from %.0f HP to %.0f HP! " +
            "You'll feel a huge difference in acceleration and top speed. " +
            "Ghost recommends this for a thrilling upgrade!",
            newPart.getName(), oldHP, newHP
        );
    }

    public String explainUpgradeForEngineer(Car car, Part newPart, FIARegulations fia) {
        double oldHP = car.getCrankHP();
        double newHP = oldHP + newPart.getPowerGain();
        boolean fiaLegal = fia.isLegal(newPart.getCategory());

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
            "Technical Analysis: Adding %s increases crank HP from %.0f to %.0f.\n", 
            newPart.getName(), oldHP, newHP
        ));
        sb.append("This affects torque delivery, fuel mapping, and may require ECU recalibration.\n");
        sb.append("Aerodynamic balance and drivetrain stress will also change.\n");
        sb.append(fiaLegal
            ? "This setup is FIA legal for your class.\n"
            : "Warning: This setup breaks FIA regulations, but here's how you could apply for a waiver or adapt it for legal compliance.\n"
        );
        sb.append("Ghost recommends monitoring telemetry after installation for optimal tuning.");
        return sb.toString();
    }

    public String explainForCoach(Driver driver, GhostMemory memory) {
        double avgLap = memory.getAverageLapTime();
        String habit = memory.getDriverHabit(driver.getName());
        return String.format(
            "Driver %s's average lap time is %.2f seconds. " +
            "Recent driving habit: %s\n" +
            "Ghost suggests focusing on corner exit speed and consistency. " +
            "Reviewing telemetry logs can help tailor your coaching for maximum improvement.",
            driver.getName(), avgLap, habit
        );
    }
}