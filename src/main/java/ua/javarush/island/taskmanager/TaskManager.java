package ua.javarush.island.taskmanager;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskManager extends Thread {
    private final Queue<Callable<Void>> tasks;
    private static final long DAY_DURATION = 5000;

    public TaskManager(Queue<Callable<Void>> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(runDay(tasks), 0, DAY_DURATION, TimeUnit.MILLISECONDS);
    }

    private Runnable runDay(Queue<Callable<Void>> tasks) {
        return () -> {
            ExecutorService executorService = Executors.newWorkStealingPool();
            try {
                executorService.invokeAll(tasks);
                executorService.shutdown();
                if (!executorService.awaitTermination(DAY_DURATION, TimeUnit.MILLISECONDS)) {
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

    private void printStatistic() {
        System.out.println("*****");
        System.out.println("print stats");
        System.out.println("*****");
    }
}
