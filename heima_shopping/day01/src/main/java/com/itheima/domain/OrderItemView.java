package com.itheima.domain;

/**
 *  订单项数据
 */
public class OrderItemView {
    //商品主键
    private String pid;
    //购买的数量
    private int count;
    //价格小计
    private double subTotal;
    //商品名
    private String pname;
    //商品图片
    private String pimage;
    //商品商城价格
    private double shop_price;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public double getShop_price() {
        return shop_price;
    }

    public void setShop_price(double shop_price) {
        this.shop_price = shop_price;
    }
}
