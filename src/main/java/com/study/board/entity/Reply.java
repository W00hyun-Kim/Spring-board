package com.study.board.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
    long sequenceId ==> primary key
    User user ==> ManyToOne(user가 부모) ==> 댓글작성자
    Board board ==> ManyToOne (board가 부모) ==> 댓글이 포함되는 게시글
    Timestamp createDate ==> 작성일
    String content ==> 댓글 내용
*/

@Entity(name = "reply")
@Data
@Getter @Setter
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer replyId;

    @NonNull
    @Column(nullable=false, length = 120)
    private String content;

    @ManyToOne
    @JoinColumn(name="id")
    private Board board;

    @CreationTimestamp
    private Timestamp createDate;

    public Reply() {}
}