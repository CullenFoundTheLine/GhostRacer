package Ghost;

import java.util.*;
import model.TurnSection;
import model.TrackMemory;
import model.LapMetrics;
import Ghost.GhostMind;
import Ghost.GhostHUD;
import Ghost.GhostMemory;
import Ghost.GhostAdvisor;
import Ghost.DriftBattleRegulations;
import Ghost.RacePredictor;
import Ghost.WorldRacingMap;
import Core.Car;
import Core.CarCatalog;
import Core.Driver;
import Core.DriverHealth;
import Core.DriverProfile;
import Core.ECUSettings;
import Core.FIARegulations;
import Core.Part;
import Core.RaceData;
import Core.TrackInfo;
import java.util.Scanner;
// Ensure the UIFACE.UserManager and UIFACE.User classes exist and are in the correct package
// import UIFACE.UserManager;
// import UIFACE.User;

public class GhostRacerCore {
    private List<Driver> drivers;
    private List<RaceData> raceDataList;
    private GhostMind ghostMind;
    private GhostHUD hud;
    private ECUSettings ecu;
    private Map<String, DriverProfile> driverProfiles;
    private GhostMemory memory;
    private FIARegulations regulations;
    private TrackMemory trackMemory;
    private WorldRacingMap.Track selectedTrack;
    private CarCatalog carCatalog = new CarCatalog();
    // private UserManager userManager = new UserManager();
    // private Map<String, User> driverUserMap = new HashMap<>();
    private Map<String, Car> driverCarMap = new HashMap<>();

    // Add these fields if not already present
    private GhostAdvisor advisor = new GhostAdvisor();
    private DriftBattleRegulations driftReg = new DriftBattleRegulations();
    private RacePredictor racePredictor = new RacePredictor();
    private WorldRacingMap worldMap = new WorldRacingMap();

    // New fields for best lap tracking
    private Map<String, Double> bestLapTime = new HashMap<>();
    private Map<String, Integer> bestLapNumber = new HashMap<>();

    public GhostRacerCore() {
        drivers = new ArrayList<>();
        raceDataList = new ArrayList<>();
        memory = new GhostMemory();
        regulations = new FIARegulations();
        ghostMind = new GhostMind(memory, regulations);
        hud = new GhostHUD();
        ecu = new ECUSettings(true, true, true, true, true);
        driverProfiles = new HashMap<>();

        // Example: when adding a car
        Car supra = new Car("Toyota Supra", "Drift");
        carCatalog.addCar(supra);
        Car ferrari = new Car("Ferrari 488", "Race");
        carCatalog.addCar(ferrari);
        // You can add more cars as needed

        // Example usages to ensure imports are used:
        advisor.explainUpgradeForEngineer(
            new Car("Demo", "Race"), 
            new Part("DemoPart", 10, "Turbo"), 
            new FIARegulations()
        );

        driftReg.analyzeDriftLap(new ArrayList<>(), new HashMap<>());

        racePredictor.predictMove(
            new Car("Demo", "Race"),
            new Car("DemoFront", "Race"),
            new TrackInfo("DemoTrack", 100, "corner", 0, 1, false),
            null, // TurnSection
            null, // TrackMemory
            0.0, 0.0, 1, new DriverHealth(), 0, false, false
        );

        worldMap.printTrackSelection();
    }

    public void loadTrack(String region, String trackName) {
        WorldRacingMap map = new WorldRacingMap();
        List<WorldRacingMap.Track> regionTracks = map.getTracksByRegion(region);

        for (WorldRacingMap.Track track : regionTracks) {
            if (track.getName().equalsIgnoreCase(trackName)) {
                selectedTrack = track;
                break;
            }
        }

        if (selectedTrack == null) {
            System.out.println("Track not found. Defaulting to sample layout.");
            this.trackMemory = new TrackMemory(trackName, 5.8, 12.0, 3);
        } else {
            System.out.println("Loaded: " + selectedTrack);
            this.trackMemory = new TrackMemory(selectedTrack.getName(), 5.8, 12.0, 3);
        }

        // Mock layout (for now)
        trackMemory.addTurn(1, 100, 45, 160, 120, 1, "YELLOW", "Turn 1");
        trackMemory.addTurn(2, 80, 90, 120, 90, 2, "RED", "Turn 2");
        trackMemory.addTurn(3, 150, 30, 180, 170, 0, "GREEN", "Turn 3");
    }

