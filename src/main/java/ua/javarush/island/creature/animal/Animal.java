package ua.javarush.island.creature.animal;

import lombok.Getter;
import lombok.Setter;
import ua.javarush.island.creature.Creature;
import ua.javarush.island.creature.ability.CanBreed;
import ua.javarush.island.creature.ability.CanEat;
import ua.javarush.island.creature.ability.CanMove;
import ua.javarush.island.map.Area;
import ua.javarush.island.map.Direction;
import ua.javarush.island.map.Island;
import ua.javarush.island.settings.BaseAnimalSettings;
import ua.javarush.island.worldgenerator.CreatureFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
public abstract class Animal extends Creature implements CanMove, CanBreed, CanEat {
    private static final double DELTA_WEIGHT = 0.2;

    private final BaseAnimalSettings settings;
    private double currentWeight;
    private double currentSatiety;
    private boolean isHungry = true;
    private boolean isMoved = false;
    private boolean isReadyForBreeding = true;

    protected Animal(BaseAnimalSettings settings) {
        this.settings = settings;
        currentWeight = settings.getClassWeight();
        this.name = settings.getClassIcon() + getType();
    }

    @Override
    public void eat(Area area) {
        getLock().lock();
        try {
            List<Creature> possibleVictims = searchForFood(area);
            ThreadLocalRandom rnd = ThreadLocalRandom.current();
            if (!possibleVictims.isEmpty()) {
                Animal animal = (Animal) possibleVictims.get(rnd.nextInt(possibleVictims.size()));
                eatOtherAnimal(animal);
            }
        } finally {
            getLock().unlock();
        }
    }

    @Override
    public <T extends Creature> Optional<T> breed(Area area) {
        if (!this.isReadyForBreeding) {
            return Optional.empty();
        }
        getLock().lock();
        try {
            List<Animal> sameTypeResidents = area.getResidents(this.getType());
            if (sameTypeResidents.size() < settings.getClassMaxPopulation()) {
                Optional<Animal> optionalAnimal = sameTypeResidents.stream()
                        .filter(Animal::isReadyForBreeding)
                        .filter(animal -> !animal.equals(this))
                        .findFirst();
                if (optionalAnimal.isPresent()) {
                    Animal animal = optionalAnimal.get();
                    animal.getLock().lock();
                    try {
                        if (animal.isReadyForBreeding) {
                            animal.setReadyForBreeding(false);
                            this.setReadyForBreeding(false);
                            Animal animal1 = CreatureFactory.getAnimal(this.getClass(), settings);
                            return (Optional<T>) Optional.of(animal1);
                        }
                    } finally {
                        animal.getLock().unlock();
                    }
                }
            }
            return Optional.empty();
        } finally {
            getLock().unlock();
        }
    }

    @Override
    public boolean move(Area area) {
        if (isMoved) {
            return false;
        }
        int stepSize = settings.getClassStepSize();
        Area newArea = area;
        while (stepSize != 0) {
            newArea = getNextArea(newArea);
            stepSize -= 1;
        }
        if (area.equals(newArea) || newArea.getResidents(this.getType()).size() == settings.getClassMaxPopulation()) {
            isMoved = true;
            return false;
        }
        newArea.addResident(this);
        isMoved = true;
        return true;
    }

    public boolean isReadyForBreeding() {
        return isReadyForBreeding;
    }

    public void setReadyForBreeding(boolean readiness) {
        isReadyForBreeding = readiness;
    }

    protected void eatOtherAnimal(Animal animal) {
        animal.getLock().lock();
        try {
            if (tryToEat(animal)) {
                animal.setAlive(false);
                changeSatiety(animal.getCurrentWeight());
            }
        } finally {
            animal.getLock().unlock();
        }
    }

    protected void changeSatiety(double foodWeight) {
        currentSatiety += foodWeight;
        setCurrentWeight(foodWeight * DELTA_WEIGHT);
        if (currentSatiety >= settings.getClassFoodNeeded()) {
            setHungry(false);
        }
    }

    protected List<Creature> searchForFood(Area area) {
        List<Creature> possibleVictims = new ArrayList<>();
        List<String> foodTypes = settings.getClassFoodPreferences().entrySet().stream()
                .filter(entry -> entry.getValue() != 0)
                .map(Map.Entry::getKey)
                .toList();
        for (String foodType : foodTypes) {
            possibleVictims.addAll(area.getResidents(foodType).stream()
                    .filter(Creature::isAlive)
                    .toList());
        }
        return possibleVictims;
    }

    private Direction getRandomDirection() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        Direction[] directions = Direction.values();
        return directions[rnd.nextInt(directions.length)];
    }

    private boolean tryToEat(Animal animal) {
        if (animal.isAlive()) {
            ThreadLocalRandom rnd = ThreadLocalRandom.current();
            int luckyChance = rnd.nextInt(101);
            return luckyChance <= settings.getClassFoodPreferences().get(animal.getType());
        } else return false;
    }

    private Area getNextArea(Area area) {
        Island island = area.getIsland();
        int currentX = area.getPointX();
        int currentY = area.getPointY();
        Direction direction = getRandomDirection();
        int newX = currentX + direction.getDeltaX();
        int newY = currentY + direction.getDeltaY();
        Area newArea;
        Area[][] areas = island.getAreas();
        try {
            newArea = areas[newX][newY];
        } catch (ArrayIndexOutOfBoundsException ex) {
            newArea = getNextArea(area);
        }
        return newArea;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", icon='" + settings.getClassIcon() + '\'' +
                ", currentWeight=" + getCurrentWeight() +
                ", foodNeeded=" + settings.getClassFoodNeeded() +
                ", isHungry=" + isHungry +
                ", isReadyForBreeding=" + isReadyForBreeding +
                ", maxPopulation=" + settings.getClassMaxPopulation() +
                ", stepSize=" + settings.getClassStepSize() +
                '}';
    }
}
