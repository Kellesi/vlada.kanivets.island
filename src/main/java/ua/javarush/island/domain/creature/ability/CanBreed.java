package ua.javarush.island.domain.creature.ability;

import ua.javarush.island.domain.creature.Creature;
import ua.javarush.island.domain.map.Area;

import java.util.Optional;

public interface CanBreed {
    <T extends Creature> Optional<T> breed(Area area);
}
