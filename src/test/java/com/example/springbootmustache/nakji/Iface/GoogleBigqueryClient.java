package com.example.springbootmustache.nakji.Iface;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

public interface GoogleBigqueryClient {

    @RequestLine("GET /bigquery/v2/projects/{projectId}/queries/{jobId}")
    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer {token}"
    })
    Response getQuery(@Param("token") String token
                      , @Param("projectId") String projectId
                      , @Param("jobId") String jobId);
}
