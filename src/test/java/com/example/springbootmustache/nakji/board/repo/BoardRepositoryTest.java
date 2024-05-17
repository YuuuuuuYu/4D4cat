package com.example.springbootmustache.nakji.board.repo;

import com.example.springbootmustache.nakji.board.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("데이터 생성 후 조회 테스트")
    public void testSaveAndFindById() {
        // Given
        Board board = new Board();
        board.setTitle("Test Title");
        board.setContent("Test Content");
        board.setCreateId(1);
        board.setCreateDate(new java.util.Date());

        // When
        Board savedBoard = boardRepository.save(board);
        Optional<Board> foundBoard = boardRepository.findById(savedBoard.getBoardId());

        // Then
        assertThat(foundBoard).isPresent();
        assertThat(foundBoard.get().getTitle()).isEqualTo("Test Title");
        assertThat(foundBoard.get().getContent()).isEqualTo("Test Content");
    }
}