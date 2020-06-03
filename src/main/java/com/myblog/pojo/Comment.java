package com.myblog.pojo;

import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Comment {
    private Long id;
    private String nickname;
    private String email;
//    private String content;
    private String avatar;
    private Date createTime;
    private Long blogid;
    private List<Comment> replyComments;
}
