package ua.javarush.island.map;

import ua.javarush.island.creature.Creature;
import ua.javarush.island.creature.animal.Animal;
import ua.javarush.island.creature.plant.Plant;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Area {
    ReentrantLock lock = new ReentrantLock(true);
    private final int pointX;
    private final int pointY;
    private final Island island;
    private List<? super Animal> residents;
    private List<? super Plant> plants;

    public Area(List<? super Animal> residents,
                List<? super Plant> plants,
                int pointX, int pointY, Island island) {
        this.residents = Collections.synchronizedList(residents);
        this.plants = plants;
        this.pointX = pointX;
        this.pointY = pointY;
        this.island = island;
    }

    public Area(int pointX, int pointY, Island island) {
        this.pointX = pointX;
        this.pointY = pointY;
        this.island = island;
    }

    public List<? super Animal> getAllResidents() {
        return residents;
    }

    public List<? super Plant> getAllPlants() {
        return plants;
    }

    public List<Animal> getResidents(String animalType) {
        return residents.stream().map(Animal.class::cast).filter(animal -> animal.getType().equals(animalType)).toList();
    }

    public List<Plant> getPlants(String plantType) {
        return plants.stream().map(Plant.class::cast).filter(plant -> plant.getType().equals(plantType)).toList();
    }

    public <T extends Animal> void addResident(T animal) {
        residents.add(animal);
    }

    public <T extends Animal> void removeResident(T animal) {
        residents.remove(animal);
    }

    public String getName() {
        return String.format("Area %d-%d", pointX, pointY);
    }

    public Map<String, Long> getCurrentPopulation() {
        return residents.stream().map(Animal.class::cast).collect(Collectors.groupingBy(Animal::getType, Collectors.counting()));
    }

    public void removeAllDeads() {
        residents = residents.stream().map(Animal.class::cast).filter(Creature::isAlive).collect(Collectors.toList());
    }

    public Island getIsland() {
        return island;
    }

    public int getPointX() {
        return pointX;
    }

    public int getPointY() {
        return pointY;
    }

    public ReentrantLock getLock() {
        return lock;
    }
}
