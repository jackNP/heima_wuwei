package com.itheima.domain;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *  购物车对象
 *   包含: 总金额,购物项
 *   购物项可能是多个商品组成
 *   集合Map<商品的主键,值 购物项>
 */
public class Cart {
    private double total; //总金额
    // 集合Map<商品的主键,值 购物项>
    private Map<String,  CartItem> itemMap = new HashMap<String, CartItem>();

    public void clearItems(){
        itemMap.clear();
        total=0;
    }

    /**
     * 移除购物车中的购物项
     * Map集合中的相同键,移除
     * 总金额计算
     */
    public void removeItem(String pid){
        CartItem cartItem =  itemMap.remove(pid);
        //计算总金额= 总金额-被移除掉购物项的小计
        total -= cartItem.getSubTotal();
    }
    /**
     * 添加购物车方法
     * 购物项CartItem,存储到购物车的Map集合
     *   判断:
     *     购物车中是否有这个购物项
     *     如果有,取出原有的购物项,修改数量
     *     如果无,直接添加购物项
     *  计算总价格
     *
     *  CartItem 购物项: 商品,数量,小计
     */
    public void addCart(CartItem cartItem){
        //判断,是否存在购物项
        String pid = cartItem.getProduct().getPid();
        if(itemMap.containsKey(pid)){
            //有这个购物项,取出原有购物项
            CartItem old =  itemMap.get(pid);
            //修改数量,原有的数量+新购物项数量
            old.setCount( old.getCount() +  cartItem.getCount());
        }else {
            //没有购物项,存储集合
            itemMap.put(pid,cartItem);
        }
        //从新计算总金额
        total+= cartItem.getSubTotal();
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


    /**
     *   集合Map<商品的主键,值 购物项>
     *   商品主键pid作为的键
     *   值是购物项
     *     购物项中,包含商品,商品必然有主键pid
     *     Map<pid, cartitem.product.pid>
     *   getItemMap方法不用返回Map,返回购物项
     *   一个Map集合中个,购物车可能是多个
     *   Map接口方法 values() Map中的值,存储Collection集合
     */
    public Collection<CartItem> getItemMap() {
      return  itemMap.values();
    }

    public void setItemMap(Map<String, CartItem> itemMap) {
        this.itemMap = itemMap;
    }
}
