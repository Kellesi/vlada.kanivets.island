package ua.javarush.island;

import ua.javarush.island.service.console.InfoWriter;
import ua.javarush.island.domain.map.Island;
import ua.javarush.island.service.console.ValuesValidator;
import ua.javarush.island.service.statistic.StatisticCollector;
import ua.javarush.island.service.taskmanager.DailyTask;
import ua.javarush.island.service.taskmanager.TaskManager;

import java.util.Scanner;

public class Runner {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        InfoWriter writer = InfoWriter.getStatisticWriter();
        ValuesValidator validator = new ValuesValidator();
        writer.print("Input island width:");
        int width = validator.validateNumberInput(scanner);
        writer.print("Input island height:");
        int height = validator.validateNumberInput(scanner);
        writer.print("Input day duration in ms:");
        long dayDuration = validator.validateNumberInput(scanner);
        writer.print("Choose statistic level: \n(TOTAL/AREA)");
        InfoWriter.StatisticLevel statLvl = validator.validateModeInput(scanner);
        InfoWriter.setStatisticLevel(statLvl);
        Island island = new Island(width, height);
        island.inhabitIsland();
        StatisticCollector sm = new StatisticCollector(island);
        writer.total("Init population: \n" + sm.getTotalAnimalPopulationByType());
        DailyTask dailyTask = new DailyTask(island, sm);
        new TaskManager(dailyTask, dayDuration).run();
    }
}
