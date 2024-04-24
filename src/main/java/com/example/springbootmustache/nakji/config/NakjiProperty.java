package com.example.springbootmustache.nakji.config;

import com.example.springbootmustache.common.UserProperties;
import com.example.springbootmustache.nakji.api.client.GoogleAccessTokenClient;
import com.example.springbootmustache.nakji.api.client.GoogleSearchClient;
import com.example.springbootmustache.nakji.api.client.NaverSearchClient;
import feign.Feign;
import feign.Response;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "profile.nakji")
public class NakjiProperty extends UserProperties {
    private String imgUrl;
    private String srcUrl;
    private String NAVER_ID;
    private String NAVER_KEY;
    private String GOOGLE_KEY;
    private String GOOGLE_CX;
    private String GOOGLE_CLIENT_ID;
    private String GOOGLE_CLIENT_SECRET;

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

    public void setGoogleClientId(String googleClientId) {
        this.GOOGLE_CLIENT_ID = googleClientId;
    }

    public void setGoogleClientSecret(String googleClientSecret) {
        this.GOOGLE_CLIENT_SECRET = googleClientSecret;
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

    public Response naverSearchConnection(String id, String search) {
        Response response = null;
        String query = "".equals(nvl(search)) ? "bag" : search;
        String serviceId = "".equals(nvl(id)) ? "blog.json" : id;
        String url = "https://openapi.naver.com";

        NaverSearchClient client = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(NaverSearchClient.class, url);

        response = client.search(NAVER_ID, NAVER_KEY, serviceId, query);

        return response;
    }

    public Response googleSearchConnection(String search) {
        Response response = null;
        String query = "".equals(nvl(search)) ? "bag" : search;
        String url = "https://www.googleapis.com";

        GoogleSearchClient client = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(GoogleSearchClient.class, url);

        response = client.search(GOOGLE_KEY, GOOGLE_CX, query);

        return response;
    }

    public String activeCodeApi(String scope, String redirect_uri) {
        String url = "https://accounts.google.com/o/oauth2/v2/auth";
        String response_type = "code";

        StringBuilder uri = new StringBuilder(url);
        uri.append("?scope=").append(scope)
                .append("&response_type=").append(response_type)
                .append("&client_id=").append(GOOGLE_CLIENT_ID)
                .append("&redirect_uri=").append(redirect_uri);

        return uri.toString();
    }

    public Response accessTokenApi(String redirect_uri, String activeCode) {
        Response response = null;
        String url = "https://oauth2.googleapis.com";
        String grand_type = "authorization_code";

        StringBuilder param = new StringBuilder();

        GoogleAccessTokenClient client = Feign.builder()
                .target(GoogleAccessTokenClient.class, url);

        param.append("code=").append(activeCode)
                .append("&client_id=").append(GOOGLE_CLIENT_ID)
                .append("&client_secret=").append(GOOGLE_CLIENT_SECRET)
                .append("&redirect_uri=").append(redirect_uri)
                .append("&grant_type=").append(grand_type);

        response = client.getAccessToken(param.toString());

        return response;
    }
}
