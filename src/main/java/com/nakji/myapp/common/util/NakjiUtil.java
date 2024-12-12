package com.nakji.myapp.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class NakjiUtil {
    public static final DateTimeFormatter DATE_INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static <T> String toStringForRequestBody(T dto) {
        StringBuilder sb = new StringBuilder();
        Class<?> dtoClass = dto.getClass();

        Field[] fields = dtoClass.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isFinal(field.getModifiers())) {
                continue; // final 필드는 제외
            }
            field.setAccessible(true); // private 필드 접근 허용
            try {
                Object value = field.get(dto);
                if (value == null) continue;

                sb.append(field.getName()).append("=").append(value).append("&");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 마지막 "&" 제거
        if (!sb.isEmpty()) sb.setLength(sb.length() - 1);

        return sb.toString();
    }

    /**
     * 'yyyyMMdd' 형식의 날짜 문자열을 'yyyy-MM-dd' 형식으로 변환
     */
    public static LocalDate formatDateString(String dateStr) {
        if (dateStr == null) {
            throw new IllegalArgumentException("Input date string cannot be null");
        }

        try {
            LocalDate date = LocalDate.parse(dateStr, DATE_INPUT_FORMATTER);
            return date;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Input date string must be in 'yyyyMMdd' format and a valid date", e);
        }
    }

    public static JsonNode readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body, StandardCharsets.UTF_8);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            String responseBody = lineReader.lines().collect(Collectors.joining("\n"));
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readTree(responseBody);
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }

    public static void printJson(String str) throws JsonProcessingException {
        // ObjectMapper 인스턴스 생성
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        // 들여쓰기 수준을 기본값으로 설정
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        List<String> list = objectMapper.readValue(str, List.class);

        System.out.println(objectMapper.writeValueAsString(list));
    }
}