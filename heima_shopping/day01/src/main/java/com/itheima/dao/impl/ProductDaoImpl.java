package com.itheima.dao.impl;

import com.itheima.dao.ProductDao;
import com.itheima.domain.Product;
import com.itheima.utils.C3P0Utils;
import com.itheima.web.Constr;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
//最热商品查询10条，并且需要已上架属性的
    @Override
    public List<Product> findIsHot() throws SQLException {
        String sql = "select * from product where is_hot =? and pflag=? limit ?,?";
        return qr.query(sql,new BeanListHandler<Product>(Product.class), Constr.ISHOT, Constr.PFLAG,0,9);
    }
    //最新商品查询10条
    @Override
    public List<Product> findNew() throws SQLException {
        //拼写查询最新商品SQL
        String sql = "select * from product where pflag=? order by pdate desc limit ?,?";
        return qr.query(sql,new BeanListHandler<Product>(Product.class),Constr.PFLAG,0,9);

    }

    //单查商品的方法
    @Override
    public Product findById(String pid) throws SQLException {
        String sql = "SELECT * FROM product where pid = ?";
        return qr.query(sql,new BeanHandler<Product>(Product.class),pid);
    }
    //分类分页查询，当前页数，要显示几条，分类码
    @Override
    public List<Product> findByPage(int currentPage, int pageSize, String cid) throws SQLException {
        String sql = "SELECT * FROM product where cid = ? limit ?,?";
        return qr.query(sql,new BeanListHandler<Product>(Product.class),cid,(currentPage-1)*pageSize,pageSize);
    }

    @Override
    public long getTotalCount(String cid) throws SQLException {
        String sql = "SELECT count(*) FROM product where cid = ? ";
        //返回Long整形条数
        return qr.query(sql,new ScalarHandler<Long>(),cid);
    }

    @Override
    public List<Product> findByPage(int currentPage, int pageSize) throws SQLException {
        String sql = "SELECT * FROM product limit ?,?";
        return qr.query(sql,new BeanListHandler<Product>(Product.class),(currentPage-1)*pageSize,pageSize);

    }

    @Override
    public Long getTotalCount() throws SQLException {
        String sql = "SELECT count(*) FROM product ";
        //返回Long整形条数
        return qr.query(sql,new ScalarHandler<Long>());
    }

    @Override
    public void addProduct(Product p) throws SQLException {
        String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {
                p.getPid(),p.getPname(),p.getMarket_price(),p.getShop_price(),
                p.getPimage(),p.getPdate(),p.getIs_hot(),p.getPdesc(),
                p.getPflag(),p.getCid()
        };
        qr.update(sql,params);
    }
}
