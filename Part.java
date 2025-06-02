public class Part {
    private final String name;
    private final double simBoost;
    private final String category;

    public Part(String name, double simBoost, String category) {
        this.name = name;
        this.simBoost = simBoost;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getSimBoost() {
        return simBoost;
    }

    public String getCategory() {
        return category;
    }

    public void print() {
        System.out.printf("Part: %s | Boost: +%.1f | Category: %s%n", name, simBoost, category);
    }
}
