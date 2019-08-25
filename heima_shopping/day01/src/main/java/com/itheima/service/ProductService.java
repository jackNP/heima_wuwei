package com.itheima.service;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    //查询最热门商品
    List<Product> findIsHot();
    //查询最新商品
    List<Product> findNew();
    //根据主键查询商品详情
    Product findById(String pid);
    //分类分页查询，当前页数，要显示几条，分类码
    PageBean<Product> findByPage(int currentPage, int pageSize, String cid);
    //分页显示所有商品
    PageBean<Product> findByPage(int currentPage,int pageSize);

    //方法添加新商品数据
    void addProduct(Product product);
}
