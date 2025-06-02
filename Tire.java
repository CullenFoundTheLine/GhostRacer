public class Tire {
    private final String name;
    private final double gripModifier;
    private final double durability;     // max durability
    private final double tempRange;      // ideal temp range (e.g. 90–100°C)

    public Tire(String name, double gripModifier, double durability, double tempRange) {
        this.name = name;
        this.gripModifier = gripModifier;
        this.durability = durability;
        this.tempRange = tempRange;
    }

    public String getName() {
        return name;
    }

    public double getGripModifier() {
        return gripModifier;
    }

    public double getMaxDurability() {
        return durability;
    }

    public double getTempRange() {
        return tempRange;
    }

    public void printTireInfo() {
        System.out.printf("Tire: %s | Grip Mod: %.2f | Durability: %.1f | Temp Range: %.1f°C%n",
                name, gripModifier, durability, tempRange);
    }
}


