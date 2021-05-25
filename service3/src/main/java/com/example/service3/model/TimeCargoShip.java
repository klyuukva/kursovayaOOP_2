package com.example.service3.model;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeCargoShip {

    private ArrivalTime arrivalTime;
    private DepartureTime departureTime;

    public TimeCargoShip(CargoShip cargoShip)
    {
        arrivalTime = new ArrivalTime();
        departureTime = new DepartureTime(cargoShip);
    }

    public class ArrivalTime {
        GregorianCalendar arrivalTime = new GregorianCalendar();
        private final int MONTH = 30;

        public ArrivalTime() {
            Calendar fistDate = new GregorianCalendar();
            arrivalTime.set(Calendar.DAY_OF_MONTH, (int) (fistDate.get(Calendar.DAY_OF_MONTH) + Math.random() * MONTH));
            arrivalTime.set(Calendar.HOUR_OF_DAY, (int) (Math.random() * 24));
            arrivalTime.set(Calendar.MINUTE, (int) (Math.random() * 60));
            arrivalTime.set(Calendar.SECOND, (int) (Math.random() * 60));
        }

        public GregorianCalendar getArrivalTime() {
            return arrivalTime;
        }

        public long toLong() {
            return arrivalTime.getTimeInMillis();
        }
    }

    public ArrivalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(ArrivalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public class DepartureTime {
        GregorianCalendar departureTime;

        public DepartureTime(CargoShip cargoShip) {
            this.departureTime = new GregorianCalendar();
            Date temp = new Date();
            temp.setTime(cargoShip.getTeu() / cargoShip.getType().getSpeed() + (cargoShip.getTeu() % cargoShip.getType().getSpeed() == 0 ? 0 : 1));
            departureTime.setTime(temp);
        }

        public GregorianCalendar getDepartureTime() {
            return departureTime;
        }

        public long toLong() {
            return departureTime.getTimeInMillis();
        }
    }

    public DepartureTime getDepartureTime() {
        return departureTime;
    }

    @Override
    public String toString() {
        return "TimeCargoShip{" +
                "arrivalTime=" + arrivalTime.getArrivalTime() +
                ", departureTime=" + departureTime.getDepartureTime() +
                '}';
    }
}