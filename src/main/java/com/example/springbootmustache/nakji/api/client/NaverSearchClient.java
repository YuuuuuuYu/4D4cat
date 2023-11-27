package com.example.springbootmustache.nakji.api.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

public interface NaverSearchClient {

    @RequestLine("GET /v1/search/{serviceId}?query={query}")
    @Headers({
            "Content-Type: application/json;charset=UTF-8",
            "Accept-Charset: UTF-8",
            "X-Naver-Client-Id: {client_id}",
            "X-Naver-Client-Secret: {client_secret}"
    })
    Response search(@Param("client_id") String client_id, @Param("client_secret") String client_secret, @Param("serviceId") String serviceId, @Param("query") String query);
}
