package ua.javarush.island.map;

import lombok.Getter;
import ua.javarush.island.creature.Creature;
import ua.javarush.island.creature.animal.Animal;
import ua.javarush.island.creature.plant.Plant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Getter
public class Area {
    private final int pointX;
    private final int pointY;
    private final Island island;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Map<Class<? extends Animal>, List<Animal>> residents;
    private List<Plant> plants;

    public Area(Map<Class<? extends Animal>, List<Animal>> residents,
                List<Plant> plants,
                int pointX, int pointY, Island island) {
        this.residents = residents;
        this.plants = plants;
        this.pointX = pointX;
        this.pointY = pointY;
        this.island = island;
    }

    public Map<Class<? extends Animal>, List<Animal>> getAllResidents() {
        return residents;
    }

    public List<Plant> getAllPlants() {
        return plants;
    }

    public List<Animal> getResidents(Class<? extends Animal> animalClass) {
        return residents.get(animalClass);
    }

    public List<Plant> getPlants(String plantType) {
        return plants.stream().map(Plant.class::cast).filter(plant -> plant.getType().equals(plantType)).collect(Collectors.toList());
    }

    public void addResident(Animal animal) {
        residents.get(animal.getClass()).add(animal);
    }

    public void removeResident(Animal animal) {
        residents.get(animal.getClass()).remove(animal);
    }

    public String getName() {
        return String.format("Area %d-%d", pointX, pointY);
    }

    public Map<Class<? extends Animal>, Integer> getCurrentPopulation() {
        Map<Class<? extends Animal>, Integer> classToPopulation = new HashMap<>();
        for (Map.Entry<Class<? extends Animal>, List<Animal>> entry : residents.entrySet()) {
            classToPopulation.put(entry.getKey(), entry.getValue().size());
        }
        return classToPopulation;
    }

    public void removeAllDeads() {
        for (Map.Entry<Class<? extends Animal>, List<Animal>> entry : residents.entrySet()) {
            List<Animal> aliveAnimals = entry.getValue().stream().filter(Creature::isAlive).collect(Collectors.toList());
            residents.get(entry.getKey()).retainAll(aliveAnimals);
        }
    }
}
