package com.example.springbootmustache.nakji.api.naver.controller;

import com.example.springbootmustache.nakji.api.naver.service.NaverApiService;
import com.example.springbootmustache.nakji.api.common.model.SearchForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/nakji/api/naver")
@RequiredArgsConstructor
public class NaverApiController {

    private final NaverApiService apiService;

    @GetMapping("/info")
    @ResponseBody
    public String index() {

        return "";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<SearchForm> naverSearchApi(@RequestParam(name = "serviceId", required = false) String serviceId
                                            , @RequestParam(name = "query", required = false) String query) {

        return apiService.naverSearch(serviceId, query);
    }
}