package com.myblog;

import com.myblog.dao.BlogRepository;
import com.myblog.pojo.Blog;
import com.myblog.pojo.Tag;
import com.myblog.pojo.TagCount;
import com.myblog.pojo.Type;
import com.myblog.service.TypeService;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

@SpringBootTest
class MyblogApplicationTests {

    @Autowired
    TypeService typeService;

    @Autowired
    BlogRepository blogRepository;
//    @Autowired
//    BlogService blogService;
    @Test
    public void test01(){
        String url="http://129.63.52.114:8081";
        int index=url.indexOf("//");
        String ipprot=url.substring(index+2);
        int portindex=ipprot.indexOf(":");
        String ip=ipprot.substring(0,portindex);
        System.out.println(ip);
    }




}



