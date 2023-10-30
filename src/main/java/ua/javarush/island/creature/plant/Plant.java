package ua.javarush.island.creature.plant;

import ua.javarush.island.creature.abilities.CanBreed;
import ua.javarush.island.creature.Creature;
import ua.javarush.island.map.Area;
import ua.javarush.island.settings.BasePlantSettings;
import ua.javarush.island.worldgenerator.CreatureFactory;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Plant extends Creature implements CanBreed {
    public static final int CHANCE_TO_BREED = 30;
    private final BasePlantSettings settings;

    protected Plant(BasePlantSettings settings) {
        this.settings = settings;
        this.name = settings.getClassIcon() + getType();
    }

    @Override
    public String getName() {
        return settings.getClassIcon() + getType() + getName();
    }

    @Override
    public <T> Optional<T> breed(Area area) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        if (rnd.nextInt(101) < CHANCE_TO_BREED) {
            return (Optional<T>) Optional.of(CreatureFactory.getPlant(this.getClass(), settings));
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "Plant{" +
                "name='" + name + '\'' +
                ", icon='" + settings.getClassIcon() + '\'' +
                ", defaultWeight=" + settings.getClassWeight() +
                ", maxPopulation=" + settings.getClassMaxPopulation() +
                ", type='" + getType() + '\'' +
                '}';
    }
}
