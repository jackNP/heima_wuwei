package com.itheima.service;

import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.domain.PageBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrdersService {
    //事务处理2个操作方法
    void addOrders(Orders orders,List<OrderItem> orderItemList);
    //分页的业务层方法
    PageBean<Orders> myOrderWithPage(int currentPage, int pageSize, String uid);
    //订单详情、
    Orders info(String oid);
    //修改订单数据,收货人,地址,电话
    void updateOrder(String name,String address,String telephone,String oid);
    //修改订单状态,已经付款
    void updateOrderState(String oid);

//    //添加订单的方法，传递订单信息类对象
//    void submitOrder(Orders orders)throws SQLException;
//    //添加订单项方法，传递订单商品类对象
//    void submitOrderItem(List<OrderItem> orderItemList)throws SQLException;

}
