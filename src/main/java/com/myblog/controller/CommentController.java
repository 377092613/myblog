package com.myblog.controller;

import com.myblog.pojo.Comment;
import com.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/addComment")
    public void addComment(String nickname, String email, String avatar, Long blogid, Long commentid, HttpServletResponse response) throws IOException {
        String text=avatar;
        if(commentid!=null){
            int index=avatar.indexOf(":");
            text=avatar.substring(index+1);
        }
        Comment comment=new Comment();
        comment.setEmail(email);
        comment.setNickname(nickname);
        comment.setAvatar(text);
        comment.setCreateTime(new Date());
        comment.setBlogid(blogid);
        commentService.addComment(comment,commentid);
        response.getWriter().write("aaa");
    }
}
