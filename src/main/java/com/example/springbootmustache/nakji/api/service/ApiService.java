package com.example.springbootmustache.nakji.api.service;

import com.example.springbootmustache.nakji.common.CommonService;
import com.example.springbootmustache.nakji.config.NakjiProperty;
import com.example.springbootmustache.nakji.model.SearchForm;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ApiService {
    private final NakjiProperty nakji;
    private final CommonService commonService;

    public ApiService(CommonService commonService, NakjiProperty nakji) {
        this.commonService = commonService;
        this.nakji = nakji;
    }

    public List<SearchForm> naverSearch(String serviceId, String query) {
        JsonNode resultJson = null;
        JsonNode jsonMap = null;
        List<SearchForm> returnPage = null;

        try (Response response = nakji.naverSearchConnection(serviceId, query)) {
            if (response.status() == 200){
                returnPage = new ArrayList<>();
                resultJson = commonService.readBody(response.body().asInputStream());
                jsonMap = resultJson.findValue("items");

                Iterator<JsonNode> elements = jsonMap.elements();
                JsonNode item = null;
                while (elements.hasNext()) {
                    item = elements.next();
                    returnPage.add(new SearchForm(item.get("title").asText(), item.get("description").asText(), item.get("link").asText()));
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return returnPage;
    }

    public List<SearchForm> googleSearch(String query) {
        JsonNode resultJson = null;
        JsonNode jsonMap = null;
        List<SearchForm> returnPage = null;

        try (Response response = nakji.googleSearchConnection(query)) {
            if (response.status() == 200){
                returnPage = new ArrayList<>();
                resultJson = commonService.readBody(response.body().asInputStream());
                jsonMap = resultJson.findValue("items");

                Iterator<JsonNode> elements = jsonMap.elements();
                JsonNode item = null;
                while (elements.hasNext()) {
                    item = elements.next();
                    returnPage.add(new SearchForm(item.get("title").asText(), item.get("snippet").asText(), item.get("link").asText()));
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return returnPage;
    }

    public String getActiveCode(String param) {
        String scope = "https://www.googleapis.com/auth/bigquery";
        String redirect_uri = "http://localhost:8888/nakji/api"+param;

        return nakji.activeCodeApi(scope, redirect_uri);
    }

    public String getAccessToken(String code) {
        String redirect_uri = "http://localhost:8888/nakji/api/admin/token";
        String accessToken = null;
        JsonNode resultJson = null;

        try (Response response = nakji.accessTokenApi(redirect_uri, code)) {
            if (response.status() == 200) {
                resultJson = commonService.readBody(response.body().asInputStream());
                accessToken = resultJson.findValue("access_token").textValue();

            } else {
                System.err.println("Error Response: " + response.toString());
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return accessToken;
    }
}
