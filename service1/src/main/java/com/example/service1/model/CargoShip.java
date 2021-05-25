package com.example.service1.model;

public class CargoShip {
    private String name;
    private TypeOfCargo type;
    private int teu; //twenty-foot equivalent unit

    public enum TypeOfCargo {
        LOOSE(50), //0
        LIQUID(70), //1
        CONTAINER(100); //2

        private int speed;

        TypeOfCargo(int speed) {
            this.speed = speed;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }
    }

    public CargoShip(String name, TypeOfCargo type, int teu) {
        this.name = name;
        this.type = type;
        this.teu = teu;
    }

    public CargoShip() {
        InputOutput.Reader input = new InputOutput.Reader();
        this.name = input.setName();
        this.type = generateRandomTypeOfCargo();
        this.teu = (int) (500 + Math.random() * 233000);
    }

    private TypeOfCargo generateRandomTypeOfCargo() {
        int index = (int) (Math.random() * TypeOfCargo.values().length);
        return TypeOfCargo.values()[index];
    }

    public String getName() {
        return name;
    }

    public TypeOfCargo getType() {
        return type;
    }

    public int getTeu() {
        return teu;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(TypeOfCargo type) {
        this.type = type;
    }

    public void setTeu(int teu) {
        this.teu = teu;
    }

    //@Override
//    public String toString() {
//        return "Cargo ship's name - " + getName() + "\nType - " + getType();
//    }


    @Override
    public String toString() {
        return "CargoShip{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", teu=" + teu +
                '}';
    }
}
