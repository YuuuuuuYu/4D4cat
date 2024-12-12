package com.nakji.myapp.labs.search.naver.controller;

import com.nakji.myapp.labs.common.model.SearchForm;
import com.nakji.myapp.labs.search.naver.service.NaverApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/labs/naver")
public class NaverApiController {
    private final NaverApiService apiService;

    @GetMapping("/search")
    public List<SearchForm> naverSearchApi(@RequestParam(name = "serviceId", required = false) String serviceId
                                            , @RequestParam(name = "query", required = false) String query) {

        return apiService.naverSearch(serviceId, query);
    }
}