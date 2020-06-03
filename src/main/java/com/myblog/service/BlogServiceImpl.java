package com.myblog.service;

import com.myblog.dao.BlogRepository;
import com.myblog.pojo.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogRepository blogRepository;

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.getAllBlog();
    }

    @Override
    public void insertBlog(Blog blog, String tags, String typeid) {
        blogRepository.insertBlog(blog,tags,typeid);
    }

    @Override
    public void deleteBlogById(Long id) {
        blogRepository.deleteBlogById(id);
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.getBlogById(id);
    }

    @Override
    public void updateBlog(Blog blog, String tags,String typeid) {
        blogRepository.updateBlog(blog,tags,typeid);
    }

    @Override
    public List<Blog> getBlogsBySearch(String title, String typeid, Boolean recommened) {
//        System.out.println("searchtypeid1:"+typeid);
//        if(typeid.equals("0")){
//            typeid=null;
//        }
        System.out.println("title:"+title+",typeid:"+typeid+",recommend:"+recommened);
        return blogRepository.getBlogsBySearch(title,typeid,recommened);
    }

    @Override
    public int getBlogNumberByTypeid(Long typeid) {
        return blogRepository.getBlogNumberByTypeid(typeid);
    }

    @Override
    public int getBlogNumberByTagid(Long tagid) {
        return blogRepository.getBlogNumberByTagid(tagid);
    }

    @Override
    public void addViews(Long id) {
        blogRepository.addViews(id);
    }

    @Override
    public List<Blog> getBlogByTypeid(Long id) {
        return blogRepository.getBlogByTypeid(id);
    }

    @Override
    public List<Blog> getBlogByTagid(Long id) {
        return blogRepository.getBlogByTagid(id);
    }

    @Override
    public List<Blog> getAllBlogForpublished() {
        return blogRepository.getAllBlogForpublished();
    }
}
