package com.myblog.service;

import com.myblog.pojo.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogService {
    public List<Blog> getAllBlogs();
    public void insertBlog(Blog blog,String tags,String typeid);
    public void deleteBlogById(Long id);
    public Blog getBlogById(Long id);
    public void updateBlog(Blog blog, String tags,String typeid);
    public List<Blog> getBlogsBySearch(String title,String typeid,Boolean recommened);
    public int getBlogNumberByTypeid(Long typeid);
    public int getBlogNumberByTagid(Long tagid);
    public void addViews(Long id);
    public List<Blog> getBlogByTypeid(Long id);
    public List<Blog> getBlogByTagid(Long id);
    public List<Blog> getAllBlogForpublished();
}
