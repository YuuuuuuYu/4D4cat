package com.example.springbootmustache.nakji.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nakji/page")
public class PageController {

    @GetMapping("/{path}")
    public String index(Model model, @PathVariable String path) {
        String convertPath = path.replace("-", "/");
        return "nakji/" + convertPath;
    }

}
