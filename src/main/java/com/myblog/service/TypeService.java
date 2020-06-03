package com.myblog.service;

import com.github.pagehelper.PageInfo;
import com.myblog.pojo.Type;

import java.util.List;


public interface TypeService {
    void saveType(Type type);
    Type getType(Long id);
    void updateType(Long id,Type type);
    void deleteType(Long id);
    PageInfo<Type> getTypeList(Integer pageNum, Integer sizeNum, boolean count);
    Type getTypeByName(String name);
    List<Type> getAllType();
}
