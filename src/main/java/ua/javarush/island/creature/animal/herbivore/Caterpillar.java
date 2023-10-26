package ua.javarush.island.creature.animal.herbivore;

public class Caterpillar extends Herbivore {
    private static int id;
    public Caterpillar(){
        this.name=getClass().getSimpleName()+(++id);
    }

    @Override
    public String getName(){
        return name;
    }
}
