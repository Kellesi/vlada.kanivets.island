package ua.javarush.island.worldgenerator;

import ua.javarush.island.creature.animal.Animal;
import ua.javarush.island.creature.AnimalType;
import ua.javarush.island.creature.PlantType;
import ua.javarush.island.creature.plant.Plant;
import ua.javarush.island.settings.CreatureSettings;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class CreatureFactory {
    private static final Map<String, LinkedHashMap> defaultSettingsMap = CreatureSettings.getSettingMap();

    private CreatureFactory() {
    }

    public static Animal getAnimal(Class<? extends Animal> animalClass) {
        Animal instance;
        try {
            instance = animalClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        setDefaultsToAnimal(instance);
        return instance;
    }

    public static void setDefaultsToAnimal(Animal animal) {
        LinkedHashMap linkedHashMap = defaultSettingsMap.get(animal.getType());
        animal.setDefaultWeight((double)linkedHashMap.get("defaultWeight"));
        animal.setCurrentWeight(animal.getDefaultWeight());
        animal.setIcon((String) linkedHashMap.get("icon"));
        animal.setName(animal.getIcon() + animal.getName());
        animal.setFoodNeeded((double) linkedHashMap.get("foodNeeded"));
        animal.setFoodPreferences((Map<String, Integer>) linkedHashMap.get("foodPreferences"));
        animal.setMaxPopulation((int) linkedHashMap.get("maxPopulation"));
        animal.setStepSize((int) linkedHashMap.get("stepSize"));
    }
    public static void setDefaultsToPlant(Plant plant) {
        LinkedHashMap linkedHashMap = defaultSettingsMap.get(plant.getType());
        plant.setDefaultWeight((double)linkedHashMap.get("defaultWeight"));
        plant.setIcon((String) linkedHashMap.get("icon"));
        plant.setName(plant.getIcon() + plant.getName());
        plant.setMaxPopulation((int) linkedHashMap.get("maxPopulation"));
    }

    public static Plant getPlant(Class<? extends Plant> plantClass) {
        Plant instance;
        try {
            instance = plantClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        setDefaultsToPlant(instance);
        return instance;
    }

    public static List<? super Plant> getPlants(PlantType[] plantTypes) {
        List<? super Plant> producedPlants = new ArrayList<>();
        for (PlantType type : plantTypes) {
            Class<? extends Plant> clss = type.getClss();
            int population = getAmountToCreate(clss);
            for (int i = 0; i < population; i++) {
                producedPlants.add(getPlant(clss));
            }
        }
        return producedPlants;
    }

    public static List<? super Animal> getAnimals(AnimalType[] animalTypes) {
        List<? super Animal> producedAnimals = new ArrayList<>();
        for (AnimalType type : animalTypes) {
            Class<? extends Animal> clss = type.getClss();
            int population = getAmountToCreate(clss);
            for (int i = 0; i < population; i++) {
                producedAnimals.add(getAnimal(clss));
            }
        }
        return producedAnimals;
    }

    private static int getAmountToCreate(Class<?> clss) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int maxPopulation = (int) defaultSettingsMap.get(clss.getSimpleName()).get("maxPopulation");
        return rnd.nextInt(maxPopulation+1);
    }
}
