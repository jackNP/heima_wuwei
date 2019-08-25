package com.itheima.web.filter;

//导入servlet 的 Filter 包
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//@WebFilter拦截器，过滤网址
@WebFilter (urlPatterns = "/*")
public class AjaxFilter implements Filter {
//    设置ajax可以跨域访问的类

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                            FilterChain filterChain)
            throws IOException, ServletException {
//        设置ajax跨域访问，设置响应头
        //强转，request,response
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //允许AJAX跨域访问,HBuilder中的内容
        response.setHeader("Access-Control-Allow-Origin", "http://www.itheima331.com:8020");
//AJAX访问允许客户端保存cookie
        response.setHeader("Access-Control-Allow-Credentials","true");
        //放行，所有经过的访问连接,都可以跨域访问，和保存cookie
        filterChain.doFilter(request,response);

    }

    @Override
    public void destroy() {

    }
}
