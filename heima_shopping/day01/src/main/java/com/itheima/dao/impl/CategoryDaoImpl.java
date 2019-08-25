package com.itheima.dao.impl;


import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.utils.C3P0Utils;
import com.itheima.utils.UUIDUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

        @Override
        public List<Category> findAll() throws SQLException {
        String sql = "select * from category";
        //注意，new Bean   List   Handler<Category>  是list
        return qr.query(sql,new BeanListHandler<Category>(Category.class));
    }

    @Override
    public void addCategory(String cname) throws SQLException {
            String sql = "insert into category values(?,?)";
            qr.update(sql, UUIDUtils.getUUID(),cname);
    }

    @Override
    public Category findById(String cid) throws SQLException {
        String sql= "select * from category where cid=?";
        Category category =qr.query(sql,new BeanHandler<Category>(Category.class),cid);
        return category;
    }

    @Override
    public void updateCategory(String cid, String cname) throws SQLException {
        String sql= "update category set cname=? where cid = ?  ";
        qr.update(sql,cname,cid);
    }

    @Override
    public void delCategory(String cid) throws SQLException {
        String sql = "delete from category where cid = ?";
        qr.update(sql,cid);
    }
}
