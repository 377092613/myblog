package com.myblog.pojo;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class User {
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String email;
    //头像
    private String avatar;
    //用户类型
    private Integer type;
    private Date createTime;
    private Date updateTime;
    private String blogs;

}
