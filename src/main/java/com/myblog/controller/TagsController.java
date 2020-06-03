package com.myblog.controller;

import com.github.pagehelper.PageInfo;
import com.myblog.pojo.Tag;
import com.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.RequestWrapper;

@Controller
@RequestMapping("/manage")
public class TagsController {
    @Autowired
    TagService tagService;

    @RequestMapping("/tagpage/{pageNum}/{sizeNum}/{count}")
    public String doPage(@PathVariable Integer pageNum, @PathVariable Integer sizeNum, @PathVariable boolean count, RedirectAttributes attributes){
        PageInfo pageInfo=tagService.getTagList(pageNum,sizeNum,count);
        attributes.addFlashAttribute("pageInfo",pageInfo);
        return "redirect:/manage/tags";
    }

    @RequestMapping("/tags")
    public ModelAndView toTags(@ModelAttribute PageInfo pageInfo, HttpSession session){
        if(pageInfo.getPageNum()==0){
            pageInfo=tagService.getTagList(1,5,true);
        }
        String message=(String)session.getAttribute("message");
        ModelAndView mv=new ModelAndView();
        mv.addObject("pageInfo",pageInfo);
        if(message!=null){
            mv.addObject("message",message);
            session.setAttribute("message",null);
        }
        mv.setViewName("admin/tags");
        return mv;
    }

    @RequestMapping("/updateTag")
    public String checkData(String id,String name,String pageid,HttpSession session){
        PageInfo pageInfo=tagService.getTagList(1,5,true);
        Tag tag=tagService.getTagByName(name);
        if(tag!=null) {
            session.setAttribute("error", "该标签名已经存在");
            session.setAttribute("name", tag.getName());
            if(!id.equals("")) {
                session.setAttribute("id", tag.getId());
                session.setAttribute("pageid", pageid);
            }
        }else{
            if(!id.equals("")){
                tag=new Tag();
                tag.setName(name);
                tagService.createTag(tag);
                session.setAttribute("message","添加成功");
                System.out.println("jinru ："+pageInfo.getPages());
                return"redirect:/manage/tagpage/"+pageInfo.getPages()+"/5/true";
            }else{
                tag=new Tag();
                tag.setId(Long.parseLong(id));
                tag.setName(name);
                System.out.println("tag:"+tag);
                tagService.updateTag(tag);
                session.setAttribute("message","修改成功");
                return "redirect:/manage/tagpage/" + pageid + "/5/true";
            }
        }
        /*if(!id.equals("")){
            if(tag!=null){
                session.setAttribute("error","该标签名已经存在");
            }else{
                tag=new Tag();
                tag.setId(Long.parseLong(id));
                tag.setName(name);
                System.out.println("tag:"+tag);
                tagService.updateTag(tag);
                session.setAttribute("message","修改成功");
                return "redirect:/manage/tagpage/" + pageid + "/5/true";
                }

        }else{
            tag=new Tag();
            tag.setName(name);
            tagService.createTag(tag);
            session.setAttribute("message","添加成功");
            System.out.println("jinru ："+pageInfo.getPages());
            return"redirect:/manage/tagpage/"+pageInfo.getPages()+"/5/true";
        }*/

        return "forward:/manage/tag-input";
    }

    @RequestMapping("/editByTagid/{id}/{pageid}")
    public String pretoTag(@PathVariable Long id, @PathVariable String pageid,HttpSession session){
        Tag tag=tagService.getTagByid(id);
        session.setAttribute("tag",tag);
        session.setAttribute("pageid",Integer.parseInt(pageid));
        return "redirect:/manage/tag-input";
    }

    @RequestMapping(value = {"/tag-input"})
    public String toTagInput(Model model, HttpServletRequest request){
        Tag tag=(Tag)request.getSession().getAttribute("tag");
        String error=(String)request.getSession().getAttribute("error");
        if(tag!=null){
            model.addAttribute("name",tag.getName());
            model.addAttribute("id",tag.getId());
            model.addAttribute("pageid",request.getSession().getAttribute("pageid"));
            model.addAttribute("error",error);
            request.getSession().setAttribute("tag",null);
            request.getSession().setAttribute("pageid",null);
            request.getSession().setAttribute("error",null);
        }else{
            model.addAttribute("name",request.getSession().getAttribute("name"));
            model.addAttribute("id",request.getSession().getAttribute("id"));
            model.addAttribute("pageid",request.getSession().getAttribute("pageid"));
            model.addAttribute("error",error);
            request.getSession().setAttribute("name",null);
            request.getSession().setAttribute("pageid",null);
            request.getSession().setAttribute("id",null);
            request.getSession().setAttribute("error",null);
        }
        return "admin/tag-input";
    }

    @RequestMapping("deleteByTagid/{id}/{pageid}")
    public String deleteByTagid(@PathVariable  Long id,@PathVariable  int pageid,HttpSession session){
        tagService.deleteByTagid(id);
        session.setAttribute("message","删除成功");
        return "redirect:/manage/tagpage/"+pageid+"/5/true";
    }
}
