package ua.javarush.island.map;

import ua.javarush.island.creature.AnimalType;
import ua.javarush.island.creature.PlantType;
import ua.javarush.island.worldgenerator.CreatureFactory;

public class Island {
    private final int width;
    private final int height;
    private final Area[][] areas;

    public Island(int width, int height) {
        this.width = width;
        this.height = height;
        areas = new Area[width][height];
    }

    public void inhabitIsland() {
        for (int i = 0; i < areas.length; i++) {
            for (int j = 0; j < areas[i].length; j++) {
                areas[i][j] = new Area(CreatureFactory.getAnimals(AnimalType.values()),
                        CreatureFactory.getPlants(PlantType.values()), i, j);
            }
        }
    }

    public Area[][] getAreas() {
        return areas;
    }
}
