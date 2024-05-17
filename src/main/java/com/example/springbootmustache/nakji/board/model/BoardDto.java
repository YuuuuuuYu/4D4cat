package com.example.springbootmustache.nakji.board.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BoardDto {
    private Long boardId;

    private String title;
    private String content;

    private int writerId;
    private Long viewCnt;
    private String useYn;
    private Date updateDate;

    public static BoardDto entityToDto(Board board) {
        BoardDto dto = new BoardDto();
        dto.setBoardId(board.getBoardId());
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setWriterId(board.getUpdateId());
        dto.setViewCnt(board.getViewCnt());
        dto.setUseYn(board.getUseYn());
        dto.setUpdateDate(board.getUpdateDate());
        return dto;
    }
}