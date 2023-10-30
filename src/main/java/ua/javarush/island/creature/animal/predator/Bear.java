package ua.javarush.island.creature.animal.predator;

import ua.javarush.island.settings.BaseAnimalSettings;

public class Bear extends Predator {
    private static int counter;

    public Bear(BaseAnimalSettings settings) {
        super(settings);
        this.name = name + (++counter);
    }

    @Override
    public String getName() {
        return name;
    }
}
