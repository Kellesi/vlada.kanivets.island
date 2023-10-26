package ua.javarush.island.creature;

import ua.javarush.island.creature.plant.Grass;
import ua.javarush.island.creature.plant.Plant;
import ua.javarush.island.settings.BasePlantSettings;
import ua.javarush.island.settings.CreatureSettings;

public enum PlantType {
    GRASS(Grass.class, new BasePlantSettings(CreatureSettings.getSettingMap().get("Grass")));
    Class<? extends Plant> clss;
    BasePlantSettings settings;

    public Class<? extends Plant> getClss() {
        return clss;
    }

    public BasePlantSettings getSettings() {
        return settings;
    }

    PlantType(Class<? extends Plant> plant, BasePlantSettings settings) {
        this.clss = plant;
        this.settings = settings;
    }
}
