package com.example.springbootmustache.nakji.api.service;

import com.example.springbootmustache.nakji.api.client.OpenAIGptClient;
import com.example.springbootmustache.nakji.common.CommonService;
import com.example.springbootmustache.nakji.config.NakjiProperty;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Feign;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiServiceTest {

    @Mock
    private NakjiProperty nakji;

    @Mock
    private CommonService commonService;

    @InjectMocks
    private ApiService apiService;

    @BeforeEach
    void setup() {}

    @Test
    void naverSearch() {
    }

    @Test
    void googleSearch() {
    }

    @Test
    @DisplayName("OPENAI Gpt-4 Callboack Test")
    @Disabled
    void openAIGpt() throws IOException {
        String prompt = "안녕하세요";
        String url = "https://api.openai.com";
        String OPENAI_KEY = ""; // Test 할 때만 기입
        String model = "gpt-4";
        String requestBody = "{\"messages\":[{\"role\":\"user\", \"content\":\""+prompt+"\"}],\"model\":\""+model+"\"}";

        // given
        OpenAIGptClient client = Feign.builder().target(OpenAIGptClient.class, url);
        Response mockResponse = client.prompt(OPENAI_KEY, prompt);

        // when
        when(nakji.openAIGptConnection(requestBody)).thenReturn(mockResponse);
        String resultMsg = apiService.openAIGpt(prompt);

        assertNull(apiService.openAIGpt(prompt));
        assertEquals(resultMsg, responseToJson(mockResponse));
        verify(apiService).openAIGpt(prompt);
    }

    String responseToJson(Response response) throws IOException {
        JsonNode toJson = commonService.readBody(response.body().asInputStream());
        JsonNode resultJson = toJson.at("/choices/0/message/content");

        return resultJson.toString();
    }
}