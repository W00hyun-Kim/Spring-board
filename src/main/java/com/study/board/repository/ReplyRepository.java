package com.study.board.repository;

import com.study.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Query("select r from reply r where r.board.id = :id")
    List<Reply> findReplyBoardId(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("delete from reply r where r.replyId = :id")
    void deleteByReplyId(@Param("id") Integer id);

}
