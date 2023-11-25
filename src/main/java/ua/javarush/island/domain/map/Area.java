package ua.javarush.island.domain.map;

import lombok.Getter;
import ua.javarush.island.service.console.InfoWriter;
import ua.javarush.island.domain.creature.AnimalType;
import ua.javarush.island.domain.creature.Creature;
import ua.javarush.island.domain.creature.PlantType;
import ua.javarush.island.domain.creature.animal.Animal;
import ua.javarush.island.domain.creature.plant.Plant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Getter
public class Area {
    private static final InfoWriter writer = InfoWriter.getStatisticWriter();

    private final int pointX;
    private final int pointY;
    private final Island island;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Map<Class<? extends Animal>, List<Animal>> residents;
    private final Map<Class<? extends Plant>, List<Plant>> plants;

    public Area(Map<Class<? extends Animal>, List<Animal>> residents,
                Map<Class<? extends Plant>, List<Plant>> plants,
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

    public Map<Class<? extends Plant>, List<Plant>> getAllPlants() {
        return plants;
    }

    public List<Animal> getResidents(Class<? extends Animal> animalClass) {
        return residents.get(animalClass) == null ? new ArrayList<>() : residents.get(animalClass);
    }

    public List<Plant> getPlants(String plantType) {
        return plants.get(PlantType.valueOf(plantType.toUpperCase()).getClss());
    }

    public void addNewPlants(Map<Class<? extends Plant>, List<Plant>> newPlants) {
        for (Map.Entry<Class<? extends Plant>, List<Plant>> plantByClass : newPlants.entrySet()) {
            plants.get(plantByClass.getKey()).addAll(plantByClass.getValue());
        }
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

    public Map<String, Integer> getCurrentPopulation() {
        Map<String, Integer> animalIconToPopulation = new HashMap<>();
        for (Map.Entry<Class<? extends Animal>, List<Animal>> entry : residents.entrySet()) {
            String animalIcon = AnimalType.valueOf(entry.getKey().getSimpleName().toUpperCase()).getSettings().getClassIcon();
            animalIconToPopulation.put(animalIcon, entry.getValue().size());
        }
        return animalIconToPopulation;
    }

    public int removeAllDeads() {
        int before = 0;
        int after = 0;
        for (Map.Entry<Class<? extends Animal>, List<Animal>> entry : residents.entrySet()) {
            before += entry.getValue().size();
            List<Animal> aliveAnimals = entry.getValue().stream().filter(Creature::isAlive).collect(Collectors.toList());
            after += aliveAnimals.size();
            residents.get(entry.getKey()).retainAll(aliveAnimals);
        }
        return before - after;
    }

    public int removeAllEatenPlants() {
        int before = 0;
        int after = 0;
        for (Map.Entry<Class<? extends Plant>, List<Plant>> entry : plants.entrySet()) {
            before += entry.getValue().size();
            List<Plant> alivePlants = entry.getValue().stream().filter(Creature::isAlive).collect(Collectors.toList());
            after += alivePlants.size();
            plants.get(entry.getKey()).retainAll(alivePlants);
        }
        return before - after;
    }
}
