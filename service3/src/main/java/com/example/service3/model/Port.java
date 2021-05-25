package com.example.service3.model;

import java.util.*;
import java.util.concurrent.*;

public class Port {

    public static final int HOUR = 3600000;
    public static final int PENALTY_PER_HOUR = 100;
    public static final int CRANE_PRICE = 30000;

    private static class UnloadingShips implements Runnable {
        private final Crane crane;
        private final Schedule.LineOfSchedule ship;
        private final Statistic.Total totalStatistic;

        public UnloadingShips(Crane crane, Schedule.LineOfSchedule ship, Statistic.Total totalStatistic) {
            this.crane = crane;
            this.ship = ship;
            this.totalStatistic = totalStatistic;
        }

        @Override
        public void run() {
            if (crane.getDelay() != 0) {
                crane.setDelay(crane.getDelay() - 1);
                return;
            }
            synchronized (ship) {
                ship.getCargoShip().setTeu(Math.max(ship.getCargoShip().getTeu() - crane.getTypeOfCrane().getSpeed(), 0));
                if (ship.getCargoShip().getTeu() == 0) {
                    crane.setDelay((long) (Math.random() * 1441) / 60);
                    synchronized (totalStatistic) {
                        totalStatistic.setAverageUnloadDelay(totalStatistic.getAverageUnloadDelay() + crane.getDelay());
                        totalStatistic.setMaxUnloadDelay(Math.max(totalStatistic.getMaxUnloadDelay(), crane.getDelay()));
                    }
                }
            }
        }
    }

    public static Statistic dataProcessing(Schedule schedule, int[] cranesCount) {
        List<Queue<Crane>> cranes = new ArrayList<>(3);
        List<List<Schedule.LineOfSchedule>> cargoShips = new ArrayList<>(3);
        List<List<Statistic.CargoShipStatistic>> statistics = new ArrayList<>(3);
        Statistic.Total totalStatistic = new Statistic.Total();
        for (int i = 0; i < 3; i++) {
            cargoShips.add(new ArrayList<>());
            statistics.add(new ArrayList<>());
            cranes.add(new LinkedList<>());
            for (int j = 0; j < cranesCount[i]; j++) {
                Crane crane = new Crane(CargoShip.TypeOfCargo.values()[i], true);
                cranes.get(i).add(crane);
                totalStatistic.setAverageUnloadDelay(totalStatistic.getAverageUnloadDelay() + crane.getDelay());
                totalStatistic.setMaxUnloadDelay(Math.max(totalStatistic.getMaxUnloadDelay(), crane.getDelay()));
            }
        }

        List<Schedule.LineOfSchedule> list = schedule.getScheduleList();
        long starterTime = Long.MAX_VALUE;
        long endTime = Long.MIN_VALUE;
        for (Schedule.LineOfSchedule elementOfSchedule : list) {
            elementOfSchedule.getTimeCargoShip().getArrivalTime().arrivalTime
                    .set(Calendar.DAY_OF_MONTH, (int) (-7 + Math.random() * 8));

            starterTime = Math.min(elementOfSchedule.getTimeCargoShip().getArrivalTime().toLong(), starterTime);
            endTime = Math.max(elementOfSchedule.getTimeCargoShip().getArrivalTime().toLong() +
                    elementOfSchedule.getTimeCargoShip().getDepartureTime().toLong(), endTime);

            if (elementOfSchedule.getCargoShip().getType() == CargoShip.TypeOfCargo.LOOSE) {
                cargoShips.get(0).add(elementOfSchedule);
                statistics.get(0).add(new Statistic.CargoShipStatistic(elementOfSchedule.getCargoShip().getName(),
                        elementOfSchedule.getTimeCargoShip().getArrivalTime().arrivalTime,
                        0, 0, 0));
            }

            if (elementOfSchedule.getCargoShip().getType() == CargoShip.TypeOfCargo.LIQUID) {
                cargoShips.get(1).add(elementOfSchedule);
                statistics.get(1).add(new Statistic.CargoShipStatistic(elementOfSchedule.getCargoShip().getName(),
                        elementOfSchedule.getTimeCargoShip().getArrivalTime().arrivalTime,
                        0, 0, 0));
            }

            if (elementOfSchedule.getCargoShip().getType() == CargoShip.TypeOfCargo.CONTAINER) {
                cargoShips.get(2).add(elementOfSchedule);
                statistics.get(2).add(new Statistic.CargoShipStatistic(elementOfSchedule.getCargoShip().getName(),
                        elementOfSchedule.getTimeCargoShip().getArrivalTime().getArrivalTime(),
                        0, 0, 0));
            }
        }


        for (int i = 0; i < 3; i++) {
            cargoShips.get(i).sort(Comparator.comparingLong(a -> a.getTimeCargoShip().getArrivalTime().toLong()));
        }

        unloading(cranes, cargoShips, statistics, totalStatistic, starterTime, endTime);

        int[] requiredCargoShips = totalStatistic.getPenaltyForTypes();
        for (int i = 0; i < 3; i++) {
            totalStatistic.setCountOfCargoShips(i, requiredCargoShips[i] / CRANE_PRICE);
        }

        return new Statistic(statistics, totalStatistic);
    }

