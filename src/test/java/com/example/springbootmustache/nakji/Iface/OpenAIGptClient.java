package com.example.springbootmustache.nakji.Iface;

import feign.Headers;
import feign.RequestLine;
import feign.Response;

public interface OpenAIGptClient {

    @RequestLine("POST /v1/chat/completions")
    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer {AI_KEY}"
    })
    Response prompt(String body);

}
