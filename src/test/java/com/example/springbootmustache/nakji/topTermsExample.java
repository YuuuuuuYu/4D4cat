package com.example.springbootmustache.nakji;

import com.example.springbootmustache.nakji.Iface.GoogleTopTermsClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Feign;
import feign.Response;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

public class topTermsExample extends commonProperties {

    @Test
    void googleTopTermsTest() throws JsonProcessingException, JSONException {
        String projectId = "mapping-1559551857011";
        String datasetId = "_9996b01ee4f09b65b48ee6d884dc7087a7462f34";
        String jobId = "bquxjob_33a9efe9_18c0fc537ce";
        String url = "https://bigquery.googleapis.com";
        String query = "SELECT refresh_date AS Day, term AS Top_Term, rank FROM `bigquery-public-data.google_trends.top_terms` WHERE rank <= 5 AND refresh_date >= DATE_SUB(CURRENT_DATE(), INTERVAL 2 DAY) GROUP BY Day, Top_Term, rank ORDER BY rank";
        String token = "ya29.a0AfB_byCneYYMjgpCq1egKXPB56eHj5bUZfrkPgAEtsgnsB9cniWViHN9GSJxG3SC0_4FJdBvKEhUGq3l0Yd4MIUjNS_ssdh2K3jpHuqNzyREdlChPX7bjjuY7Wz4VjV5SnoelX-KxdC-i_k383opq3-QvnxzZvgUdnyIaCgYKAaUSAQ4SFQHGX2Mi2Hw6gttZNnKpiRm3osvP8g0171";
        JsonNode resultJson = null;
        JsonNode schema = null;
        JsonNode rows = null;

        GoogleTopTermsClient client = Feign.builder().target(GoogleTopTermsClient.class, url);

        try (Response response = client.getQuery(projectId, jobId, token)) {
            System.out.println(response.request());
            if (response.status() == 200) {
                resultJson = readBody(response.body().asInputStream());
                System.out.println(resultJson);

            } else {
                System.err.println("Error Response: " + response.toString());
            }

        } catch(Exception e) {
            e.printStackTrace();

        }

        schema = resultJson.findValue("schema");
        rows = resultJson.findValue("rows");

        System.out.println(schema.toString());
        System.out.println(rows.toString());
    }
}
