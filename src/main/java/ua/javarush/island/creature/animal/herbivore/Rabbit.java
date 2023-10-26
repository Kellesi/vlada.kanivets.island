package ua.javarush.island.creature.animal.herbivore;

public class Rabbit extends Herbivore {
    private static int id;
    public Rabbit(){
        this.name=getClass().getSimpleName()+(++id);
    }

    @Override
    public String getName(){
        return name;
    }
}
