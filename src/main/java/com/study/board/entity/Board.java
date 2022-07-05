package com.study.board.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String title;

    @Column(name = "time")
    private String time;
    @PrePersist             //DB에 해당 테이블의 insert 연산을 실행할 때 아래와 같이 실행하도록 하는 annotation
    public void createdAt() {
        this.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();
    }

    @Column
    private String content;

    @Column
    private String filename;

    @Column
    private String filepath;
}