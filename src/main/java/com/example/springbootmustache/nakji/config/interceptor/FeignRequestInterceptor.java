package com.example.springbootmustache.nakji.config.interceptor;

import com.example.springbootmustache.nakji.api.model.FeignRequestInfo;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Optional;

public class FeignRequestInterceptor implements RequestInterceptor {

    private final FeignRequestInfo headerInfo;

    public FeignRequestInterceptor(FeignRequestInfo headerInfo) {
        this.headerInfo = headerInfo;
    }

    @Override
    public void apply(RequestTemplate template) {
        String url = template.url();

        if (url.startsWith("/nakji/api")) {
            template.header("Content-Type", Optional.ofNullable(headerInfo.getContentType()).orElse("application/json;charset=UTF-8"));
            template.header("Accept-Charset", Optional.ofNullable(headerInfo.getAcceptCharset()).orElse("UTF-8"));
        }
    }
}
