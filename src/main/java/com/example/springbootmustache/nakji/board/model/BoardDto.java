package com.example.springbootmustache.nakji.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
    private Long boardId;

    private String title;
    private String content;

    private int writerId;
    private Long viewCnt;
    private String useYn;
}