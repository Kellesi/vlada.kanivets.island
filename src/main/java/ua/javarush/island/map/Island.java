package ua.javarush.island.map;

public class Island {
    private final int width;
    private final int height;
    private Area[][] areas;
    public Island(int width,int height){
        this.width=width;
        this.height = height;
        areas = new Area[width][height];
    }


}
