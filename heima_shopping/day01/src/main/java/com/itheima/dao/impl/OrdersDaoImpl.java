package com.itheima.dao.impl;

import com.itheima.dao.OrdersDao;
import com.itheima.domain.OrderItem;
import com.itheima.domain.OrderItemView;
import com.itheima.domain.Orders;
import com.itheima.utils.C3P0Utils;
import com.itheima.web.Constr;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrdersDaoImpl implements OrdersDao {
    private QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

    /**
     *分页查询订单
     *
     * 通过用户主键查询，所有订单信息
     */

    public List<Orders> myOrderWithPage(int currentPage,int pageSize,String uid) throws SQLException{
        //拼写查询该用户的订单
        String sql="select * from orders where uid=? limit ?,?";
        List<Orders> ordersList = qr.query(sql,new BeanListHandler<Orders>(Orders.class),uid,(currentPage-1)*pageSize,pageSize);
        //遍历所有的订单
        for (Orders order : ordersList) {
            String oid = order.getOid();
            sql= "SELECT p.pid, p.pname, p.pimage, p.shop_price, o.count, o.subtotal \n" +
                    "FROM product  p,orderitem o\n" +
                    "WHERE p.pid = o.pid AND o.oid=?";
            List<OrderItemView> orderItemViewList =
                    qr.query(sql,new BeanListHandler<OrderItemView>(OrderItemView.class),oid);
            //把取得的订单项视图实体类，添加进订单项类的视图属性中
            order.setOrderViewList(orderItemViewList);
        }
        return ordersList;
    }
//得到订单总数
    @Override
    public Long getOrdersCount(String uid) throws SQLException {
        String sql = "select count(*) from orders where uid=?";
        return qr.query(sql,new ScalarHandler<Long>(),uid);
    }

    @Override
    public Orders info(String oid) throws SQLException {
        String sql = "select * from orders where oid=?";
        Orders orders = qr.query(sql,new BeanHandler<Orders>(Orders.class),oid);
        //拿到订单后，要取得，订单商品视图
        sql= "SELECT p.pid, p.pname, p.pimage, p.shop_price, o.count, o.subtotal \n" +
                "FROM product  p,orderitem o\n" +
                "WHERE p.pid = o.pid AND o.oid=?";
        List<OrderItemView> orderItemViewList = qr.query(sql,new BeanListHandler<OrderItemView>(OrderItemView.class),oid);
        //把订单详情视图，赋值给，订单的视图属性
        orders.setOrderViewList(orderItemViewList);
        return orders;
    }

    @Override
    public void updateOrder(String name, String address, String telephone, String oid) throws SQLException {
        String sql = "update orders set name=?,address=?,telephone=? where oid=?";
        Object[] params = {
                name,address,telephone,oid
        };
        qr.update(sql,params);
    }

    @Override
    public void updateOrderState(String oid) throws SQLException {
        String sql = "update orders set state= ? where id=?";
        qr.update(sql, Constr.ORDER_YIFUKUAN,oid);
    }


    @Override
    public void submitOrder(Connection conn,Orders orders) throws SQLException {
//添加订单表
        String sql = "insert into orders(oid,ordertime,total,state,uid) values(?,?,?,?,?)";
        Object[] params = {
            orders.getOid(),orders.getOrdertime(),orders.getTotal(),orders.getState(),orders.getUid()
        };
        qr.update(conn,sql,params);
    }

    @Override
    public void submitOrderItem(Connection conn,OrderItem orderItem) throws SQLException {
        //添加订单商品表
        String sql = "insert into orderitem values(?,?,?,?)";
        Object[] params = {
                orderItem.getCount(),orderItem.getSubTotal(),orderItem.getPid(),orderItem.getOid()
        };
        qr.update(conn,sql,params);
    }
}
