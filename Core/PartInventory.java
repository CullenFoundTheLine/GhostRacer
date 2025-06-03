package Core;
import java.util.*;

public class PartInventory {
    private Map<String, List<Part>> partsByCategory = new HashMap<>();

    public void addPart(Part part) {
        partsByCategory.putIfAbsent(part.getCategory(), new ArrayList<>());
        partsByCategory.get(part.getCategory()).add(part);
    }

    public List<Part> getPartsByCategory(String category) {
        return partsByCategory.getOrDefault(category, new ArrayList<>());
    }

    public void showAllParts() {
        System.out.println("=== PART INVENTORY ===");
        for (String category : partsByCategory.keySet()) {
            System.out.println("Category: " + category);
            for (Part part : partsByCategory.get(category)) {
                part.print();
            }
        }
    }

    public Part findPart(String name) {
        for (List<Part> parts : partsByCategory.values()) {
            for (Part part : parts) {
                if (part.getName().equalsIgnoreCase(name)) {
                    return part;
                }
            }
        }
        return null;
    }
}


