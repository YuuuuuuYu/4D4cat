package com.example.springbootmustache;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

@SpringBootTest
public class NaverSearchExample {

    //private final static String ID = System.getProperty("api.naver.id");
    //private final static String KEY = System.getProperty("api.naver.key");
    private final static String ID = "DXUdYJLVPWaC2kxD_PUm";
    private final static String KEY = "SFvAihes1e";

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
            connection.setRequestProperty("X-Naver-Client-Id", ID);
            connection.setRequestProperty("X-Naver-Client-Secret", KEY);

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
}