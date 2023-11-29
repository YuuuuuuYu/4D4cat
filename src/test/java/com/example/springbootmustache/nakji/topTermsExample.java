package com.example.springbootmustache.nakji;

import com.example.springbootmustache.nakji.Iface.GoogleBigqueryClient;
import com.example.springbootmustache.nakji.Iface.GoogleBigqueryJobClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.RetryOption;
import com.google.cloud.bigquery.*;
import feign.Feign;
import feign.Response;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.threeten.bp.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class topTermsExample extends commonProperties {

    String getBigqueryJobId(String projectId, String url, String token, String query) throws JSONException {
        JsonNode resultJson = null;
        JsonNode jobId = null;

        GoogleBigqueryJobClient client = Feign.builder().target(GoogleBigqueryJobClient.class, url);
        String requestBody = "{'configuration': {'query': {'query': '"+query+"','useLegacySql': false}}}";

        try (Response response = client.createQuery(token, projectId, requestBody)) {
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

        jobId = resultJson.at("/jobReference/jobId");

        return jobId.asText();
    }

    @Test
    void googleTopTermsTest() throws JSONException {
        String projectId = "mapping-1559551857011";
        String url = "https://bigquery.googleapis.com";
        String token = "ya29.a0AfB_byCiGyEf6MYxcrQa98KXeryCjS9SgGiTzQWmhJpzfuP7wLUxXq5qmuDwJ7B_c5I9KSkh7YdbqDBamO_E6aPPbPdoUmLDJk0FUre6XmI7va19WtG3NloBDfm4I2o7lr1LMV_qyxdWKfEPtkr8vE_AQp4l-ULQggaCgYKAVQSAQ4SFQHGX2MiEcmULqRmAYTnC-5bTY9PEw0169";
        int rank = 3;
        int start_diff = 2;
        int end_diff = 0;
        JsonNode resultJson = null;

        String query = "SELECT refresh_date AS Day, term AS Top_Term, rank FROM `bigquery-public-data.google_trends.top_terms` " +
                        "WHERE rank <= "+rank +
                        " AND refresh_date between DATE_SUB(CURRENT_DATE(), INTERVAL "+start_diff+" DAY)" +
                                                " AND DATE_SUB(CURRENT_DATE(), INTERVAL "+end_diff+" DAY) " +
                        "GROUP BY Day, Top_Term, rank ORDER BY Day, rank";

        String jobId = getBigqueryJobId(projectId, url, token, query);
        GoogleBigqueryClient client = Feign.builder().target(GoogleBigqueryClient.class, url);

        try (Response response = client.getQuery(token, projectId, jobId)) {
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

        int totalRows = resultJson.findValue("totalRows").asInt();

        if (totalRows > 0) {
            StringBuilder resultArr = null;
            List<String> list = new ArrayList<>();
            List<JsonNode> rows = resultJson.findValues("v");
            Iterator<JsonNode> itr = rows.iterator();

            while(itr.hasNext()) {
                resultArr = new StringBuilder(itr.next().asText())
                            .append(",").append(itr.next().asText())
                            .append(",").append(itr.next().asText());

                list.add(resultArr.toString());
            }

            list.forEach(System.out::println);
        }

    }

    @Test
    void googleBigqueryConfigTest() throws InterruptedException {
        String projectId = "mapping-1559551857011";
        String token = "ya29.a0AfB_byDtM3AQEIDTmxKSRhEOg-DE4EFcMqCsurM_htQitzEeFkldA3jcly2cOBLo6mbQHR3rKuZPQbQ_f_zwvoxL_s42k4yhqWNuf8GqxdIBv75CSksqbe1SKwPIpSBYJrZfvsfuDPc53XVU-sVjH9LHTdod3tDc9AaCgYKAV0SAQ4SFQHGX2MiX_db4iymE8CcKJVk1qWImQ0169";
        int rank = 3;
        int start_diff = 1;
        int end_diff = 0;

        String query = "SELECT refresh_date AS Day, term AS Top_Term, rank FROM `bigquery-public-data.google_trends.top_terms` " +
                        "WHERE rank <= "+rank +
                                " AND refresh_date between DATE_SUB(CURRENT_DATE(), INTERVAL "+start_diff+" DAY)" +
                                                        " AND DATE_SUB(CURRENT_DATE(), INTERVAL "+end_diff+" DAY) " +
                        "GROUP BY Day, Top_Term, rank ORDER BY Day, rank";


        BigQuery bigquery = BigQueryOptions.newBuilder()
                                    .setProjectId(projectId)
                .setCredentials(GoogleCredentials.newBuilder().setAccessToken(AccessToken.newBuilder().setTokenValue(token).build()).build().toBuilder().build())
                                    .build()
                                    .getService();
        // 쿼리 실행에 대한 정보를 담은 JobConfiguration 객체를 생성합니다.
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
                                                                .setUseLegacySql(false)
                                                                .build();

        // Create a job ID so that we can safely retry.
        JobId jobId = JobId.of(UUID.randomUUID().toString());
        Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

        // Wait for the query to complete.
        queryJob = queryJob.waitFor(RetryOption.initialRetryDelay(Duration.ofSeconds(1)),
                RetryOption.totalTimeout(Duration.ofMinutes(1)));

        // Check for errors
        if (queryJob == null) {
            throw new RuntimeException("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            // You can also look at queryJob.getStatus().getExecutionErrors() for all
            // errors, not just the latest one.
            throw new RuntimeException(queryJob.getStatus().getError().toString());
        }

        // Get the results.
        TableResult result = queryJob.getQueryResults();

        StringBuilder resultArr = null;
        List<String> list = new ArrayList<>();
        // Print all pages of the results.
        for (FieldValueList row : result.iterateAll()) {
            // String type
            resultArr = new StringBuilder(row.get(0).getStringValue());
            resultArr.append(",").append(row.get(1).getStringValue());
            resultArr.append(",").append(row.get(2).getStringValue());

            list.add(resultArr.toString());
        }

        list.forEach(System.out::println);
    }
}
