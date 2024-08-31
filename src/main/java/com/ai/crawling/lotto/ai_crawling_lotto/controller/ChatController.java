package com.ai.crawling.lotto.ai_crawling_lotto.controller;

import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ChatController {

    private final VertexAiGeminiChatModel vertexAiGeminiChatModel;

    public ChatController(VertexAiGeminiChatModel vertexAiGeminiChatModel) {
        this.vertexAiGeminiChatModel = vertexAiGeminiChatModel;
    }

    @GetMapping("/chat")
    public String chat(@RequestBody String message) {

        String vertexAiGeminiResponse = vertexAiGeminiChatModel.call(message);
        String responses = vertexAiGeminiResponse;
        return responses;
    }
}