package com.itheima.web;

import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/cart")
public class CartServlet extends BaseServlet {
    //商品业务层的接口对象
    private ProductService productService = BeanFactory.newInstance(ProductService.class);

    //购物车功能
    public void addCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //得到商品的主键和购买数量
        String pid = request.getParameter("pid");
        int count = Integer.parseInt(request.getParameter("count"));
        //根据商品主键查到，商品
        Product product = productService.findById(pid);
        //        商品数量存到购物项对象
        CartItem cartItem = new CartItem();//购物项，有商品和数量
        cartItem.setCount(count);
        cartItem.setProduct(product);
        //用购物车的添加方法，把购物项，整进去
        Cart cart = getCart(request);
        cart.addCart(cartItem);
        Result result = new Result(Result.SUCCESS,"购物车添加成功",cart);
        response.getWriter().print(JSONObject.fromObject(result));
    }

    /***
     * 方法获取购物车对象
     * 购物车对象存储到session 对象
     * 去除购物车，没有购物车，新建
     * ,获取购物车，只要内部，我们其他不调用
     */
    private  Cart getCart(HttpServletRequest request){
        //session 域中，去除购物车对象,
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if(cart==null){
            cart=new Cart();
            //把购物车对象，放进session
            request.getSession().setAttribute("cart",cart);
        }
        return cart;
    }
    /***
     * 展示购物车的方法
     * 转成JSON响应
     */
    public void showCart(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Cart cart = getCart(request);
        Result re = new Result(Result.SUCCESS,"购物车查询成功",cart);
        response.getWriter().print(JSONObject.fromObject(re));
    }

    //删除购物项的方法
    public void removeItem(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //接收商品的主键
        String  pid = request.getParameter("pid");
        //从这个request中拿购物车对象
        Cart cart = getCart(request);
        //购物车删除购物项的方法，传入商品项ID
        cart.removeItem(pid);
        Result re = new Result(Result.SUCCESS,"删除购物项成功");
        response.getWriter().print(JSONObject.fromObject(re));
    }
    //清空购物车
    public void clearItem(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Cart cart = getCart(request);
        cart.clearItems();
        Result re = new Result(Result.SUCCESS,"清空购物项成功");
        response.getWriter().print(JSONObject.fromObject(re));
    }
}
