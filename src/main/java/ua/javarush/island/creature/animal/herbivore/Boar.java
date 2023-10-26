package ua.javarush.island.creature.animal.herbivore;

public class Boar extends Herbivore {
    private static int id;
    public Boar(){
        this.name=getClass().getSimpleName()+(++id);
    }

    @Override
    public String getName(){
        return name;
    }
}
