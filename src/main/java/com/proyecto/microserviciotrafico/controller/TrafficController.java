package com.proyecto.microserviciotrafico.controller;

import com.proyecto.microserviciotrafico.service.TrafficService;
import com.proyecto.microserviciotrafico.model.Car;
import com.proyecto.microserviciotrafico.model.TrafficLight;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TrafficController {

    private final TrafficService trafficService;

    public TrafficController(TrafficService trafficService) {
        this.trafficService = trafficService;
    }

    @GetMapping("/traffic/cars")
    public List<Car> getCars() {
        trafficService.moveCars(); // Actualizar posiciones de coches
        return trafficService.getCars();
    }

    @GetMapping("/traffic/traffic-lights")
    public List<TrafficLight> getTrafficLights() {
        trafficService.updateTrafficLights(); // Cambiar estados de semáforos
        return trafficService.getTrafficLights();
    }
}
