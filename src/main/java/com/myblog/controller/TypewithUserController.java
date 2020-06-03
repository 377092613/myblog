package com.myblog.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.pojo.Type;
import com.myblog.service.TypeService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.UsesSunMisc;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manage")
public class TypewithUserController {
    @Autowired
    TypeService typeService;

    @RequestMapping("/page/{pageNum}/{sizeNum}/{count}")
    public String doPage(@PathVariable Integer pageNum, @PathVariable Integer sizeNum, @PathVariable boolean count, RedirectAttributes attributes){
        PageInfo pageInfo=typeService.getTypeList(pageNum,sizeNum,count);
        attributes.addFlashAttribute("pageInfo",pageInfo);
        return "redirect:/manage/types";
    }

    @RequestMapping(value = {"/addType"})
    public String addType(String name,RedirectAttributes redirectAttributes,HttpSession session){
        Type type;
        if(typeService.getTypeByName(name)!=null){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("errormsg","该分类名已存在");
            map.put("name",name);
            session.setAttribute("error",map);
            return "forward:/manage/type-input";
        }
            type= new Type();
            type.setName(name);
            typeService.saveType(type);
            session.setAttribute("message","添加成功");
            PageInfo pageInfo = typeService.getTypeList(1, 5, true);
            return "redirect:/manage/page/" + pageInfo.getPages() + "/5/true";
    }

//    @RequestMapping("/editByTypeid/{id}/{pageid}")
//    public String editByTypeId(@PathVariable Long id,@PathVariable Integer pageid){
//        return "manage/type-edit";
//    }

    @RequestMapping("/editByTypeid/{id}/{pageid}")
    public String editByTypeId(@PathVariable Long id,@PathVariable Integer pageid,RedirectAttributes redirectAttributes){
        Type type=typeService.getType(id);
        redirectAttributes.addFlashAttribute("type",type);
        redirectAttributes.addFlashAttribute("pageid",pageid);

        return "redirect:/manage/type-edit";
    }


    @RequestMapping("/editType")
    public String editType(Long id,String name,int pageid,HttpSession session){
        Type type;
        if(typeService.getTypeByName(name)!=null){
            type=typeService.getTypeByName(name);
            Map<String,Object> map=new HashMap<String,Object>();
            if(type.getId()!=id){
                map.put("errormsg","该分类名已存在");
            }
            map.put("id",id);
            map.put("name",name);
            map.put("pageid",pageid);
            session.setAttribute("error",map);
            return "forward:/manage/type-edit";
        }
        type=new Type();
        type.setId(id);
        type.setName(name);
        typeService.updateType(id,type);
        session.setAttribute("message","更新成功");
        return "redirect:/manage/page/"+pageid+"/5/true";
    }

    @RequestMapping("/deleteByTypeid/{id}/{pageid}")
    public String deleteTypeById(@PathVariable Long id, @PathVariable Integer pageid, HttpSession session){
        typeService.deleteType(id);
        session.setAttribute("message","删除成功");
        return "redirect:/manage/page/"+pageid+"/5/true";
    }

//    @RequestMapping("/checkType")
//    public String doCheck(Long id,String name,int pageid,HttpSession session){
//            Map<String,Object> errormap=new HashMap<String,Object>();
//            errormap.put("name","name");
//            errormap.put("id",id);
//            errormap.put("pageid",pageid);
//            errormap.put("errormsg","该分类已经存在");
//            session.setAttribute("error",errormap);
//            System.out.println("进入");
//            return "forward:/manage/type-edit";
//    }

}
