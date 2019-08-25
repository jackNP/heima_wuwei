package com.itheima.domain;

import java.util.List;

/**
 *  封装实现分页查询的数据
 *
 *  List<Product> 分页只能显示商品数据
 *  适合多有的数据集合
 *
 *  PageBean<User> pb = new PageBean()
 */
public class PageBean <T>{
    //当前页数
    private int currentPage;
    //总数量
    private long totalCount;
    //总页数
    private int totalPage;
    //每页显示条数
    private int pageSize;
    //分页中的数据,集合的数据类型,写成泛型
    private List<T> list;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
