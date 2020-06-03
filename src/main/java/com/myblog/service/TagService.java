package com.myblog.service;

import com.github.pagehelper.PageInfo;
import com.myblog.pojo.Tag;

import java.util.List;

public interface TagService {
    public PageInfo getTagList(Integer pageNum, Integer sizeNum, boolean count);
    public Tag getTagByid(Long id);
    public Tag getTagByName(String name);
    public void createTag(Tag tag);
    public void updateTag(Tag tag);
    public void deleteByTagid(Long id);
    public List<Tag> getAllTag();
}
