package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.entity.Reply;

import java.util.Optional;

public interface ReplyService {

    Reply replyWrite(Reply reply, Integer id);

    void replyDelete(int id);

}
