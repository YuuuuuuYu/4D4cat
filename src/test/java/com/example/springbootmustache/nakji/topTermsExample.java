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
    void googleTopTermsTest() throws JsonProcessingException, JSONException {
        String projectId = "crested-column-401906";
        String datasetId = "_4b4e36620224ba95c01416e7d9d98a9ff1e48d38";
        String jobId = "bquxjob_79cc349e_18bc75a0643";
        String host = "https://bigquery.googleapis.com/bigquery/v2/projects/"+projectId+"/datasets/"+datasetId;
        String query = "SELECT refresh_date AS Day, term AS Top_Term, rank FROM `bigquery-public-data.google_trends.top_terms` WHERE rank <= 5 AND refresh_date >= DATE_SUB(CURRENT_DATE(), INTERVAL 2 DAY) GROUP BY Day, Top_Term, rank ORDER BY rank";
        StringBuilder text = new StringBuilder();
        JsonNode resultJson = null;
        JsonNode jsonMap = null;

        // post로 테스트 다시 해봐야함
        URL url = null;
        HttpURLConnection connection = null;
        try {
            text.append(host);
            text.append("?key=").append(GOOGLE_KEY).append("&cx=").append(GOOGLE_CX);
            url = new URL(text.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);

            resultJson = readBody(connection.getInputStream());
            System.out.println(resultJson);

        } catch(Exception e) {
            e.printStackTrace();

        } finally {
            if(connection != null) connection.disconnect();
        }

        jsonMap = resultJson.findValue("items");
        printJson(jsonMap.toString());
    }
}
