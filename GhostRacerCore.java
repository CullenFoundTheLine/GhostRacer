import java.util.*;


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
    private RacePredictor racePredictor = new RacePredictor(); // Add predictor

    public GhostRacerCore() {
        drivers = new ArrayList<>();
        raceDataList = new ArrayList<>();
        memory = new GhostMemory();
        regulations = new FIARegulations();
        ghostMind = new GhostMind(memory, regulations);
        hud = new GhostHUD();
        ecu = new ECUSettings(true, true, true, true, true);
        driverProfiles = new HashMap<>();
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
    }

    public void runRace(int laps) {
        Random rand = new Random();
        System.out.println();

        for (int lap = 1; lap <= laps; lap++) {
            System.out.println("==== LAP " + lap + " ====\n");

            for (Driver driver : drivers) {
                String name = driver.getName();
                DriverProfile profile = driver.getProfile();

                ecu.adjustForStyle(profile.getPreferredStyle());

                double totalLapTime = 0;
                double totalTireWear = 0;
                double totalBrakePressure = 0;

                // Create a DriverHealth object for this driver
                DriverHealth driverHealth = new DriverHealth();

                for (TurnSection turn : trackMemory.getTurns()) {
                    double speed = turn.getEntrySpeed() + rand.nextDouble() * 10;
                    boolean canDrift = speed > turn.getExitSpeed();
                    double tireTemp = 80 + rand.nextDouble() * 25;
                    double tireWear = rand.nextDouble() * 10;
                    double throttle = Math.random();
                    double brakeForce = Math.random();
                    double cornerSpeed = turn.getExitSpeed() - 5 + rand.nextDouble() * 10;
                    double suspensionLoad = 0.8 + Math.random() * 0.2;
                    double gapBehind = rand.nextDouble() * 2;
                    double gapAhead = rand.nextDouble() * 2;

                    int brakeZoneIntensity = turn.getBrakeZoneIntensity();

                    Map<String, Double> partHealth = new HashMap<>();
                    partHealth.put("Tires", 100 - tireWear);
                    partHealth.put("Suspension", 90 - (tireWear * 0.5));

                    // Simulate car and frontCar (minimal, as Car is a stub)
                    Car playerCar = new Car("SimCar", "Race");
                    Car frontCar = new Car("FrontCar", "Race");

                    // Simulate severeContact and carDamaged for demo
                    boolean severeContact = brakeZoneIntensity == 2 && rand.nextDouble() < 0.2;
                    boolean carDamaged = tireWear > 8.5;

                    // Update driver health based on race events
                    driverHealth.update(
                        gapAhead < 0.5, // closeRace
                        canDrift,       // overtaking (using canDrift as a proxy)
                        severeContact   // crashOrMistake
                    );

                    // Call RacePredictor
                    RacePredictor.Decision decision = racePredictor.predictMove(
                        playerCar,
                        frontCar,
                        new TrackInfo(turn.getName(), turn.getEntrySpeed(), "corner", 0, 1, false), // minimal TrackInfo
                        0, // trackPosition (not used)
                        gapAhead,
                        4, // currentGear (stub)
                        driverHealth,
                        brakeZoneIntensity,
                        severeContact,
                        carDamaged
                    );

                    System.out.println("Driver: " + name + " | Turn: " + turn.getName() + " | Decision: " + decision);

                    String action = ghostMind.decideAction(
                            turn.getAngleDegrees(),
                            speed,
                            canDrift,
                            profile.getPreferredStyle(),
                            partHealth,
                            tireTemp
                    );

                    double sectionTime = 5 + rand.nextDouble() * 3;
                    totalLapTime += sectionTime;
                    totalTireWear += tireWear;
                    totalBrakePressure += brakeForce;

                    RaceData data = new RaceData(
                            name, sectionTime, tireTemp, tireWear,
                            speed, throttle, brakeForce,
                            cornerSpeed, suspensionLoad,
                            gapBehind, gapAhead
                    );

                    hud.updateDisplay(data, ecu);
                }

                driver.logLapTime(totalLapTime);
                memory.recordLapTime(name, totalLapTime);
                memory.recordDriverHabit(name, "Style: " + profile.getPreferredStyle());

                raceDataList.add(new RaceData(
                        name, totalLapTime, totalTireWear, totalTireWear,
                        0, 0, 0, 0, 0, 0, 0 // Placeholder for summary RaceData
                ));

                trackMemory.recordLapData(lap, totalLapTime, totalTireWear, totalBrakePressure, lap == 2 ? lap : -1);
                Map<String, Object> lapDataMap = trackMemory.getLapData().get(lap).toMap();
                Map<String, Double> lapDataDoubleMap = new HashMap<>();
                for (Map.Entry<String, Object> entry : lapDataMap.entrySet()) {
                    if (entry.getValue() instanceof Number) {
                        lapDataDoubleMap.put(entry.getKey(), ((Number) entry.getValue()).doubleValue());
                    }
                }
                ghostMind.analyzeLapPerformance(lapDataDoubleMap, totalLapTime, totalTireWear);
            }
        }
    }
}




