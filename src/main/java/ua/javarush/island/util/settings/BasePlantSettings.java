package ua.javarush.island.util.settings;

import java.util.Map;

public class BasePlantSettings {
    private final Map settingsMap;

    public BasePlantSettings(Map settingsMap) {
        this.settingsMap = settingsMap;
    }

    public String getClassIcon() {
        return (String) settingsMap.get("icon");
    }

    public double getClassWeight() {
        return (double) settingsMap.get("defaultWeight");
    }

    public int getClassMaxPopulation() {
        return (int) settingsMap.get("maxPopulation");
    }
}
