package ua.javarush.island.worldgenerator;

import ua.javarush.island.creature.animal.Animal;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalFactory {
    private AnimalFactory(){
    }

    public static Animal getAnimal(Class<? extends Animal> animalClass){
        Animal instance;
        try {
                instance = animalClass.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        return instance;
    }

    public static List<? super Animal> getAnimals(List<Class<? extends Animal>> animalClasses){
        List<? super Animal> producedAnimals=new ArrayList<>();
        for (Class<? extends Animal> clss: animalClasses) {
           int population= getAmountToCreate(clss);
            for (int i = 0; i < population; i++) {
                producedAnimals.add(getAnimal(clss));
            }
        }
        return producedAnimals;
    }
    private static int getAmountToCreate(Class<? extends Animal> clss){
        int randomPopulation;
        try {
            ThreadLocalRandom rnd= ThreadLocalRandom.current();
            Field field = clss.getDeclaredField("maxPopulation");
            field.setAccessible(true);
            int maxPopulation = (int) field.get(null);
            randomPopulation= rnd.nextInt(maxPopulation);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        return randomPopulation;
    }
}
