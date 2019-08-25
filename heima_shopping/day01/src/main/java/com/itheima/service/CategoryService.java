package com.itheima.service;

import com.itheima.domain.Category;

import java.util.List;

public interface CategoryService {
     //改造为redis ,取值

     List<Category> findAll();
     void addCategory(String cname);
     Category findById(String cid);
     void updateCategory(String cid,String cname);
     void delCategory(String cid);

}