    public void addDriver(String name) {
        Driver driver = new Driver(name);
        drivers.add(driver);
        driverProfiles.put(name, driver.getProfile());
        memory.recordDriverHabit(name, "Unknown");

        // Assign a car to the driver (for demo, use Supra for first, Ferrari for second, etc.)
        Car car = carCatalog.getAllCars().size() > drivers.size() - 1
            ? carCatalog.getAllCars().get(drivers.size() - 1)
            : carCatalog.getAllCars().get(0);
        driverCarMap.put(name, car);

        // Register and map user (for demo, password is "pass" and role is DRIVER)
        // userManager.registerUser(name, "pass", User.Role.DRIVER);
        // driverUserMap.put(name, userManager.login(name, "pass"));
    }

    public void runRace(int laps) {
        Random rand = new Random();
        Map<String, Integer> mistakeCount = new HashMap<>();
    

        for (int lap = 1; lap <= laps; lap++) {
            for (Driver driver : drivers) {
                String name = driver.getName();
                LapMetrics lapMetrics = trackMemory.getLapData().get(lap);
                if (lapMetrics == null) continue;
                double lapTime = lapMetrics.getLapTime();
                double tireWear = lapMetrics.getTireWear();
                double brakeTemp = lapMetrics.getBrakePressure() * 1000; // Example conversion
                String mistake = ""; // Replace with real mistake detection if available

                // Lap Time Drop
                if (lap > 1) {
                    double prevLapTime = trackMemory.getLapData().get(lap - 1).getLapTime();
                    if (lapTime > prevLapTime * 1.03) {
                        memory.recordFeedback("Lap " + lap, name, "coach",
                            "Lap time dropped in sector 2.",
                            "Focus on exit speed and smooth throttle.");
                        memory.recordFeedback("Lap " + lap, name, "engineer",
                            "Possible tire degradation; monitor wear.",
                            "Consider pitting or adjusting driving style.");
                    }
                }

                // Consistent Lap Time (3+ laps within 1% of each other)
                if (lap >= 3) {
                    double lap1 = trackMemory.getLapData().get(lap - 2).getLapTime();
                    double lap2 = trackMemory.getLapData().get(lap - 1).getLapTime();
                    if (Math.abs(lapTime - lap1) / lap1 < 0.01 && Math.abs(lapTime - lap2) / lap2 < 0.01) {
                        memory.recordFeedback("Lap " + lap, name, "coach",
                            "Great consistency, keep it up!",
                            "Maintain your rhythm and focus.");
                        memory.recordFeedback("Lap " + lap, name, "driver",
                            "Car feels stable, rhythm is good.",
                            "Keep doing what you're doing.");
                    }
                }

                // Tire Wear High
                if (tireWear > 75) {
                    memory.recordFeedback("Lap " + lap, name, "engineer",
                        "Rear tires at limit.", "Consider pit or smoother driving.");
                    memory.recordFeedback("Lap " + lap, name, "coach",
                        "Tire wear high.", "Smooth inputs to save tires.");
                }

                // Brake Temp High
                if (brakeTemp > 950) {
                    memory.recordFeedback("Lap " + lap, name, "engineer",
                        "Brake temps high.", "Try gentler braking.");
                }

                // Mistake in sector (simulate Turn 3 mistake every 4th lap)
                if (lap % 4 == 0) {
                    mistake = "Turn 3";
                    int count = mistakeCount.getOrDefault(name, 0) + 1;
                    mistakeCount.put(name, count);
                    memory.recordFeedback("Lap " + lap, name, "coach",
                        "Missed apex Turn 3.", "Practice braking point.");
                    memory.recordFeedback("Lap " + lap, name, "driver",
                        "Struggling with entry in Turn 3.", "Will focus on entry next lap.");
                    // Repeated mistake
                    if (count >= 2) {
                        memory.recordFeedback("Lap " + lap, name, "coach",
                            "Same issue in Turn 3—focus on entry speed.",
                            "Review brake balance and entry.");
                        memory.recordFeedback("Lap " + lap, name, "engineer",
                            "Repeated Turn 3 mistake.", "Check brake bias and setup.");
                    }
                }

                // Simulate improvement (mistake resolved)
                if (lap % 4 == 1 && lap > 4 && mistakeCount.getOrDefault(name, 0) > 0) {
                    memory.recordFeedback("Lap " + lap, name, "coach",
                        "Nice job fixing Turn 3!", "Keep up the focus.");
                    mistakeCount.put(name, 0);
                }

                // Pit Stop (simulate every 5th lap)
                if (lap % 5 == 0) {
                    memory.recordFeedback("Lap " + lap, name, "engineer",
                        "Good stop, gained a position!", "Monitor tire temps on out lap.");
                    memory.recordFeedback("Lap " + lap, name, "coach",
                        "Focus on pit entry line next time.", "Aim for smoother entry.");
                }

                // Track best lap time and number
                if (!bestLapTime.containsKey(name) || lapTime < bestLapTime.get(name)) {
                    bestLapTime.put(name, lapTime);
                    bestLapNumber.put(name, lap);
                }
            }
        }

        // Session End Feedback
        for (Driver driver : drivers) {
            String name = driver.getName();
            memory.recordFeedback("Session End", name, "coach",
                "You improved most in sector 3. Practice high-speed corners next.",
                "Review your best lap for reference.");
            memory.recordFeedback("Session End", name, "engineer",
                "Tire wear high, recommend harder compound.",
                "Analyze tire data for next event.");
            // New code for driver feedback
            Integer bestLap = bestLapNumber.get(name);
            String driverMsg;
            if (bestLap != null) {
                driverMsg = "Felt confident on lap " + bestLap + ", need to replicate that.";
            } else {
                driverMsg = "Felt confident on my best lap, need to replicate that.";
            }
            memory.recordFeedback("Session End", name, "driver",
                driverMsg,
                "Study your best lap and repeat those inputs.");
        }

        memory.printFeedbackForRole("coach");
        memory.printFeedbackForRole("engineer");
        memory.printFeedbackForRole("driver");
        printSessionDialogueWithInput();
    }

