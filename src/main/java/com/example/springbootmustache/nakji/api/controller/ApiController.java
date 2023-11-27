package com.example.springbootmustache.nakji.api.controller;

import com.example.springbootmustache.nakji.api.service.ApiService;
import com.example.springbootmustache.nakji.model.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/nakji/api")
public class ApiController {

    @Autowired
    ApiService apiService;

    @GetMapping("/naver")
    public List<SearchForm> naverSearchApi(@RequestParam(name = "serviceId", required = false) String serviceId
                                           , @RequestParam(name = "query", required = false) String query) {

        return apiService.naverSearch(serviceId, query);
    }

    @GetMapping("/google")
    public List<SearchForm> googleSearchApi(@RequestParam(name = "query", required = false) String query) {

        return apiService.googleSearch(query);
    }
}
