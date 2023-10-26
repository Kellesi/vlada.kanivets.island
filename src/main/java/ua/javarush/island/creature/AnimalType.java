package ua.javarush.island.creature;

import ua.javarush.island.creature.animal.Animal;
import ua.javarush.island.creature.animal.herbivore.Boar;
import ua.javarush.island.creature.animal.herbivore.Buffalo;
import ua.javarush.island.creature.animal.herbivore.Caterpillar;
import ua.javarush.island.creature.animal.herbivore.Deer;
import ua.javarush.island.creature.animal.herbivore.Duck;
import ua.javarush.island.creature.animal.herbivore.Goat;
import ua.javarush.island.creature.animal.herbivore.Horse;
import ua.javarush.island.creature.animal.herbivore.Mouse;
import ua.javarush.island.creature.animal.herbivore.Rabbit;
import ua.javarush.island.creature.animal.herbivore.Sheep;
import ua.javarush.island.creature.animal.predator.Bear;
import ua.javarush.island.creature.animal.predator.Boa;
import ua.javarush.island.creature.animal.predator.Eagle;
import ua.javarush.island.creature.animal.predator.Fox;
import ua.javarush.island.creature.animal.predator.Wolf;

public enum AnimalType {
    WOLF(Wolf.class),
    BOA(Boa.class),
    FOX(Fox.class),
    BEAR(Bear.class),
    EAGLE(Eagle.class),
    HORSE(Horse.class),
    DEER(Deer.class),
    RABBIT(Rabbit.class),
    MOUSE(Mouse.class),
    GOAT(Goat.class),
    SHEEP(Sheep.class),
    BOAR(Boar.class),
    BUFFALO(Buffalo.class),
    DUCK(Duck.class),
    CATERPILLAR(Caterpillar.class);
    Class<? extends Animal> clss;

    public Class<? extends Animal> getClss() {
        return clss;
    }

    AnimalType(Class<? extends Animal> animal) {
        this.clss = animal;
    }

}
