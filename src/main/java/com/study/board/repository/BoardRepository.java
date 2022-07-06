package com.study.board.repository;

import com.study.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Modifying
    @Query("update Board p set p.countView = p.countView + 1 where p.id = :id")
    int updateView(@Param("id") Integer id);

    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);




}