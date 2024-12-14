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
        cars.add(new Car("car1", 0, 3, "EAST")); // En la carretera horizontal
        cars.add(new Car("car2", 1, 6, "SHOUT"));
        cars.add(new Car("car3", 3, 6, "SHOUT"));
        cars.add(new Car("car4", 5, 6, "SHOUT"));
        cars.add(new Car("car5", 7, 6, "SHOUT"));
        cars.add(new Car("car6", 1, 0, "WEST"));
        cars.add(new Car("car7", 3, 0, "NORTH"));
        cars.add(new Car("car8", 5, 0, "NORTH"));
        cars.add(new Car("car9", 7, 0, "NORTH"));

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
        for (Car car : cars) {
            if (car.getDirection().equals("EAST")) {
                int nextX = car.getX() + 1;

                // Verificar si hay un semáforo en la siguiente casilla
                TrafficLight trafficLight = getTrafficLightAt(nextX, car.getY());
                if (trafficLight != null && trafficLight.getState().equals("RED")) {
                    continue; // Si el semáforo está en rojo, el coche se detiene
                }

                // Verificar si hay un coche en la siguiente casilla
                if (getCarAt(nextX, car.getY()) != null) {
                    continue; // Si hay un coche, el coche se detiene
                }

                // Si alcanza una intersección, puede girar
                if (isIntersection(nextX, car.getY())) {
                    if (rd.nextBoolean()) {
                        car.setDirection("SOUTH");
                    }
                }

                if (isRoad(nextX, car.getY())) {
                    car.setX(nextX);
                }

                // Si el coche se sale del mapa, eliminarlo y crear uno nuevo
                if (car.getX() >= GRID_WIDTH) {
                    cars.remove(car);
                    newCars.add(createNewCar("WEST"));
                }
            } else if (car.getDirection().equals("SOUTH")) {
                int nextY = car.getY() -1;

                // Verificar si hay un semáforo en la siguiente casilla
                TrafficLight trafficLight = getTrafficLightAt(car.getX(), nextY);
                if (trafficLight != null && trafficLight.getState().equals("RED")) {
                    continue; // Si el semáforo está en rojo, el coche se detiene
                }

                // Verificar si hay un coche en la siguiente casilla
                if (getCarAt(car.getX(), nextY) != null) {
                    continue; // Si hay un coche, el coche se detiene
                }

                if (isRoad(car.getX(), nextY)) {
                    car.setY(nextY);
                }

                // Si el coche se sale del mapa, eliminarlo y crear uno nuevo
                if (car.getY() >= GRID_HEIGHT) {
                    cars.remove(car);
                    newCars.add(createNewCar("NORTH"));
                }
            } else if (car.getDirection().equals("WEST")) {
                int nextX = car.getX() -1;

                // Verificar si hay un semáforo en la siguiente casilla
                TrafficLight trafficLight = getTrafficLightAt(nextX, car.getY());
                if (trafficLight != null && trafficLight.getState().equals("RED")) {
                    continue; // Si el semáforo está en rojo, el coche se detiene
                }

                // Verificar si hay un coche en la siguiente casilla
                if (getCarAt(nextX, car.getY()) != null) {
                    continue; // Si hay un coche, el coche se detiene
                }

                // Si alcanza una intersección, puede girar
                if (isIntersection(nextX, car.getY())) {
                    if (rd.nextBoolean()) {
                        car.setDirection("NORTH");
                    }
                }

                if (isRoad(nextX, car.getY())) {
                    car.setX(nextX);
                }

                // Si el coche se sale del mapa, eliminarlo y crear uno nuevo
                if (car.getX() < 0) {
                    cars.remove(car);
                    newCars.add(createNewCar("EAST"));
                }
            }else if (car.getDirection().equals("NORTH")) {
                int nextY = car.getY() +1;

                // Verificar si hay un semáforo en la siguiente casilla
                TrafficLight trafficLight = getTrafficLightAt(car.getX(), nextY);
                if (trafficLight != null && trafficLight.getState().equals("RED")) {
                    continue; // Si el semáforo está en rojo, el coche se detiene
                }

                // Verificar si hay un coche en la siguiente casilla
                if (getCarAt(car.getX(), nextY) != null) {
                    continue; // Si hay un coche, el coche se detiene
                }

                if (isRoad(car.getX(), nextY)) {
                    car.setY(nextY);
                }

                // Si el coche se sale del mapa, eliminarlo y crear uno nuevo
                if (car.getY() >= GRID_HEIGHT) {
                    cars.remove(car);
                    newCars.add(createNewCar("SOUTH"));
                }
            }
        }
        cars.removeIf(car -> car.getX() < 0 || car.getX() >= GRID_WIDTH || car.getY() < 0 || car.getY() >= GRID_HEIGHT);
        cars.addAll(newCars);
    }

    private Car createNewCar(String direction) {
        Random rd = new Random();
        int x = 0, y = 0;
        switch (direction) {
            case "EAST":
                x = 0;
                y = 3;
                break;
            case "WEST":
                x = GRID_WIDTH - 1;
                y = 3;
                break;
            case "NORTH":
                x = rd.nextInt(GRID_WIDTH);
                y = GRID_HEIGHT - 1;
                break;
            case "SOUTH":
                x = rd.nextInt(GRID_WIDTH);
                y = 0;
                break;
        }
        return new Car("car" + (cars.size() + 1), x, y, direction);
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