    public void printSessionDialogue() {
        for (Driver driver : drivers) {
            String name = driver.getName();
            List<LapMetrics> laps = new ArrayList<>();
            for (int i = 1; i <= trackMemory.getLapData().size(); i++) {
                LapMetrics m = trackMemory.getLapData().get(i);
                if (m != null) laps.add(m);
            }
            // Example: Detect a problem
            for (int i = 0; i < laps.size(); i++) {
                LapMetrics m = laps.get(i);
                if (m.getTireWear() > 12) {
                    System.out.println("==== LAP " + (i+1) + " ====");
                    System.out.println("Driver: " + name + " | Lap Time: " + String.format("%.2f", m.getLapTime()) +
                        " | Tire Wear: " + String.format("%.1f", m.getTireWear()) + "% | Rear sliding on exit");
                    System.out.println("[Driver] \"Rear feels unstable—can we lower rear tire pressures?\"");
                    System.out.println("[Engineer] \"Confirmed: rear tire temps are up. Suggest lowering pressure by 1 psi and monitoring grip.\"");
                    System.out.println("[Coach] \"Focus on smooth throttle exits in Turns 3 and 6 to reduce tire strain.\"");
                    System.out.println();
                }
            }
        }
    }

    public void printSessionDialogueWithInput() {
        Scanner scanner = new Scanner(System.in);

        for (Driver driver : drivers) {
            String name = driver.getName();
            List<LapMetrics> laps = new ArrayList<>();
            for (int i = 1; i <= trackMemory.getLapData().size(); i++) {
                LapMetrics m = trackMemory.getLapData().get(i);
                if (m != null) laps.add(m);
            }
            for (int i = 0; i < laps.size(); i++) {
                LapMetrics m = laps.get(i);
                if (m.getTireWear() > 12) {
                    System.out.println("==== LAP " + (i+1) + " ====");
                    System.out.println("Driver: " + name + " | Lap Time: " + String.format("%.2f", m.getLapTime()) +
                        " | Tire Wear: " + String.format("%.1f", m.getTireWear()) + "% | Rear sliding on exit");

                    // DRIVER INPUT with callback
                    System.out.print("[Driver] Enter your feedback/request: ");
                    String driverInput = scanner.nextLine();
                    System.out.println("[Engineer] " + generateEngineerResponse(driverInput));
                    System.out.println("[Coach] " + generateCoachResponse(driverInput));

                    // Allow driver to clarify
                    System.out.print("[Driver] Do you want to clarify or ask again? (yes/no): ");
                    String clarify = scanner.nextLine();
                    while (clarify.equalsIgnoreCase("yes")) {
                        System.out.print("[Driver] Enter your clarification or new feedback: ");
                        driverInput = scanner.nextLine();
                        System.out.println("[Engineer] " + generateEngineerResponse(driverInput));
                        System.out.println("[Coach] " + generateCoachResponse(driverInput));
                        System.out.print("[Driver] Clarify again? (yes/no): ");
                        clarify = scanner.nextLine();
                    }

                    // ENGINEER INPUT with callback
                    System.out.print("[Engineer] Enter your question or suggestion for " + name + ": ");
                    String engineerInput = scanner.nextLine();
                    System.out.println("[Driver] " + generateDriverResponse(engineerInput));
                    System.out.print("[Engineer] Clarify or ask again? (yes/no): ");
                    clarify = scanner.nextLine();
                    while (clarify.equalsIgnoreCase("yes")) {
                        System.out.print("[Engineer] Enter your clarification or new question: ");
                        engineerInput = scanner.nextLine();
                        System.out.println("[Driver] " + generateDriverResponse(engineerInput));
                        System.out.print("[Engineer] Clarify again? (yes/no): ");
                        clarify = scanner.nextLine();
                    }

                    // COACH INPUT with callback
                    System.out.print("[Coach] Enter your advice or question for " + name + ": ");
                    String coachInput = scanner.nextLine();
                    System.out.println("[Driver] " + generateDriverResponse(coachInput));
                    System.out.print("[Coach] Clarify or ask again? (yes/no): ");
                    clarify = scanner.nextLine();
                    while (clarify.equalsIgnoreCase("yes")) {
                        System.out.print("[Coach] Enter your clarification or new advice: ");
                        coachInput = scanner.nextLine();
                        System.out.println("[Driver] " + generateDriverResponse(coachInput));
                        System.out.print("[Coach] Clarify again? (yes/no): ");
                        clarify = scanner.nextLine();
                    }
                }
            }
        }
        // scanner.close(); // Only close if you are done with all input in your app
    }

