import java.util.HashMap;
import java.util.Map;

public class LapMetrics {
    public double lapTimeSeconds, tireWearPercent, avgBrakePressure;
    private int pitStopLap;

    public LapMetrics(double lapTimeSeconds, double tireWearPercent, double avgBrakePressure, int pitStopLap) {
        this.lapTimeSeconds = lapTimeSeconds;
        this.tireWearPercent = tireWearPercent;
        this.avgBrakePressure = avgBrakePressure;
        this.pitStopLap = pitStopLap;
    }
private class TireWearTemperature {


    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("lapTime", lapTimeSeconds);
        return map;
    }
}
