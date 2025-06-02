
import java.util.*;

public class FIARegulations {
    private Set<String> legalParts;

    public FIARegulations() {
        legalParts = new HashSet<>(Arrays.asList("Turbo", "Suspension", "Tires", "ECU", "Brakes"));
    }

    public boolean isLegal(String partName) {
        return legalParts.contains(partName);
    }

    public void addLegalPart(String part) {
        legalParts.add(part);
    }
}
