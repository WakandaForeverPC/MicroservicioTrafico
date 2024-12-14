package com.proyecto.microserviciotrafico.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TrafficUpdater {

    private final TrafficService trafficService;

    public TrafficUpdater(TrafficService trafficService) {
        this.trafficService = trafficService;
    }

    @Scheduled(fixedRate = 2000)
    public void updateTraffic() {
        trafficService.moveCars();
        trafficService.updateTrafficLights();
    }
}
