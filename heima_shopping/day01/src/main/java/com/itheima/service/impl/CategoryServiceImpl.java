package com.itheima.service.impl;

import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.JedisUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = BeanFactory.newInstance(CategoryDao.class);
    @Override
    public List<Category> findAll() {
        //改造为redis ,取值
        List<Category> categoryList = null;

        try {
            categoryList = categoryDao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        Jedis jedis = JedisUtils.getJedis();
//        //获取jedis,中对象字符串
//        String category = jedis.get("category");
//        try {
//            //判断是否有数据
//            if (category==null){
//                //没有数据，就去数据库中查，得到数据
//                categoryList = categoryDao.findAll();
////                数据集合转成json存储到redis
//                jedis.set("category", JSONObject.fromObject(category).toString());
//
//            }else {
//                /****
//                 * category 转成集合返回
//                 *  toList ,JSON格式字符串转成集合List,
//                 *   参数JSONArray,对象，
//                 *    被转后的集合的泛型的class对象
//                 */
//                //redis查到数据了
//                //把从redis,得到的category对象字符串，转为JSONArray对象
//                JSONArray ja = JSONArray.fromObject(category);
//                //把JSONArray对象，给tolist方法，并传入要解析成的实体类
//                //得到实体类的集合
//                categoryList = JSONArray.toList(ja,Category.class);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            //关闭jedis数据库0
//            JedisUtils.close(jedis);
//        }
        return categoryList;
    }
//  增加商品分类
    @Override
    public void addCategory(String cname) {
        try {
            categoryDao.addCategory(cname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category findById(String cid) {
        Category category = null;
        try {
            category = categoryDao.findById(cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public void updateCategory(String cid, String cname) {
        try {
            categoryDao.updateCategory(cid,cname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delCategory(String cid) {
        try {
            categoryDao.delCategory(cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
