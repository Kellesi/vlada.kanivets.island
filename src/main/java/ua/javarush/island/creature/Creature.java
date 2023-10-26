package ua.javarush.island.creature;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Creature {
    protected String name;
    private String icon;
    private boolean isAlive = true;
    private double defaultWeight;
    private int maxPopulation;
    private String type = this.getClass().getSimpleName();
}
