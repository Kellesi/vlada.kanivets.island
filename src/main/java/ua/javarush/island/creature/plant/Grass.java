package ua.javarush.island.creature.plant;

public class Grass extends Plant{
    private static int id;
    public Grass(){
        this.name=getClass().getSimpleName()+(++id);
    }

    @Override
    public String getName(){
        return name;
    }
}
