package com.example.springbootmustache;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/api/search")
public class apiController {

    @GetMapping("/naver")
    public String naver(Model model) {
        model.addAttribute("title", "Hello, World!");
        model.addAttribute("message", "This is a mustache template.");
        return "index";
    }
}
