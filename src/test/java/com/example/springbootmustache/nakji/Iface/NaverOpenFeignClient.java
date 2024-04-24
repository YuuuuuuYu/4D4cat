package com.example.springbootmustache.nakji.Iface;


import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

public interface NaverOpenFeignClient {

    @RequestLine("GET /v1/search/blog.json?query={query}")
    @Headers({
            "Content-Type: application/json;charset=UTF-8",
            "Accept-Charset: UTF-8",
            "X-Naver-Client-Id: DXUdYJLVPWaC2kxD_PUm",
            "X-Naver-Client-Secret: SFvAihes1e"
    })
    Response search(@Param("query") String query);
}
