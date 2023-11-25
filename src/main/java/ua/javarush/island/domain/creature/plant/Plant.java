package ua.javarush.island.domain.creature.plant;

import lombok.Getter;
import ua.javarush.island.domain.creature.ability.CanBreed;
import ua.javarush.island.domain.creature.Creature;
import ua.javarush.island.domain.map.Area;
import ua.javarush.island.util.settings.BasePlantSettings;
import ua.javarush.island.util.worldgenerator.CreatureFactory;

import java.util.Optional;

@Getter
public abstract class Plant extends Creature implements CanBreed {
    private final BasePlantSettings settings;

    protected Plant(BasePlantSettings settings) {
        this.settings = settings;
    }

    @Override
    public <T extends Creature> Optional<T> breed(Area area) {
        if (!isReadyForBreeding() || area.getAllPlants().get(this.getClass()).size() == settings.getClassMaxPopulation()) {
            return Optional.empty();
        }
        return (Optional<T>) Optional.of(CreatureFactory.getPlant(this.getClass(), settings));
    }
}
