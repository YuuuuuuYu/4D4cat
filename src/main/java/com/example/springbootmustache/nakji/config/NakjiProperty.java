package com.example.springbootmustache.nakji.config;

import com.example.springbootmustache.common.UserProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

@Component
@ConfigurationProperties(prefix = "profile.nakji")
public class NakjiProperty extends UserProperties {
    private String imgUrl;
    private String srcUrl;
    private String NAVER_ID;
    private String NAVER_KEY;
    private String GOOGLE_KEY;
    private String GOOGLE_CX;

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }

    public void setNaverId(String naverId) {
        NAVER_ID = naverId;
    }

    public void setNaverKey(String naverKey) {
        NAVER_KEY = naverKey;
    }

    public void setGoogleKey(String googleKey) {
        GOOGLE_KEY = googleKey;
    }

    public void setGoogleCx(String googleCx) {
        GOOGLE_CX = googleCx;
    }

    public String getImgUrl() {
        return imgUrl;
    }
    public String getSrcUrl() {
        return srcUrl;
    }

    public HttpURLConnection naverSearchConnection(String id, String search) {
        System.out.println(NAVER_ID);

        String query = "".equals(search) ? "bag" : search;
        String serviceId = "".equals(id) ? "blog.json" : id;
        String host = "https://openapi.naver.com/v1/search/"+serviceId;

        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(host+"?query="+query);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("X-Naver-Client-Id", NAVER_ID);
            connection.setRequestProperty("X-Naver-Client-Secret", NAVER_KEY);

        } catch(Exception e) {
            e.printStackTrace();

        }

        return connection;
    }

}
