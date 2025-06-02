import java.util.*;

public class WorldRacingMap {

    public enum TrackType {
        CIRCUIT, DRIFT, FIA_CERTIFIED, NON_FIA, STREET, OVAL
    }

    public static class Track {
        private final String name;
        private final String location;
        private final TrackType type;
        private final List<String> fiaRegulations;

        public Track(String name, String location, TrackType type, List<String> fiaRegulations) {
            this.name = name;
            this.location = location;
            this.type = type;
            this.fiaRegulations = fiaRegulations != null ? fiaRegulations : new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public String getLocation() {
            return location;
        }

        public TrackType getType() {
            return type;
        }

        public List<String> getFiaRegulations() {
            return fiaRegulations;
        }

        @Override
        public String toString() {
            return name + " (" + location + ", " + type + ")";
        }
    }

    private final Map<String, List<Track>> regionMap = new HashMap<>();

    public WorldRacingMap() {
        loadTracks();
    }

    private void loadTracks() {
        addTrack("Europe", new Track("Nurburgring Nordschleife", "Germany", TrackType.FIA_CERTIFIED, List.of("FIA Appendix O", "Safety Barriers", "Driver Licensing")));
        addTrack("Europe", new Track("Spa-Francorchamps", "Belgium", TrackType.FIA_CERTIFIED, List.of("FIA Grade 1", "Track Limits", "Overtaking Zones")));
        addTrack("Europe", new Track("Ebisu Circuit", "Japan", TrackType.DRIFT, List.of("JDDA Rules")));

        addTrack("North America", new Track("Laguna Seca", "USA", TrackType.CIRCUIT, List.of("SCCA", "FIA Grade 2")));
        addTrack("North America", new Track("Formula Drift Long Beach", "USA", TrackType.DRIFT, List.of("Formula Drift Judging Criteria")));

        addTrack("Asia Pacific", new Track("Suzuka Circuit", "Japan", TrackType.FIA_CERTIFIED, List.of("FIA Grade 1", "Curfew Rules", "Safety Car Use")));
        addTrack("Asia Pacific", new Track("Tsukuba Circuit", "Japan", TrackType.DRIFT, List.of("Time Attack Safety Protocols")));

        addTrack("South America", new Track("Interlagos", "Brazil", TrackType.FIA_CERTIFIED, List.of("FIA Grade 1", "Weather Protocols")));

        addTrack("Middle East", new Track("Yas Marina", "UAE", TrackType.FIA_CERTIFIED, List.of("FIA Grade 1", "Pit Speed Limits")));

        addTrack("Africa", new Track("Kyalami Grand Prix Circuit", "South Africa", TrackType.FIA_CERTIFIED, List.of("FIA Safety Fence Rules")));

        addTrack("Australia", new Track("Mount Panorama (Bathurst)", "Australia", TrackType.NON_FIA, List.of("V8 Supercars Rules", "Rolling Start Protocol")));
    }

    private void addTrack(String region, Track track) {
        regionMap.computeIfAbsent(region, k -> new ArrayList<>()).add(track);
    }

    public List<Track> getTracksByRegion(String region) {
        return regionMap.getOrDefault(region, new ArrayList<>());
    }

    public List<Track> getAllTracks() {
        List<Track> all = new ArrayList<>();
        for (List<Track> list : regionMap.values()) {
            all.addAll(list);
        }
        return all;
    }

    public void printTrackSelection() {
        System.out.println("=== WORLD RACING TRACK SELECTION ===");
        for (Map.Entry<String, List<Track>> entry : regionMap.entrySet()) {
            System.out.println("\n-- " + entry.getKey() + " --");
            for (Track track : entry.getValue()) {
                System.out.println(track);
            }
        }
    }
}
