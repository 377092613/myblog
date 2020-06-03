package com.myblog.dao;

import com.myblog.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserRepository {
    public User findUserByusernameAndpassword(String username, String password);
}
