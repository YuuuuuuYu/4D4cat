package com.example.springbootmustache.nakji;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

@SuppressWarnings("unchecked")
public class commonProperties {

    //private final static String ID = System.getProperty("api.naver.id");
    //private final static String KEY = System.getProperty("api.naver.key");
    protected final static String NAVER_ID = "DXUdYJLVPWaC2kxD_PUm";
    protected final static String NAVER_KEY = "SFvAihes1e";
    //private final static String GOOGLE_ID = "895857791329-mi3g48445im7h7o1lceoslcef0vgg30u.apps.googleusercontent.com/";
    //private final static String GOOGLE_KEY = "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";
    protected final static String GOOGLE_KEY = "AIzaSyBxdEGZKno9zIewJzFjMzCqngx_dxuWdHE";
    protected final static String GOOGLE_CX = "c7afc5a33141f45f6";

    protected JsonNode readBody(InputStream body){
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

    protected void printJson(String str) throws JsonProcessingException, JSONException {
        // ObjectMapper 인스턴스 생성
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        // 들여쓰기 수준을 기본값으로 설정
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        List<String> list = objectMapper.readValue(str, List.class);

        System.out.println(objectMapper.writeValueAsString(list));
    }
}
