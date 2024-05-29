package com.example.springbootmustache.nakji.api.google.service;

import com.example.springbootmustache.nakji.api.common.model.SearchForm;
import com.example.springbootmustache.nakji.api.google.client.GoogleAccessTokenClient;
import com.example.springbootmustache.nakji.api.google.client.GoogleSearchClient;
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
public class GoogleApiService extends NakjiService {

    private static final String DEFAULT_QUERY = "bag";
    private static final String BASE_URL = "https://www.googleapis.com";

    public List<SearchForm> googleSearch(String query) {
        List<SearchForm> returnPage = new ArrayList<>();

        try (Response response = googleSearchConnection(query)) {
            if (response.status() == 200) {
                JsonNode resultJson = readBody(response.body().asInputStream());
                JsonNode jsonMap = resultJson.findValue("items");

                if (jsonMap != null && jsonMap.isArray()) {
                    for (JsonNode item : jsonMap) {
                        returnPage.add(new SearchForm(
                                item.get("title").asText(),
                                item.get("snippet").asText(),
                                item.get("link").asText()
                        ));
                    }
                }

            } else {
                log.info("GoogleApiService.googleSearch Request Status: {}, {}", response.status(), response.body());
                throw new BadRequestException("Bad request with status: " + response.status());
            }
        } catch (Exception e) {
            log.error("GoogleApiService.googleSearch Error: ", e);
        }

        return returnPage;
    }

    private Response googleSearchConnection(String search) {
        Response response = null;
        String query = Optional.ofNullable(search).orElse(DEFAULT_QUERY);

        GoogleSearchClient client = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(GoogleSearchClient.class, BASE_URL);

        response = client.search(NakjiProperty.getGoogleKey(), NakjiProperty.getGoogleCx(), query);

        return response;
    }

    public String getActiveCode(String param) {
        String scope = "https://www.googleapis.com/auth/bigquery";
        String redirect_uri = "http://localhost:8888/nakji/api"+param;

        return activeCodeApi(scope, redirect_uri);
    }

    public String getAccessToken(String code) {
        String redirect_uri = "http://localhost:8888/nakji/api/admin/token";

        try (Response response = accessTokenApi(redirect_uri, code)) {
            if (response.status() == 200) {
                JsonNode resultJson = readBody(response.body().asInputStream());

                return resultJson.findValue("access_token").textValue();
            } else {
                log.error("GoogleApiService.getAccessToken Error: {}", response.toString());
            }

        } catch(Exception e) {
            log.error("GoogleApiService.getAccessToken Error: ", e);
        }

        return "";
    }

    private String activeCodeApi(String scope, String redirect_uri) {
        String url = "https://accounts.google.com/o/oauth2/v2/auth";
        String response_type = "code";

        StringBuilder uri = new StringBuilder(url);
        uri.append("?scope=").append(scope)
                .append("&response_type=").append(response_type)
                .append("&client_id=").append(NakjiProperty.getGoogleClientId())
                .append("&redirect_uri=").append(redirect_uri);

        return uri.toString();
    }

    private Response accessTokenApi(String redirect_uri, String activeCode) {
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
}