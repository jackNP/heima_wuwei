package com.itheima.dao;

        import com.itheima.domain.OrderItem;
        import com.itheima.domain.Orders;

        import java.sql.Connection;
        import java.sql.SQLException;
        import java.util.List;

public interface OrdersDao {
    //添加订单的方法，传递订单信息类对象
    void submitOrder(Connection conn, Orders orders) throws SQLException;

    //添加订单项方法，传递订单商品类对象
    void submitOrderItem(Connection conn, OrderItem orderItem) throws SQLException;

    //分页查看订单
    List<Orders> myOrderWithPage(int currentPage, int pageSize, String uid) throws SQLException;
    //取得订单总数量
    Long getOrdersCount(String uid)throws SQLException;

    //订单详情方法
    Orders info(String oid)throws SQLException;

    //修改订单方法
    void updateOrder(String name,String address,String telephone,String oid)throws SQLException;
    //修改订单状态已经付款
    void updateOrderState(String oid)throws SQLException;
}