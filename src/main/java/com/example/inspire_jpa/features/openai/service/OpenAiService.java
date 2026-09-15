package com.example.inspire_jpa.features.openai.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.inspire_jpa.features.openai.domain.QuizResponseDTO;
import com.example.inspire_jpa.features.openai.domain.RecommandResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
open ai message format
messages {
    Map - {
        role : system, user, assistant
        content : xxxxxxx
    }
}
*/

@Service
@RequiredArgsConstructor
public class OpenAiService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper; // 문자열을 json으로 바꿔주는 역할
    private final OkHttpClient okHttpClient;

    @Value("${spring.ai.openai.api-key}")
    private String key;

    @Value("${spring.ai.openai.chat.options.model}")
    private String model;

    // endPoint
    // https://api.openai.com/v1, https://api.openai.com/v1/chat/completions
    private String endPoint = "https://api.openai.com/v1/chat/completions";

    // public RecommandResponseDTO recommand(String weather, String location) {
    // System.out.println("debug >>>> openai service recommand params : " + model);
    // System.out.println("debug >>>> openai service recommand params : " +
    // weather);
    // System.out.println("debug >>>> openai service recommand params : " +
    // location);

    // String prompt = """
    // 너는 날씨 전문가이고 맛있는 음식을 즐겨하는 인공지능전문가야.
    // 현재날씨에 따른 지역맛집을 추천해줘.
    // 날씨에 따라 먹고싶은 음식이 다를 수 있으며 특정지역의 맛집을 추천해줘.
    // <조건>
    // - 날씨 : "%s"
    // - 위치 : "%s"
    // </조건>
    // <출력예시>
    // {
    // "weather" : "날씨",
    // "location" : "위치",
    // "restaurants" : [
    // {"name" : "음식점명", "category" : "분류", "reason" : "추천이유"}
    // ]
    // }
    // </출력예시>
    // """.formatted(weather, location);

    // Map<String, Object> messages = new HashMap<>();
    // messages.put("model", model);

    // Map<String, Object> user = new HashMap<>();
    // user.put("role", "user");
    // user.put("content", prompt);

    // Map<String, Object> system = new HashMap<>();
    // system.put("role", "system");
    // system.put("content", "전처리된 json 형태로만 반환해줘.");

    // messages.put("messages", List.of(user, system));

    // // Object(Map) -> JSON
    // String requestJson = null;
    // try {
    // requestJson = objectMapper.writeValueAsString(messages);
    // } catch (JsonProcessingException e) {
    // e.printStackTrace();
    // }

    // System.out.println("debug >>>> requestJson");
    // System.out.println(requestJson);

    // Request request = new Request.Builder()
    // .url(endPoint)
    // .header("Authorization", "Bearer " + key)
    // .header("Content-Type", "application/json")
    // .post(RequestBody.create(requestJson, MediaType.parse("application/json")))
    // .build();

    // Response response = null;
    // try {
    // response = okHttpClient.newCall(request).execute();
    // System.out.println("debug >>>> response");
    // System.out.println(response);

    // ///////////////////////////////

    // String responseJson = response.body().string();
    // // System.out.println(responseJson);

    // JsonNode node = objectMapper.readTree(responseJson);
    // String exr = node.at("/choices/0/message/content").asText();
    // System.out.println(exr);

    // RecommandResponseDTO result = objectMapper.readValue(exr,
    // RecommandResponseDTO.class);
    // return result;

    // } catch (IOException e) {
    // e.printStackTrace();
    // }

    // return null;
    // }

    // case 02
    // yml -> OpenAi Configuration(mode, key) - ChatClient - Prompt()
    public RecommandResponseDTO recommand(String weather, String location) {
        System.out.println("debug >>>> openai service recommand params : " + model);
        System.out.println("debug >>>> openai service recommand params : " + weather);
        System.out.println("debug >>>> openai service recommand params : " + location);

        // ChatClient - OpenAI api 직접호출하지 않고
        // ChatClient(endPoint)
        RecommandResponseDTO result = chatClient
                .prompt()
                .system("전처리된 json 형태로만 반환해줘.")
                .user("""
                        너는 날씨 전문가이고 맛있는 음식을 즐겨하는 인공지능전문가야.
                        현재날씨에 따른 지역맛집을 추천해줘.
                        날씨에 따라 먹고싶은 음식이 다를 수 있으며 특정지역의 맛집을 추천해줘.
                        <조건>
                            - 날씨 : "%s"
                            - 위치 : "%s"
                        </조건>
                        <출력예시>
                        {
                            "weather" : "날씨",
                            "location" : "위치",
                            "restaurants" : [
                                {"name" : "음식점명", "category" : "분류", "reason" : "추천이유"}
                            ]
                        }
                        </출력예시>
                        """.formatted(weather, location))
                .call()
                .entity(RecommandResponseDTO.class);

        return result;
    }

    public QuizResponseDTO quiz(String subject) {
        System.out.println("debug >>>> openai service recommand params : " + subject);

        // ChatClient - OpenAI api 직접호출하지 않고
        // ChatClient(endPoint)
        QuizResponseDTO result = chatClient
                .prompt()
                .system("""
                        너는 멋진 인공지능이고 국가공인 문제출제 전문가야.
                        반드시 json으로만 반환해줘.
                        """)
                .user("""
                        너는 문제출제 전문위원이고 %s 전공이며 박사학위를 가지고 있어.
                        <조건>
                            - 10문제를 만들어줘.
                            - ` 쓰지마.
                            - 무조건 json 형채로 만들어줘.
                        </조건>
                        <출력예시>
                        {
                            "quizs" : [
                                {"question" : <문제내용>, 
                                 "option" : [보기1, 보기2, 보기3, 보기4], 
                                 "answer" : <정답>,
                                 "desc : <해설>}
                            ]
                        }
                        </출력예시>
                        """.formatted(subject))
                .call()
                .entity(QuizResponseDTO.class);

        return result;
    }
}
