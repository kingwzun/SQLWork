package study.filter;

import study.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter(filterName = "login",urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("LoginFilter.init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("LoginFilter.doFilter");
        HttpServletRequest httpServletRequest=(HttpServletRequest) request;
        HttpServletResponse httpServletResponse=(HttpServletResponse) response;
//        打印用户的访问地址
        String servletPath = httpServletRequest.getServletPath();
        System.out.println("servletPath"+servletPath);
        //放行不需要登录就能完成的界面
        if(servletPath.endsWith(".jpg")
                ||servletPath.endsWith(".png")
                ||servletPath.endsWith(".js")
                ||servletPath.endsWith(".css")
                ||servletPath.equals("/login.jsp")
                ||servletPath.equals("/auth")
                ||servletPath.equals("/login")){
            chain.doFilter(request,response);
            return;
        }
        //判断是否登录
        HttpSession session = httpServletRequest.getSession();
        User user = (User)session.getAttribute("user");
        //没有登录，重定向登录界面
        if(user==null){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login.jsp");
            return;
        }
        //放行
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        System.out.println("LoginFilter.destroy");
    }
}
