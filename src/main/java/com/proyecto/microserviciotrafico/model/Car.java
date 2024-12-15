package com.proyecto.microserviciotrafico.model;

public class Car {
    private String id;
    private int x;
    private int y;
    private String direction;
    private String color;

    public Car(String id, int x, int y, String direction, String color) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
    public String getColor(){
        return color;
    }
    public void setColor(String color){
        this.color = color;
    }
}