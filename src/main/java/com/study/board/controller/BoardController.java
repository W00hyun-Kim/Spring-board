package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.sevice.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;
    @GetMapping("/board/write")    //localhost:8090/board/write
    public String boardWriteForm() {

        return "boardWrite";
    }

    @PostMapping("/board/writePro")
    public String boardWritePro(Board board) {

        boardService.write(board);
        System.out.println(board.getTitle());

        return "";
    }

    @GetMapping("board/list")           //해당 url로 연결하면
    public String boardList(Model model) {

        model.addAttribute("list", boardService.boardList());

        return "boardList";             //아래 html 파일로 연결을 시켜준다.
    }

    @GetMapping("/board/view") // localhost:8080/board/view?id=1
    public String boardView(Model model, Integer id) {
        model.addAttribute("board", boardService.boardView(id));
        return "boardView";
    }

    @GetMapping("board/delete")           //localhost:8090/board/delete?id=1
    public String boardDelete(Integer id) {
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
    public String boardUpdate(@PathVariable("id") Integer id, Board board){

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        return "redirect:/board/list";

    }

}
