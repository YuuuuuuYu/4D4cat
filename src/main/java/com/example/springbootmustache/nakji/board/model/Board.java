package com.example.springbootmustache.nakji.board.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;


import java.util.Date;

@Getter
@Entity
@Table(name="board")
public class Board {

    @Id
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
