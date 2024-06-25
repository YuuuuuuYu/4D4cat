package com.example.springbootmustache.nakji.api.woori.controller;

import com.example.springbootmustache.nakji.api.woori.model.WooriMailDto;
import com.example.springbootmustache.nakji.api.woori.model.WooriMailRequestDto;
import com.example.springbootmustache.nakji.api.woori.service.WooriMailApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nakji/api/woorimail")
@RequiredArgsConstructor
public class WooriMailApiController {

    private final WooriMailApiService apiService;

    @GetMapping("/")
    @ResponseBody
    public String index() {

        return "";
    }

    @PostMapping("/send")
    @ResponseBody
    public void wooriMailApi(@RequestBody WooriMailRequestDto dto, Model model) {
        WooriMailDto sendDto = apiService.setDto(dto);

        model.addAttribute("result", apiService.sendMail(sendDto));
    }
}
