package com.example.springbootmustache.nakji.api.openai.service;

import com.example.springbootmustache.nakji.api.openai.client.OpenAIGptClient;
import com.example.springbootmustache.nakji.common.config.NakjiProperty;
import com.example.springbootmustache.nakji.common.service.NakjiService;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Feign;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class OpenAIApiService extends NakjiService {

    public String openAIGpt(String prompt) {
        if (prompt == null || prompt.isEmpty()) return "";

        String model = "gpt-4o";
        String requestBody = String.format("{\"messages\":[{\"role\":\"user\", \"content\":\"%s\"}],\"model\":\"%s\"}", prompt, model);

        try (Response response = openAIGptConnection(requestBody)) {
            if (response.status() == 200) {
                JsonNode resultJson = readBody(response.body().asInputStream());
                JsonNode jsonMap = resultJson.at("/choices/0/message/content");
                return Optional.ofNullable(jsonMap).map(JsonNode::toString).orElse("");

            } else {
                log.info("OpenAIApiService.openAIGpt Request Status: {}, {}", response.status(), response.body());
                throw new BadRequestException("Bad request with status: " + response.status());
            }

        } catch (Exception e) {
            log.error("OpenAIApiService.openAIGpt Error: ", e);
            return "";
        }
    }

    private Response openAIGptConnection(String body) {
        Response response = null;
        String url = "https://api.openai.com";

        OpenAIGptClient client = Feign.builder().target(OpenAIGptClient.class, url);
        response = client.prompt(NakjiProperty.getOpenaiKey(), body);

        return response;
    }
}