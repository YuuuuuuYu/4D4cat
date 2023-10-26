package com.example.springbootmustache.common;

public class UserProperties {

    public String nvl(String value) {
        return value == null ? "" : value;
    }
}
