package ua.javarush.island.domain.creature.animal;

import lombok.Getter;
import lombok.Setter;
import ua.javarush.island.domain.creature.AnimalType;
import ua.javarush.island.domain.creature.Creature;
import ua.javarush.island.domain.creature.ability.CanBreed;
import ua.javarush.island.domain.creature.ability.CanEat;
import ua.javarush.island.domain.creature.ability.CanMove;
import ua.javarush.island.domain.map.Area;
import ua.javarush.island.domain.map.Direction;
import ua.javarush.island.domain.map.Island;
import ua.javarush.island.util.settings.BaseAnimalSettings;
import ua.javarush.island.util.worldgenerator.CreatureFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
public abstract class Animal extends Creature implements CanMove, CanBreed, CanEat {
    private static final double DELTA_WEIGHT = 0.2;
    private static final double MINIMUM_WEIGHT_RATIO = 0.6;

    private final BaseAnimalSettings settings;
    private double currentWeight;
    private double currentSatiety;
    private boolean isHungry = true;
    private boolean isMoved = false;

    protected Animal(BaseAnimalSettings settings) {
        this.settings = settings;
        currentWeight = settings.getClassWeight();
    }

    @Override
    public void eat(Area area) {
        getLock().lock();
        int numberOfHuntingAttempts = 5;
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        try {
            if (!isAlive()) {
                return;
            }
            while (isHungry && numberOfHuntingAttempts != 0) {
                List<Creature> possibleVictims = searchForFood(area);
                if (!possibleVictims.isEmpty()) {
                    Animal animal = (Animal) possibleVictims.get(rnd.nextInt(possibleVictims.size()));
                    eatOtherAnimal(animal);
                    numberOfHuntingAttempts--;
                } else {
                    break;
                }
            }
        } finally {
            getLock().unlock();
        }
    }

    @Override
    public <T extends Creature> Optional<T> breed(Area area) {
        if (!this.isReadyForBreeding()) {
            return Optional.empty();
        }
        List<Animal> sameTypeResidents = area.getResidents(this.getClass());
        if (!sameTypeResidents.isEmpty() && sameTypeResidents.size() < settings.getClassMaxPopulation()) {
            Optional<Animal> optionalAnimal = sameTypeResidents.stream()
                    .filter(Animal::isReadyForBreeding)
                    .filter(animal -> !animal.equals(this))
                    .findFirst();
            if (optionalAnimal.isPresent()) {
                Animal animal = optionalAnimal.get();
                Animal animal1 = this.hashCode() > animal.hashCode() ? this : animal;
                Animal animal2 = this.hashCode() < animal.hashCode() ? this : animal;
                animal1.getLock().lock();
                animal2.getLock().lock();
                try {
                    if (animal.isReadyForBreeding() && this.isReadyForBreeding()) {
                        animal.setReadyForBreeding(false);
                        this.setReadyForBreeding(false);
                        Animal newAnimal = CreatureFactory.getAnimal(this.getClass(), settings);
                        newAnimal.setReadyForBreeding(false);
                        return (Optional<T>) Optional.of(newAnimal);
                    }
                } finally {
                    animal2.getLock().unlock();
                    animal1.getLock().unlock();
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void move(Area area) {
        int stepSize = settings.getClassStepSize();
        if (isMoved || stepSize == 0) {
            return;
        }
        Area newArea = area;
        while (stepSize != 0) {
            try {
                newArea = getNextArea(newArea);
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
            stepSize -= 1;
        }
        if (area.equals(newArea) || newArea.getResidents(this.getClass()).size() == settings.getClassMaxPopulation()) {
            isMoved = true;
            return;
        }
        newArea.getLock().lock();
        try {
            if (newArea.getResidents(this.getClass()).size() != settings.getClassMaxPopulation()) {
                newArea.addResident(this);
                isMoved = true;
            }
        } finally {
            newArea.getLock().unlock();
        }
        area.getLock().lock();
        try {
            if (isMoved) {
                area.removeResident(this);
            }
        } finally {
            area.getLock().unlock();
        }
    }

    public void newDaySets() {
        setReadyForBreeding(true);
        setMoved(false);
        if (isHungry) {
            currentWeight = currentWeight - (settings.getClassFoodNeeded() - currentSatiety) * DELTA_WEIGHT;
        }
        setCurrentSatiety(0);
        setHungry(true);
        if (settings.getClassWeight() * MINIMUM_WEIGHT_RATIO > currentWeight) {
            setAlive(false);
        }
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
        setCurrentWeight(currentWeight + (foodWeight * DELTA_WEIGHT));
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
            possibleVictims.addAll(area.getResidents(AnimalType.valueOf(foodType.toUpperCase()).getClss()).stream()
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
            return luckyChance <= settings.getClassFoodPreferences().get(animal.getClass().getSimpleName());
        } else return false;
    }

    private Area getNextArea(Area area) {
        Island island = area.getIsland();
        int currentX = area.getPointX();
        int currentY = area.getPointY();
        Direction direction = getRandomDirection();
        int newX = currentX + direction.getDeltaX();
        int newY = currentY + direction.getDeltaY();
        Area[][] areas = island.getAreas();
        return areas[newX][newY];
    }
}
