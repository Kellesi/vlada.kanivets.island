package ua.javarush.island.creature.abilities;

import ua.javarush.island.map.Area;

import java.util.Optional;

public interface CanBreed {
    <T>Optional<T> breed(Area area);
}

