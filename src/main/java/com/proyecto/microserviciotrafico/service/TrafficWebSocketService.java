package com.proyecto.microserviciotrafico.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TrafficWebSocketService {

    private final SimpMessagingTemplate template;
    private final TrafficService trafficService;

    public TrafficWebSocketService(SimpMessagingTemplate template, TrafficService trafficService) {
        this.template = template;
        this.trafficService = trafficService;
    }

    @Scheduled(fixedRate = 2000)
    public void sendTrafficUpdates() {
        template.convertAndSend("/topic/traffic-cars", trafficService.getCars());
        template.convertAndSend("/topic/traffic-lights", trafficService.getTrafficLights());
    }
}
