package com.example.springbootmustache.nakji;

import com.example.springbootmustache.nakji.Iface.NaverOpenFeignClient;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Feign;
import feign.Response;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class naverApiClientExample extends commonProperties  {

    private static final String NAVER_API_BASE_URL = "https://openapi.naver.com";

    @Test
    void naverSearchTest() throws IOException {
        String query = "bag";
        JsonNode resultJson = null;
        JsonNode jsonMap = null;

        NaverOpenFeignClient client = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(NaverOpenFeignClient.class, NAVER_API_BASE_URL);

        try (Response response = client.search(query)) {

            if (response.status() == 200) {
                resultJson = readBody(response.body().asInputStream());
                jsonMap = resultJson.findValue("items");
                System.out.println(jsonMap.toString());
            } else {
                System.err.println("Error Response: " + response.toString());
            }
        }
    }
}
