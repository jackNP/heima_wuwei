package com.itheima.web;

import com.itheima.domain.Category;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.CategoryService;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/admin")
public class AdminServlet extends BaseServlet {
    private CategoryService categoryService = BeanFactory.newInstance(CategoryService.class);
    private ProductService productService = BeanFactory.newInstance(ProductService.class);

    //查询全部分类
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categoryList = categoryService.findAll();
        Result result = new Result(Result.SUCCESS,"查询成功",categoryList);
        response.getWriter().print(JSONObject.fromObject(result));
    }
    //增加分类
    public void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cname = request.getParameter("fenglei");
        categoryService.addCategory(cname);
        Result result = new Result(Result.SUCCESS,"查询成功");
        response.getWriter().print(JSONObject.fromObject(result));
    }

    //查询单个分类名称
    public void findById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        Category category = categoryService.findById(cid);
        Result result = new Result(Result.SUCCESS,"查询成功",category);
        response.getWriter().print(JSONObject.fromObject(result));
    }

//    修改单个分类属性
        public void updateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String cid = request.getParameter("cid");
            String cname = request.getParameter("modifyname");
            categoryService.updateCategory(cid,cname);
            Result result = new Result(Result.SUCCESS,"查询成功");
            response.getWriter().print(JSONObject.fromObject(result));
        }
    //    删除单个分类属性
    public void delCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        categoryService.delCategory(cid);
        Result result = new Result(Result.SUCCESS,"查询成功");
        response.getWriter().print(JSONObject.fromObject(result));
    }


    public void findPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //当前页码，需要转型
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        //每页显示的条数
        int pageSize = 10;

        PageBean<Product> productPageBean=productService.findByPage(currentPage,pageSize);
        Result result = new Result(Result.SUCCESS,"查询成功",productPageBean);
        response.getWriter().print(JSONObject.fromObject(result));
    }



}
