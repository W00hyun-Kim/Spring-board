package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public void write(Board board, MultipartFile file) throws Exception {

        String projectPath = System.getProperty("user.dir") +
                            "\\src\\main\\resources\\static\\files";
        UUID uuid = UUID.randomUUID();  //식별자 랜덤으로 이름만들기
        String fileName ="";

        if(file.getOriginalFilename()=="") {
            fileName = "No files";
        } else {
            fileName = uuid + "_" + file.getOriginalFilename();
        }
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);

            board.setFilename(fileName);
            board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }

    // 게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable) {
        //localhost:8090/board/list?page=1&size=10
        return boardRepository.findAll(pageable);   //findall()에 jpa를 이용하여 pageable이라는 클래스를 넘겨서 정보를 가지고 url에 페이지정보와 사이즈 넘김
    }

    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {
        //findBy(컬럼이름) ---> 검색할 때 정확히 일치해야 찾을 수 있음
        //findBy(컬럼이름)Containing --> 김우현 검색하고 싶을 때 김만 검색해도 됨
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer id) {

        return boardRepository.findById(id).get();
    }
}