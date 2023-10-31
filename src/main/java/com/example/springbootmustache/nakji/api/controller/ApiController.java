package com.example.springbootmustache.nakji.api.controller;

import com.example.springbootmustache.nakji.api.service.ApiService;
import com.example.springbootmustache.nakji.model.PageView;
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

    @GetMapping("/")
    public PageView index(PageView pageView) {

        return pageView;
    }

    @GetMapping("/naver.text")
    public String naverSearchApi(@RequestParam(name = "query", required = false) String query) {
        List<PageView> pageResult = apiService.naverSearch(query);

        return apiService.searchResultToTemplate(pageResult);
    }

    @GetMapping("/google")
    public String googleSearchApi(@RequestParam(name = "query", required = false) String query) {
        List<PageView> pageResult = apiService.googleSearch(query);

        return apiService.searchResultToTemplate(pageResult);
    }
}
