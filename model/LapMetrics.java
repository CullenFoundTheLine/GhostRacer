package model;
import java.util.HashMap;
import java.util.Map;
import Core.DriverHealth;

public class LapMetrics {
    private double lapTime;
    private double tireWear;
    private double brakePressure;
    private int pitLap;

    // Default constructor
    public LapMetrics() {
        this.lapTime = 0.0;
        this.tireWear = 0.0;
        this.brakePressure = 0.0;
        this.pitLap = -1;
    }

    public LapMetrics(double lapTime, double tireWear, double brakePressure, int pitLap) {
        this.lapTime = lapTime;
        this.tireWear = tireWear;
        this.brakePressure = brakePressure;
        this.pitLap = pitLap;
    }

    public double getLapTime() {
        return lapTime;
    }

    public double getTireWear() {
        return tireWear;
    }

    public double getBrakePressure() {
        return brakePressure;
    }

    public int getPitLap() {
        return pitLap;
    }

    public void recordDriverHealth(DriverHealth health) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recordDriverHealth'");
    }

    public String getAverageHeartRate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAverageHeartRate'");
    }

    public int getAverageStressLevel() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAverageStressLevel'");
    }

    // Removed duplicate getTireWear() method to resolve compilation error.

    public Object getPitStopLapObj() {
        return pitLap;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("lapTime", lapTime);
        map.put("tireWear", tireWear);
        map.put("brakePressure", brakePressure);
        map.put("pitStopLap", pitLap);
        return map;
    }
}


