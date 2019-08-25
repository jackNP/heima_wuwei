package com.itheima.service.impl;

import com.itheima.dao.ProductDao;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao = BeanFactory.newInstance(ProductDao.class);
    @Override
    public List<Product> findIsHot() {
        List<Product> productList = null;
        try {
            productList = productDao.findIsHot();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public List<Product> findNew() {
        List<Product> productList = null;
        try {
            productList = productDao.findNew();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public Product findById(String pid) {
        Product product =null;
        try {
            product = productDao.findById(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public PageBean<Product> findByPage(int currentPage, int pageSize, String cid) {

        //分类显示商品的类
        PageBean<Product> productPageBean = new PageBean<Product>();
        try {
            //设置当前页
            productPageBean.setCurrentPage(currentPage);
            //设置显示页码
            productPageBean.setPageSize(pageSize);
            //设置分过类的和显示条数的商品集合
            productPageBean.setList(productDao.findByPage(currentPage,pageSize,cid));
            //取总商品条数
            long totalCount = productDao.getTotalCount(cid);
            //把商品条数，设置进分类集合类
            productPageBean.setTotalCount(totalCount);
            //通过总商品条数/每页显示条数，得到总页数。
            // 注意,除出来有小数点，向上取整，还有类型转换问题
            int totalPage = (int) Math.ceil(totalCount*1.0/pageSize);
            //再设置页码数
            productPageBean.setTotalPage(totalPage);
        }catch (Exception e){
            e.printStackTrace();
        }
        return productPageBean;
    }

    @Override
    public PageBean<Product> findByPage(int currentPage, int pageSize) {
        //分类显示商品的类
        PageBean<Product> productPageBean = new PageBean<Product>();
        try {
            //设置当前页
            productPageBean.setCurrentPage(currentPage);
            //设置显示的条数
            productPageBean.setPageSize(pageSize);
            //设置显示条数的商品集合
            productPageBean.setList(productDao.findByPage(currentPage,pageSize));
            //取总商品条数
            long totalCount = productDao.getTotalCount();
            //把商品条数，设置进分类集合类
            productPageBean.setTotalCount(totalCount);
            //通过总商品条数/每页显示条数，得到总页数。
            // 注意,除出来有小数点，向上取整，还有类型转换问题
            int totalPage = (int) Math.ceil(totalCount*1.0/pageSize);
            //再设置页码数
            productPageBean.setTotalPage(totalPage);
        }catch (Exception e){
            e.printStackTrace();
        }
        return productPageBean;
    }

    @Override
    public void addProduct(Product product) {
        try {
            productDao.addProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
