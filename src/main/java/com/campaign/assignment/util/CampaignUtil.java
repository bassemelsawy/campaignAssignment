package com.campaign.assignment.util;

import com.campaign.assignment.model.Input;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class CampaignUtil {

    private ClassLoader classLoader;

    Map<String, Set<String>> campaignDataMap = new HashMap<>();
    Map<String, Integer> distributeMap = new HashMap<>();

    public CampaignUtil() {
        this.classLoader = getClass().getClassLoader();
    }

    @PostConstruct
    public void load() {
        InputStream inputStream = classLoader.getResourceAsStream("campaignData.txt");
        assert inputStream != null;
        campaignDataMap = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
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
        Map<String, Integer> countMap;
        List<String> segments = inputLine.getSegments();

        if (segments.size() > 0) countMap = loadAllMatchedCampaigns(segments);
        else return "no campaign";

        Set<String> sameCampaignSet = findMaxCampaign(countMap);
        countMap.clear();

        return sameCampaignSet.size() == 0 ? "no campaign" : distributeLoad(sameCampaignSet);
    }

    private Map<String, Integer> loadAllMatchedCampaigns(List<String> segments) {
        Map<String, Integer> countMap = new HashMap<>();
        for (String segment : segments) {
            campaignDataMap.getOrDefault(segment, new HashSet<>()).forEach(elm ->
                    countMap.put(elm, countMap.getOrDefault(elm, 0) + 1)
            );
        }
        return countMap;
    }

    private Set<String> findMaxCampaign(Map<String, Integer> countMap) {
        AtomicInteger max = new AtomicInteger();
        Set<String> sameCampaignSet = new HashSet<>();
        countMap.forEach((key, value) -> {
            if (value > max.get()) {
                sameCampaignSet.clear();
                sameCampaignSet.add(key);
                max.set(value);
            } else if (value == max.get()) {
                sameCampaignSet.add(key);
            }
        });
        return sameCampaignSet;
    }

    private String distributeLoad(Set<String> sameCampaignSet) {
        AtomicInteger count = new AtomicInteger(Integer.MAX_VALUE);
        AtomicReference<String> element = new AtomicReference<>("");
        sameCampaignSet.forEach(elm -> {
            if (distributeMap.getOrDefault(elm, 0) < count.get()) {
                count.set(distributeMap.getOrDefault(elm, 0));
                element.set(elm);
            }
        });
        distributeMap.put(element.get(), distributeMap.getOrDefault(element.get(), 0) + 1);
        return element.get();
    }
}

