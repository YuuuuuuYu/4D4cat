package com.example.springbootmustache.nakji;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nakji")
public class NakjiController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Hello, World!");
        model.addAttribute("message", "This is a mustache template.");
        return "nakji/index";
    }
}
