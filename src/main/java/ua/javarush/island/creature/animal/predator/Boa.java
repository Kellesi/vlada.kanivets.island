package ua.javarush.island.creature.animal.predator;

public class Boa extends Predator {
    private static int id;

    public Boa(){
        this.name=getClass().getSimpleName()+(++id);
        setCurrentWeight(getDefaultWeight());
    }

    @Override
    public String getName(){
        return name;
    }

}
