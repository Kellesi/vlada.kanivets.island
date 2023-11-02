package ua.javarush.island.creature.plant;

import lombok.Getter;
import ua.javarush.island.creature.ability.CanBreed;
import ua.javarush.island.creature.Creature;
import ua.javarush.island.map.Area;
import ua.javarush.island.settings.BasePlantSettings;
import ua.javarush.island.worldgenerator.CreatureFactory;

import java.util.Optional;

@Getter
public abstract class Plant extends Creature implements CanBreed {
    private final BasePlantSettings settings;

    protected Plant(BasePlantSettings settings) {
        this.settings = settings;
        this.name = settings.getClassIcon() + getType();
    }

    @Override
    public <T extends Creature> Optional<T> breed(Area area) {
        if (area.getAllPlants().size() < settings.getClassMaxPopulation()) {
            return (Optional<T>) Optional.of(CreatureFactory.getPlant(this.getClass(), settings));
        }
        return Optional.empty();
    }

    @Override
    public String getName() {
        return settings.getClassIcon() + getType() + getName();
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
