package com.example.springbootmustache.nakji;

import com.example.springbootmustache.nakji.Iface.OpenAIGptClient;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Feign;
import feign.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class openAIGptTest extends commonProperties  {
    private static final String BASE_URL = "https://api.openai.com";

    @DisplayName("GPT API 호출")
    @Test
    void gptTest() throws IOException {
        JsonNode resultJson = null;
        JsonNode jsonMap = null;

        String requestBody = "{\"messages\":[{\"role\":\"user\", \"content\":\"hello\"}],\"model\":\"gpt-4\"}";
        OpenAIGptClient client = Feign.builder().target(OpenAIGptClient.class, BASE_URL);

        try (Response response = client.prompt(requestBody)) {
            if (response.status() == 200) {
                resultJson = readBody(response.body().asInputStream());
                jsonMap = resultJson.at("/choices/0/message/content");
                System.out.println(jsonMap.toString());
            } else {
                System.err.println("Error Response: " + response.toString());
                System.err.println(new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8));
            }
        }
    }
}