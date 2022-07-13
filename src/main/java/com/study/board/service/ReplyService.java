package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.entity.Reply;
import com.study.board.repository.BoardRepository;
import com.study.board.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private BoardRepository boardRepository;

    public Reply replyWrite(Reply reply, Integer id) {

        Optional<Board> findBoard = boardRepository.findById(id);
        reply.setBoard(findBoard.get());

        return replyRepository.save(reply);
    }

    public void replyDelete(Reply reply) {
        replyRepository.delete(reply);
    }


}
