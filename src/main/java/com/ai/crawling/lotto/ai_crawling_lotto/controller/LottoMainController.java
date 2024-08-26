package com.ai.crawling.lotto.ai_crawling_lotto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ai.crawling.lotto.ai_crawling_lotto.service.LottoStoreService;

import dto.StoreInfoCount;

@Controller
public class LottoMainController {
    @Autowired
    private LottoStoreService lottoStoreService;

    @GetMapping("/")
    public String home(Model model) {
        return "redirect:/top-stores";
    }

    @GetMapping("/top-stores")
    public String getTopStores(Model model) {
        List<StoreInfoCount> topStores = lottoStoreService.getTopStores();
        model.addAttribute("topStores", topStores);
        return "top-stores";
    }
}
