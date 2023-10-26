package ua.javarush.island.creature.plant;

import ua.javarush.island.creature.abilities.CanBreed;
import ua.javarush.island.creature.Creature;
import ua.javarush.island.map.Area;
import ua.javarush.island.worldgenerator.CreatureFactory;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Plant extends Creature implements CanBreed {
    public static final int CHANCE_TO_BREED=30;
    @Override
    public <T>Optional<T> breed(Area area) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        if (rnd.nextInt(101)<CHANCE_TO_BREED){
            return (Optional<T>) Optional.of(CreatureFactory.getPlant(this.getClass()));
        }
        return Optional.empty();
    }
    @Override
    public String toString() {
        return "Plant{" +
                "name='" + name + '\'' +
                ", icon='" + getIcon() + '\'' +
                ", defaultWeight=" + getDefaultWeight() +
                ", maxPopulation=" + getMaxPopulation() +
                ", type='" + getType() + '\'' +
                '}';
    }
}
