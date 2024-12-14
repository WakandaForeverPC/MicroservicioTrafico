package com.proyecto.microserviciotrafico.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trafico")
public class TraficoController {

    @GetMapping
    public String obtenerTrafico() {
        // Lógica para obtener información de tráfico
        return "Información de tráfico";
    }

    @PostMapping
    public String crearTrafico(@RequestBody String nuevoTrafico) {
        // Lógica para crear nueva información de tráfico
        return "Nuevo tráfico creado";
    }
}