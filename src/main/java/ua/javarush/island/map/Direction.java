package ua.javarush.island.map;

import lombok.Getter;

@Getter
public enum Direction {
    EAST(1, 0),
    WEST(-1, 0),
    NORTH(0, -1),
    SOUTH(0, 1);

    private final int deltaX;
    private final int deltaY;

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
}
