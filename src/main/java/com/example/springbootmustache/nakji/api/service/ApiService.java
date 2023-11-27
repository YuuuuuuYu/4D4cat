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

    @Autowired
    NakjiProperty nakji;

    @Autowired
    CommonService commonService;

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
}
