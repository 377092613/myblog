package com.myblog.dao;

import com.myblog.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TypeRepository {
    void saveType(Type type);
    Type getType(Long id);
    void updateType(Long id,Type type);
    void deleteType(Long id);
    List<Type> getAllType();
    Type getTypeByName(String name);
}
