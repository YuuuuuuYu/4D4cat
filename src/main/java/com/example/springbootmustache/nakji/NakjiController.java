package com.example.springbootmustache.nakji;

import com.example.springbootmustache.nakji.api.service.ApiService;
import com.example.springbootmustache.nakji.model.PageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/nakji")
public class NakjiController {

    @Autowired
    ApiService apiService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Hello, World!");
        model.addAttribute("message", "This is a mustache template.");
        return "nakji/index";
    }

}
