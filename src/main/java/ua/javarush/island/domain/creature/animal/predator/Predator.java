package ua.javarush.island.domain.creature.animal.predator;

import ua.javarush.island.domain.creature.animal.Animal;
import ua.javarush.island.util.settings.BaseAnimalSettings;

public abstract class Predator extends Animal {
    protected Predator(BaseAnimalSettings settings) {
        super(settings);
    }
}
