package com.campaign.assignment.util;

import com.campaign.assignment.model.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Component
public class CampaignUtil {

    final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private ClassLoader classLoader;

    public CampaignUtil() throws IOException {
        load();
    }

    Map<String, Set<String>> map = new HashMap<>();
    Map<String, Integer> distributeMap = new HashMap<>();

    @Autowired
    ResourceLoader resourceLoader;

    public void load() {
        this.classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("campaignData.txt");
        map = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .map(line -> line.split(" "))
                .distinct()
                .collect(HashMap::new,
                        (map, arr) -> {
                            for (int i = 1; i < arr.length; i++) {
                                map.putIfAbsent(arr[i], new HashSet<>());
                                map.get(arr[i]).add(arr[0]);
                            }
                        },
                        (map1, map2) -> { });
    }

    public String process(Input inputLine) {
        Map<String, Integer> countMap = new HashMap<>();
        List<String> listOfSegments = inputLine.getSegments();

        System.out.println(listOfSegments.toString());

        for (int i = 1; i < listOfSegments.size(); i++) {
            map.getOrDefault(listOfSegments.get(i), new HashSet<>()).forEach(elm -> {
                countMap.put(elm, countMap.getOrDefault(elm, 0) + 1);
            });
        }

        AtomicInteger max = new AtomicInteger();
        Set<String> set = new HashSet<>();
        countMap.forEach((key, value) -> {
            if (value > max.get()) {
                set.clear();
                set.add(key);
                max.set(value);
            } else if (value == max.get()) {
                set.add(key);
            }
        });
        AtomicReference<String>  element = new AtomicReference<>("");
        if (set.size() == 0)
            LOGGER.info("no campaign");
        else {
            countMap.clear();
            AtomicInteger count = new AtomicInteger(Integer.MAX_VALUE);

            set.forEach(elm -> {
                if (distributeMap.getOrDefault(elm, 0) < count.get()) {
                    count.set(distributeMap.getOrDefault(elm, 0));
                    element.set(elm);
                }
            });
            distributeMap.put(element.get(), distributeMap.getOrDefault(element.get(), 0) + 1);
            LOGGER.info("" + element.get());
        }
        return element.get();
    }
}
