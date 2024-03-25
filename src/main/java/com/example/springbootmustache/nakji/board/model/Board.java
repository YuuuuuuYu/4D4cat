package com.example.springbootmustache.nakji.board.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Table(name = "Board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column
    private String title;
    @Column
    private String content;
    @Column
    private int writerId;
    @Column
    private Long viewCnt;
    @Column
    private String useYn;

    @Column
    private int createId;
    @Column
    private Date createDate;
    @Column
    private int updateId;
    @Column
    private Date updateDate;
}
