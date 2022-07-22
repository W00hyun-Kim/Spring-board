package com.study.board.service;

import com.study.board.DTO.Pagination;
import com.study.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public interface BoardService {

    Pagination getPagination(Pageable pageable, Page<Board> list);

    void write(Board board);


    // 게시글 리스트 처리s
    Page<Board> boardList(Pageable pageable) ;


    Page<Board> boardSearchList(String searchKeyword, Pageable pageable) ;


    // 특정 게시글 불러오기
    Board boardView(Integer id);


    @Transactional
    int updateView(int id) ;

    //특정 게시글 지우기
    void boardDelete(Integer id) ;
}
