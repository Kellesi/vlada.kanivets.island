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
import ua.javarush.island.settings.BaseAnimalSettings;
import ua.javarush.island.settings.CreatureSettings;

public enum AnimalType {
    WOLF(Wolf.class, new BaseAnimalSettings(CreatureSettings.getSettingMap().get("Wolf"))),
    BOA(Boa.class, new BaseAnimalSettings(CreatureSettings.getSettingMap().get("Boa"))),
    FOX(Fox.class, new BaseAnimalSettings(CreatureSettings.getSettingMap().get("Fox"))),
    BEAR(Bear.class, new BaseAnimalSettings(CreatureSettings.getSettingMap().get("Bear"))),
    EAGLE(Eagle.class, new BaseAnimalSettings(CreatureSettings.getSettingMap().get("Eagle"))),
    HORSE(Horse.class, new BaseAnimalSettings(CreatureSettings.getSettingMap().get("Horse"))),
    DEER(Deer.class, new BaseAnimalSettings(CreatureSettings.getSettingMap().get("Deer"))),
    RABBIT(Rabbit.class, new BaseAnimalSettings(CreatureSettings.getSettingMap().get("Rabbit"))),
    MOUSE(Mouse.class, new BaseAnimalSettings(CreatureSettings.getSettingMap().get("Mouse"))),
    GOAT(Goat.class, new BaseAnimalSettings(CreatureSettings.getSettingMap().get("Goat"))),
    SHEEP(Sheep.class, new BaseAnimalSettings(CreatureSettings.getSettingMap().get("Sheep"))),
    BOAR(Boar.class, new BaseAnimalSettings(CreatureSettings.getSettingMap().get("Boar"))),
    BUFFALO(Buffalo.class, new BaseAnimalSettings(CreatureSettings.getSettingMap().get("Buffalo"))),
    DUCK(Duck.class, new BaseAnimalSettings(CreatureSettings.getSettingMap().get("Duck"))),
    CATERPILLAR(Caterpillar.class, new BaseAnimalSettings(CreatureSettings.getSettingMap().get("Caterpillar")));
    private final Class<? extends Animal> clss;
    private final BaseAnimalSettings settings;

    public Class<? extends Animal> getClss() {
        return clss;
    }

    public BaseAnimalSettings getSettings() {
        return settings;
    }

    AnimalType(Class<? extends Animal> animal, BaseAnimalSettings settings) {
        this.clss = animal;
        this.settings = settings;
    }

}
