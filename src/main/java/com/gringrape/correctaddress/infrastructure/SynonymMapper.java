package com.gringrape.correctaddress.infrastructure;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SynonymMapper {
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws IOException {
        File regionMappingfile = ResourceUtils.getFile("classpath:region_synonyms.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(regionMappingfile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\\|");

                String from = values[0];
                String to = values[1];

                this.addMapping(from, to);
            }
        }

        File citiesFile = ResourceUtils.getFile("classpath:cities.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(citiesFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\\|");

                String city = values[0];

                this.addCityMapping(city);
            }
        }
    }

    private void addCityMapping(String city) {
        if (city.length() >= 3 && !city.contains(" ")) {
            dictionary.put(cutLast(city), city);
        }

        if (city.contains(" ")) {
            String[] s = city.split(" ");
            String a = s[0];
            String b = s[1];

            dictionary.put(cutLast(a) + " " + cutLast(b), city);
        }
    }

    private static String cutLast(String city) {
        if (city.length() <= 2) {
            return city;
        }
        return city.substring(0, city.length() - 1);
    }

    private final Map<String, String> dictionary = new HashMap<>();

    public String map(String key) {
        if (!dictionary.containsKey(key)) {
            return key;
        }
        return dictionary.get(key);
    }

    public void addMapping(String key, String value) {
        dictionary.put(key, value);
    }

    public String mapFirst(String query) {
        List<String> keys = dictionary.keySet().stream().toList();
        for (String key : keys) {
            if (query.startsWith(key) && query.charAt(key.length()) == ' ') {
                return dictionary.get(key) + cutFirst(query, key);
            }
        }
        return query;
    }

    private String cutFirst(String query, String key) {
        return query.substring(key.length());
    }
}
