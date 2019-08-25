package com.itheima.dao;

import com.itheima.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    //最热商品
    List<Product> findIsHot() throws SQLException;
    //最新商品
    List<Product> findNew() throws SQLException;
    //根据商品id单查商品
    Product findById(String pid) throws SQLException;
    //分类分页查询，当前页数，要显示几条，分类码
    List<Product> findByPage(int currentPage,int pageSize,String cid) throws SQLException;
    //获取记录的条数
    long getTotalCount(String cid)throws SQLException;
    //分页显示所有商品
    List<Product> findByPage(int currentPage,int pageSize) throws SQLException;
    //获取商品总条数
    Long getTotalCount()throws SQLException;
    //方法添加新商品数据
    void addProduct(Product product)throws SQLException;
}
