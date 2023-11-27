package com.example.springbootmustache.nakji.Iface;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

public interface GoogleAccessCodeClient {

    @RequestLine("GET /o/oauth2/v2/auth?scope={scope}&response_type={response_type}&client_id={client_id}&redirect_uri={redirect_uri}")
    @Headers({
            "Content-Type: application/json"
    })
    Response getAccessCode(@Param("scope") String scope
                        , @Param("response_type") String response_type
                        , @Param("client_id") String client_id
                        , @Param("redirect_uri") String redirect_uri
                        , @Param("access_type") String access_type);
}
