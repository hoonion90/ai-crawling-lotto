package com.ai.crawling.lotto.ai_crawling_lotto.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.StoreInfo;
import dto.StoreInfoCount;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class LottoStoreService {

    public List<StoreInfoCount> getTopStores() {
        List<StoreInfo> stores = loadStoresFromJson();
        Map<StoreInfo, Long> storeCounts = stores.stream()
                .collect(Collectors.groupingBy(
                    store -> new StoreInfo(store.getStoreName(), store.getLocation()),
                    Collectors.counting()
                ));
    
        return storeCounts.entrySet().stream()
                .map(entry -> new StoreInfoCount(entry.getKey(), entry.getValue()))
                .sorted((a, b) -> b.getCount().compareTo(a.getCount()))
                .collect(Collectors.toList());
    }

    private List<StoreInfo> loadStoresFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("lotto_winning_store.json"), new TypeReference<List<StoreInfo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}