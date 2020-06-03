package com.myblog.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.pojo.Blog;
import com.myblog.service.BlogService;
import com.sun.imageio.plugins.common.ImageUtil;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manage")
public class BlogInputController {
    @Autowired
    BlogService blogService;
    @RequestMapping("/insertblog")
    public String insertblog(Blog blog, String tag, String typeid, HttpSession session,String use){
        if(use.equals("create")) {
            Page<Object> page = PageHelper.startPage(1, 5, true);
            List<Blog> blogs = blogService.getAllBlogs();
            PageInfo pageInfo = new PageInfo(blogs);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
            Date date = new Date();
            String time = sdf.format(date);
            blog.setCreateTime(time);
            blog.setUpdateTime(time);
            blog.setViews(0);
            blogService.insertBlog(blog, tag, typeid);
            session.setAttribute("message", "添加成功");
            return "redirect:/manage/blogpage/" + pageInfo.getPages() + "/5/true";
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
            Date date = new Date();
            String time = sdf.format(date);
            blog.setUpdateTime(time);
            blogService.updateBlog(blog,tag,typeid);
            session.setAttribute("message", "更新成功");
            return "redirect:/manage/blogpage/1/5/true";
        }
    }

    @RequestMapping("/deleteblog/{blogid}/{pageNum}")
    public String deleteBlogByid(@PathVariable Long blogid, @PathVariable int pageNum, HttpServletRequest request){
        blogService.deleteBlogById(blogid);
        request.getSession().setAttribute("message","删除成功");
        return "redirect:/manage/blogpage/"+pageNum+"/5/true";
    }

    @RequestMapping("/editblog/{blogid}/{pageNum}/{userid}")
    public String editBlog(@PathVariable Long blogid,@PathVariable int pageNum,@PathVariable Long userid,HttpSession session){
        Blog blog=blogService.getBlogById(blogid);
        session.setAttribute("blog",blog);
        String path="/createblog/"+userid;
        return "redirect:/manage"+path;
    }


    @RequestMapping("/blogs/search")
    public String doSearch(String title,String typeid,Boolean recommened,HttpSession session){
        System.out.println("typeid:"+typeid);
//        Page<Object> page= PageHelper.startPage(1,5,true);
//        List<Blog> blogs=blogService.getBlogsBySearch(title,typeid,recommened);
//        PageInfo pageInfo=new PageInfo(blogs);
//        attributes.addFlashAttribute("pageInfo",pageInfo);
        Map<String,Object> map=new HashMap<String,Object>();
        if(!title.equals("")) {
            map.put("title", title);
        }
        if(typeid!=null){
            map.put("typeid",typeid);
        }
        if(recommened!=null){
            map.put("recommend",recommened);
        }
        session.setAttribute("searchmap",map);
        return "redirect:/manage/blogpage/1/5/true";
    }



    @RequestMapping("uploadImage")
    @PostMapping(value = "/uploadfile")
    public @ResponseBody Map<String,Object> demo(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file, HttpServletRequest request) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        //保存
        try {
            Date date=new Date();
            long time=date.getTime();
            String ip=null;
            String path=null;
            String os=System.getProperty("os.name");
            if(os.toLowerCase().startsWith("win")) {
                path="F:\\img\\upload";
                ip="localhost";
            }else if(os.toLowerCase().contains("linux")){
                path="/SpringBoot/img/upload";
                String url=request.getRequestURL().toString();
                System.out.println("我的url:"+url);
                if(url.contains("http://")||url.contains("https://")){
                    int index=url.indexOf("//");

                    String ipprot=url.substring(index+2);
                    int portindex=ipprot.indexOf(":");
                    if(portindex!=-1){
                        ip=ipprot.substring(0,portindex);
                    }else{
                        ip=ipprot.substring(0,ipprot.indexOf("/"));
                    }
                }else{
                    int index=url.indexOf(":");
                    System.out.println("url是:"+url);
                    if(index==-1){
                        index=url.indexOf("/");
                    }
                    ip=url.substring(0,index);
                }
            }
            File imageFolder = new File(path);
            File targetFile = new File(imageFolder, time + file.getOriginalFilename());
            if (!targetFile.getParentFile().exists()) {
                if (os.contains("linux")) {
                    targetFile.setWritable(true, false);
                }
                targetFile.getParentFile().mkdirs();
            }


            int port = request.getLocalPort();
            file.transferTo(targetFile);
            resultMap.put("success", 1);
            resultMap.put("message", "上传成功！");
            resultMap.put("url","http://"+ip+":"+port+"/img/upload/"+time+file.getOriginalFilename());
        } catch (Exception e) {
            resultMap.put("success", 0);
            resultMap.put("message", "上传失败！");
            e.printStackTrace();
        }
        return resultMap;
    }

}
