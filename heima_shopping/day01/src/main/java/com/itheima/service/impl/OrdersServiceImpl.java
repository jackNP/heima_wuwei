package com.itheima.service.impl;

import com.itheima.dao.OrdersDao;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.domain.PageBean;
import com.itheima.service.OrdersService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class OrdersServiceImpl implements OrdersService {
    private OrdersDao ordersDao = BeanFactory.newInstance(OrdersDao.class);

    //订单业务层获取分页对象方法
    public PageBean<Orders> myOrderWithPage(int currentPage,int pageSize,String uid){
        PageBean<Orders> pb =new PageBean<Orders>();
        //封装数据
        try {
            pb.setCurrentPage(currentPage);
            pb.setPageSize(pageSize);
            //查询Dao层获取订单对象
            List<Orders> ordersList = ordersDao.myOrderWithPage(currentPage,pageSize,uid);
            //分页集合，存所有订单对象
            pb.setList(ordersList);
            //订单总数
            Long totalCount= ordersDao.getOrdersCount(uid);
            //设置总数量
            pb.setTotalCount(totalCount);
            //设置总页数
            pb.setTotalPage((int)(Math.ceil(totalCount*1.0/pageSize)));

        }catch (Exception ex){
            ex.printStackTrace();
        }


        return pb;
    }

    @Override
    public Orders info(String oid) {
        Orders orders = null;
        try {
            orders = ordersDao.info(oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public void updateOrder(String name, String address, String telephone, String oid) {
        try {
            ordersDao.updateOrder(name,address,telephone,oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOrderState(String oid) {
        try {
            ordersDao.updateOrderState(oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addOrders(Orders orders, List<OrderItem> orderItemList) {
        Connection conn = null;
        try {
            conn= ConnectionManager.getConnection();
            ConnectionManager.begin();
            ordersDao.submitOrder(conn,orders);
            for (OrderItem orderItem : orderItemList) {
                ordersDao.submitOrderItem(conn,orderItem);
            }
            ConnectionManager.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                ConnectionManager.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            ConnectionManager.close();
        }
    }




//    Connection conn;
//
//    {
//        try {
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/store331","root","123");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void addOrders(Orders orders, List<OrderItem> orderItemList) {
//        //自己获取一个数据库的连接
//        try {
//            //开启事务
//            conn.setAutoCommit(false);
//            //执行操作
//            ordersDao.submitOrder(conn,orders);
//            for (OrderItem orderItem : orderItemList) {
//                ordersDao.submitOrderItem(conn,orderItem);
//            }
//            //操作完成，提交事务
//            conn.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            //有异常，回滚事务
//            try {
//                conn.rollback();
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
//        }finally {
//            if(conn!=null){
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//
//
//    }
//    @Override
//    public void submitOrder(Orders orders)  {
//        try {
//            ordersDao.submitOrder(orders);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void submitOrderItem(List<OrderItem> orderItemList)  {
//        //订单项的加入，集合需要遍历，每次，调用一次dao
//
//        try {
//            for (OrderItem orderItem : orderItemList) {
//                ordersDao.submitOrderItem(orderItem);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
}
