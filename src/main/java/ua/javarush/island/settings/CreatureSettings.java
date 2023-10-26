package ua.javarush.island.settings;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.javarush.island.creature.Creature;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CreatureSettings {
    private static Map<String, LinkedHashMap> defaultSettingsMap = readSettingsToMap();

    private CreatureSettings() {

    }

    public static Map<String, LinkedHashMap> getSettingMap() {
        Map<String, LinkedHashMap> map = new HashMap<>();
        map.putAll(defaultSettingsMap);
        return map;
    }

    private static Map<String, LinkedHashMap> readSettingsToMap() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/java/ua/javarush/island/settings/creatures.json");
        try {
            List<? super Creature> settingsList = mapper.readValue(file, new TypeReference<>() {
            });
            defaultSettingsMap = settingsList.stream()
                    .map(LinkedHashMap.class::cast)
                    .collect(Collectors.toMap(map -> map.get("type").toString(), values -> values));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return defaultSettingsMap;
    }
}
