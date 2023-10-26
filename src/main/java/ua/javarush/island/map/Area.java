package ua.javarush.island.map;

import ua.javarush.island.creature.animal.Animal;
import ua.javarush.island.creature.plant.Plant;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Area {
    private final int pointX;
    private final int pointY;
    private List<? super Animal> residents;
    private List<? super Plant> plants;

    public Area(List<? super Animal> residents,
                List<? super Plant> plants,
                int pointX, int pointY) {
        this.residents = residents;
        this.plants = plants;
        this.pointX=pointX;
        this.pointY=pointY;
    }
    public Area(int pointX, int pointY) {
        this.pointX=pointX;
        this.pointY=pointY;
    }
    public void setResidents(List<? super Animal> residents){
        this.residents = residents;
    }
    public void setPlants(List<? super Plant> plants){
        this.plants = plants;
    }
    public List<? super Animal> getAllResidents(){
        return residents;
    }
    public List<Animal> getResidents(String animalType){
        return residents.stream().map(Animal.class::cast).filter(animal -> animal.getType().equals(animalType)).toList();
    }
    public List<Plant> getPlants(String plantType){
        return plants.stream().map(Plant.class::cast).filter(plant -> plant.getType().equals(plantType)).toList();
    }
    public <T extends Animal>void addResident(T animal){
        residents.add(animal);
    }
    public String getName(){
        return String.format("Area %d-%d",pointX,pointY);
    }
    public Map<String, Long> getCurrentPopulation(){
        return residents.stream().map(Animal.class::cast).collect(Collectors.groupingBy(Animal::getType, Collectors.counting()));
    }
}
