package ua.javarush.island.creature.animal.herbivore;

public class Buffalo extends Herbivore {
    private static int id;
    public Buffalo(){
        this.name=getClass().getSimpleName()+(++id);
    }

    @Override
    public String getName(){
        return name;
    }
}
