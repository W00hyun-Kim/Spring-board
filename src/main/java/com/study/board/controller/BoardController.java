package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.entity.Reply;
import com.study.board.repository.ReplyRepository;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private ReplyRepository replyRepository;

    @GetMapping("/board/write") //localhost:8090/board/write
    public String boardWriteForm() {

        return "boardWrite";
    }

    @GetMapping("/board/message")
    public String NoFileAlert(Model model) {

        //파일이 없을 때 예외처리
        model.addAttribute("message", "다운로드할 파일이 없습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception {
        if(board.getTitle().isEmpty()) {
            model.addAttribute("message", "제목을 입력해주세요.");
            model.addAttribute("searchUrl", "/board/write");
            return "message";
        } else if(board.getContent().isEmpty()) {
            model.addAttribute("message", "내용을 입력해주세요.");
            model.addAttribute("searchUrl", "/board/write");
            return "message";
        }

        boardService.write(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    //페이징 처리하기
    //nowPage = 현재 페이지
    //startPage = 블럭에서 보여줄 시작 페이지
    //endPage = 블럭에서 보여줄 마지막 페이지
    @GetMapping("/board/list")
    public String boardList(Model model,    //page: default페이지, size:한 페이지 게시글 수, sort:정렬 기준 컬럼, direction: 정렬 순서
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword) {

        int pPage = 0; int nPage =0; int countPerPage = 10;
        Page<Board> list = null;

        if(searchKeyword == null) {
            list = boardService.boardList(pageable);
        }else {
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() +1;
        int totalBlock = list.getTotalPages();
        int startPage = ((nowPage-1)/ countPerPage) * countPerPage + 1;
        if(startPage == 1) {
            pPage = 1;
        }
        if(startPage > 10) {
            pPage = startPage - countPerPage;
        }

        int endPage = startPage + countPerPage -1;
        if(endPage > totalBlock) {
            endPage = totalBlock;
            nPage = endPage;
        } else {
            nPage = endPage +1;
        }

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("pPage", pPage);
        model.addAttribute("nPage", nPage);
        model.addAttribute("pageSize", 10);
        model.addAttribute("nnPage", totalBlock);
        model.addAttribute("ppPage", 1);

        return "boardList";
    }

    @GetMapping("/board/view") // localhost:8080/board/view?id=1
    public String boardView(Model model, Integer id) {
        model.addAttribute("board", boardService.boardView(id));
        boardService.updateView(id);

        //댓글 list가 계속 보여지기 위해서 view에도 명시해둠
        List<Reply> replyLists = replyRepository.findReplyBoardId(id);
        model.addAttribute("replyLists",replyLists);

        return "boardView";
    }

    @GetMapping("/board/delete/{id}") // localhost:8080/board/delete?id=1
    public String boardDelete(@PathVariable("id") Integer id) {

        boardService.boardDelete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id,
                              Model model) {

        model.addAttribute("board", boardService.boardView(id));

        return "boardModify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board) throws Exception {

        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardTemp.setWriter(board.getWriter());
        boardTemp.setTime(nowTime);

// file editing function -->
//        boardTemp.setFilename(board.getFilename());
//        boardTemp.setFilepath(board.getFilepath());

//        boardService.write(boardTemp, file);
        boardService.write(boardTemp);

        return "redirect:/board/list";

    }
}

