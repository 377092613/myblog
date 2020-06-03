package com.myblog.pojo;

import lombok.*;

import java.util.Comparator;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Blog implements Comparable {
    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    private String description;
    //标记(原创、转载、翻译)
    private String flag;
    //浏览次数
    private Integer views;
    //赞赏功能是否开启
    private boolean appreciation;
    //转载功能是否开启
    private boolean shareStatement;
    //评论功能是否开启
    private boolean commentable;
    //是否发布
    private boolean published;
    //是否推荐
    private boolean recommened;

    private String createTime;

    private String updateTime;

    private Type type;

    private List<Tag> tags;

    private Long userid;

    private List<Comment> comments;

    public boolean getAppreciation() {
        return appreciation;
    }

    public boolean getShareStatement() {
        return shareStatement;
    }

    public boolean getCommentable() {
        return commentable;
    }

    public boolean getPublished() {
        return published;
    }

    public boolean getRecommened() {
        return recommened;
    }


    @Override
    public int compareTo(Object o) {
        return ((Blog)o).getUpdateTime().compareTo(this.getUpdateTime());
    }
}
