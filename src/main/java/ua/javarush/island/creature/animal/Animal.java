package ua.javarush.island.creature.animal;

import ua.javarush.island.creature.abilities.Breedable;
import ua.javarush.island.creature.abilities.Movable;

public abstract class Animal implements Movable, Breedable {
    protected static long id;
    protected int currentWeight;
    protected String name;
    protected boolean isHungry=true;

    public abstract String getName();
}
