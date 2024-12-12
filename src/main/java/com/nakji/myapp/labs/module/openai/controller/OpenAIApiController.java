package com.nakji.myapp.labs.module.openai.controller;

import com.nakji.myapp.labs.module.openai.service.OpenAIApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/labs/openai")
public class OpenAIApiController {
    private final OpenAIApiService apiService;

    @GetMapping("/")
    public String index() {
        return "gpt!";
    }

    @GetMapping("/call")
    public String openAIGptApi(@RequestParam(name = "prompt", required = false) String prompt) {
        return apiService.openAIGpt(prompt);
    }
}