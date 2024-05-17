package com.example.springbootmustache.nakji.board.model;

import autovalue.shaded.org.jetbrains.annotations.NotNull;
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

    @NotNull("Title cannot be null")
    private String title;

    @NotNull("Content cannot be null")
    private String content;
    private Long viewCnt;
    private String useYn;

    @NotNull("createId cannot be null")
    private int createId;
    private Date createDate;
    private int updateId;
    private Date updateDate;
}
