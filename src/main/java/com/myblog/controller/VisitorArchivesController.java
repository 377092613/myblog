package com.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class VisitorArchivesController {
    @RequestMapping("/haha")
    public String doMap(Model model){
        Map<String,String> map=new HashMap();
        map.put("1","1");
        map.put("2","2");
        model.addAttribute("map",map);
        return "MyTest";
    }
}
