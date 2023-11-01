package ua.javarush.island.creature.animal.herbivore;

import ua.javarush.island.creature.Creature;
import ua.javarush.island.creature.animal.Animal;
import ua.javarush.island.creature.plant.Plant;
import ua.javarush.island.map.Area;
import ua.javarush.island.settings.BaseAnimalSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Herbivore extends Animal {

    public Herbivore(BaseAnimalSettings settings) {
        super(settings);
    }

    @Override
    public void eat(Area area) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        List<Creature> possibleVictims = new ArrayList<>(searchForFood(area));
        possibleVictims.addAll(area.getAllPlants().stream()
                .filter(Creature::isAlive)
                .toList());
        Creature victim = possibleVictims.get(rnd.nextInt(possibleVictims.size()));
        if (victim instanceof Animal animal) {
            eatOtherAnimal(animal);
        }
        if (victim instanceof Plant plant) {
            plant.getLock().lock();
            try {
                if (plant.isAlive()) {
                    plant.setAlive(false);
                    changeSatiety(plant.getSettings().getClassWeight());
                }
            } finally {
                plant.getLock().unlock();
            }
        }
    }
}
