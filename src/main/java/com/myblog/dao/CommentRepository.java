package com.myblog.dao;

import com.myblog.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommentRepository {
    public void addComment(Comment comment, Long replyCommendid);
}
