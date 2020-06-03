package com.myblog.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.pojo.Blog;
import com.myblog.pojo.Type;
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
public class VisitorTypeController {

    @Autowired
    TypeService typeService;
    @Autowired
    BlogService blogService;

    @RequestMapping("/type/{pageNum}/{sizeNum}/{count}/{typeid}")
    public String toTypes(@PathVariable Integer pageNum, @PathVariable Integer sizeNum, @PathVariable boolean count, @PathVariable Long typeid , RedirectAttributes attributes, HttpSession session){
//        List<Type> types=typeService.getAllType();
        PageHelper.startPage(pageNum,sizeNum,count);
        List<Blog> blogs=blogService.getBlogByTypeid(typeid);
        System.out.println("typeid:"+typeid);
        PageInfo pageInfo=new PageInfo(blogs);
        attributes.addFlashAttribute("pageInfo",pageInfo);
        session.setAttribute("activeTypeId",typeid);
        return "redirect:/types";
    }


}
