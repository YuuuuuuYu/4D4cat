package com.nakji.myapp.labs.naver.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nakji.myapp.common.property.ThirdPartyProperties;
import com.nakji.myapp.common.util.NakjiUtil;
import com.nakji.myapp.labs.common.model.SearchForm;
import com.nakji.myapp.labs.naver.client.NaverProfileClient;
import com.nakji.myapp.labs.naver.client.NaverSearchClient;
import feign.Feign;
import feign.Response;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NaverApiService {
    private static final String DEFAULT_QUERY = "bag";
    private static final String DEFAULT_SERVICE_ID = "blog.json";
    private static final String BASE_URL = "https://openapi.naver.com";

    private final ThirdPartyProperties secrets;

    public List<SearchForm> naverSearch(String serviceId, String query) {
        List<SearchForm> returnPage = new ArrayList<>();

        try (Response response = naverSearchConnection(serviceId, query)) {
            if (response.status() == 200) {
                JsonNode resultJson = NakjiUtil.readBody(response.body().asInputStream());
                JsonNode jsonMap = resultJson.findValue("items");

                if (jsonMap != null && jsonMap.isArray()) {
                    jsonMap.forEach(item -> {
                        String title = Optional.ofNullable(item.get("title")).map(JsonNode::asText).orElse("");
                        String description = Optional.ofNullable(item.get("description")).map(JsonNode::asText).orElse("");
                        String link = Optional.ofNullable(item.get("link")).map(JsonNode::asText).orElse("");

                        returnPage.add(new SearchForm(title, description, link));
                    });
                }
            } else {
                log.info("NaverApiService.naverSearch Request Status: {}, Body: {}", response.status(), response.body().toString());
                throw new BadRequestException("Bad request with status: " + response.status());
            }
        } catch (Exception e) {
            log.error("NaverApiService.naverSearch Error: ", e);
        }

        return returnPage;
    }

    private Response naverSearchConnection(String id, String search) {
        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(NaverSearchClient.class, BASE_URL)
                .search(secrets.naver().naverId(),
                        secrets.naver().naverKey(),
                        Optional.ofNullable(id).orElse(DEFAULT_SERVICE_ID),
                        Optional.ofNullable(search).orElse(DEFAULT_QUERY));
    }

    public Response naverProfileConnection(String tokenType, String tokenValue) {
        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(NaverProfileClient.class, BASE_URL)
                .search(tokenType, tokenValue);
    }
}