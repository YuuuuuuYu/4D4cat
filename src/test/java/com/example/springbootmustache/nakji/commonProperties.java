package com.example.springbootmustache.nakji;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class commonProperties {

    //private final static String ID = System.getProperty("api.naver.id");
    //private final static String KEY = System.getProperty("api.naver.key");
    protected final static String NAVER_ID = "DXUdYJLVPWaC2kxD_PUm";
    protected final static String NAVER_KEY = "SFvAihes1e";
    protected final static String GOOGLE_CLIENT_ID = "895857791329-mi3g48445im7h7o1lceoslcef0vgg30u.apps.googleusercontent.com";
    protected final static String GOOGLE_CLIENT_SECRET = "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";
    protected final static String GOOGLE_KEY = "AIzaSyBxdEGZKno9zIewJzFjMzCqngx_dxuWdHE";
    protected final static String GOOGLE_CX = "c7afc5a33141f45f6";

    protected JsonNode readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body, StandardCharsets.UTF_8);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            responseBody.append(lineReader.lines().collect(Collectors.joining("\n")));

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody.toString());

            return jsonNode;
        } catch (IOException e) {
            e.printStackTrace();

        }

        return null;
    }

    protected void printJson(String str) throws JsonProcessingException, JSONException {
        // ObjectMapper 인스턴스 생성
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        // 들여쓰기 수준을 기본값으로 설정
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        List<String> list = objectMapper.readValue(str, List.class);

        System.out.println(objectMapper.writeValueAsString(list));
    }

    protected String getActiveCode() {
        String host = "https://accounts.google.com/o/oauth2/v2/auth";
        String scope = "https://www.googleapis.com/auth/bigquery.readonly";
        String redirect_uri = "http://localhost:8888/nakji/code";
        String response_type = "code";
        StringBuilder text = new StringBuilder();
        String activeCode = null;

        URL url = null;
        HttpURLConnection connection = null;
        try {
            text.append(host);
            text.append("?scope=").append(scope).append("&response_type=").append(response_type)
                    .append("&client_id=").append(GOOGLE_CLIENT_ID).append("&redirect_uri=").append(redirect_uri);
            url = new URL(text.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);

            String redirectedUrl = connection.getURL().getQuery().split("&")[0];
            System.out.println("Redirected URL: " + redirectedUrl);

            if (redirectedUrl.contains("authError")) return activeCode;

            activeCode = redirectedUrl.replace("code=", "");
            System.out.println(activeCode);

        } catch(Exception e) {
            e.printStackTrace();

        } finally {
            if(connection != null) connection.disconnect();
        }

        return activeCode;
    }

    protected String getAccessToken() {
        String tokenUrl = "https://oauth2.googleapis.com/token";

        URL url = null;
        HttpURLConnection connection = null;
        try {
            String requestBody = "{\"Authorization\":\"Basic " + Base64.getEncoder().encodeToString((GOOGLE_CLIENT_ID + ":" + GOOGLE_CLIENT_SECRET).getBytes()) + "\"}";
            url = new URL(tokenUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("Authorization", requestBody);
            connection.setRequestProperty("Content-Length", Integer.toString(requestBody.getBytes().length)); // Content-Length 헤더 추가

            // 요청 본문 전송
            connection.setDoOutput(true);
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(requestBody.getBytes("UTF-8"));
            }
            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);
            System.out.println(readBody(connection.getInputStream()));

        } catch(Exception e) {
            e.printStackTrace();

        } finally {
            if(connection != null) connection.disconnect();
        }

        return "t";
    }
}
