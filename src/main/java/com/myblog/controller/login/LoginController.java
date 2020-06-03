package com.myblog.controller.login;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.dao.TypeRepository;
import com.myblog.pojo.Blog;
import com.myblog.pojo.Tag;
import com.myblog.pojo.Type;
import com.myblog.pojo.User;
import com.myblog.service.BlogService;
import com.myblog.service.TagService;
import com.myblog.service.TypeService;
import com.myblog.service.UserService;
import com.myblog.util.MD5Utils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manage")
public class LoginController {
    @Autowired
    UserService service;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;
    @Autowired
    BlogService blogService;

    @RequestMapping("/index")
    public String toadminIndex(){
        return "admin/index";
    }

    @RequestMapping("/login")
    public String doLogin(@RequestParam String username,@RequestParam String password,HttpSession session, RedirectAttributes attributes){
        User user=service.checkUser(username, MD5Utils.code(password));
        if(user==null){
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/login";
        }else{
            user.setPassword(null);
            session.setAttribute("user",user);
            return "redirect:/manage/index";
        }
    }


    @RequestMapping("/logout")
    public String doLogout(HttpSession session){
        session.setAttribute("user",null);
        return "redirect:/login";
    }

    @RequestMapping("/blogs")
    public String toAdmin(HttpServletRequest request,Model model,@ModelAttribute PageInfo pageInfo)
    {
        if(pageInfo.getPageNum()==0){
            Page<Object> page= PageHelper.startPage(1,5,true);
            pageInfo=new PageInfo(blogService.getAllBlogs());
        }
        User user=(User)request.getSession().getAttribute("user");
        List<Type> types=typeService.getAllType();
        Type all=new Type();
        all.setId((long)0);
        all.setName("所有");
        types.add(0,all);
        model.addAttribute("message",((String)request.getSession().getAttribute("message")));
        request.getSession().setAttribute("message",null);
        model.addAttribute("user",user);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("types",types);
        if(request.getSession().getAttribute("searchmap")!=null){
            Map<String,Object> searchmap= (Map) request.getSession().getAttribute("searchmap");
            if(searchmap.get("title")!=null){
                model.addAttribute("searchtitle",searchmap.get("title").toString());
            }

            System.out.println("gettypeid:"+searchmap.get("typeid"));
            if(searchmap.get("typeid")!=null){
                Type type=typeService.getType(Long.parseLong((String)searchmap.get("typeid")));
                if(type!=null) {
                    model.addAttribute("typename", type.getName());
                    model.addAttribute("typeid", Long.parseLong((String)searchmap.get("typeid")));
                }else{
                    if(searchmap.get("typeid").equals("0")){
                        model.addAttribute("typename","所有");
                        model.addAttribute("typeid",0);
                    }
                }

            }

            if(searchmap.get("recommend")!=null){
                if((Boolean)searchmap.get("recommend")==true) {
                    model.addAttribute("recommened", "checked");
                }
                }
            request.getSession().setAttribute("searchmap",null);
        }
        return "admin/blogs";
    }

    @RequestMapping("/types")
    public ModelAndView toTypes(@ModelAttribute("pageInfo") PageInfo pageInfo,HttpSession session){
        if(pageInfo.getPageNum()==0){
            pageInfo=typeService.getTypeList(1,10,true);
        }
        String message=(String)session.getAttribute("message");
        ModelAndView mv=new ModelAndView();
        mv.addObject("pageInfo",pageInfo);
        if(message!=null){
            mv.addObject("message",message);
            session.setAttribute("message",null);
        }
        mv.setViewName("admin/types");
        return mv;
    }

    @RequestMapping("/createblog/{userid}")
    public String toBloginput(@PathVariable Long userid,Model model,HttpSession session)
    {
        List<Type> types=typeService.getAllType();
        List<Tag> tags=tagService.getAllTag();
        model.addAttribute("userid",userid);
        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        if(session.getAttribute("blog")!=null){
            model.addAttribute("blog",session.getAttribute("blog"));
            model.addAttribute("use","update");
            List<Tag> tagList=((Blog)session.getAttribute("blog")).getTags();
            if(!tagList.isEmpty()) {
                StringBuffer sb = new StringBuffer();
                String tagids;
                for (Tag tag : tagList) {
                    sb.append(tag.getId() + ",");
                }
                tagids = sb.substring(0, (sb.length() - 1));
                model.addAttribute("tagList", tagList);
                model.addAttribute("tagids", tagids);
            }
                session.setAttribute("blog",null);
            return "admin/blogs-update";
        }else {
            model.addAttribute("use","create");
            return "admin/blogs-input";
        }
    }

    @RequestMapping("/type-input")
    public String typeinput(HttpSession session,Model model){
        Map<String,Object> map=(Map<String,Object>)session.getAttribute("error");
        if(map!=null){
            model.addAttribute("error",map.get("errormsg"));
            model.addAttribute("name",map.get("name"));
            session.setAttribute("error",null);
        }
        return "admin/type-input";
    }

    @RequestMapping("/type-edit")
    public String typeedit(@ModelAttribute("type") Type type,@ModelAttribute("pageid") int pageid,Model model,HttpSession session){
//        try {

            Map<String, Object> map = (Map<String, Object>) session.getAttribute("error");
            if (map != null) {
                System.out.println("map name:"+map.get("name"));
                type.setName((String) map.get("name"));
                type.setId((Long) map.get("id"));
                if(map.get("pageid")!=null) {
                    pageid = (int) map.get("pageid");
                }
                if(map.get("errormsg")!=null) {
                    model.addAttribute("error", (String) map.get("errormsg"));
                }
            }
//        }catch (Exception e) {
                model.addAttribute("name", type.getName());
                model.addAttribute("id", type.getId());
                model.addAttribute("pageid", pageid);
                session.setAttribute("error",null);
//        }
        return "admin/type-edit";
    }

    @RequestMapping("/test")
    public String toTest(){
        return "admin/test";
    }

}
