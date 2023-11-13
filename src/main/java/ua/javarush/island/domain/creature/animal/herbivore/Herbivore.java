package ua.javarush.island.domain.creature.animal.herbivore;

import ua.javarush.island.domain.creature.Creature;
import ua.javarush.island.domain.creature.animal.Animal;
import ua.javarush.island.domain.creature.plant.Grass;
import ua.javarush.island.domain.creature.plant.Plant;
import ua.javarush.island.domain.map.Area;
import ua.javarush.island.util.settings.BaseAnimalSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Herbivore extends Animal {

    protected Herbivore(BaseAnimalSettings settings) {
        super(settings);
    }

    @Override
    public void eat(Area area) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        while (isHungry()) {
            List<Creature> possibleVictims = new ArrayList<>(searchForFood(area));
            possibleVictims.addAll(area.getAllPlants().get(Grass.class).stream()
                    .filter(Creature::isAlive)
                    .toList());
            getLock().lock();
            try {
                if (!isAlive() || possibleVictims.isEmpty()) {
                    break;
                }
                Creature victim = possibleVictims.get(rnd.nextInt(possibleVictims.size()));
                if (victim instanceof Animal animal) {
                    eatOtherAnimal(animal);
                }
                if (victim instanceof Plant plant) {
                    plant.getLock().lock();
                    if (plant.isAlive()) {
                        plant.setAlive(false);
                        changeSatiety(plant.getSettings().getClassWeight());
                    }
                    plant.getLock().unlock();
                }
            } finally {
                getLock().unlock();
            }
        }
    }
}
