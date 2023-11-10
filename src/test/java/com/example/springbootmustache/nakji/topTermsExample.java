package com.example.springbootmustache.nakji;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.util.stream.Collectors;
import java.net.HttpURLConnection;
import java.net.URL;

public class topTermsExample extends commonProperties {

    @Test
    void googleSearchTest() throws JsonProcessingException, JSONException {
        String projectId = "crested-column-401906";
        String host = "https://bigquery.googleapis.com/v2/projects/"+projectId+"/queries";
        String query = "SELECT refresh_date AS Day, term AS Top_Term, rank FROM `bigquery-public-data.google_trends.top_terms` WHERE rank <= 10 AND refresh_date >= DATE_SUB(CURRENT_DATE(), INTERVAL 2 DAY) GROUP BY Day, Top_Term, rank ORDER BY rank";
        StringBuilder text = new StringBuilder();
        JsonNode resultJson = null;
        JsonNode jsonMap = null;

        // post로 테스트 다시 해봐야함
        URL url = null;
        HttpURLConnection connection = null;
        try {
            text.append(host);
            url = new URL(text.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            // 쿼리 전송
            String body = "{\n" +
                    "  \"query\": \"" + query + "\"\n" +
                    "}";
            PrintWriter writer = new PrintWriter(connection.getOutputStream());
            writer.print(body);
            writer.close();

            // 응답 수신
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.lines().collect(Collectors.joining("\n"));
            reader.close();

            System.out.println(response);
            /*
            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);

            resultJson = readBody(connection.getInputStream());
            System.out.println(resultJson);
            */
        } catch(Exception e) {
            e.printStackTrace();

        } finally {
            if(connection != null) connection.disconnect();
        }

        jsonMap = resultJson.findValue("items");
        printJson(jsonMap.toString());
    }
}
