package com.study.board.repository;

import com.study.board.entity.Board;
import com.study.board.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

//    @Modifying
//    @Query(value = "select * from reply r where u.id = :id", nativeQuery = true)
//    void replyView(@Param("id") int id);


}
