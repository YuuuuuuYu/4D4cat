package com.example.springbootmustache.nakji.board.repo;

import com.example.springbootmustache.nakji.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByBoardId(Long id);
}
