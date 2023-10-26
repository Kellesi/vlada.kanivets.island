package ua.javarush.island.creature.animal.herbivore;

public class Goat extends Herbivore {
    private static int id;
    public Goat(){
        this.name=getClass().getSimpleName()+(++id);
    }

    @Override
    public String getName(){
        return name;
    }
}
