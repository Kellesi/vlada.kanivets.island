package ua.javarush.island.creature;

import ua.javarush.island.creature.plant.Grass;
import ua.javarush.island.creature.plant.Plant;

public enum PlantType {
    GRASS(Grass.class);
    Class<? extends Plant> clss;

    public Class<? extends Plant> getClss() {
        return clss;
    }

    PlantType(Class<? extends Plant> plant) {
        this.clss = plant;
    }
}
