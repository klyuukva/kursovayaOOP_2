package com.example.service2.model;

import java.util.*;

public class Schedule {
    public class LineOfSchedule {
        private CargoShip cargoShip;
        private TimeCargoShip timeCargoShip;

        public LineOfSchedule() {
            this.cargoShip = new CargoShip();
            this.timeCargoShip = new TimeCargoShip(cargoShip);
        }

        public CargoShip getCargoShip() {
            return cargoShip;
        }

        public TimeCargoShip getTimeCargoShip() {
            return timeCargoShip;
        }

        @Override
        public String toString() {
            return "LineOfSchedule{" +
                    "cargoShip=" + cargoShip +
                    ", timeCargoShip=" + timeCargoShip +
                    '}';
        }
    }

    private List<LineOfSchedule> scheduleList = new ArrayList<>();

    public Schedule(int numberOfLine) {
        for (int i = 0; i < numberOfLine; i++) {
            this.scheduleList.add(new LineOfSchedule());
        }
    }

    public List<LineOfSchedule> getScheduleList() {
        return scheduleList;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleList=" + scheduleList +
                '}';
    }

// 12:00 1 апр
    // 13:00 1 апр
    // 14:00 1 мар
}
