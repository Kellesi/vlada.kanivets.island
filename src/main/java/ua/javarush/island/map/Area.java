package ua.javarush.island.map;

import ua.javarush.island.creature.animal.Animal;
import ua.javarush.island.creature.plant.Plant;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class Area {
    private final int pointX;
    private final int pointY;
    private Map<Class<? super Animal>, Set<? super Animal>> residents= Collections.EMPTY_MAP;
    private Map<Class<? super Plant>, Set<? super Plant>> plants= Collections.EMPTY_MAP;

    public Area(Map<Class<? super Animal>, Set<? super Animal>> residents,
                Map<Class<? super Plant>, Set<? super Plant>> plants,
                int pointX, int pointY) {
        this.residents = residents;
        this.plants = plants;
        this.pointX=pointX;
        this.pointY=pointY;
    }
    public Area(int pointX, int pointY) {
        this.pointX=pointX;
        this.pointY=pointY;
    }
}
