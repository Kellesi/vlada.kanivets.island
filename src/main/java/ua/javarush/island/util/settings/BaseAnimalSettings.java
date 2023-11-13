package ua.javarush.island.util.settings;

import java.util.Map;

public class BaseAnimalSettings {
    private final Map settingsMap;

    public BaseAnimalSettings(Map settingsMap) {
        this.settingsMap = settingsMap;
    }

    public String getClassIcon() {
        return (String) settingsMap.get("icon");
    }

    public double getClassWeight() {
        return (double) settingsMap.get("defaultWeight");
    }

    public double getClassFoodNeeded() {
        return (double) settingsMap.get("foodNeeded");
    }

    public Map<String, Integer> getClassFoodPreferences() {
        return (Map<String, Integer>) settingsMap.get("foodPreferences");
    }

    public int getClassMaxPopulation() {
        return (int) settingsMap.get("maxPopulation");
    }

    public int getClassStepSize() {
        return (int) settingsMap.get("stepSize");
    }
}
