package com.example.springbootmustache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.StringTokenizer;

@SpringBootTest
public class searchExample {

    //private final static String ID = System.getProperty("api.naver.id");
    //private final static String KEY = System.getProperty("api.naver.key");
    private final static String NAVER_ID = "DXUdYJLVPWaC2kxD_PUm";
    private final static String NAVER_KEY = "SFvAihes1e";
    //private final static String GOOGLE_ID = "895857791329-mi3g48445im7h7o1lceoslcef0vgg30u.apps.googleusercontent.com/";
    //private final static String GOOGLE_KEY = "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";
    private final static String GOOGLE_KEY = "AIzaSyBxdEGZKno9zIewJzFjMzCqngx_dxuWdHE";
    private final static String GOOGLE_CX = "c7afc5a33141f45f6";


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

    private static JsonNode readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body, Charset.forName("UTF-8"));


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody.toString());

            return jsonNode;
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }

    private static void printJson(String str) throws JsonProcessingException, JSONException {
        // ObjectMapper 인스턴스 생성
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        // 들여쓰기 수준을 기본값으로 설정
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        List<String> list = objectMapper.readValue(str, List.class);

        System.out.println(objectMapper.writeValueAsString(list));

    }
}