package ua.javarush.island.creature.animal.predator;

public class Bear extends Predator {
    private static int id;
    private static int maxPopulation =5;
    private static int defaultWeight =500;
    private static int foodNeeded =80;
    private static int stepSize =2;

    protected int movingRange;
    public Bear(){
        this.name=getClass().getSimpleName()+(++id);
        currentWeight=defaultWeight;
    }
    @Override
    public String getName(){
        return name;
    }
}
