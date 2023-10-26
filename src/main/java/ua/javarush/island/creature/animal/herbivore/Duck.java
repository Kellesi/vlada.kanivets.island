package ua.javarush.island.creature.animal.herbivore;

public class Duck extends Herbivore {
    private static int id;
    public Duck(){
        this.name=getClass().getSimpleName()+(++id);
    }

    @Override
    public String getName(){
        return name;
    }
}
