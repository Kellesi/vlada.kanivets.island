package ua.javarush.island.creature.animal.predator;

public class Eagle extends Predator {
    private static int id;

    public Eagle(){
        this.name=getClass().getSimpleName()+(++id);
        setCurrentWeight(getDefaultWeight());
    }

    @Override
    public String getName(){
        return name;
    }

}
