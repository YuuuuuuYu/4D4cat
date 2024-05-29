package com.example.springbootmustache.nakji.api.openai.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

public interface OpenAIGptClient {

    @RequestLine("POST /v1/chat/completions")
    @Headers({
            "Content-Type: application/json;charset=UTF-8",
            "Authorization: Bearer {AI_KEY}"
    })
    Response prompt(@Param("AI_KEY") String key, String body);
}