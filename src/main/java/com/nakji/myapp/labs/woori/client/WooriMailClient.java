package com.nakji.myapp.labs.woori.client;

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
