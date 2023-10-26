package ua.javarush.island.creature.animal.predator;

public class Wolf extends Predator {
    private static int id;

    public Wolf(){
        this.name=getClass().getSimpleName()+(++id);
        setCurrentWeight(getDefaultWeight());
    }

    @Override
    public String getName(){
        return name;
    }

}
