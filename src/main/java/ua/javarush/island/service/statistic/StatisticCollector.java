package ua.javarush.island.service.statistic;

import ua.javarush.island.domain.creature.AnimalType;
import ua.javarush.island.domain.creature.animal.Animal;
import ua.javarush.island.domain.creature.plant.Plant;
import ua.javarush.island.domain.map.Area;
import ua.javarush.island.domain.map.Island;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class StatisticCollector {
    private final Island island;
    private final Area[][] areas;
    private int day;
    private AtomicInteger diedAnimals = new AtomicInteger();
    private AtomicInteger eatenPlants = new AtomicInteger();
    private AtomicInteger newbornAnimals = new AtomicInteger();

    public StatisticCollector(Island island) {
        this.island = island;
        areas = island.getAreas();
    }

    public int getTotalAnimalPopulation() {
        int totalPopulation = 0;
        for (Area[] areas1 : areas) {
            for (Area area : areas1) {
                for (Map.Entry<Class<? extends Animal>, List<Animal>> animals : area.getAllResidents().entrySet()) {
                    totalPopulation += animals.getValue().size();
                }
            }
        }
        return totalPopulation;
    }

    public int getTotalPlantsNumber() {
        int totalPopulation = 0;
        for (Area[] areas1 : areas) {
            for (Area area : areas1) {
                for (Map.Entry<Class<? extends Plant>, List<Plant>> plants : area.getAllPlants().entrySet()) {
                    totalPopulation += plants.getValue().size();
                }
            }
        }
        return totalPopulation;
    }

    public Map<String, Integer> getTotalAnimalPopulationByType() {
        Map<String, Integer> animalIconToTotalPopulation = initAnimalIconMap();
        for (Area[] areas1 : areas) {
            for (Area area : areas1) {
                Map<String, Integer> areaCurrentPopulation = area.getCurrentPopulation();
                areaCurrentPopulation.forEach((key, value) -> animalIconToTotalPopulation.merge(key, value, Integer::sum));
            }
        }
        return animalIconToTotalPopulation;
    }

    public Map<String, Map<String, Integer>> getAreaAnimalPopulationByType() {
        Map<String, Map<String, Integer>> areaToTotalPopulation = new HashMap<>();
        for (Area[] areas1 : areas) {
            for (Area area : areas1) {
                areaToTotalPopulation.put(area.getName(), area.getCurrentPopulation());
            }
        }
        return areaToTotalPopulation;
    }

    public void reset() {
        diedAnimals = new AtomicInteger();
        eatenPlants = new AtomicInteger();
        newbornAnimals = new AtomicInteger();
    }

    public void increaseDeadCounter(int amount) {
        diedAnimals.getAndAdd(amount);
    }

    public void increaseEatenPlants(int amount) {
        eatenPlants.getAndAdd(amount);
    }

    public void increaseNewbornCounter() {
        newbornAnimals.getAndIncrement();
    }

    public AtomicInteger getAmountOfDiedAnimals() {
        return diedAnimals;
    }

    public AtomicInteger getAmountOfEatenPlants() {
        return eatenPlants;
    }

    public AtomicInteger getAmountOfNewbornAnimals() {
        return newbornAnimals;
    }

    private Map<String, Integer> initAnimalIconMap() {
        Map<String, Integer> newAnimalIconMap = new HashMap<>();
        AnimalType[] values = AnimalType.values();
        for (AnimalType value : values) {
            newAnimalIconMap.put(value.getSettings().getClassIcon(), 0);
        }
        return newAnimalIconMap;
    }

    public int getDayAndIncrement() {
        return day++;
    }
}
