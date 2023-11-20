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
    private Long board_id;

    @Column
    private String title;
    @Column
    private String content;
    @Column
    private int writer_id;
    @Column
    private Long view_cnt;
    @Column
    private String use_yn;

    @Column
    private int create_id;
    @Column
    private Date create_date;
    @Column
    private int update_id;
    @Column
    private Date update_date;
}
