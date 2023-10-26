package ua.javarush.island.creature.animal.herbivore;

import ua.javarush.island.creature.animal.Animal;
import ua.javarush.island.creature.plant.Plant;
import ua.javarush.island.map.Area;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Herbivore extends Animal {
    private static final int CHANCE_TO_CHOOSE_A_PLANT = 70;

    @Override
    public boolean eat(Area area) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        Optional<String> victimType = chooseVictimType();
        if (victimType.get().equals("Grass")){
            List<Plant> possibleVictims =
                    area.getPlants(victimType.get()).stream()
                            .map(Plant.class::cast)
                            .filter(Plant::isAlive)
                            .toList();
            Plant plant = possibleVictims.get(rnd.nextInt(possibleVictims.size()));
            plant.setAlive(false);
            changeSatiety(plant.getDefaultWeight());
            System.out.println(plant.getName());
            return true;
        }
        return eatOtherAnimal(area, victimType.get());
    }

    @Override
    protected Optional<String> chooseVictimType() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        if (super.chooseVictimType().isPresent() && rnd.nextInt(100) > CHANCE_TO_CHOOSE_A_PLANT) {
            return super.chooseVictimType();
        } else {
            return Optional.of("Grass");
        }
    }
}
