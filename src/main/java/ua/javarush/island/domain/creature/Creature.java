package ua.javarush.island.domain.creature;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.locks.ReentrantLock;

@Setter
@Getter
public abstract class Creature {
    private boolean isAlive = true;
    private boolean isReadyForBreeding = true;
    private ReentrantLock lock = new ReentrantLock();
}
