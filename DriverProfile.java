public class DriverProfile {
    private String driverName;
    private String preferredStyle = "Balanced"; // Can be updated later

    public DriverProfile(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getPreferredStyle() {
        return preferredStyle;
    }

    public void setPreferredStyle(String style) {
        this.preferredStyle = style;
    }
}