    private static void unloading(List<Queue<Crane>> cranes, List<List<Schedule.LineOfSchedule>> cargoShips,
                                  List<List<Statistic.CargoShipStatistic>> statistics, Statistic.Total totalStatistic,
                                  long starterTime, long endTime) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Queue<Crane> busyCranes = new LinkedList<>();
        for (long time = starterTime; time < endTime; time += HOUR) {
            for (int i = 0; i < cargoShips.size(); i++) {
                for (int j = 0; j < cargoShips.get(i).size(); j++) {
                    Statistic.CargoShipStatistic statistic = statistics.get(i).get(j);
                    Schedule.LineOfSchedule ship = cargoShips.get(i).get(j);
                    if (ship.getTimeCargoShip().getArrivalTime().toLong() < time) {
                        continue;
                    }
                    if (ship.getCargoShip().getTeu() == 0) {
                        if (statistic.getUnloadingDuration() == 0) {
                            statistic.setUnloadingDuration(time - statistic.getUnloadingStartTime());
                        }
                        continue;
                    }

                    Crane crane1 = null;
                    Crane crane2 = null;
                    if (!cranes.get(i).isEmpty()) {
                        if (statistic.getUnloadingStartTime() == 0) {
                            statistic.setUnloadingStartTime(time);
                        }
                        crane1 = cranes.get(i).poll();
                        executor.execute(new UnloadingShips(crane1, ship, totalStatistic));
                        if (!cranes.get(i).isEmpty()) {
                            crane2 = cranes.get(i).poll();
                            executor.execute(new UnloadingShips(crane2, ship, totalStatistic));
                        }
                    } else {
                        statistic.setWaitingTime(statistic.getWaitingTime() + HOUR);
                    }

                    if (crane1 != null) {
                        busyCranes.add(crane1);
                    }
                    if (crane2 != null) {
                        busyCranes.add(crane2);
                    }
                }

                for (Crane crane : busyCranes) {
                    cranes.get(i).add(crane);
                }
                busyCranes.clear();
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int unloadedCargoShips = 0;
        for (List<Schedule.LineOfSchedule> cargoShip : cargoShips) {
            unloadedCargoShips += cargoShip.size();
        }
        totalStatistic.setUnloadedCargoShips(unloadedCargoShips);
        for (int i = 0; i < cargoShips.size(); i++) {
            for (Statistic.CargoShipStatistic statistic : statistics.get(i)) {
                totalStatistic.setAverageWaitingTime(totalStatistic.getAverageWaitingTime() + statistic.getWaitingTime());
                totalStatistic.setPenalty(totalStatistic.getPenalty() + statistic.getWaitingTime() / HOUR * PENALTY_PER_HOUR);
                totalStatistic.setPenaltyForTypes(i,
                        (int) (totalStatistic.getPenaltyForTypes()[i] + statistic.getWaitingTime() / HOUR * PENALTY_PER_HOUR));
            }
        }
        totalStatistic.setAverageUnloadDelay(totalStatistic.getAverageUnloadDelay() / totalStatistic.getUnloadedCargoShips());
        totalStatistic.setAverageWaitingTime(totalStatistic.getAverageWaitingTime() / totalStatistic.getUnloadedCargoShips());
    }
}
