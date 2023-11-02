package ua.javarush.island.map;

import lombok.Getter;
import ua.javarush.island.creature.Creature;
import ua.javarush.island.creature.animal.Animal;
import ua.javarush.island.creature.plant.Plant;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Getter
public class Area {
    ReentrantLock lock = new ReentrantLock(true);
    private final int pointX;
    private final int pointY;
    private final Island island;
    private List<Animal> residents;
    private List<Plant> plants;

    public Area(List<Animal> residents,
                List<Plant> plants,
                int pointX, int pointY, Island island) {
        this.residents = residents;
        this.plants = plants;
        this.pointX = pointX;
        this.pointY = pointY;
        this.island = island;
    }

    public List<Animal> getAllResidents() {
        return residents;
    }

    public List<Plant> getAllPlants() {
        return plants;
    }

    public List<Animal> getResidents(String animalType) {
        return residents.stream().map(Animal.class::cast).filter(animal -> animal.getType().equals(animalType)).collect(Collectors.toList());
    }

    public List<Plant> getPlants(String plantType) {
        return plants.stream().map(Plant.class::cast).filter(plant -> plant.getType().equals(plantType)).collect(Collectors.toList());
    }

    public void addResident(Animal animal) {
        residents.add(animal);
    }

    public void removeResident(Animal animal) {
        residents.remove(animal);
    }

    public String getName() {
        return String.format("Area %d-%d", pointX, pointY);
    }

    public Map<String, Long> getCurrentPopulation() {
        return residents.stream().collect(Collectors.groupingBy(Animal::getType, Collectors.counting()));
    }

    public void removeAllDeads() {
        residents = residents.stream().filter(Creature::isAlive).collect(Collectors.toList());
    }
}
