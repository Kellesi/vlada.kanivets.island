package ua.javarush.island.service.console;

import java.util.Scanner;

public class ValuesValidator {
    public InfoWriter.StatisticLevel validateModeInput(Scanner scanner) {
        boolean correct = false;
        String lvl = "";
        while (!correct) {
            lvl = scanner.next().toUpperCase();
            try {
                InfoWriter.StatisticLevel.valueOf(lvl);
            } catch (IllegalArgumentException ex) {
                System.out.println("Wrong statistic mode. Please, try again:");
                continue;
            }
            correct = true;
        }
        return InfoWriter.StatisticLevel.valueOf(lvl);
    }

    public int validateNumberInput(Scanner scanner) {
        boolean correct = false;
        String line;
        int number = 0;
        while (!correct) {
            line = scanner.nextLine();
            try {
                number = Integer.parseInt(line);
                if (number <= 0) {
                    throw new NumberFormatException("Number should be >0.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Wrong input." + ex.getMessage() + " Please, try again:");
                continue;
            }
            correct = true;
        }
        return number;
    }
}
