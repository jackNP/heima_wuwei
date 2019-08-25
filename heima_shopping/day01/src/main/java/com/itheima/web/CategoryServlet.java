package com.itheima.web;

import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/category")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = BeanFactory.newInstance(CategoryService.class);


//导航分类数据查询
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Category> categoryList = categoryService.findAll();
        Result result = new Result(Result.SUCCESS,"查询导航数据成功",categoryList);
        response.getWriter().print(JSONObject.fromObject(result));
    }
}
