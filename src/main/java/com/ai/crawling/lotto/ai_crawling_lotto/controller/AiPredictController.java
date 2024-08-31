package com.ai.crawling.lotto.ai_crawling_lotto.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.NumberSequence;
import io.github.cdimascio.dotenv.Dotenv;

@Controller
@RequestMapping("/predict")
public class AiPredictController {

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final Dotenv dotenv = Dotenv.configure().filename("info.env").load();
    private static final String API_KEY = dotenv.get("OPENAI_API_KEY");
    private static final String GOOGLEAI_API_KEY = dotenv.get("GOOGLEAI_API_KEY");
    
    @GetMapping("/lotto")
    public String predictNextNumber(Model model) {
        System.out.println(API_KEY);
        // JSON 파일을 읽어 NumberSequence 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        NumberSequence numberSequence;

        try {
            File jsonFile = new File("pastNumbers.json");
            numberSequence = objectMapper.readValue(jsonFile, NumberSequence.class);
        } catch (IOException e) {
            model.addAttribute("error", "Failed to read JSON file");
            return "error"; // 에러 페이지를 반환
        }

        // NumberSequence 객체에서 숫자 리스트를 가져옴
        List<Integer> numbers = numberSequence.getNumbers();
        System.out.println("Received numbers: " + numbers);

        // 프롬프트 생성 및 예측 호출
        String prompt = "The following is a sequence of numbers: " + numbers.toString() +
                ". Please predict the next number in the sequence.";

        // String prediction = callGoogleGemini(prompt);
        String prediction = callChatGPT(prompt);

        model.addAttribute("prediction", prediction);

        // prediction.html 템플릿을 반환
        return "prediction";
    }

    // private String callGoogleGemini(String prompt) {
    //     try (PredictionServiceClient predictionServiceClient = PredictionServiceClient.create()) {
    //         // Google Gemini API 모델 경로
    //         String modelName = String.format("projects/%s/locations/%s/models/%s", projectId, location, modelId);

    //         // 요청 페이로드 생성
    //         PredictRequest predictRequest = PredictRequest.newBuilder()
    //             .setEndpoint(modelName)
    //             .setPayload(PredictRequest.Payload.newBuilder()
    //                 .addInputs(Value.newBuilder().setStringValue(prompt).build())
    //                 .build())
    //             .build();

    //         // 예측 호출
    //         PredictResponse response = predictionServiceClient.predict(predictRequest);

    //         // 응답에서 예측 결과 추출
    //         List<Value> predictions = response.getPredictionsList();
    //         if (!predictions.isEmpty()) {
    //             return predictions.get(0).getStringValue().trim();
    //         } else {
    //             return "No prediction available";
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         throw new RuntimeException("Failed to call Google Gemini API", e);
    //     }
    // }

    private String callChatGPT(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo");  // 또는 "gpt-3.5-turbo"
        body.put("messages", List.of(
            Map.of("role", "system", "content", "You are a number sequence predictor."),
            Map.of("role", "user", "content", prompt)
        ));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
            OPENAI_API_URL, HttpMethod.POST, requestEntity, Map.class);

        // API 응답에서 choices 리스트를 가져옴
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");

        // 첫 번째 choice에서 message를 가져옴
        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");

        // message에서 content를 가져옴
        String prediction = (String) message.get("content");

        return prediction.trim();
    }
}
