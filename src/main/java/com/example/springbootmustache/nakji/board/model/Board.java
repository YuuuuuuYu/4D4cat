package com.example.springbootmustache.nakji.board.model;

import lombok.Getter;

import java.util.Date;

@Getter
public class Board {

    private Long board_id;

    private String title;
    private String content;
    private int writer_id;
    private Long view_cnt;
    private String use_yn;

    private int create_id;
    private Date create_date;
    private int update_id;
    private Date update_date;
}
