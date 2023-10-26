package ua.javarush.island.creature.animal.predator;

public class Fox extends Predator {
    private static int id;

    public Fox(){
        this.name=getClass().getSimpleName()+(++id);
        setCurrentWeight(getDefaultWeight());
    }

    @Override
    public String getName(){
        return name;
    }

}
