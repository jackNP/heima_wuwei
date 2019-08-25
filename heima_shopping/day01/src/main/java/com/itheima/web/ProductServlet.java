package com.itheima.web;

import com.itheima.domain.PageBean;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/product")
public class ProductServlet extends BaseServlet{
    private ProductService productService = BeanFactory.newInstance(ProductService.class);
    public void findNewsAndHot(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> isHotList = productService.findIsHot();
        List<Product> newList = productService.findNew();
        //把俩list ,装进map
        Map<String,List<Product>> map = new HashMap<>();
        map.put("ishot",isHotList);
        map.put("news",newList);
        Result result = new Result(Result.SUCCESS,"查询商品成功",map);
        response.getWriter().print(JSONObject.fromObject(result));

    }
    //分类查询
    public void findById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pid = request.getParameter("pid");
        System.out.println(pid);
        Product p = productService.findById(pid);
        System.out.println(p);
        Result result = new Result(Result.SUCCESS,"查询商品成功",p);
        response.getWriter().print(JSONObject.fromObject(result));
    }
    //分类，分页
    public void findByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取当前页
        String currentPage = request.getParameter("currentPage");
        if(currentPage==null || currentPage.equals("null")){
            currentPage ="1";
        }
        //每页显示个数
        int pageSize = 12;
        //分类，id
        String cid = request.getParameter("cid");
        //注意integer的拆装箱
        PageBean<Product> pb =productService.findByPage(Integer.parseInt(currentPage),pageSize,cid);
        Result result = new Result(Result.SUCCESS,"查询分页数据成功",pb);
        response.getWriter().print(JSONObject.fromObject(result));
    }
}
