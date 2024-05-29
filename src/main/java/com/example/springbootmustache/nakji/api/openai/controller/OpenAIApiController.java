package com.example.springbootmustache.nakji.api.openai.controller;

import com.example.springbootmustache.nakji.api.openai.service.OpenAIApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/nakji/api/gpt")
@RequiredArgsConstructor
public class OpenAIApiController {

    private final OpenAIApiService apiService;

    @GetMapping("/")
    @ResponseBody
    public String index() {

        return "";
    }

    @GetMapping("/search")
    @ResponseBody
    public String openAIGptApi(@RequestParam(name = "prompt", required = false) String prompt) {

        return apiService.openAIGpt(prompt);
    }
}