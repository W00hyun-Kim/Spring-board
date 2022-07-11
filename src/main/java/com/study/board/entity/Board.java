package com.study.board.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@Getter @Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(length = 500)
    private String title;

    @Column(name = "time")
    private String time;
    @PrePersist             //DB에 해당 테이블의 insert 연산을 실행할 때 아래와 같이 실행하도록 하는 annotation
    public void createdAt() {
        this.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
    }

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int countView;

    @Column
    private String filename;

    @Column
    private String filepath;



}