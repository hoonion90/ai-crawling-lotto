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

    public void crawlAndSave(int startDrawNo, int endDrawNo) throws IOException{
        List<StoreInfo> allStoreInfos = new ArrayList<>();

        endDrawNo = startDrawNo + 1; //임시

        for(int drawNo = startDrawNo; drawNo <= endDrawNo; drawNo ++){
            String url = SPOT_URL + drawNo;
            Document document = Jsoup.connect(url).get();
            Elements rows = document.select("div.group_content:first-of-type tbody tr");

            for(Element row: rows){
                String storeName = row.select("td").get(1).text();
                String location = row.select("td").get(3).text();
                
                StoreInfo storeInfo = new StoreInfo(storeName, location);
                allStoreInfos.add(storeInfo);

                System.out.println(storeName); 
                System.out.println(location); 
            }
        }

        objectMapper.writeValue(new File("lotto_winning_store.json"), allStoreInfos);
    }
}
