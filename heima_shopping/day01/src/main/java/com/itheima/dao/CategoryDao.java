package com.itheima.dao;

import com.itheima.domain.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    //查询分类数据
    List<Category> findAll() throws SQLException;
    void addCategory(String cname) throws SQLException;
    Category findById(String cid) throws SQLException;
    void updateCategory(String cid,String cname) throws SQLException;
    void delCategory(String cid)throws SQLException;
}
