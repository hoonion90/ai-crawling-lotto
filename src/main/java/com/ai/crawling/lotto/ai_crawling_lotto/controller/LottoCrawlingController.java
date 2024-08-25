package com.ai.crawling.lotto.ai_crawling_lotto.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.crawling.lotto.ai_crawling_lotto.service.LastRoundCheckService;
import com.ai.crawling.lotto.ai_crawling_lotto.service.LottoSpotCrawlerService;

@RestController
public class LottoCrawlingController {
    @Autowired
    private LastRoundCheckService lastRoundCheckService;

    @Autowired
    private LottoSpotCrawlerService lottoSpotCrawlerService;

    @GetMapping("/admin/crawl-lotto-spot")
    public String crawlLotto() throws IOException {
        int latestDrawNo = lastRoundCheckService.getLastestRoundNo();
        int startDrawNo = 262; // 시작 회차

        lottoSpotCrawlerService.crawlAndSave(startDrawNo, latestDrawNo);
        return "lastDrawNo: " + Integer.toString(latestDrawNo) + ", 장소 크롤링 성공적..!";
    }

}
