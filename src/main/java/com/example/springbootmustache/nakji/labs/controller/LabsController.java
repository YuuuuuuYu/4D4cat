package com.example.springbootmustache.nakji.labs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nakji/labs")
public class LabsController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("labsIntro", "/nakji/lnb/labs/labs_intro");
        return "/nakji/lnb/labs";
    }

    @GetMapping("/md_editor")
    public String md_editor(Model model) {
        model.addAttribute("labsMdEditor", "/nakji/lnb/labs/labs_MD_Editor");
        return "/nakji/lnb/labs";
    }
}
