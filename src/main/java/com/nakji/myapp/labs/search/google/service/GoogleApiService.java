package com.nakji.myapp.labs.search.google.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nakji.myapp.common.config.ThirdPartyProperties;
import com.nakji.myapp.common.util.NakjiUtil;
import com.nakji.myapp.labs.common.model.SearchForm;
import com.nakji.myapp.labs.search.google.client.GoogleSearchClient;
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
public class GoogleApiService {
    private final ThirdPartyProperties secrets;
    private static final String DEFAULT_QUERY = "bag";
    private static final String BASE_URL = "https://www.googleapis.com";

    public List<SearchForm> googleSearch(String query) {
        List<SearchForm> returnPage = new ArrayList<>();

        try (Response response = googleSearchConnection(query)) {
            if (response.status() == 200) {
                JsonNode resultJson = NakjiUtil.readBody(response.body().asInputStream());
                JsonNode jsonMap = resultJson.findValue("items");

                if (jsonMap != null && jsonMap.isArray()) {
                    jsonMap.forEach(item -> {
                        String title = Optional.ofNullable(item.get("title")).map(JsonNode::asText).orElse("");
                        String snippet = Optional.ofNullable(item.get("snippet")).map(JsonNode::asText).orElse("");
                        String link = Optional.ofNullable(item.get("link")).map(JsonNode::asText).orElse("");

                        returnPage.add(new SearchForm(title, snippet, link));
                    });
                }
            } else {
                log.info("GoogleApiService.googleSearch Request Status: {}, Body: {}", response.status(), response.body().toString());
                throw new BadRequestException("Bad request with status: " + response.status());
            }
        } catch (Exception e) {
            log.error("GoogleApiService.googleSearch Error: ", e);
        }

        return returnPage;
    }

    private Response googleSearchConnection(String search) {
        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(GoogleSearchClient.class, BASE_URL)
                .search(secrets.google().googleKey(),
                        secrets.google().googleCx(),
                        Optional.ofNullable(search).orElse(DEFAULT_QUERY));
    }
}