package com.example.springbootmustache.nakji.model;

public class SearchForm {
    private String title;
    private String content;
    private String link;

    public SearchForm(String title, String content, String link) {
        this.title = title;
        this.content = content;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "title: " + title + ", content: " + content + ", link: " + link;
    }
}
