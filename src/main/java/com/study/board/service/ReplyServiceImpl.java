package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.entity.Reply;
import com.study.board.repository.BoardRepository;
import com.study.board.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReplyServiceImpl implements ReplyService{

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private BoardRepository boardRepository;

    public List<Reply> replyWrite(Reply reply, Integer id) {

        Optional<Board> findBoard = boardRepository.findById(id);
        reply.setBoard(findBoard.get());
        //reply.setWriter(reply.getWriter());
        replyRepository.save(reply);
        List<Reply> replyLists = replyRepository.findReplyBoardId(id);


        return replyLists;
    }

    public void replyDelete(int replyid) {
        System.out.println("replyDelete" + replyid);
        replyRepository.deleteByReplyId(replyid);
    }


}
