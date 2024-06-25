package com.example.springbootmustache.nakji.common.config;

import com.example.springbootmustache.common.UserProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "profile.nakji")
public class NakjiProperty extends UserProperties {
    private static String imgUrl;
    private static String srcUrl;
    private static String domain;
    private static String NAVER_ID;
    private static String NAVER_KEY;
    private static String GOOGLE_KEY;
    private static String GOOGLE_CX;
    private static String GOOGLE_CLIENT_ID;
    private static String GOOGLE_CLIENT_SECRET;
    private static String OPENAI_KEY;
    private static String WOORIMAIL_KEY;

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setNaverId(String naverId) {
        NAVER_ID = naverId;
    }

    public void setNaverKey(String naverKey) {
        NAVER_KEY = naverKey;
    }

    public void setGoogleClientId(String googleClientId) {
        GOOGLE_CLIENT_ID = googleClientId;
    }

    public void setGoogleClientSecret(String googleClientSecret) {
        GOOGLE_CLIENT_SECRET = googleClientSecret;
    }

    public void setGoogleKey(String googleKey) {
        GOOGLE_KEY = googleKey;
    }

    public void setGoogleCx(String googleCx) {
        GOOGLE_CX = googleCx;
    }

    public void setOPENAI_KEY(String openai_key) {
        OPENAI_KEY = openai_key;
    }

    public void setWoorimailKey(String woorimailKey) {
        WOORIMAIL_KEY = woorimailKey;
    }

    public static String getNaverId() {
        return NAVER_ID;
    }

    public static String getNaverKey() {
        return NAVER_KEY;
    }

    public static String getGoogleKey() {
        return GOOGLE_KEY;
    }

    public static String getGoogleCx() {
        return GOOGLE_CX;
    }

    public static String getGoogleClientId() {
        return GOOGLE_CLIENT_ID;
    }

    public static String getGoogleClientSecret() {
        return GOOGLE_CLIENT_SECRET;
    }

    public static String getOpenaiKey() {
        return OPENAI_KEY;
    }

    public static String getWoorimailKey() {
        return WOORIMAIL_KEY;
    }

    public static String getDomain() {
        return domain;
    }

    public String getImgUrl() {
        return imgUrl;
    }
    public String getSrcUrl() {
        return srcUrl;
    }
}