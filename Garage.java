import Core.Car;
import java.util.*;

public class Garage {
    private List<Car> ownedCars = new ArrayList<>();
    private String owner;

    public Garage(String owner) {
        this.owner = owner;
    }

    public void addCar(Car car) {
        ownedCars.add(car);
    }

    public void removeCar(Car car) {
        ownedCars.remove(car);
    }

    public List<Car> getOwnedCars() {
        return ownedCars;
    }

    public void showGarage() {
        System.out.println("=== " + owner + "'s Garage ===");
        if (ownedCars.isEmpty()) {
            System.out.println("No cars currently in garage.");
        } else {
            for (Car car : ownedCars) {
                car.printDetails();
            }
        }
    }

    public Car findCarByModel(String model) {
        for (Car car : ownedCars) {
            if (car.getModel().equalsIgnoreCase(model)) {
                return car;
            }
        }
        return null;
    }
}

