package ua.javarush.island.creature.animal.predator;

public class Bear extends Predator {
    private static int id;

    public Bear(){
        this.name=getClass().getSimpleName()+(++id);
        setCurrentWeight(getDefaultWeight());
    }

    @Override
    public String getName(){
        return name;
    }

}
