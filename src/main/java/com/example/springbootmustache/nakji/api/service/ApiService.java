package com.example.springbootmustache.nakji.api.service;

import com.example.springbootmustache.nakji.common.CommonService;
import com.example.springbootmustache.nakji.config.NakjiProperty;
import com.example.springbootmustache.nakji.model.SearchForm;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ApiService {

    @Autowired
    NakjiProperty nakji;

    @Autowired
    CommonService commonService;

    public List<SearchForm> naverSearch(String query) {
        JsonNode resultJson = null;
        JsonNode jsonMap = null;
        HttpURLConnection connection = null;
        List<SearchForm> returnPage = null;
        int responseCode = -1;

        try {
            connection = nakji.naverSearchConnection("", query);
            responseCode = connection.getResponseCode();
            returnPage = new ArrayList<>();

            if (responseCode == HttpURLConnection.HTTP_OK){
                resultJson = commonService.readBody(connection.getInputStream());
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

        } finally {
            if (connection != null) connection.disconnect();
        }

        return returnPage;
    }

    public List<SearchForm> googleSearch(String query) {
        JsonNode resultJson = null;
        JsonNode jsonMap = null;
        HttpURLConnection connection = null;
        List<SearchForm> returnPage = null;
        int responseCode = -1;

        try {
            connection = nakji.googleSearchConnection(query);
            responseCode = connection.getResponseCode();
            returnPage = new ArrayList<>();

            if (responseCode == HttpURLConnection.HTTP_OK){
                resultJson = commonService.readBody(connection.getInputStream());
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

        } finally {
            if (connection != null) connection.disconnect();
        }

        return returnPage;
    }
}
