package com.example.springbootmustache.nakji.api.naver.service;

import com.example.springbootmustache.nakji.api.common.model.SearchForm;
import com.example.springbootmustache.nakji.api.naver.client.NaverSearchClient;
import com.example.springbootmustache.nakji.common.config.NakjiProperty;
import com.example.springbootmustache.nakji.common.service.NakjiService;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Feign;
import feign.Response;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NaverApiService extends NakjiService {

    private static final String DEFAULT_QUERY = "bag";
    private static final String DEFAULT_SERVICE_ID = "blog.json";
    private static final String BASE_URL = "https://openapi.naver.com";

    public List<SearchForm> naverSearch(String serviceId, String query) {
        List<SearchForm> returnPage = new ArrayList<>();

        try (Response response = naverSearchConnection(serviceId, query)) {
            if (response.status() == 200) {
                JsonNode resultJson = readBody(response.body().asInputStream());
                JsonNode jsonMap = resultJson.findValue("items");

                if (jsonMap != null && jsonMap.isArray()) {
                    for (JsonNode item : jsonMap) {
                        returnPage.add(new SearchForm(
                                item.get("title").asText(),
                                item.get("description").asText(),
                                item.get("link").asText()
                        ));
                    }
                }
            } else {
                log.info("NaverApiService.naverSearch Request Status: {}, {}", response.status(), response.body());
                throw new BadRequestException("Bad request with status: " + response.status());

            }
        } catch (Exception e) {
            log.error("NaverApiService.naverSearch Error: ", e);
        }

        return returnPage;
    }

    private Response naverSearchConnection(String id, String search) {
        Response response = null;
        String query = Optional.ofNullable(search).orElse(DEFAULT_QUERY);
        String serviceId = Optional.ofNullable(id).orElse(DEFAULT_SERVICE_ID);

        NaverSearchClient client = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(NaverSearchClient.class, BASE_URL);

        response = client.search(NakjiProperty.getNaverId(), NakjiProperty.getNaverKey(), serviceId, query);

        return response;
    }
}