package com.example.springbootmustache.nakji.api.woori.client;

import feign.Headers;
import feign.RequestLine;
import feign.Response;

public interface WooriMailClient {

    @RequestLine("POST /")
    @Headers({
            "Content-Type: application/x-www-form-urlencoded"
    })
    Response call(String param);
}
