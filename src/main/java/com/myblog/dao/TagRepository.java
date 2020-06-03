package com.myblog.dao;

import com.myblog.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
@Mapper
public interface TagRepository {
    public List<Tag> getTagList();
    public Tag getTagByid(Long id);
    public void createTag(Tag tag);
    public Tag getTagByName(String name);
    public void updateTag(Tag tag);
    public void deleteByTagid(Long id);
}
