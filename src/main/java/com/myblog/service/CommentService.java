package com.myblog.service;

import com.myblog.pojo.Comment;

public interface CommentService {
    public void addComment(Comment comment, Long replyCommendid);
}
