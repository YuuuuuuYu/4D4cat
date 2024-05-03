package com.example.springbootmustache.nakji.page.controller;

import com.example.springbootmustache.nakji.page.model.BindingDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/nakji/page")
public class PageController {

    @GetMapping("/{path}")
    public String index(Model model, @PathVariable String path) {
        String convertPath = path.replace("-", "/");
        return "nakji/" + convertPath;
    }

    @PostMapping("/{path}")
    public String indexPost(Model model, @PathVariable String path, @RequestBody List<BindingDto> bindingObj) {
        String convertPath = path.replace("-", "/");
        model.addAttribute("bindingObj", bindingObj);

        return "nakji/" + convertPath;
    }
}
