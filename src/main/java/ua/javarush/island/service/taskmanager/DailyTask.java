package ua.javarush.island.service.taskmanager;

import ua.javarush.island.domain.creature.PlantType;
import ua.javarush.island.domain.creature.animal.Animal;
import ua.javarush.island.domain.creature.plant.Plant;
import ua.javarush.island.domain.map.Area;
import ua.javarush.island.domain.map.Island;
import ua.javarush.island.service.statistic.StatisticCollector;
import ua.javarush.island.util.worldgenerator.CreatureFactory;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DailyTask {
    private final StatisticCollector statistic;
    private final Area[][] areas;

    public DailyTask(Island island, StatisticCollector statistic) {
        this.statistic = statistic;
        areas = island.getAreas();
    }

    public StatisticCollector getStatisticManager() {
        return statistic;
    }

    public Queue<Callable<Void>> getBeforeNewDayTasks() {
        Queue<Callable<Void>> tasks = new ConcurrentLinkedQueue<>();
        for (Area[] areas1 : areas) {
            for (Area area : areas1) {
                tasks.add(() -> {
                    increasePlants(area);
                    return null;
                });
                resetAnimalFlags(area);
            }
        }
        return tasks;
    }

    public Queue<Callable<Void>> getEatingTasks() {
        Queue<Callable<Void>> tasks = new ConcurrentLinkedQueue<>();
        for (Area[] areas1 : areas) {
            for (Area area : areas1) {
                tasks.add(() -> {
                    everyoneEat(area);
                    return null;
                });
            }
        }
        return tasks;
    }

    public Queue<Callable<Void>> getBreedingTasks() {
        Queue<Callable<Void>> tasks = new ConcurrentLinkedQueue<>();
        for (Area[] areas1 : areas) {
            for (Area area : areas1) {
                tasks.add(() -> {
                    everyoneBreed(area);
                    return null;
                });
            }
        }
        return tasks;
    }

    public Queue<Callable<Void>> getMovingTasks() {
        Queue<Callable<Void>> tasks = new ConcurrentLinkedQueue<>();
        for (Area[] areas1 : areas) {
            for (Area area : areas1) {
                tasks.addAll(everyoneMoveByOne(area));
            }
        }
        return tasks;
    }

    public Queue<Callable<Void>> getEatingTasksForASingleArea() {
        Queue<Callable<Void>> tasks = new ConcurrentLinkedQueue<>();
        for (Area[] areas1 : areas) {
            for (Area area : areas1) {
                tasks.addAll(everyoneEatByOne(area));
            }
        }
        return tasks;
    }

    public Queue<Callable<Void>> getBreedingTasksForASingleArea() {
        Queue<Callable<Void>> tasks = new ConcurrentLinkedQueue<>();
        for (Area[] areas1 : areas) {
            for (Area area : areas1) {
                tasks.addAll(everyoneBreedByOne(area));
            }
        }
        return tasks;
    }

    private void everyoneEat(Area area) {
        Map<Class<? extends Animal>, List<Animal>> allResidentsMap = area.getAllResidents();
        for (Map.Entry<Class<? extends Animal>, List<Animal>> entry : allResidentsMap.entrySet()) {
            List<Animal> allResidents = entry.getValue();
            if (allResidents.isEmpty()) {
                return;
            }
            for (Animal resident : allResidents) {
                resident.eat(area);
            }
            statistic.increaseDeadCounter(area.removeAllDeads());
            statistic.increaseEatenPlants(area.removeAllEatenPlants());
        }
    }

    private void everyoneBreed(Area area) {
        Map<Class<? extends Animal>, List<Animal>> allResidentsByClass = area.getAllResidents();
        for (Map.Entry<Class<? extends Animal>, List<Animal>> entry : allResidentsByClass.entrySet()) {
            List<Animal> allResidents = entry.getValue();
            if (allResidents.isEmpty()) {
                return;
            }
            ListIterator<Animal> iterator = allResidents.listIterator();
            while (iterator.hasNext()) {
                Optional<Animal> breed = iterator.next().breed(area);
                if (breed.isPresent()) {
                    iterator.add(breed.get());
                    statistic.increaseNewbornCounter();
                }
            }
        }
    }

    private void resetAnimalFlags(Area area) {
        Map<Class<? extends Animal>, List<Animal>> allResidentsMap = area.getAllResidents();
        for (Map.Entry<Class<? extends Animal>, List<Animal>> entry : allResidentsMap.entrySet()) {
            List<Animal> allResidents = entry.getValue();
            if (allResidents.isEmpty()) {
                return;
            }
            for (Animal resident : allResidents) {
                resident.newDaySets();
            }
        }
    }

    private void increasePlants(Area area) {
        Map<Class<? extends Plant>, List<Plant>> allPlants = area.getAllPlants();
        for (Map.Entry<Class<? extends Plant>, List<Plant>> entry : allPlants.entrySet()) {
            List<Plant> plants = entry.getValue();
            if (plants.isEmpty()) {
                area.addNewPlants(CreatureFactory.getPlants(PlantType.values()));
            }
            ListIterator<Plant> iterator = plants.listIterator();
            while (iterator.hasNext()) {
                Optional<Plant> breed = iterator.next().breed(area);
                if (breed.isPresent()) {
                    iterator.add(breed.get());
                }
            }
        }
    }

    private Queue<Callable<Void>> everyoneMoveByOne(Area area) {
        Queue<Callable<Void>> tasks = new ConcurrentLinkedQueue<>();
        Map<Class<? extends Animal>, List<Animal>> allResidentsMap = area.getAllResidents();
        for (Map.Entry<Class<? extends Animal>, List<Animal>> entry : allResidentsMap.entrySet()) {
            List<Animal> allResidents = entry.getValue();
            if (!allResidents.isEmpty()) {
                for (Animal resident : allResidents) {
                    tasks.add(() -> {
                        resident.move(area);
                        return null;
                    });
                }
            }
        }
        return tasks;
    }

    private Queue<Callable<Void>> everyoneEatByOne(Area area) {
        Queue<Callable<Void>> tasks = new ConcurrentLinkedQueue<>();
        Map<Class<? extends Animal>, List<Animal>> allResidentsMap = area.getAllResidents();
        for (Map.Entry<Class<? extends Animal>, List<Animal>> entry : allResidentsMap.entrySet()) {
            List<Animal> allResidents = entry.getValue();
            if (!allResidents.isEmpty()) {
                for (Animal resident : allResidents) {
                    tasks.add(() -> {
                        resident.eat(area);
                        return null;
                    });
                }
            }
        }
        return tasks;
    }

    private Queue<Callable<Void>> everyoneBreedByOne(Area area) {
        Queue<Callable<Void>> tasks = new ConcurrentLinkedQueue<>();
        Map<Class<? extends Animal>, List<Animal>> allResidentsMap = area.getAllResidents();
        for (Map.Entry<Class<? extends Animal>, List<Animal>> entry : allResidentsMap.entrySet()) {
            List<Animal> allResidents = entry.getValue();
            if (!allResidents.isEmpty()) {
                for (Animal resident : allResidents) {
                    tasks.add(() -> {
                        resident.breed(area);
                        return null;
                    });
                }
            }
        }
        return tasks;
    }
}