    // --- Response Generators ---

    private String generateEngineerResponse(String driverInput) {
        String input = driverInput.toLowerCase();
        if (input.contains("tire") || input.contains("pressure")) {
            return "Great feedback. We'll adjust tire pressures and monitor grip closely. This should improve stability and tire life.";
        } else if (input.contains("brake")) {
            return "Thanks for letting us know. We'll check brake temps and bias to ensure optimal performance.";
        } else if (input.contains("oversteer")) {
            return "We'll adjust rear suspension and diff to reduce oversteer.";
        } else if (input.contains("understeer")) {
            return "We'll soften the front or add more front grip to help with oversteer.";
        } else if (input.contains("engine")) {
            return "We'll check engine temps and mapping for optimal performance.";
        } else if (input.contains("fuel")) {
            return "We'll monitor fuel burn and suggest short-shifting if needed.";
        }
        return "Thanks for your input. We'll analyze the data and make the best possible adjustments for you.";
    }

    private String generateCoachResponse(String driverInput) {
        String input = driverInput.toLowerCase();
        if (input.contains("corner") || input.contains("exit")) {
            return "Focus on smooth throttle and steering on corner exit. You're making great progress!";
        } else if (input.contains("brake")) {
            return "Remember your braking markers and stay relaxed on the pedal. Consistency is key!";
        } else if (input.contains("apex")) {
            return "Keep your eyes up and aim for consistent apexes. You're on the right track!";
        } else if (input.contains("mistake")) {
            return "Mistakes happen—reset, refocus, and nail the next lap!";
        }
        return "Excellent awareness. Keep building on these improvements each lap!";
    }

    private String generateDriverResponse(String input) {
        String lower = input.toLowerCase();
        if (lower.contains("tire") || lower.contains("pressure")) {
            return "Copy that. I'll pay close attention to tire management and give feedback after the next stint.";
        } else if (lower.contains("brake")) {
            return "Understood. I'll adjust my braking and let you know how it feels.";
        } else if (lower.contains("apex") || lower.contains("corner")) {
            return "Got it. I'll focus on my line and corner exits.";
        } else if (lower.contains("engine")) {
            return "I'll monitor engine temps and report any issues.";
        }
        return "Thanks for the advice. I'll apply it and keep you updated!";
    }
}