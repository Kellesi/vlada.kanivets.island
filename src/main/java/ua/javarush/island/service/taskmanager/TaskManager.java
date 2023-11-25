package ua.javarush.island.service.taskmanager;

import ua.javarush.island.service.console.InfoWriter;
import ua.javarush.island.service.statistic.StatisticCollector;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskManager {
    private final DailyTask dailyTask;
    private final StatisticCollector statistic;
    private final long dayDuration;

    public TaskManager(DailyTask tasks, long dayDuration) {
        this.dailyTask = tasks;
        this.statistic = dailyTask.getStatisticManager();
        this.dayDuration = dayDuration;
    }

    public void run() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(runDay(), 0, dayDuration, TimeUnit.MILLISECONDS);
    }

    private Runnable runDay() {
        return () -> {
            ExecutorService executorService = Executors.newWorkStealingPool();
            try {
                Queue<Callable<Void>> eatingTasks = dailyTask.getEatingTasks();
                Queue<Callable<Void>> breedingTasks = dailyTask.getBreedingTasks();
                Queue<Callable<Void>> movingTasks = dailyTask.getMovingTasks();
                Queue<Callable<Void>> newDayTasks = dailyTask.getBeforeNewDayTasks();

                List<Future<Void>> eatingProcess = executorService.invokeAll(eatingTasks);
                awaitCompletion(eatingProcess);
                List<Future<Void>> breedingProcess = executorService.invokeAll(breedingTasks);
                awaitCompletion(breedingProcess);
                List<Future<Void>> movingProcess = executorService.invokeAll(movingTasks);
                awaitCompletion(movingProcess);
                executorService.invokeAll(newDayTasks);
                executorService.shutdown();
                if (!executorService.awaitTermination(dayDuration, TimeUnit.MILLISECONDS)) {
                    executorService.shutdownNow();
                }
                if (executorService.isShutdown()) {
                    printStatistic();
                }
            } catch (InterruptedException e) {
                printStatistic();
            }
        };
    }

    private void awaitCompletion(List<Future<Void>> futures) {
        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    private void printStatistic() {
        InfoWriter writer = InfoWriter.getStatisticWriter();
        writer.print("*****");
        writer.total("Day №" + statistic.getDayAndIncrement());
        writer.total(statistic.getTotalAnimalPopulationByType().toString());
        writer.total("Total population " + statistic.getTotalAnimalPopulation());
        writer.total("Total plant number " + statistic.getTotalPlantsNumber());
        writer.total("Newborn " + statistic.getAmountOfNewbornAnimals());
        writer.total("Died animals " + statistic.getAmountOfDiedAnimals());
        writer.total("Eaten plants " + statistic.getAmountOfEatenPlants());
        for (Map.Entry<String, Map<String, Integer>> areaToPopulation : statistic.getAreaAnimalPopulationByType().entrySet()) {
            writer.area(areaToPopulation.getKey() + " " + areaToPopulation.getValue());
        }
        statistic.reset();
    }
}
