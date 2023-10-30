package ua.javarush.island.creature;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Creature {
    protected String name;
    private boolean isAlive = true;
    private String type = this.getClass().getSimpleName();
}
