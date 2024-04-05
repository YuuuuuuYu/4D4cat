package com.example.springbootmustache.nakji.api.model;

public class FeignRequestInfo {
    private String contentType;
    private String acceptCharset;

    public FeignRequestInfo(String contentType, String acceptCharset) {
        this.contentType = contentType;
        this.acceptCharset = acceptCharset;
    }

    public String getContentType() {
        return contentType;
    }
    public String getAcceptCharset() {
        return acceptCharset;
    }
}
