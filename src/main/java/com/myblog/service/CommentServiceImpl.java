package com.myblog.service;

import com.myblog.dao.CommentRepository;
import com.myblog.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Override
    public void addComment(Comment comment, Long replyCommendid) {
        commentRepository.addComment(comment,replyCommendid);
    }
}
