package study.controller;

import com.alibaba.druid.util.StringUtils;
import study.pojo.User;
import study.service.IUserService;
import study.service.impl.UserServiceImpl;
import study.utils.JSONResult;
import study.utils.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet  extends HttpServlet {

    private IUserService userService = new UserServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        switch (method) {
            case "login":
                login(req, resp);
                break;
            case "logout":
                logout(req, resp);
                break;
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("UserServlet.login");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String code = req.getParameter("code");

        //先判断验证码是否正确
        HttpSession session = req.getSession();
        String codeInSession = (String) session.getAttribute("codeInSession");
        //验证码错误，返回错误信息
        if(StringUtils.isEmpty(code)||!codeInSession.equalsIgnoreCase(code)){
            JSONUtil.obj2Json(JSONResult.error("验证码为空或错误"),resp);
            return;
        }
        //验证码正确，验证用户名和 密码是否正确
        User user=userService.login(name,password);

        if (user!=null) {
            session.setAttribute("user",user);
            JSONUtil.obj2Json(JSONResult.ok("登录成功"), resp);
        } else {
            JSONUtil.obj2Json(JSONResult.error("用户名或密码错误"), resp);
        }
    }
    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("UserServlet.logout");

        HttpSession session = req.getSession();
        //删除session凭证
        session.invalidate();
//        session.removeAttribute("user");
        //跳转到登录界面
        resp.sendRedirect(req.getContextPath()+"/login.jsp");
    }
}
