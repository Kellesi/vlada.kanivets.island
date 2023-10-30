package ua.javarush.island.creature.animal.predator;

import ua.javarush.island.settings.BaseAnimalSettings;

public class Wolf extends Predator {
    private static int counter;

    public Wolf(BaseAnimalSettings settings) {
        super(settings);
        this.name = name + (++counter);
    }

    @Override
    public String getName() {
        return name;
    }
}
