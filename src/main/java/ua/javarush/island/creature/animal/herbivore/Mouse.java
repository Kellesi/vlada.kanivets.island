package ua.javarush.island.creature.animal.herbivore;

public class Mouse extends Herbivore {
    private static int id;
    public Mouse(){
        this.name=getClass().getSimpleName()+(++id);
    }

    @Override
    public String getName(){
        return name;
    }
}
