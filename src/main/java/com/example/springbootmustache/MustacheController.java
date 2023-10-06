package com.example.springbootmustache;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MustacheController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Hello, World!");
        model.addAttribute("message", "This is a mustache template.");
        return "index";
    }

}