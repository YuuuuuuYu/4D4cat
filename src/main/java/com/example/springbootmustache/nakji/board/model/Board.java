package com.example.springbootmustache.nakji.board.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String title;
    private String content;

    private int writerId;
    private Long viewCnt;
    private String useYn;

    private int createId;
    private Date createDate;
    private int updateId;
    private Date updateDate;
}
