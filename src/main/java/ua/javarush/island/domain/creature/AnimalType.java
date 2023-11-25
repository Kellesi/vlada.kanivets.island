package ua.javarush.island.domain.creature;

import ua.javarush.island.domain.creature.animal.Animal;
import ua.javarush.island.domain.creature.animal.herbivore.Boar;
import ua.javarush.island.domain.creature.animal.herbivore.Buffalo;
import ua.javarush.island.domain.creature.animal.herbivore.Caterpillar;
import ua.javarush.island.domain.creature.animal.herbivore.Deer;
import ua.javarush.island.domain.creature.animal.herbivore.Duck;
import ua.javarush.island.domain.creature.animal.herbivore.Goat;
import ua.javarush.island.domain.creature.animal.herbivore.Horse;
import ua.javarush.island.domain.creature.animal.herbivore.Mouse;
import ua.javarush.island.domain.creature.animal.herbivore.Rabbit;
import ua.javarush.island.domain.creature.animal.herbivore.Sheep;
import ua.javarush.island.domain.creature.animal.predator.Bear;
import ua.javarush.island.domain.creature.animal.predator.Boa;
import ua.javarush.island.domain.creature.animal.predator.Eagle;
import ua.javarush.island.domain.creature.animal.predator.Fox;
import ua.javarush.island.domain.creature.animal.predator.Wolf;
import ua.javarush.island.util.settings.BaseAnimalSettings;
import ua.javarush.island.util.settings.CreatureSettingsLoader;

public enum AnimalType {
    WOLF(Wolf.class, new BaseAnimalSettings(CreatureSettingsLoader.getSettingMap().get("Wolf"))),
    BOA(Boa.class, new BaseAnimalSettings(CreatureSettingsLoader.getSettingMap().get("Boa"))),
    FOX(Fox.class, new BaseAnimalSettings(CreatureSettingsLoader.getSettingMap().get("Fox"))),
    BEAR(Bear.class, new BaseAnimalSettings(CreatureSettingsLoader.getSettingMap().get("Bear"))),
    EAGLE(Eagle.class, new BaseAnimalSettings(CreatureSettingsLoader.getSettingMap().get("Eagle"))),
    HORSE(Horse.class, new BaseAnimalSettings(CreatureSettingsLoader.getSettingMap().get("Horse"))),
    DEER(Deer.class, new BaseAnimalSettings(CreatureSettingsLoader.getSettingMap().get("Deer"))),
    RABBIT(Rabbit.class, new BaseAnimalSettings(CreatureSettingsLoader.getSettingMap().get("Rabbit"))),
    MOUSE(Mouse.class, new BaseAnimalSettings(CreatureSettingsLoader.getSettingMap().get("Mouse"))),
    GOAT(Goat.class, new BaseAnimalSettings(CreatureSettingsLoader.getSettingMap().get("Goat"))),
    SHEEP(Sheep.class, new BaseAnimalSettings(CreatureSettingsLoader.getSettingMap().get("Sheep"))),
    BOAR(Boar.class, new BaseAnimalSettings(CreatureSettingsLoader.getSettingMap().get("Boar"))),
    BUFFALO(Buffalo.class, new BaseAnimalSettings(CreatureSettingsLoader.getSettingMap().get("Buffalo"))),
    DUCK(Duck.class, new BaseAnimalSettings(CreatureSettingsLoader.getSettingMap().get("Duck"))),
    CATERPILLAR(Caterpillar.class, new BaseAnimalSettings(CreatureSettingsLoader.getSettingMap().get("Caterpillar")));
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
