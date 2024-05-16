package com.example.springbootmustache.nakji.board.service;

import com.example.springbootmustache.nakji.board.model.Board;
import com.example.springbootmustache.nakji.board.model.BoardDto;
import com.example.springbootmustache.nakji.board.repo.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Board 객체 조회 테스트")
    public void testGetBoardById() {
        Date now = new Date();
        Board board = new Board();

        board.setBoardId(1L);
        board.setTitle("Test Title");
        board.setContent("Test Content");
        board.setWriterId(1);
        board.setViewCnt(0L);
        board.setUseYn("Y");
        board.setCreateId(1);
        board.setCreateDate(now);
        board.setUpdateId(1);
        board.setUpdateDate(now);

        when(boardRepository.findById(1L)).thenReturn(Optional.of(board));

        BoardDto dto = boardService.getBoardById(1L);

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