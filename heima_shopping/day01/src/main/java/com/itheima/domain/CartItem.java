package com.itheima.domain;

/**
 *  购物项
 *    包含: 商品对象,数量,小计
 */
public class CartItem {
    private Product product;
    private int count;
    private double subTotal;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 购物项中的小计价格
     * 价格=商品单价*购买数量
     */
    public double getSubTotal() {
        return subTotal = product.getShop_price()*count;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
