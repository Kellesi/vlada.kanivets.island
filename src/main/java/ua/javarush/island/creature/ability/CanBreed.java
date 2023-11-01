package ua.javarush.island.creature.ability;

import ua.javarush.island.creature.Creature;
import ua.javarush.island.creature.animal.Animal;
import ua.javarush.island.map.Area;

import java.util.Optional;

public interface CanBreed {
    <T extends Creature> Optional<T> breed(Area area);
}

