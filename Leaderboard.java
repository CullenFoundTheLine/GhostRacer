import java.util.*;

public class Leaderboard {
    private Map<String, Record> driverRecords = new HashMap<>();

    public void addRecord(String driverName, double lapTime, String carModel) {
        Record newRecord = new Record(driverName, lapTime, carModel);
        driverRecords.put(driverName, newRecord);  // Replace old lap if exists
    }

    public void showTop(int top) {
        List<Record> sorted = new ArrayList<>(driverRecords.values());
        sorted.sort(Comparator.comparingDouble(r -> r.lapTime));

        System.out.println("\n=== LEADERBOARD (Top " + top + ") ===");
        for (int i = 0; i < Math.min(top, sorted.size()); i++) {
            Record r = sorted.get(i);
            System.out.printf("%d. %s - %.3f sec (%s)%n", i + 1, r.driverName, r.lapTime, r.carModel);
        }
    }

    public int getDriverPosition(String driverName) {
        List<Record> sorted = new ArrayList<>(driverRecords.values());
        sorted.sort(Comparator.comparingDouble(r -> r.lapTime));

        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i).driverName.equals(driverName)) {
                return i + 1;
            }
        }
        return -1; // Not found
    }

    private static class Record {
        String driverName;
        double lapTime;
        String carModel;

        public Record(String driverName, double lapTime, String carModel) {
            this.driverName = driverName;
            this.lapTime = lapTime;
            this.carModel = carModel;
        }
    }
}


