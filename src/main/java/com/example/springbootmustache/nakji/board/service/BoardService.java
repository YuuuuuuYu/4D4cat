package com.example.springbootmustache.nakji.board.service;

import com.example.springbootmustache.nakji.board.model.Board;
import com.example.springbootmustache.nakji.board.model.BoardDto;
import com.example.springbootmustache.nakji.board.repo.BoardRepository;
import com.example.springbootmustache.nakji.common.service.NakjiService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService extends NakjiService {

    @Autowired
    private BoardRepository boardRepository;

    public BoardDto getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("Board not found"));
        return BoardDto.entityToDto(board);
    }

    public List<BoardDto> findAll() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(BoardDto::entityToDto).collect(Collectors.toList());
    }

    @Transactional
    public BoardDto createBoard(BoardDto boardDto) {
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setViewCnt(0L);
        board.setUseYn("Y");
        board.setCreateId(boardDto.getWriterId());
        board.setCreateDate(new Date());
        board.setUpdateId(boardDto.getWriterId());
        board.setUpdateDate(new Date());
        boardRepository.save(board);

        return BoardDto.entityToDto(board);
    }

    @Transactional
    public BoardDto updateBoard(Long boardId, BoardDto boardDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new EntityNotFoundException("Board not found"));
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setUseYn(boardDto.getUseYn());
        board.setUpdateId(boardDto.getWriterId());
        board.setUpdateDate(new Date());
        boardRepository.save(board);

        return BoardDto.entityToDto(board);
    }
}
