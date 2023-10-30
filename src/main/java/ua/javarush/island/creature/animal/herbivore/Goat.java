package ua.javarush.island.creature.animal.herbivore;

import ua.javarush.island.settings.BaseAnimalSettings;

public class Goat extends Herbivore {
    private static int counter;

    public Goat(BaseAnimalSettings settings) {
        super(settings);
        this.name = name + (++counter);
    }

    @Override
    public String getName() {
        return name;
    }
}
