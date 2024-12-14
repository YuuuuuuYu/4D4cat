package com.nakji.myapp.labs.common.controller;

import com.nakji.myapp.labs.common.model.LabsInfo;
import com.nakji.myapp.labs.common.service.LabsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/labs")
public class LabsController {
    private final LabsService labsService;

    @GetMapping("/{thirdParty}")
    public String labs(Model model, @PathVariable String thirdParty) {
        LabsInfo labsInfo = labsService.getThirdPartyInfo(thirdParty);
        String thirdPartyName = labsInfo.thirdPartyName();
        switch (thirdPartyName) {
            case "naver", "google" -> {
                model.addAttribute("isSearch", labsInfo.isSearch());
            } case "openai" -> {
                model.addAttribute("isOpenai", true);
            } case "woorimail" -> {
                model.addAttribute("isWoorimail", true);
            }
        }
        model.addAttribute("title", "Labs");
        model.addAttribute("thirdPartyName", labsInfo.thirdPartyName());
        model.addAttribute("thirdPartyList", labsService.getThirdPartyList());

        return "labs";
    }
}
