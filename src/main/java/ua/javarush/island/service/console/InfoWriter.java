package ua.javarush.island.service.console;

public class InfoWriter {
    private static InfoWriter writer;
    private static StatisticLevel level = StatisticLevel.TOTAL;

    protected InfoWriter() {
    }

    public static InfoWriter getStatisticWriter() {
        if (writer == null) {
            writer = new InfoWriter();
        }
        return writer;
    }

    public static void setStatisticLevel(StatisticLevel statisticLevel) {
        level = statisticLevel;
    }

    public void print(String message) {
        System.out.println(message);
    }

    public void total(String message) {
        if (level.ordinal() == StatisticLevel.TOTAL.ordinal()) {
            System.out.println(message);
        }
    }

    public void area(String message) {
        if (level.ordinal() == StatisticLevel.AREA.ordinal()) {
            System.out.println(message);
        }
    }

    public enum StatisticLevel {
        TOTAL, AREA
    }
}
