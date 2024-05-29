package com.example.springbootmustache.nakji.api.common.model;

import lombok.Getter;

@Getter
public class SearchForm {
    private String title;
    private String content;
    private String link;

    public SearchForm(String title, String content, String link) {
        this.title = title;
        this.content = content;
        this.link = link;
    }

    @Override
    public String toString() {
        return "title: " + title + ", content: " + content + ", link: " + link;
    }
}
