package ua.javarush.island.domain.creature;

import ua.javarush.island.domain.creature.plant.Grass;
import ua.javarush.island.domain.creature.plant.Plant;
import ua.javarush.island.util.settings.BasePlantSettings;
import ua.javarush.island.util.settings.CreatureSettingsLoader;

public enum PlantType {
    GRASS(Grass.class, new BasePlantSettings(CreatureSettingsLoader.getSettingMap().get("Grass")));

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
