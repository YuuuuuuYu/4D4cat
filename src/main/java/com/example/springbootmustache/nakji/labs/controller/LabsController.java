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

        return "/nakji/lnb/labs";
    }
}
