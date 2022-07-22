package com.study.board.controller;

import com.study.board.DTO.Pagination;
import com.study.board.entity.Board;
import com.study.board.entity.Reply;
import com.study.board.repository.ReplyRepository;
import com.study.board.service.BoardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardServiceImpl boardService;

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
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
                            Pageable pageable,
                            String searchKeyword) {

        Page<Board> list = null;

        if(searchKeyword == null) {
            list = boardService.boardList(pageable);
        }else {
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        Pagination page = boardService.getPagination(pageable, list);

        model.addAttribute("list", list);
        model.addAttribute("p", page);
        model.addAttribute("searchKeyword", searchKeyword);

        return "boardList";
    }

    @GetMapping("/board/view") // localhost:8080/board/view?id=1
    public String boardView(@ModelAttribute Reply reply, Model model, Integer id) {
        model.addAttribute("board", boardService.boardView(id));
        boardService.updateView(id);

        //댓글 list가 계속 보여지기 위해서 view에도 명시해둠
        List<Reply> replyLists = replyRepository.findReplyBoardId(id);
        model.addAttribute("replyLists",replyLists);
        model.addAttribute("reply",reply);

        return "boardView";
    }

    @PostMapping("/board/delete/") //localhost:9090/board/view?id=1 조건에 해당하는 모든 것을 뽑을 때
    public String boardDelete(@PathParam("id") Integer id) {

        boardService.boardDelete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}") //localhost:9090/board/view 뽑고싶은거 하나일 때
    public String boardModify(@PathVariable("id") Integer id, Model model) {

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
        boardService.write(boardTemp);

        return "redirect:/board/list";

    }
}

