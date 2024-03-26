package com.example.springbootmustache.nakji.board.controller;

import com.example.springbootmustache.nakji.model.PageView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/nakji/board")
public class BoardController {

    @GetMapping("/")
    public PageView index(PageView pageView) {

        return pageView;
    }
}
