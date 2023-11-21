package com.example.springbootmustache.nakji;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/nakji")
public class NakjiController {

    @GetMapping("/")
    public String index(Model model) {

        return "nakji/index";
    }

    @GetMapping("/code")
    @ResponseBody
    public String activeCodeApi(@RequestParam(name = "code", required = false) String code) {

        return code;
    }
}