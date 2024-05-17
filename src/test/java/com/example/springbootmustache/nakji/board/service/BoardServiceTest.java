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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    private Board singleBoard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        singleBoard = new Board();
        singleBoard.setBoardId(1L);
        singleBoard.setTitle("Test Title");
        singleBoard.setContent("Test Content");
        singleBoard.setViewCnt(0L);
        singleBoard.setUseYn("Y");
        singleBoard.setCreateId(1);
        singleBoard.setCreateDate(new Date());
        singleBoard.setUpdateId(1);
        singleBoard.setUpdateDate(new Date());
    }

    @Test
    @DisplayName("Board 객체 조회 테스트")
    public void testGetBoardById() {
        // Given
        when(boardRepository.findById(1L)).thenReturn(Optional.of(singleBoard));

        // When
        BoardDto dto = boardService.getBoardById(1L);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getBoardId()).isEqualTo(1L);
        assertThat(dto.getTitle()).isEqualTo("Test Title");
        assertThat(dto.getContent()).isEqualTo("Test Content");
        assertThat(dto.getWriterId()).isEqualTo(1);
        assertThat(dto.getViewCnt()).isEqualTo(0L);
        assertThat(dto.getUseYn()).isEqualTo("Y");
    }

    @Test
    @DisplayName("Board 객체 전체 조회 테스트")
    public void testFindAll() {
        List<Board> boardList = new ArrayList<>();
        Board board = null;

        for (long boardId = 1L; boardId <= 5L; boardId++) {
            board = singleBoard;
            board.setBoardId(boardId);
            boardList.add(board);
        }

        // Given
        when(boardRepository.findAll()).thenReturn(boardList);

        // When
        List<BoardDto> dtoList = boardService.findAll();

        // Then
        assertThat(dtoList).isNotNull();
        assertThat(dtoList.size()).isEqualTo(5);
        assertThat(dtoList.get(0).getBoardId()).isEqualTo(1L);
        assertThat(dtoList.get(1).getBoardId()).isEqualTo(2L);
        assertThat(dtoList.get(4).getBoardId()).isEqualTo(5L);
    }

    @Test
    @DisplayName("Board 객체 추가 테스트")
    public void testCreateBoard() {
        BoardDto boardDto = new BoardDto();
        boardDto.setTitle("Test Title");
        boardDto.setContent("Test Content");
        boardDto.setWriterId(1);
        boardDto.setUseYn("Y");

        // Given
        when(boardRepository.save(any(Board.class))).thenReturn(singleBoard);

        // When
        BoardDto createdBoardDto = boardService.createBoard(boardDto);

        // Then
        assertEquals(boardDto.getTitle(), createdBoardDto.getTitle());
        assertEquals(boardDto.getContent(), createdBoardDto.getContent());
        assertEquals(boardDto.getWriterId(), createdBoardDto.getWriterId());
        assertEquals(boardDto.getUseYn(), createdBoardDto.getUseYn());
    }

    @Test
    @DisplayName("Board 객체 수정 테스트")
    void testUpdateBoard() {
        // Given
        Long boardId = 1L;
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(singleBoard));
        when(boardRepository.save(any(Board.class))).thenReturn(singleBoard);

        BoardDto updatedBoardDto = new BoardDto();
        updatedBoardDto.setTitle("Updated Title");
        updatedBoardDto.setContent("Updated Content");
        updatedBoardDto.setWriterId(101);
        updatedBoardDto.setUseYn("N");

        // When
        BoardDto result = boardService.updateBoard(boardId, updatedBoardDto);

        // Then
        assertEquals(updatedBoardDto.getTitle(), result.getTitle());
        assertEquals(updatedBoardDto.getContent(), result.getContent());
        assertEquals(updatedBoardDto.getWriterId(), result.getWriterId());
        assertEquals(updatedBoardDto.getUseYn(), result.getUseYn());
    }
}