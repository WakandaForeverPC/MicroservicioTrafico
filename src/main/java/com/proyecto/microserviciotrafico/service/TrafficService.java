package com.proyecto.microserviciotrafico.service;

import com.proyecto.microserviciotrafico.model.Car;
import com.proyecto.microserviciotrafico.model.TrafficLight;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class TrafficService {
    private List<Car> cars;
    private List<TrafficLight> trafficLights;
    private final int GRID_WIDTH = 9;
    private final int GRID_HEIGHT = 7;

    public TrafficService() {
        cars = new ArrayList<>();
        trafficLights = new ArrayList<>();

        // Inicializar coches de prueba
        cars.add(new Car("car1", 0, 3, "EAST", "#98b8c6")); // En la carretera horizontal
        cars.add(new Car("car2", 1, 0, "SOUTH", "#9b111e"));
        cars.add(new Car("car4", 5, 0, "SOUTH", "#fafbfd"));
        cars.add(new Car("car6", 0, 3, "WEST", "#308446"));
        cars.add(new Car("car9", 7, 6, "NORTH", "black"));

        // Inicializar semáforos en puntos específicos del tablero
        trafficLights.add(new TrafficLight("tl1", 3, 3, "RED"));
        trafficLights.add(new TrafficLight("tl2", 5, 3, "GREEN"));

        // Programar el cambio de semáforos cada 4 segundos
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::updateTrafficLights, 0, 4, TimeUnit.SECONDS);
    }

    public void moveCars() {
        Random rd = new Random();
        List<Car> newCars = new ArrayList<>();
        List<Car> carsToRemove = new ArrayList<>();

        for (Car car : cars) {
            switch (car.getDirection()) {
                case "EAST":
                    int nextXEast = car.getX() + 1;

                    // Check if there is a traffic light in the next cell
                    TrafficLight trafficLightEast = getTrafficLightAt(nextXEast, car.getY());
                    if (trafficLightEast != null && trafficLightEast.getState().equals("RED")) {
                        continue; // If the traffic light is red, the car stops
                    }

                    // Check if there is a car in the next cell
                    Car nextCarEast = getCarAt(nextXEast, car.getY());
                    if (nextCarEast != null && nextCarEast.getDirection().equals("EAST")) {
                        continue; // If there is a car in the same direction, the car stops
                    }

                    if (isRoad(nextXEast, car.getY())) {
                        car.setX(nextXEast);
                    }

                    // Check if the car is at an intersection and has a 50% chance to turn right
                    if (isIntersection(nextXEast, car.getY()) && rd.nextBoolean()) {
                        car.setDirection("NORTH");
                    }

                    // If the car goes off the map, mark it for removal and create a new one
                    if (car.getX() >= GRID_WIDTH) {
                        carsToRemove.add(car);
                        newCars.add(createNewCar("EAST", 0, car.getY(), car.getColor()));
                    }
                    break;

                case "WEST":
                    int nextXWest = car.getX() - 1;

                    // Check if there is a traffic light in the next cell
                    TrafficLight trafficLightWest = getTrafficLightAt(nextXWest, car.getY());
                    if (trafficLightWest != null && trafficLightWest.getState().equals("RED")) {
                        continue; // If the traffic light is red, the car stops
                    }

                    // Check if there is a car in the next cell
                    Car nextCarWest = getCarAt(nextXWest, car.getY());
                    if (nextCarWest != null && nextCarWest.getDirection().equals("WEST")) {
                        continue; // If there is a car in the same direction, the car stops
                    }

                    if (isRoad(nextXWest, car.getY())) {
                        car.setX(nextXWest);
                    }

                    // Check if the car is at an intersection and has a 50% chance to turn right
                    if (isIntersection(nextXWest, car.getY()) && rd.nextBoolean()) {
                        car.setDirection("SOUTH");
                    }

                    // If the car goes off the map, mark it for removal and create a new one
                    if (car.getX() < 0) {
                        carsToRemove.add(car);
                        newCars.add(createNewCar("WEST", GRID_WIDTH - 1, car.getY(), car.getColor()));
                    }
                    break;

                case "NORTH":
                    int nextYNorth = car.getY() - 1;

                    // Check if there is a traffic light in the next cell
                    TrafficLight trafficLightNorth = getTrafficLightAt(car.getX(), nextYNorth);
                    if (trafficLightNorth != null && trafficLightNorth.getState().equals("RED")) {
                        continue; // If the traffic light is red, the car stops
                    }

                    // Check if there is a car in the next cell
                    Car nextCarNorth = getCarAt(car.getX(), nextYNorth);
                    if (nextCarNorth != null && nextCarNorth.getDirection().equals("NORTH")) {
                        continue; // If there is a car in the same direction, the car stops
                    }

                    if (isRoad(car.getX(), nextYNorth)) {
                        car.setY(nextYNorth);
                    }

                    // Check if the car is at an intersection and has a 50% chance to turn right
                    if (isIntersection(car.getX(), nextYNorth) && rd.nextBoolean()) {
                        car.setDirection("WEST");
                    }

                    // If the car goes off the map, mark it for removal and create a new one
                    if (car.getY() < 0) {
                        carsToRemove.add(car);
                        newCars.add(createNewCar("NORTH", car.getX(), GRID_HEIGHT - 1, car.getColor()));
                    }
                    break;

                case "SOUTH":
                    int nextYSouth = car.getY() + 1;

                    // Check if there is a traffic light in the next cell
                    TrafficLight trafficLightSouth = getTrafficLightAt(car.getX(), nextYSouth);
                    if (trafficLightSouth != null && trafficLightSouth.getState().equals("RED")) {
                        continue; // If the traffic light is red, the car stops
                    }

                    // Check if there is a car in the next cell
                    Car nextCarSouth = getCarAt(car.getX(), nextYSouth);
                    if (nextCarSouth != null && nextCarSouth.getDirection().equals("SOUTH")) {
                        continue; // If there is a car in the same direction, the car stops
                    }

                    if (isRoad(car.getX(), nextYSouth)) {
                        car.setY(nextYSouth);
                    }

                    // Check if the car is at an intersection and has a 50% chance to turn right
                    if (isIntersection(car.getX(), nextYSouth) && rd.nextBoolean()) {
                        car.setDirection("EAST");
                    }

                    // If the car goes off the map, mark it for removal and create a new one
                    if (car.getY() >= GRID_HEIGHT) {
                        carsToRemove.add(car);
                        newCars.add(createNewCar("SOUTH", car.getX(), 0, car.getColor()));
                    }
                    break;
            }
        }

        // Remove cars that went off the map
        cars.removeAll(carsToRemove);
        // Add new cars
        cars.addAll(newCars);
    }

    private Car createNewCar(String direction, int x, int y, String color) {
        return new Car("car" + (cars.size() + 1), x, y, direction, color);
    }

    private Car getCarAt(int x, int y) {
        for (Car car : cars) {
            if (car.getX() == x && car.getY() == y) {
                return car;
            }
        }
        return null;
    }

    private TrafficLight getTrafficLightAt(int x, int y) {
        for (TrafficLight trafficLight : trafficLights) {
            if (trafficLight.getX() == x && trafficLight.getY() == y) {
                return trafficLight;
            }
        }
        return null;
    }

    public void updateTrafficLights() {
        for (TrafficLight trafficLight : trafficLights) {
            if (trafficLight.getState().equals("RED")) {
                trafficLight.setState("GREEN");
            } else {
                trafficLight.setState("RED");
            }
        }
    }

    private boolean isRoad(int x, int y) {
        // Validar si la posición es una carretera horizontal, vertical o intersección
        if (y == 3 || x % 2 == 1) {
            return true; // Carreteras horizontales o verticales
        }
        return false;
    }

    private boolean isIntersection(int x, int y) {
        // Validar si la posición es una intersección
        return (y == 3 && x % 2 == 1);
    }

    public List<Car> getCars() {
        return cars;
    }

    public List<TrafficLight> getTrafficLights() {
        return trafficLights;
    }
}