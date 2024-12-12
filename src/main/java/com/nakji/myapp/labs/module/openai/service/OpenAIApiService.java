package com.nakji.myapp.labs.module.openai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nakji.myapp.common.config.ThirdPartyProperties;
import com.nakji.myapp.common.util.NakjiUtil;
import com.nakji.myapp.labs.module.openai.client.OpenAIGptClient;
import feign.Feign;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OpenAIApiService {
    private final ThirdPartyProperties secrets;
    private static final String BASE_URL = "https://api.openai.com";

    public String openAIGpt(String prompt) {
        if (prompt == null || prompt.isEmpty()) return "";

        String model = "gpt-4o";
        String requestBody = String.format("{\"messages\":[{\"role\":\"user\", \"content\":\"%s\"}],\"model\":\"%s\"}", prompt, model);

        try (Response response = openAIGptConnection(requestBody)) {
            if (response.status() == 200) {
                JsonNode resultJson = NakjiUtil.readBody(response.body().asInputStream());
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
        return Feign.builder()
                .target(OpenAIGptClient.class, BASE_URL)
                .prompt(secrets.openai().openaiKey(), body);
    }
}