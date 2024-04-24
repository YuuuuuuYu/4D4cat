package com.example.springbootmustache.nakji;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.HttpURLConnection;
import java.net.URL;

@SpringBootTest
@SuppressWarnings("unchecked")
public class searchExample extends commonProperties {

    @Test
    void neverSearchTest() {
        String serviceId = "blog.json";
        String host = "https://openapi.naver.com/v1/search/"+serviceId;
        String text = "가방";
        JsonNode resultJson = null;
        JsonNode jsonMap = null;

        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(host+"?query="+text);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("X-Naver-Client-Id", NAVER_ID);
            connection.setRequestProperty("X-Naver-Client-Secret", NAVER_KEY);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                resultJson = readBody(connection.getInputStream());
            } else { // 오류 발생
                resultJson = readBody(connection.getErrorStream());
            }

        } catch(Exception e) {
            e.printStackTrace();

        } finally {
            if(connection != null) connection.disconnect();
        }

        jsonMap = resultJson.findValue("items");
        System.out.println(jsonMap.toString());
    }

    @Test
    void googleSearchTest() throws JsonProcessingException, JSONException {
        String host = "https://www.googleapis.com/customsearch/v1";
        String q = "bag";
        StringBuilder text = new StringBuilder();
        JsonNode resultJson = null;
        JsonNode jsonMap = null;

        URL url = null;
        HttpURLConnection connection = null;
        try {
            text.append(host).append("?").append("key=").append(GOOGLE_KEY).append("&cx=").append(GOOGLE_CX).append("&q=").append(q);
            url = new URL(text.toString());
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            int responseCode = connection.getResponseCode();
            //System.out.println(responseCode);

            resultJson = readBody(connection.getInputStream());
            //System.out.println(resultJson);
        } catch(Exception e) {
            e.printStackTrace();

        } finally {
            if(connection != null) connection.disconnect();
        }

        jsonMap = resultJson.findValue("items");
        printJson(jsonMap.toString());
    }

}