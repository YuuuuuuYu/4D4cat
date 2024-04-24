package com.example.springbootmustache.nakji.board.controller;

import com.example.springbootmustache.nakji.model.PageView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nakji/board")
public class BoardController {

    @GetMapping("/")
    public String index(Model model) {

        return "/nakji/lnb/board";
    }

    @GetMapping("/view")
    public PageView getView(PageView pageView) {

        return pageView;
    }
}
