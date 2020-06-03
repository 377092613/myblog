package com.myblog.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myblog.dao.TypeRepository;
import com.myblog.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    TypeRepository typeRepository;

    @Override
    public void saveType(Type type) {
        typeRepository.saveType(type);
    }

    @Override
    public Type getType(Long id) {
        return typeRepository.getType(id);
    }

    @Override
    public void updateType(Long id, Type type) {
        Type findtype=typeRepository.getType(id);
        if(findtype==null){
            try {
                throw new Exception("更新出错");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        typeRepository.updateType(id,type);
    }

    @Override
    public void deleteType(Long id) {
        typeRepository.deleteType(id);
    }

    @Override
    public PageInfo getTypeList(Integer pageNum, Integer sizeNum, boolean count) {
        Page page = PageHelper.startPage(pageNum,sizeNum,count);
        List<Type> list=typeRepository.getAllType();
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @Override
    public Type getTypeByName(String name) {
        Type type=typeRepository.getTypeByName(name);
        return type;
    }

    @Override
    public List<Type> getAllType() {
        return typeRepository.getAllType();
    }
}
