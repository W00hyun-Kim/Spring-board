package com.study.board.service;

import com.study.board.DTO.Pagination;
import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardRepository boardRepository;

    public Pagination getPagination(Pageable pageable, Page<Board> list) {

        Pagination page = new Pagination();
//        Page<Board> list = boardList(pageable);
        int pPage = 0; int nPage = 0;
        int countPerPage =10;
        int nowPage = list.getPageable().getPageNumber()+1;
        int totalBlock = list.getTotalPages();
        int startPage = ((nowPage-1)/countPerPage) * countPerPage + 1;
        if(startPage == 1) {
            pPage = 1;
        }
        if(startPage > 10) {
            pPage = startPage - countPerPage;
        }

        int endPage = startPage + countPerPage - 1;
        if(endPage > totalBlock) {
            endPage = totalBlock;
            nPage = endPage;
        } else {
            nPage = endPage + 1;
        }

        page.setcPage(nowPage);
        page.setsPage(startPage);
        page.setePage(endPage);
        page.setpPage(pPage);
        page.setnPage(nPage);
        page.setPageSize(10);
        page.setPpPage(1);
        page.setNnPage(totalBlock);

        return page;
    }

    public void write(Board board, MultipartFile file) throws IOException {

        String projectPath = System.getProperty("user.dir") +
                            "\\src\\main\\resources\\static\\files";
        UUID uuid = UUID.randomUUID();  //식별자 랜덤으로 이름만들기
        String fileName ="";

        //파일이 없을 때
        if(file.getOriginalFilename()=="") {
            board.setFilename(null);
            board.setFilepath(null);
        } else {
            fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);
            board.setFilename(fileName);
            board.setFilepath("/files/" + fileName);
        }
        boardRepository.save(board);
    }

    public void write(Board board) {
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

    @Transactional
    public int updateView(int id) {
        return boardRepository.updateView(id);
    }

    //특정 게시글 지우기
    public void boardDelete(Integer id) {

        boardRepository.deleteById(id);
    }



}