package ua.javarush.island.creature.plant;

import ua.javarush.island.settings.BasePlantSettings;

public class Grass extends Plant {
    private static int counter;

    public Grass(BasePlantSettings settings) {
        super(settings);
        this.name = name + (++counter);
    }

    @Override
    public String getName() {
        return name;
    }
}
