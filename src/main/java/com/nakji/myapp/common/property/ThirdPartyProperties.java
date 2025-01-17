package com.nakji.myapp.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix="third-party")
public record ThirdPartyProperties (
    Naver naver,
    Google google,
    Openai openai,
    Woorimail woorimail
) {
    public record Naver(String apiType, String naverId, String naverKey) {}
    public record Google(String apiType, String googleKey, String googleCx) {}
    public record Openai(String apiType, String openaiKey) {}
    public record Woorimail(String apiType, String woorimailKey, String domain) {}

    public Map<String, String> getThirdPartyType() {
        return Map.of(
                "naver", this.naver().apiType(),
                "google", this.google().apiType(),
                "openai", this.openai().apiType(),
                "woorimail", this.woorimail().apiType()
        );
    }

    public List<String> getThirdPartyList() {
        return getThirdPartyType().keySet().stream().toList();
    }
}