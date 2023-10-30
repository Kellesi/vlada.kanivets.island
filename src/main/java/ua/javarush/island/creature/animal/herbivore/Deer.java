package ua.javarush.island.creature.animal.herbivore;

import ua.javarush.island.settings.BaseAnimalSettings;

public class Deer extends Herbivore {
    private static int counter;

    public Deer(BaseAnimalSettings settings) {
        super(settings);
        this.name = name + (++counter);
    }

    @Override
    public String getName() {
        return name;
    }
}
