package Core;
public class ECUSettings {
    private boolean abs;
    private boolean tractionControl;
    private boolean driftAssist;
    private boolean counterSteering;
    private boolean autoBrakeZones;

    public ECUSettings(boolean abs, boolean tractionControl, boolean driftAssist, boolean counterSteering, boolean autoBrakeZones) {
        this.abs = abs;
        this.tractionControl = tractionControl;
        this.driftAssist = driftAssist;
        this.counterSteering = counterSteering;
        this.autoBrakeZones = autoBrakeZones;
    }

    public void adjustForStyle(String style) {
        switch (style.toLowerCase()) {
            case "aggressive":
                this.tractionControl = false;
                this.autoBrakeZones = false;
                break;
            case "conservative":
                this.abs = true;
                this.driftAssist = false;
                break;
            case "balanced":
            default:
                this.abs = true;
                this.tractionControl = true;
                break;
        }
    }

    public boolean isABS() { return abs; }
    public boolean isTractionControl() { return tractionControl; }
    public boolean isDriftAssist() { return driftAssist; }
    public boolean isCounterSteering() { return counterSteering; }
    public boolean isAutoBrakeZones() { return autoBrakeZones; }
}
