package com.proyecto.microserviciotrafico.model;

public class TrafficLight {
    private String id;
    private int x;
    private int y;
    private String state; // "RED", "GREEN"

    public TrafficLight(String id, int x, int y, String state) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.state = state;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}