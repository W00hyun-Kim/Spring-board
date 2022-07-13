package com.study.board.controller;

import com.study.board.entity.Reply;
import com.study.board.repository.ReplyRepository;
import com.study.board.service.BoardService;
import com.study.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private BoardService boardService;

    @PostMapping("/board/view/replyWrite")
    public String replyWrite(@ModelAttribute Reply reply, Integer id, Model model) {

        replyService.replyWrite(reply, id);

        List<Reply> replyLists = replyRepository.findReplyBoardId(id);
        model.addAttribute("replyLists",replyLists);
        model.addAttribute("board", boardService.boardView(id));

        return "boardView";
    }

    @PostMapping("/board/view/replyDelete")
    public String replyDelete(@ModelAttribute Reply reply) {
        replyService.replyDelete(reply);

        return "boardView";
    }

}
