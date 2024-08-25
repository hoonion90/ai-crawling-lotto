package com.ai.crawling.lotto.ai_crawling_lotto.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class LastRoundCheckService {
    private static final String LASTEST_ROUND_URL = "https://dhlottery.co.kr/store.do?method=topStore&pageGubun=L645&gameNo=5133";

    public int getLastestRoundNo() throws IOException {
        Document document = Jsoup.connect(LASTEST_ROUND_URL).get();
        Element selectedOption = document.select("select#drwNo option[selected]").first();

        return Integer.parseInt(selectedOption.val());
    }
}