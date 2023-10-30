package ua.javarush.island.taskmanager;

import ua.javarush.island.creature.animal.Animal;
import ua.javarush.island.map.Area;
import ua.javarush.island.map.Island;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DailyTask {
    private final Island island;

    public DailyTask(Island island) {
        this.island = island;
    }

    public Queue<Callable<Void>> getTasks() {
        Queue<Callable<Void>> tasks = new ConcurrentLinkedQueue<>();
        Area[][] areas = island.getAreas();
        for (Area[] areas1 : areas) {
            for (Area area : areas1) {
                tasks.add(() -> {
                    dailyLifeCycle(area);
                    return null;
                });
            }
        }
        return tasks;
    }

    private void dailyLifeCycle(Area area) {
        area.getLock().lock();
        everyoneEat(area, area.getAllResidents());
        area.removeAllDeads();
        everyoneBreed(area, area.getAllResidents());
        area.getLock().unlock();
        everyoneMove(area, area.getAllResidents());
        System.out.println("done " + area.getName());
    }

    private static void everyoneMove(Area area, List<? super Animal> allResidents) {
        if (allResidents.isEmpty()) {
            return;
        }
        ListIterator<? super Animal> it = allResidents.listIterator();
        while (it.hasNext()) {
            Animal animal = (Animal) it.next();
            animal.move(area);
            it.remove();
        }
    }

    private static void everyoneBreed(Area area, List<? super Animal> allResidents) {
        if (allResidents.isEmpty()) {
            return;
        }
        ListIterator<? super Animal> it = allResidents.listIterator();
        while (it.hasNext()) {
            Animal animal = (Animal) it.next();
            Optional<Object> breed = animal.breed(area);
            if (breed.isPresent()) {
                it.add((Animal) breed.get());
            }
        }
    }

    private static void everyoneEat(Area area, List<? super Animal> allResidents) {
        if (allResidents.isEmpty()) {
            return;
        }
        for (Object resident : allResidents) {
            Animal animal = (Animal) resident;
            animal.eat(area);
        }
    }
}
