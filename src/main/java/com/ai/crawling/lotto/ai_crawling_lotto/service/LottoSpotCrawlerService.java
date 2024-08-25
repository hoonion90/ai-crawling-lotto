package com.ai.crawling.lotto.ai_crawling_lotto.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.StoreInfo;

@Service
public class LottoSpotCrawlerService {
    private static final String SPOT_URL = "https://dhlottery.co.kr/store.do?method=topStore&pageGubun=L645&gameNo=5133&drwNo=";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void crawlAndSave(int startDrawNo, int endDrawNo) throws IOException {
        List<StoreInfo> allStoreInfos = new ArrayList<>();
        for (int drawNo = startDrawNo; drawNo <= endDrawNo; drawNo++) {
            String url = SPOT_URL + drawNo;
            try {
                Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .timeout(10000)
                    .get();

                Elements rows = document.select("div.group_content:has(h4.title:contains(1등 배출점)) table.tbl_data_col tbody tr");

                // Elements rows = document.select("table.tbl_data_col tbody tr");

                for (Element row : rows) {
                    Elements tds = row.select("td");
                    if (tds.size() >= 4) {
                        String storeName = tds.get(1).text();
                        String location = tds.get(3).text();

                        StoreInfo storeInfo = new StoreInfo(storeName, location);
                        allStoreInfos.add(storeInfo);

                        System.out.println("상호명: " + storeName);
                        System.out.println("소재지: " + location);
                        System.out.println("-------------------");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error crawling draw number " + drawNo + ": " + e.getMessage());
            }
        }

        objectMapper.writeValue(new File("lotto_winning_store.json"), allStoreInfos);
    }
}