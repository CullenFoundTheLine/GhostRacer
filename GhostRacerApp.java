import java.util.*;

public class GhostRacerApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GhostRacerCore core = new GhostRacerCore();

    public static void main(String[] args) {
        System.out.println("==== Welcome to GhostRacer ====");

        // Step 1: Choose race type
        String raceType = chooseRaceType();

        // Step 2: Choose region
        String region = chooseRegion();

        // Step 3: Choose track within region
        String track = chooseTrack(region);
        core.loadTrack(region, track);

        // Step 4: Set weather conditions
        String weather = chooseWeather();
        System.out.println("Weather set to: " + weather);

        // Step 5: Add drivers
        System.out.println("Enter driver names (type 'done' when finished):");
        while (true) {
            System.out.print("Driver: ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("done")) break;
            core.addDriver(name);
        }

        // Step 6: Set lap count and start race
        System.out.print("How many laps? ");
        int laps = Integer.parseInt(scanner.nextLine());
        System.out.println("Starting " + raceType + " on " + track + " in " + weather + " conditions...");
        core.runRace(laps);
    }

    private static String chooseRaceType() {
        System.out.println("Select Race Type:");
        System.out.println("1. Circuit Race");
        System.out.println("2. Drift Battle");
        System.out.println("3. Time Attack");
        System.out.print("Choice: ");
        int choice = Integer.parseInt(scanner.nextLine());
        return switch (choice) {
            case 2 -> "Drift Battle";
            case 3 -> "Time Attack";
            default -> "Circuit Race";
        };
    }

    private static String chooseRegion() {
        System.out.println("Select Region:");
        System.out.println("1. Europe");
        System.out.println("2. North America");
        System.out.println("3. Asia Pacific");
        System.out.println("4. South America");
        System.out.println("5. Middle East");
        System.out.println("6. Africa");
        System.out.println("7. Australia");
        System.out.print("Choice: ");
        return switch (Integer.parseInt(scanner.nextLine())) {
            case 2 -> "North America";
            case 3 -> "Asia Pacific";
            case 4 -> "South America";
            case 5 -> "Middle East";
            case 6 -> "Africa";
            case 7 -> "Australia";
            default -> "Europe";
        };
    }

    private static String chooseTrack(String region) {
        WorldRacingMap map = new WorldRacingMap();
        List<WorldRacingMap.Track> tracks = map.getTracksByRegion(region);
        System.out.println("Select Track in " + region + ":");
        for (int i = 0; i < tracks.size(); i++) {
            System.out.println((i + 1) + ". " + tracks.get(i).getName());
        }
        System.out.print("Choice: ");
        int choice = Integer.parseInt(scanner.nextLine());
        return tracks.get(choice - 1).getName();
    }

    private static String chooseWeather() {
        System.out.println("Select Weather Condition:");
        System.out.println("1. Dry");
        System.out.println("2. Wet");
        System.out.println("3. Stormy");
        System.out.println("4. Foggy");
        System.out.print("Choice: ");
        return switch (Integer.parseInt(scanner.nextLine())) {
            case 2 -> "Wet";
            case 3 -> "Stormy";
            case 4 -> "Foggy";
            default -> "Dry";
        };
    }
}






