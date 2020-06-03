package com.myblog.service;

import com.myblog.pojo.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

public interface UserService {
    User checkUser(String username, String password);

}
