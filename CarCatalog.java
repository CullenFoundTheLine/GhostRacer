import java.util.*;

public class CarCatalog {
    private List<Car> allCars = new ArrayList<>();

    public CarCatalog() {}

    public void addCar(Car car) {
        allCars.add(car);
    }

    public List<Car> getAllCars() {
        return allCars;
    }

    public Car findCarByModel(String model) {
        for (Car car : allCars) {
            if (car.getModel().equalsIgnoreCase(model)) {
                return car;
            }
        }
        return null;
    }

    // Prepares telemetry data from the Car object for GHOST AI analysis
    public RaceData prepareRaceData(Car car, String driverName, double lapTime, double tireWear, double gapBehind, double gapAhead) {
        return new RaceData(
                driverName,
                lapTime,
                car.getTireTemp(),
                tireWear,
                car.getSpeed(),
                car.getThrottle(),
                car.getBrakeForce(),
                car.getCornerSpeed(),
                car.getSuspensionLoad(),
                gapBehind,
                gapAhead
        );
    }
}

