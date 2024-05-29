package com.example.springbootmustache.nakji.api.google.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

public interface GoogleSearchClient {

    @RequestLine("GET /customsearch/v1?key={key}&cx={cx}&q={query}")
    @Headers({
            "Content-Type: application/json;charset=UTF-8"
    })
    Response search(@Param("key") String key, @Param("cx") String cx, @Param("query") String query);
}