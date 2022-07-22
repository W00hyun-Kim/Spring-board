package com.study.board.controller;

import com.study.board.entity.Reply;
import com.study.board.repository.ReplyRepository;
import com.study.board.service.BoardServiceImpl;
import com.study.board.service.ReplyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    @Autowired
    private ReplyServiceImpl replyService;

    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private BoardServiceImpl boardService;

    @PostMapping("/board/view/replyWrite")
    public String replyWrite(@ModelAttribute Reply reply, Integer id, Model model) {

        replyService.replyWrite(reply, id);

        List<Reply> replyLists = replyRepository.findReplyBoardId(id);
        model.addAttribute("replyLists",replyLists);
        model.addAttribute("board", boardService.boardView(id));

        return "boardView";
    }

    @PostMapping("/board/view/replyDelete/")
    public String replyDelete(Model model, @PathParam("id") Integer id,
                              @PathParam("boardId") Integer boardId
                              ) {
        System.out.println("탔는가");
        System.out.println("replyid " +id);
        System.out.println("boardid " +boardId);
        replyService.replyDelete(id);
        model.addAttribute("board", boardService.boardView(boardId));
        return "redirect:/board/view?id="+boardId;
    }

}
