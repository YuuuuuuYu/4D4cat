package com.example.springbootmustache.nakji.api.model;

public class NaverSearchInfo extends FeignRequestInfo {
    private String clientId;
    private String clientSecret;

    public NaverSearchInfo(String contentType, String acceptCharset, String clientSecret, String clientId) {
        super(contentType, acceptCharset);
        this.clientSecret = clientSecret;
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }
    public String getClientSecret() {
        return clientSecret;
    }
}
