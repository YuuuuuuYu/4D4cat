package com.example.springbootmustache.nakji;

import com.example.springbootmustache.nakji.Iface.NaverOpenFeignClient;
import feign.Feign;
import feign.Response;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class naverApiClientExample {

    private static final String NAVER_API_BASE_URL = "https://openapi.naver.com";

    @Test
    void naverSearchTest() throws IOException {
        String query = "bag";

        NaverOpenFeignClient client = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(NaverOpenFeignClient.class, NAVER_API_BASE_URL);

        try (Response response = client.search(query)) {

            if (response.status() == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(response.body().asInputStream(), StandardCharsets.UTF_8))) {
                    System.out.println(br.lines().collect(Collectors.joining("\n")));
                }
            } else {
                System.err.println("Error Response: " + response.toString());
            }
        }
    }
}
