package com.itheima.web;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

@WebServlet(urlPatterns = "/user")
public class UserServlet extends BaseServlet {
    private UserService userService = BeanFactory.newInstance(UserService.class);
//必须使用public访问修饰符
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,String[]> map = request.getParameterMap();
        User user = new User();
        try {
//            用bean工厂，的实体类容器，和map键值数据封装成真正的实体类
            BeanUtils.populate(user,map);
        }  catch (Exception e) {
            e.printStackTrace();
        }
//        user对象数据手动设置，
//        设置激活码，就是1
        user.setState(1);
//        设置主键
        user.setUid(UUIDUtils.getUUID());
        userService.register(user);
//        方法执行完成后，把要响应回去的状态码，信息，和对象格式，整成json
        Result result = new Result(Result.SUCCESS,"注册成功");
        //把有数据的Result格式对象，通过json工具类的方法，封装为json格式，
        // pring方法默认调toString，得到字符串，再写入响应response
        response.getWriter().print(JSONObject.fromObject(result));
    }

    //登录的方法
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.login(username,password);
        if(user!=null){
            request.getSession().setAttribute("user",user);
            //Cookie保存用户名,响应到客户端
            //中文转utf-8
            Cookie cookie = new Cookie("username", username );
            //响应回去的cookie，顶级域名别写错了，写错了报空指针
            cookie.setDomain("itheima331.com");
            cookie.setMaxAge(600*600);
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);

            Result result = new Result(Result.SUCCESS,"登录成功");
            response.getWriter().print(JSONObject.fromObject(result));
        }else{
            Result result =new Result(Result.FAILS,"登录失败");
            response.getWriter().print(JSONObject.fromObject(result));
        }

    }

    //退出登录的方法
    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //销毁seesion
        request.getSession().invalidate();
        //销毁cookie
        Cookie cookie = new Cookie("username", null );
        //响应回去的cookie，顶级域名别写错了，写错了报空指针
        cookie.setDomain("itheima331.com");
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);

        Result result = new Result(Result.SUCCESS,"退出登录成功");
        response.getWriter().print(JSONObject.fromObject(result));

    }
}
