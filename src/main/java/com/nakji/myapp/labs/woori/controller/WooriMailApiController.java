package com.nakji.myapp.labs.woori.controller;

import com.nakji.myapp.labs.woori.model.WooriMailDto;
import com.nakji.myapp.labs.woori.model.WooriMailRequestDto;
import com.nakji.myapp.labs.woori.service.WooriMailApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/labs/woorimail")
public class WooriMailApiController {
    private final WooriMailApiService apiService;

    @GetMapping("/")
    public String index() {
        return "woorimail!";
    }

    @PostMapping("/call")
    public void wooriMailApi(@RequestBody WooriMailRequestDto dto, Model model) {
        WooriMailDto sendDto = apiService.setDto(dto);
        model.addAttribute("result", apiService.sendMail(sendDto));
    }
}
