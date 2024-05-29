package com.example.springbootmustache.nakji.api.google.controller;

import com.example.springbootmustache.nakji.api.google.service.GoogleApiService;
import com.example.springbootmustache.nakji.api.common.model.SearchForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/nakji/api/google")
@RequiredArgsConstructor
public class GoogleApiController {

    private final GoogleApiService apiService;

    @GetMapping("/")
    @ResponseBody
    public String index() {

        return "";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<SearchForm> googleSearchApi(@RequestParam(name = "query", required = false) String query) {

        return apiService.googleSearch(query);
    }

    @GetMapping("/token")
    public String getTokenUrl(@RequestParam(name = "simple", required = false) String simple) {
        String tokenPath = (simple == null) ? "/admin/token" : "/simpleToken";
        String redirectUrl = apiService.getActiveCode(tokenPath);

        return "redirect:" + redirectUrl;
    }

    @GetMapping("/admin/token")
    @ResponseBody
    public String accessTokenApi(@RequestParam(name = "code", required = false) String code){

        return apiService.getAccessToken(code);
    }
}