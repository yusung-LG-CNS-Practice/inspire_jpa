package com.example.inspire_jpa.features.openapi.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.inspire_jpa.features.openapi.domain.ForcastRequestDTO;
import com.example.inspire_jpa.features.openapi.domain.ForcastResponseDTO;
import com.example.inspire_jpa.features.openapi.util.CategoryCode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ForcastService {

    private final ObjectMapper objectMapper;

    @Value("${openapi.servicekey}")
    private String key;

    @Value("${openapi.callBackUrl}")
    private String endPoint;

    @Value("${openapi.dataType}")
    private String type;

    public List<ForcastResponseDTO> connection(ForcastRequestDTO request) {
        System.out.println("debug >>>> fcst service connertion");
        String requestUrl = UriComponentsBuilder.fromUriString(endPoint)
                .queryParam("serviceKey", key)
                .queryParam("beach_num", request.getBeach_num())
                .queryParam("base_date", request.getBase_date())
                .queryParam("base_time", request.getBase_time())
                .queryParam("dataType", type)
                .toUriString();

        System.out.println("drbug >>>> fcst service requestUrl : " + requestUrl);

        HttpURLConnection http = null;
        InputStream stream = null;
        String result = null;

        try {
            URL url = new URL(requestUrl);
            http = (HttpURLConnection) url.openConnection();
            int status = http.getResponseCode();
            System.out.println("debug >>>> fcst service connection status : " + status);
            if (status == 200) {
                return readString(http.getInputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<ForcastResponseDTO> readString(InputStream stream) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String input = null;
        StringBuilder result = new StringBuilder();
        while ((input = br.readLine()) != null) {
            result.append(input);
        }
        System.out.println("fcst service readString ");
        System.out.println(result.toString());
        br.close();
        return parseJson(result.toString());
    }

    public List<ForcastResponseDTO> parseJson(String result) throws Exception {

        JsonNode node = objectMapper.readTree(result);
        JsonNode item = node.findValue("item");

        List<ForcastResponseDTO> list = null;

        if (item.isArray()) {
            list = Arrays.asList(objectMapper.treeToValue(item, ForcastResponseDTO[].class));
        }

        list.stream().forEach(System.out::println);

        // Q) stream() - map() - toList()
        // fcstValue + unit, categoryName에 값을 할당하는 구현
        list = list.stream().map(dto -> {
            dto.setCategoryName(CategoryCode.valueOf(dto.getCategory()).getName());
            String value = CategoryCode.getCodeValue(dto.getCategory(), dto.getFcstValue());
            String unit = CategoryCode.valueOf(dto.getCategory()).getUnit();
            dto.setFcstValue(value + unit);
            return dto;
        }).toList();

        System.out.println();
        list.stream().forEach(System.out::println);
        
        return list;
    }
}
