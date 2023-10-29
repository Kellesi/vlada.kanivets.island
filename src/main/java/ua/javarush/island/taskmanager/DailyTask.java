package ua.javarush.island.taskmanager;

import ua.javarush.island.creature.animal.Animal;
import ua.javarush.island.map.Area;
import ua.javarush.island.map.Island;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DailyTask {
    Island island;

    public DailyTask(Island island) {
        this.island = island;
    }

    public Queue<Runnable> getTasks() {
        Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();
        Area[][] areas = island.getAreas();
        for (Area[] areas1 : areas) {
            for (Area area : areas1) {
                tasks.add(() -> dailyLifeCycle(area));
            }
        }
        return tasks;
    }

    private void dailyLifeCycle(Area area) {
        area.getLock().lock();
        everyoneEat(area, area.getAllResidents());
        System.out.println("everyone eated" + area.getName());
        area.removeAllDeads();
        List<? super Animal> allResidents = area.getAllResidents();
        everyoneBreed(area, allResidents);
        System.out.println("everyone beeded" + area.getName());
        area.getLock().unlock();
        everyoneMove(area, allResidents);
        System.out.println("everyone moved" + area.getName());
    }

    private static void everyoneMove(Area area, List<? super Animal> allResidents) {
        for (Object resident : allResidents) {
            Animal animal = (Animal) resident;
            animal.move(area);
        }
    }

    private static void everyoneBreed(Area area, List<? super Animal> allResidents) {
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
        for (Object resident : allResidents) {
            Animal animal = (Animal) resident;
            animal.eat(area);
        }
    }
}
