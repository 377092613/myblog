package com.myblog.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.exception.BlogNotFundException;
import com.myblog.pojo.*;
import com.myblog.service.BlogService;
import com.myblog.service.TagService;
import com.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class BlogController {
    @Autowired
    BlogService blogService;

    @Autowired
    TagService tagService;

    @Autowired
    TypeService typeService;

    @RequestMapping("/")
    public String toIndex(@ModelAttribute PageInfo pageInfo, Model model){
        if(pageInfo.getPageNum()!=0) {
            model.addAttribute("pageInfo", pageInfo);
        }else {
            return"redirect:/blog/1/10/true";
        }
        List<Type> types=typeService.getAllType();
        Map<String,Integer> typemap=new TreeMap<String,Integer>();
        Map<String,Integer> tagmap=new TreeMap<String,Integer>();
        for (Type type:types) {
            typemap.put(type.getName(),blogService.getBlogNumberByTypeid(type.getId()));
        }
        List<Tag> tags=tagService.getAllTag();
        for(Tag tag:tags){
            tagmap.put(tag.getName(),blogService.getBlogNumberByTagid(tag.getId()));
        }
        List<Map.Entry<String,Integer>> typelist = new ArrayList<Map.Entry<String,Integer>>(typemap.entrySet());
        Collections.sort(typelist, new Comparator<Map.Entry<String,Integer>>() {
            @Override
            public int compare(Map.Entry<String,Integer> o1, Map.Entry<String,Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        List<Map.Entry<String,Integer>> taglist = new ArrayList<Map.Entry<String,Integer>>(tagmap.entrySet());
        Collections.sort(taglist, new Comparator<Map.Entry<String,Integer>>() {
            @Override
            public int compare(Map.Entry<String,Integer> o1, Map.Entry<String,Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });


        for(int i=typelist.size()-1;i>5;i--){
            typelist.remove(i);
        }
        for(int i=taglist.size()-1;i>9;i--){
            taglist.remove(i);
        }
        List<TypeCount> typeCounts=new ArrayList<TypeCount>();
        List<TagCount> tagCounts=new ArrayList<TagCount>();
        for(int i=0;i<taglist.size();i++){
            TagCount tagCount=new TagCount();
            tagCount.setName(taglist.get(i).getKey());
            Tag tag=tagService.getTagByName(taglist.get(i).getKey());
            tagCount.setId(tag.getId());
            tagCount.setNumber(taglist.get(i).getValue());
            tagCounts.add(tagCount);
        }
        for(int i=0;i<typelist.size();i++){
            TypeCount typeCount=new TypeCount();
            typeCount.setName(typelist.get(i).getKey());
            Type type=typeService.getTypeByName(typelist.get(i).getKey());
            typeCount.setId(type.getId());
            typeCount.setNumber(typelist.get(i).getValue());
            typeCounts.add(typeCount);
        }

        model.addAttribute("typeCounts",typeCounts);
        model.addAttribute("tagCounts",tagCounts);
        return "index";
    }

    @RequestMapping("/blog/{pageNum}/{sizeNum}/{count}")
//    public PageInfo toBlogIndex(@PathVariable Integer pageNum, @PathVariable Integer sizeNum, @PathVariable boolean count,RedirectAttributes redirectAttributes){
    public String toBlogIndex(@PathVariable Integer pageNum, @PathVariable Integer sizeNum, @PathVariable boolean count,RedirectAttributes redirectAttributes){
        Page<Object> page= PageHelper.startPage(pageNum,sizeNum,count);
//        List<Blog> allblog=blogService.getAllBlogForpublished();
        List<Blog> blogs=blogService.getAllBlogForpublished();
//        System.out.println(allblog.size());
//        TreeSet<Blog> treeSet=new TreeSet<>();
//        for (Blog blog:allblog) {
//            treeSet.add(blog);
//        }
//        List<Blog> blogs=new ArrayList<>();
//        Iterator iterator=treeSet.iterator();
//        while(iterator.hasNext()){
//            blogs.add((Blog)iterator.next());
//        }
//        System.out.println(blogs.size());
        PageInfo pageInfo=new PageInfo(blogs);
        redirectAttributes.addFlashAttribute("pageInfo",pageInfo);
//return pageInfo;
        return "redirect:/";
    }

    @RequestMapping("/blog/{id}")
//    public void toBlog(@PathVariable Long id,Model model){
    public String toBlog(@PathVariable Long id,Model model){
        blogService.addViews(id);
        Blog blog=blogService.getBlogById(id);
        model.addAttribute("blog",blog);
        System.out.println(blog.getAppreciation());
        return "blog";
    }

//    @ResponseBody
    @RequestMapping("/manage/blogpage/{pageNum}/{sizeNum}/{count}")
//    public PageInfo doPage(@PathVariable Integer pageNum, @PathVariable Integer sizeNum, @PathVariable boolean count, RedirectAttributes attributes){
    public String doPage(@PathVariable Integer pageNum, @PathVariable Integer sizeNum, @PathVariable boolean count, RedirectAttributes attributes, HttpSession session){
        Page<Object> page= PageHelper.startPage(pageNum,sizeNum,count);
        List<Blog> blogs;
        if(session.getAttribute("searchmap")==null) {
            blogs = blogService.getAllBlogs();
        }else{
            Map<String,Object> map= (Map<String, Object>) session.getAttribute("searchmap");
            if(((String)map.get("typeid")).equals("0")){
                blogs=blogService.getBlogsBySearch((String)map.get("title"),null,(Boolean)map.get("recommend"));
            }else {
                blogs = blogService.getBlogsBySearch((String) map.get("title"), (String) map.get("typeid"), (Boolean) map.get("recommend"));
            }
        }
        PageInfo pageInfo=new PageInfo(blogs);
        attributes.addFlashAttribute("pageInfo",pageInfo);
        return "redirect:/manage/blogs";
//        return pageInfo;
    }

    @RequestMapping("/types")
    public String toTypes(@ModelAttribute PageInfo pageInfo,HttpSession session,Model model){

        if(pageInfo.getPageNum()==0){
            List<Type> types=typeService.getAllType();
            return "redirect:type/1/10/true/"+types.get(0).getId();
        }
        model.addAttribute("pageInfo",pageInfo);
        List<Type> types=typeService.getAllType();
        List<TypeCount> typeCounts=new ArrayList<TypeCount>();
        for (Type type:types) {
            TypeCount typeCount=new TypeCount();
            typeCount.setId(type.getId());
            typeCount.setName(type.getName());
            typeCount.setNumber(blogService.getBlogNumberByTypeid(type.getId()));
            typeCounts.add(typeCount);
        }

        if(session.getAttribute("activeTypeId")!=null) {
            Long activeTypeId = (Long) session.getAttribute("activeTypeId");
            String name = typeService.getType(activeTypeId).getName();
            model.addAttribute("activeTypename", name);
            session.setAttribute("activeTypeId",null);
            model.addAttribute("activeTypeId",activeTypeId);

        }
        model.addAttribute("typeCounts",typeCounts);
        return "types";
    }

    @RequestMapping("/tags")
    public String toTags(@ModelAttribute PageInfo pageInfo,HttpSession session,Model model){
        if(pageInfo.getPageNum()==0){
            List<Tag> tags=tagService.getAllTag();
            return "redirect:tag/1/10/true/"+tags.get(0).getId();
        }
        model.addAttribute("pageInfo",pageInfo);
        List<Tag> tags=tagService.getAllTag();
        List<TagCount> tagCounts=new ArrayList<TagCount>();
        for (Tag tag:tags) {
            TagCount tagCount=new TagCount();
            tagCount.setId(tag.getId());
            tagCount.setName(tag.getName());
            tagCount.setNumber(blogService.getBlogNumberByTagid(tag.getId()));
            tagCounts.add(tagCount);
        }

        if(session.getAttribute("activeTagId")!=null) {
            Long activeTagId = (Long) session.getAttribute("activeTagId");
            String name = tagService.getTagByid(activeTagId).getName();
            model.addAttribute("activeTagname", name);
            session.setAttribute("activeTagId",null);
            model.addAttribute("activeTagId",activeTagId);
        }
        model.addAttribute("tagCounts",tagCounts);
        return "tags";
    }

    @RequestMapping("/archives")
    public String toArchives(Model model) throws ParseException {
        List<Blog> blogs=blogService.getAllBlogForpublished();
        List<String> years=new ArrayList<>();
        Map<String,List<Blog>> allblogs=new HashMap<>();
        //存放年信息
        for (Blog blog:blogs) {
            String createTime = blog.getCreateTime();
            String year = createTime.substring(0, 4);
            if (years.isEmpty()) {
                years.add(year);
            } else {
                int flag = 0;
                for (String s : years) {
                    if (s.equals(year)) {
                        flag = 1;
                    }
                }
                years.add(year);
            }
        }
        for(String year:years) {
            List<Blog> yearblogs=new ArrayList<Blog>();
            for (Blog blog : blogs) {
                if(blog.getUpdateTime().contains(year)){
                    String updateTime=blog.getUpdateTime();
//                    System.out.println(updateTime);
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date a=sdf.parse(updateTime);
                    sdf=new SimpleDateFormat("MM月dd");
                    String time=sdf.format(a);
                    Blog newblog=new Blog();
                    newblog.setUpdateTime(time);
                    newblog.setFlag(blog.getFlag());
                    newblog.setTitle(blog.getTitle());
                    newblog.setId(blog.getId());
//                    blog.setUpdateTime(time);
                    yearblogs.add(newblog);
                }
            }
            allblogs.put(year,yearblogs);
        }

//        String updateTime=blog.getUpdateTime();
//        System.out.println(updateTime);
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date a=sdf.parse(updateTime);
//        sdf=new SimpleDateFormat("MM月dd");
//        String time=sdf.format(a);
//        blog.setUpdateTime(time);

        model.addAttribute("allblogs",allblogs);
        model.addAttribute("blogcount",blogs.size());
        return "archives";
    }

    @RequestMapping("/about")
    public String toAbout(){
        return "about";
    }

    @RequestMapping("/login")
    public String toLogin(){ return "admin/login";}


}
