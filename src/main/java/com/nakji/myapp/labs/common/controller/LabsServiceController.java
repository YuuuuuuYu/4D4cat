package com.nakji.myapp.labs.common.controller;

import com.nakji.myapp.labs.common.model.SearchForm;
import com.nakji.myapp.labs.module.openai.service.OpenAIApiService;
import com.nakji.myapp.labs.search.google.service.GoogleApiService;
import com.nakji.myapp.labs.search.naver.service.NaverApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/labs")
public class LabsServiceController {
    private final NaverApiService naverApiService;
    private final GoogleApiService googleApiService;
    private final OpenAIApiService openAIApiService;

    @GetMapping("/naver/search")
    public List<SearchForm> naverSearchApi(@RequestParam(name = "serviceId", required = false) String serviceId
            , @RequestParam(name = "query", required = false) String query) {
        return naverApiService.naverSearch(serviceId, query);
    }

    @GetMapping("/google/search")
    public List<SearchForm> googleSearchApi(@RequestParam(name = "query", required = false) String query) {
        return googleApiService.googleSearch(query);
    }

    @GetMapping("/openai/call")
    public String openAIGptApi(@RequestParam(name = "prompt", required = false) String prompt) {
        return openAIApiService.openAIGpt(prompt);
    }
}
