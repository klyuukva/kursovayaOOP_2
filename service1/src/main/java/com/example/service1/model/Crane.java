package com.example.service1.model;

public class Crane {
    private final CargoShip.TypeOfCargo typeOfCrane;
    private boolean isFree;
    private long delay;

    public Crane(CargoShip.TypeOfCargo typeOfCrane, boolean isFree) {
        this.typeOfCrane = typeOfCrane;
        this.isFree = isFree;
        delay = (long) (Math.random() * 1441) / 60;
    }

    public CargoShip.TypeOfCargo getTypeOfCrane() {
        return typeOfCrane;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
