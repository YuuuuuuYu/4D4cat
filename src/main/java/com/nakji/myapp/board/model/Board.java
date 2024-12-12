package com.nakji.myapp.board.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message="Title cannot be null")
    private String title;

    @NotNull(message="Content cannot be null")
    private String content;
    private Long viewCnt;
    private String useYn;

    @NotNull(message="CreateId cannot be null")
    private int createId;
    private Date createDate;
    private int updateId;
    private Date updateDate;
}
