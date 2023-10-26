package ua.javarush.island.creature.animal.herbivore;

public class Horse extends Herbivore {
    private static int id;
    public Horse(){
        this.name=getClass().getSimpleName()+(++id);
    }

    @Override
    public String getName(){
        return name;
    }
}
