package com.example.springbootmustache.nakji.board.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class BoardDtoTest {

    @Test
    @DisplayName("Dto 변환 테스트")
    void entityToDto() {
        Date now = new Date();
        Board board = new Board();

        board.setBoardId(1L);
        board.setTitle("Test Title");
        board.setContent("Test Content");
        board.setViewCnt(0L);
        board.setUseYn("Y");
        board.setCreateId(1);
        board.setCreateDate(now);
        board.setUpdateId(1);
        board.setUpdateDate(now);

        BoardDto dto = BoardDto.entityToDto(board);

        assertThat(dto).isNotNull();
        assertThat(dto.getBoardId()).isEqualTo(1L);
        assertThat(dto.getTitle()).isEqualTo("Test Title");
        assertThat(dto.getContent()).isEqualTo("Test Content");
        assertThat(dto.getWriterId()).isEqualTo(1);
        assertThat(dto.getViewCnt()).isEqualTo(0L);
        assertThat(dto.getUseYn()).isEqualTo("Y");
        assertThat(dto.getUpdateDate()).isEqualTo(now);
    }
}