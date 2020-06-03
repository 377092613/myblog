package com.myblog.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.dao.TagRepository;
import com.myblog.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements  TagService{
    @Autowired
    TagRepository tagRepository;

    @Override
    public List<Tag> getAllTag() {
        return tagRepository.getTagList();
    }

    @Override
    public PageInfo getTagList(Integer pageNum, Integer sizeNum, boolean count) {
        PageHelper.startPage(pageNum,sizeNum,count);
        List<Tag> list=tagRepository.getTagList();
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @Override
    public Tag getTagByid(Long id) {
        return tagRepository.getTagByid(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.getTagByName(name);
    }

    @Override
    public void createTag(Tag tag) {
        tagRepository.createTag(tag);
    }

    @Override
    public void updateTag(Tag tag) {
        tagRepository.updateTag(tag);
    }

    @Override
    public void deleteByTagid(Long id) {
        tagRepository.deleteByTagid(id);
    }
}
