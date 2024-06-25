package com.example.springbootmustache.nakji.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class NakjiUtil {
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
}