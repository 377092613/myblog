package com.myblog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.pojo.Blog;
import com.myblog.service.BlogService;
import com.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class VisitorTagController {
    @Autowired
    TypeService typeService;
    @Autowired
    BlogService blogService;

    @RequestMapping("/tag/{pageNum}/{sizeNum}/{count}/{tagid}")
    public String toTags(@PathVariable Integer pageNum, @PathVariable Integer sizeNum, @PathVariable boolean count, @PathVariable Long tagid , RedirectAttributes attributes, HttpSession session){
//        List<Type> types=typeService.getAllType();
        PageHelper.startPage(pageNum,sizeNum,count);
        List<Blog> blogs=blogService.getBlogByTagid(tagid);
        PageInfo pageInfo=new PageInfo(blogs);
        attributes.addFlashAttribute("pageInfo",pageInfo);
        session.setAttribute("activeTagId",tagid);
        return "redirect:/tags";
    }

}
