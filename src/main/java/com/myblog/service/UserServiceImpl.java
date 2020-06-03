package com.myblog.service;

import com.myblog.dao.UserRepository;
import com.myblog.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user=userRepository.findUserByusernameAndpassword(username,password);

        return user;
    }
}
