package com.example.springbootmustache.nakji.api.controller;

import com.example.springbootmustache.nakji.api.service.ApiService;
import com.example.springbootmustache.nakji.model.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/nakji/api")
public class ApiController {

    @Autowired
    ApiService apiService;

    @GetMapping("/naver")
    @ResponseBody
    public List<SearchForm> naverSearchApi(@RequestParam(name = "serviceId", required = false) String serviceId
                                           , @RequestParam(name = "query", required = false) String query) {

        return apiService.naverSearch(serviceId, query);
    }

    @GetMapping("/google")
    @ResponseBody
    public List<SearchForm> googleSearchApi(@RequestParam(name = "query", required = false) String query) {

        return apiService.googleSearch(query);
    }

    @GetMapping("/token")
    public String getTokenUrl(@RequestParam(name = "simple", required = false) String simple) {

        return "redirect:"+apiService.getActiveCode(simple == null ? "/admin/token" : "/simpleToken");
    }

    @GetMapping("/admin/token")
    @ResponseBody
    public String accessTokenApi(@RequestParam(name = "code", required = false) String code){

        return apiService.getAccessToken(code);
    }

}
