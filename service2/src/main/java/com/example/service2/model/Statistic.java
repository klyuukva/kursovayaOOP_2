package com.example.service2.model;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

public class Statistic {
    private List<List<Statistic.CargoShipStatistic>> statistics;
    private Statistic.Total totalStatistic;

    public Statistic(List<List<CargoShipStatistic>> statistics, Total totalStatistic) {
        this.statistics = statistics;
        this.totalStatistic = totalStatistic;
    }

    public List<List<CargoShipStatistic>> getStatistics() {
        return statistics;
    }

    public Total getTotalStatistic() {
        return totalStatistic;
    }

    public static class CargoShipStatistic {
        private String name;
        private GregorianCalendar arrivalTime;
        private long waitingTime;
        private long unloadingStartTime;
        private long unloadingDuration;

        public CargoShipStatistic(String name, GregorianCalendar arrivalTime, long waitingTime, long unloadingStartTime,
                                  long unloadingDuration) {
            this.name = name;
            this.arrivalTime = arrivalTime;
            this.waitingTime = waitingTime;
            this.unloadingStartTime = unloadingStartTime;
            this.unloadingDuration = unloadingDuration;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public GregorianCalendar getArrivalTime() {
            return arrivalTime;
        }

        public void setArrivalTime(GregorianCalendar arrivalTime) {
            this.arrivalTime = arrivalTime;
        }

        public long getWaitingTime() {
            return waitingTime;
        }

        public void setWaitingTime(long waitingTime) {
            this.waitingTime = waitingTime;
        }

        public long getUnloadingStartTime() {
            return unloadingStartTime;
        }

        public void setUnloadingStartTime(long unloadingStartTime) {
            this.unloadingStartTime = unloadingStartTime;
        }

        public long getUnloadingDuration() {
            return unloadingDuration;
        }

        public void setUnloadingDuration(long unloadingDuration) {
            this.unloadingDuration = unloadingDuration;
        }

        @Override
        public String toString() {
            return "CargoShipStatistic{" +
                    "name='" + name + '\'' +
                    ", arrivalTime=" + arrivalTime.getTimeInMillis() +
                    ", waitingTime=" + waitingTime +
                    ", unloadingStartTime=" + unloadingStartTime +
                    ", unloadingDuration=" + unloadingDuration +
                    '}';
        }
    }

    public static class Total {
        private int unloadedCargoShips;
        private long averageWaitingTime;
        private long maxUnloadDelay;
        private long averageUnloadDelay;
        private long penalty;
        private int[] countOfCargoShips;

        private int[] penaltyForTypes;

        public Total() {
            this.countOfCargoShips = new int[3];
            this.penaltyForTypes = new int[3];
        }

        public int[] getPenaltyForTypes() {
            return penaltyForTypes;
        }

        public void setPenaltyForTypes(int type, int value) {
            this.penaltyForTypes[type] = value;
        }

        public int getUnloadedCargoShips() {
            return unloadedCargoShips;
        }

        public void setUnloadedCargoShips(int unloadedCargoShips) {
            this.unloadedCargoShips = unloadedCargoShips;
        }

        public long getAverageWaitingTime() {
            return averageWaitingTime;
        }

        public void setAverageWaitingTime(long averageWaitingTime) {
            this.averageWaitingTime = averageWaitingTime;
        }

        public long getMaxUnloadDelay() {
            return maxUnloadDelay;
        }

        public void setMaxUnloadDelay(long maxUnloadDelay) {
            this.maxUnloadDelay = maxUnloadDelay;
        }

        public long getAverageUnloadDelay() {
            return averageUnloadDelay;
        }

        public void setAverageUnloadDelay(long averageUnloadDelay) {
            this.averageUnloadDelay = averageUnloadDelay;
        }

        public long getPenalty() {
            return penalty;
        }

        public void setPenalty(long penalty) {
            this.penalty = penalty;
        }

        public int[] getCountOfCargoShips() {
            return countOfCargoShips;
        }

        public void setCountOfCargoShips(int type, int value) {
            this.countOfCargoShips[type] = value;
        }

        @Override
        public String toString() {
            return "Total{" +
                    "unloadedCargoShips=" + unloadedCargoShips +
                    ", averageWaitingTime=" + averageWaitingTime +
                    ", maxUnloadDelay=" + maxUnloadDelay +
                    ", averageUnloadDelay=" + averageUnloadDelay +
                    ", penalty=" + penalty +
                    ", countOfCargoShips=" + Arrays.toString(countOfCargoShips) +
                    ", penaltyForTypes=" + Arrays.toString(penaltyForTypes) +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "statistics=" + statistics +
                ", totalStatistic=" + totalStatistic +
                '}';
    }
}
