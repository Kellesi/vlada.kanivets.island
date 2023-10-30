package ua.javarush.island.creature.animal.predator;

import ua.javarush.island.creature.animal.Animal;
import ua.javarush.island.settings.BaseAnimalSettings;

public abstract class Predator extends Animal {
    public Predator(BaseAnimalSettings settings) {
        super(settings);
    }
}
