package com.example.springbootmustache.nakji.api.google.client;

import feign.Headers;
import feign.RequestLine;
import feign.Response;

public interface GoogleAccessTokenClient {

    @RequestLine("POST /token")
    @Headers({
            "Content-Type: application/x-www-form-urlencoded"
    })
    Response getAccessToken(String param);
}
