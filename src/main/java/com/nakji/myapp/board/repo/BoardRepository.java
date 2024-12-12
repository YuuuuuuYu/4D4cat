package com.nakji.myapp.board.repo;

import com.nakji.myapp.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> { }