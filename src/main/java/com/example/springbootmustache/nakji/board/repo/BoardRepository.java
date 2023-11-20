package com.example.springbootmustache.nakji.board.repo;

import com.example.springbootmustache.nakji.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
