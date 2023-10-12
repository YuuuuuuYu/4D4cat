package com.example.springbootmustache.main;

import com.example.springbootmustache.common.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    @Qualifier("nakjiProperty")
    private UserProperties nakji;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("nakji", nakji);

        return "index";
    }
}