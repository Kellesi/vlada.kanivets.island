package ua.javarush.island.util.worldgenerator;

import ua.javarush.island.domain.creature.animal.Animal;
import ua.javarush.island.domain.creature.AnimalType;
import ua.javarush.island.domain.creature.PlantType;
import ua.javarush.island.domain.creature.plant.Plant;
import ua.javarush.island.util.settings.BaseAnimalSettings;
import ua.javarush.island.util.settings.BasePlantSettings;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class CreatureFactory {
    private static final ThreadLocalRandom rnd = ThreadLocalRandom.current();

    private CreatureFactory() {
    }

    public static Animal getAnimal(Class<? extends Animal> animalClass, BaseAnimalSettings settings) {
        Animal instance;
        try {
            instance = animalClass.getConstructor(BaseAnimalSettings.class).newInstance(settings);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    public static Plant getPlant(Class<? extends Plant> plantClass, BasePlantSettings settings) {
        Plant instance;
        try {
            instance = plantClass.getConstructor(BasePlantSettings.class).newInstance(settings);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    public static Map<Class<? extends Plant>, List<Plant>> getPlants(PlantType[] plantTypes) {
        Map<Class<? extends Plant>, List<Plant>> classToPlants = new HashMap<>();
        for (PlantType type : plantTypes) {
            List<Plant> producedPlants = new ArrayList<>();
            int population = rnd.nextInt(100, type.getSettings().getClassMaxPopulation() + 1);
            for (int i = 0; i < population; i++) {
                producedPlants.add(getPlant(type.getClss(), type.getSettings()));
            }
            classToPlants.put(type.getClss(), producedPlants);
        }
        return classToPlants;
    }

    public static Map<Class<? extends Animal>, List<Animal>> getAnimals(AnimalType[] animalTypes) {
        Map<Class<? extends Animal>, List<Animal>> classToAnimals = new HashMap<>();
        for (AnimalType type : animalTypes) {
            List<Animal> producedAnimals = new ArrayList<>();
            int population = rnd.nextInt(type.getSettings().getClassMaxPopulation() + 1);
            for (int i = 0; i < population; i++) {
                producedAnimals.add(getAnimal(type.getClss(), type.getSettings()));
            }
            classToAnimals.put(type.getClss(), producedAnimals);
        }
        return classToAnimals;
    }
}
