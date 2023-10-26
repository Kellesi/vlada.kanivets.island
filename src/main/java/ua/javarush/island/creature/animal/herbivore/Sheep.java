package ua.javarush.island.creature.animal.herbivore;

public class Sheep extends Herbivore {
    private static int id;
    public Sheep(){
        this.name=getClass().getSimpleName()+(++id);
    }

    @Override
    public String getName(){
        return name;
    }
}
