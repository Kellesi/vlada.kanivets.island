package ua.javarush.island.creature.animal.predator;

import ua.javarush.island.settings.BaseAnimalSettings;

public class Boa extends Predator {
    private static int counter;

    public Boa(BaseAnimalSettings settings) {
        super(settings);
        this.name = name + (++counter);
    }

    @Override
    public String getName() {
        return name;
    }
}
