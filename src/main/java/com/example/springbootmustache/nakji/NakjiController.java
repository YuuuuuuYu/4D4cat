package com.example.springbootmustache.nakji;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nakji")
public class NakjiController {

    @GetMapping("/")
    public String index(Model model) {

        return "nakji/index";
    }

    @GetMapping("/{path}")
    public String getView(@PathVariable String path) {

        return "nakji/"+path;
    }
}