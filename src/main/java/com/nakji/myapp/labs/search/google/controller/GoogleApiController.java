package com.nakji.myapp.labs.search.google.controller;

import com.nakji.myapp.labs.common.model.SearchForm;
import com.nakji.myapp.labs.search.google.service.GoogleApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/labs/google")
public class GoogleApiController {
    private final GoogleApiService apiService;

    @GetMapping("/search")
    public List<SearchForm> googleSearchApi(@RequestParam(name = "query", required = false) String query) {
        return apiService.googleSearch(query);
    }
}