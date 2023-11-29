package com.example.springbootmustache.nakji.Iface;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

public interface GoogleBigqueryJobClient {

    @RequestLine("POST /bigquery/v2/projects/{projectId}/jobs")
    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer {token}"
    })
    Response createQuery(@Param("token") String token
                    , @Param("projectId") String projectId
                    , String body);
}
