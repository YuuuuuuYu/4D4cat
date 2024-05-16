package com.example.springbootmustache.nakji.board.service;

import com.example.springbootmustache.nakji.board.model.Board;
import com.example.springbootmustache.nakji.board.model.BoardDto;
import com.example.springbootmustache.nakji.board.repo.BoardRepository;
import com.example.springbootmustache.nakji.common.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    CommonService commonService;

    @Autowired
    private BoardRepository boardRepository;

    public BoardDto getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("Board not found"));
        return BoardDto.entityToDto(board);
    }
}
