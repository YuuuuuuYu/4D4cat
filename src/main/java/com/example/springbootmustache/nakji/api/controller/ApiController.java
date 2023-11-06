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

    @GetMapping("/naver")
    public List<PageView> naverSearchApi(@RequestParam(name = "query", required = false) String query) {

        return apiService.naverSearch(query);
    }

    @GetMapping("/google")
    public List<PageView> googleSearchApi(@RequestParam(name = "query", required = false) String query) {

        return apiService.googleSearch(query);
    }
}
