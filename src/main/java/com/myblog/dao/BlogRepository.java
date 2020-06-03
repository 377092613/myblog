package com.myblog.dao;

import com.myblog.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Mapper
@Repository
public interface BlogRepository {
    public List<Blog> getAllBlog();
    public void insertBlog(@Param("blog") Blog blog,@Param("tags") String tags,@Param("typeid") String typeid);
    public void deleteBlogById(Long id);
    public Blog getBlogById(Long id);
    public void updateBlog(@Param("blog") Blog blog,String tags,String typeid);
    public List<Blog> getBlogsBySearch(String title,String typeid,Boolean recommened);
    public int getBlogNumberByTypeid(Long typeid);
    public int getBlogNumberByTagid(Long tagid);
    public void addViews(Long id);
    public List<Blog> getBlogByTypeid(Long id);
    public List<Blog> getBlogByTagid(Long id);
    public List<Blog> getAllBlogForpublished();
}
