import java.util.*;

public class TrackSelector {
    private final Map<String, TrackInfo> tracks = new LinkedHashMap<>();

    public TrackSelector() {
        // Global track samples (not just for drift)
        tracks.put("Suzuka Circuit", new TrackInfo("Suzuka Circuit", 75.0, "corner", 0.0, 0.4, false));
        tracks.put("Nurburgring GP", new TrackInfo("Nurburgring GP", 110.0, "straight", 0.4, 0.7, false));
        tracks.put("Ebisu Minami", new TrackInfo("Ebisu Minami", 68.0, "corner", 0.7, 1.0, true));
        tracks.put("Monza", new TrackInfo("Monza", 120.0, "straight", 0.0, 1.0, false));
        tracks.put("Tsukuba", new TrackInfo("Tsukuba", 88.0, "corner", 0.2, 0.6, true));
    }

    public TrackInfo selectTrack(String name) {
        if (name == null || name.trim().isEmpty()) {
            log("Track selection failed: name is null or empty.");
            return null;
        }
        TrackInfo track = tracks.get(name);
        if (track == null) {
            log("Track selection failed: '" + name + "' not found.");
        } else {
            log("Track selected: " + name);
        }
        return track;
    }

    public void showAvailableTracks() {
        System.out.println("=== Available Tracks ===");
        for (String name : tracks.keySet()) {
            TrackInfo track = tracks.get(name);
            track.printTrackInfo();
            log("Track available: " + name);
        }
    }

    private void log(String message) {
        System.out.println("[TrackSelector] " + message);
    }
}
