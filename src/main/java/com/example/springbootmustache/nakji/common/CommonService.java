package com.example.springbootmustache.nakji.common;

import com.example.springbootmustache.nakji.api.client.GoogleAccessTokenClient;
import com.example.springbootmustache.nakji.api.client.GoogleSearchClient;
import com.example.springbootmustache.nakji.api.client.NaverSearchClient;
import com.example.springbootmustache.nakji.api.client.OpenAIGptClient;
import com.example.springbootmustache.nakji.config.NakjiProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import feign.Feign;
import feign.Response;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("unchecked")
public class CommonService {

    public JsonNode readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body, StandardCharsets.UTF_8);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            String responseBody = lineReader.lines().collect(Collectors.joining("\n"));
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readTree(responseBody);
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }

    public void printJson(String str) throws JsonProcessingException {
        // ObjectMapper 인스턴스 생성
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        // 들여쓰기 수준을 기본값으로 설정
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        List<String> list = objectMapper.readValue(str, List.class);

        System.out.println(objectMapper.writeValueAsString(list));
    }

    public String activeCodeApi(String scope, String redirect_uri) {
        String url = "https://accounts.google.com/o/oauth2/v2/auth";
        String response_type = "code";

        StringBuilder uri = new StringBuilder(url);
        uri.append("?scope=").append(scope)
                .append("&response_type=").append(response_type)
                .append("&client_id=").append(NakjiProperty.getGoogleClientId())
                .append("&redirect_uri=").append(redirect_uri);

        return uri.toString();
    }

    public Response accessTokenApi(String redirect_uri, String activeCode) {
        Response response = null;
        String url = "https://oauth2.googleapis.com";
        String grand_type = "authorization_code";

        StringBuilder param = new StringBuilder();

        GoogleAccessTokenClient client = Feign.builder()
                .target(GoogleAccessTokenClient.class, url);

        param.append("code=").append(activeCode)
                .append("&client_id=").append(NakjiProperty.getGoogleClientId())
                .append("&client_secret=").append(NakjiProperty.getGoogleClientSecret())
                .append("&redirect_uri=").append(redirect_uri)
                .append("&grant_type=").append(grand_type);

        response = client.getAccessToken(param.toString());

        return response;
    }

    public Response naverSearchConnection(String id, String search) {
        Response response = null;
        String query = Optional.ofNullable(search).orElse("bag");
        String serviceId = Optional.ofNullable(id).orElse("blog.json");
        String url = "https://openapi.naver.com";

        NaverSearchClient client = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(NaverSearchClient.class, url);

        response = client.search(NakjiProperty.getNaverId(), NakjiProperty.getNaverKey(), serviceId, query);

        return response;
    }

    public Response googleSearchConnection(String search) {
        Response response = null;
        String query = Optional.ofNullable(search).orElse("bag");
        String url = "https://www.googleapis.com";

        GoogleSearchClient client = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(GoogleSearchClient.class, url);

        response = client.search(NakjiProperty.getGoogleKey(), NakjiProperty.getGoogleCx(), query);

        return response;
    }

    public Response openAIGptConnection(String prompt) {
        Response response = null;
        String url = "https://api.openai.com";

        OpenAIGptClient client = Feign.builder().target(OpenAIGptClient.class, url);
        response = client.prompt(NakjiProperty.getOpenaiKey(), prompt);

        return response;
    }
}