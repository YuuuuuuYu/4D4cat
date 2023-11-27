package com.example.springbootmustache.nakji;

import com.example.springbootmustache.nakji.Iface.GoogleAccessCodeClient;
import com.example.springbootmustache.nakji.Iface.GoogleAccessTokenClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import feign.Feign;
import feign.Response;
import feign.gson.GsonEncoder;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
        String url = "https://accounts.google.com";
        String scope = URLEncoder.encode("https://www.googleapis.com/auth/bigquery.readonly", StandardCharsets.UTF_8);
        String redirect_uri = URLEncoder.encode("http://localhost:8888/nakji/code", StandardCharsets.UTF_8);
        String response_type = "code";
        String access_type = "online";
        String activeCode = null;

        GoogleAccessCodeClient client = Feign.builder()
                .encoder(new GsonEncoder())
                .target(GoogleAccessCodeClient.class, url);

        try (Response response = client.getAccessCode(scope, response_type, GOOGLE_CLIENT_ID, redirect_uri, access_type)) {
            System.out.println(response.request());
            if (response.status() == 200) {
                System.out.println(readBody(response.body().asInputStream()));

            } else {
                System.err.println("Error Response: " + response.toString());
            }

/*
            String redirectedUrl = connection.getURL().getQuery().split("&")[0];
            System.out.println("Redirected URL: " + redirectedUrl);

            if (redirectedUrl.contains("authError")) return activeCode;

            activeCode = redirectedUrl.replace("code=", "");*/
            System.out.println(activeCode);

        } catch(Exception e) {
            e.printStackTrace();
        }

        return activeCode;
    }

    protected String getAccessToken() {
        String url = "https://oauth2.googleapis.com";
        String grand_type = "authorization_code";
        String redirect_uri = "http://localhost:8888/nakji/code";
        String activeCode = "4/0AfJohXkQv8Zh-pZ1kU6TQ9CKMRV7RQVWg3aiZ1YKktMKREGZAaNbDLQtFMDQd_eSn5ktfQ";
        String accessToken = "";

        StringBuilder param = new StringBuilder();

        GoogleAccessTokenClient client = Feign.builder()
                .target(GoogleAccessTokenClient.class, url);

        param.append("code=").append(activeCode)
            .append("&client_id=").append(GOOGLE_CLIENT_ID)
            .append("&client_secret=").append(GOOGLE_CLIENT_SECRET)
            .append("&redirect_uri=").append(URLEncoder.encode(redirect_uri, StandardCharsets.UTF_8))
            .append("&grant_type=").append(grand_type);

        try (Response response = client.getAccessToken(param.toString())) {
            System.out.println(response.request());
            if (response.status() == 200) {
                System.out.println(readBody(response.body().asInputStream()));

            } else {
                System.err.println("Error Response: " + response.toString());
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return accessToken;
    }
}
