package ua.javarush.island.creature.animal.herbivore;

public class Deer extends Herbivore {
    private static int id;
    public Deer(){
        this.name=getClass().getSimpleName()+(++id);
    }

    @Override
    public String getName(){
        return name;
    }
}
