package ua.javarush.island.creature.animal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ua.javarush.island.creature.Creature;
import ua.javarush.island.creature.abilities.CanBreed;
import ua.javarush.island.creature.abilities.CanEat;
import ua.javarush.island.creature.abilities.CanMove;
import ua.javarush.island.map.Area;
import ua.javarush.island.worldgenerator.CreatureFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
public abstract class Animal extends Creature implements CanMove, CanBreed, CanEat {
    @JsonIgnore

    private double currentWeight;
    private double foodNeeded;
    private double currentSatiety;
    private Map<String, Integer> foodPreferences;
    private boolean isHungry = true;
    private boolean isReadyForBreeding = true;
    private int stepSize;
    private static final double DELTA_WEIGHT =0.2;

    @Override
    public <T> Optional<T> breed(Area area) {
        if (!this.isReadyForBreeding) {
            return Optional.empty();
        }
        List<Animal> sameTypeResidents = area.getResidents(this.getType());
        if (sameTypeResidents.size() < this.getMaxPopulation()) {
            Optional<Animal> optionalAnimal = sameTypeResidents.stream()
                    .filter(Animal::isReadyForBreeding)
                    .filter(animal -> !animal.equals(this))
                    .findFirst();
            if (optionalAnimal.isPresent()) {
                Animal animal = optionalAnimal.get();
                animal.setReadyForBreeding(false);
                this.setReadyForBreeding(false);
                Animal animal1 = CreatureFactory.getAnimal(this.getClass());
                System.out.println(animal.getName() + " and " + this.getName() + " gave birth to " + animal1.getName());
                return (Optional<T>) Optional.of(animal1);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean eat(Area area) {
        Optional<String> victimType = chooseVictimType();
        return eatOtherAnimal(area, victimType.get());
    }

    protected boolean eatOtherAnimal(Area area, String victimType) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        List<Animal> possibleVictims =
                area.getResidents(victimType).stream()
                        .map(Animal.class::cast)
                        .filter(Animal::isAlive)
                        .toList();
        Animal animal = possibleVictims.get(rnd.nextInt(possibleVictims.size()));
        System.out.println(animal.getName());
        if (tryToEat(animal)) {
            System.out.println(this.getName() + " has eaten " + animal.getName());
            animal.setAlive(false);
            changeSatiety(animal.getCurrentWeight());
            return true;
        } else {
            System.out.println(animal.getName() + " escaped");
        }
        return false;
    }


    protected void changeSatiety(double foodWeight) {
        currentSatiety += foodWeight;
        setCurrentWeight(foodWeight * DELTA_WEIGHT);
        if (currentSatiety >= foodNeeded) {
            setHungry(false);
        }
    }

    private boolean tryToEat(Animal animal) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int luckyChance = rnd.nextInt(101);
        System.out.println(luckyChance);
        return luckyChance <= foodPreferences.get(animal.getType());
    }

    protected Optional<String> chooseVictimType() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        List<String> foodTypes = foodPreferences.entrySet().stream()
                .filter(entry -> entry.getValue() != 0)
                .map(Map.Entry::getKey)
                .toList();
        if (foodTypes.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(foodTypes.get(rnd.nextInt(foodTypes.size())));
    }

    public abstract String getName();

    public boolean isReadyForBreeding() {
        return isReadyForBreeding;
    }

    public void setReadyForBreeding(boolean readiness) {
        isReadyForBreeding = readiness;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", icon='" + getIcon() + '\'' +
                ", defaultWeight=" + getDefaultWeight() +
                ", currentWeight=" + getCurrentWeight() +
                ", foodNeeded=" + foodNeeded +
                ", foodPreferences=" + foodPreferences +
                ", isHungry=" + isHungry +
                ", isReadyForBreeding=" + isReadyForBreeding +
                ", maxPopulation=" + getMaxPopulation() +
                ", stepSize=" + stepSize +
                ", type='" + getType() + '\'' +
                '}';
    }
}
