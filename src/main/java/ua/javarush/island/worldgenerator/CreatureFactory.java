package ua.javarush.island.worldgenerator;

import ua.javarush.island.creature.animal.Animal;
import ua.javarush.island.creature.AnimalType;
import ua.javarush.island.creature.PlantType;
import ua.javarush.island.creature.plant.Plant;
import ua.javarush.island.settings.BaseAnimalSettings;
import ua.javarush.island.settings.BasePlantSettings;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
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

    public static <T> T getPlant(Class<T> plantClass, BasePlantSettings settings) {
        T instance;
        try {
            instance = plantClass.getConstructor(BasePlantSettings.class).newInstance(settings);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    public static List<? super Plant> getPlants(PlantType[] plantTypes) {
        List<? super Plant> producedPlants = new ArrayList<>();
        for (PlantType type : plantTypes) {
            int population = rnd.nextInt(type.getSettings().getClassMaxPopulation() + 1);
            for (int i = 0; i < population; i++) {
                producedPlants.add(getPlant(type.getClss(), type.getSettings()));
            }
        }
        return producedPlants;
    }

    public static List<? super Animal> getAnimals(AnimalType[] animalTypes) {
        List<? super Animal> producedAnimals = new ArrayList<>();
        for (AnimalType type : animalTypes) {
            int population = rnd.nextInt(type.getSettings().getClassMaxPopulation() + 1);
            for (int i = 0; i < population; i++) {
                producedAnimals.add(getAnimal(type.getClss(), type.getSettings()));
            }
        }
        return producedAnimals;
    }
}
