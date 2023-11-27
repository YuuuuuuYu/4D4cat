package com.example.springbootmustache.nakji.config;

import com.example.springbootmustache.common.UserProperties;
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

    /*public HttpURLConnection naverSearchConnection(String id, String search) {
        String query = "".equals(nvl(search)) ? "bag" : search;
        String serviceId = "".equals(id) ? "blog.json" : id;
        String host = "https://openapi.naver.com/v1/search/"+serviceId;

        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(host+"?query="+ URLEncoder.encode(query, "UTF-8"));
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

    public HttpURLConnection googleSearchConnection(String search) {
        String query = "".equals(nvl(search)) ? "bag" : search;
        String host = "https://www.googleapis.com/customsearch/v1";

        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(host+"?key="+GOOGLE_KEY+"&cx="+GOOGLE_CX+"&q="+ URLEncoder.encode(query, "UTF-8"));
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        } catch(Exception e) {
            e.printStackTrace();

        }

        return connection;
    }*/

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
}
