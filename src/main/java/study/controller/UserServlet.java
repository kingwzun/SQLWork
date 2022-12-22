package study.controller;


import study.pojo.User;
import study.pojo.query.UserQuery;
import study.service.IUserService;
import study.service.impl.UserServiceImpl;
import study.utils.DateUtil;
import study.utils.JSONResult;
import study.utils.JSONUtil;
import study.utils.LayUITableResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private IUserService userService = new UserServiceImpl();

    //http://localhost:8080/hotel/user?method=selectAll
    //http://localhost:8080/hotel/user?method=deleteById&id=1
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        switch (method) {
            case "selectAll":
                selectAll(req, resp);
                break;
            case "selectByPage":
                selectByPage(req, resp);
                break;
            case "deleteById":
                deleteById(req, resp);
                break;
            case "deleteAll":
                deleteAll(req,resp);
                break;
            case "add":
                add(req,resp);
                break;
            case "getUserUpdatePage":
                getUserUpdatePage(req, resp);
                break;
            case "update":
                update(req,resp);
                break;
        }
    }

    private void getUserUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UserServlet.getUserUpdatePage");
        String id = req.getParameter("id");
        User user  = userService.selectById(Integer.parseInt(id));
        req.setAttribute("user", user);
        req.getRequestDispatcher("/user/user_update.jsp").forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("UserServlet.update");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        User user = new User();
        user.setId(Integer.parseInt(id));
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAvatar("");

        boolean isSuccess = userService.update(user);
        if (isSuccess) {
            JSONUtil.obj2Json(JSONResult.ok("修改成功"), resp);
        } else {
            JSONUtil.obj2Json(JSONResult.error("修改失败"), resp);
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("UserServlet.add");
        String name=req.getParameter("name");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        User user=new User();
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAvatar("");
        Boolean isSuccess = userService.add(user);
        if (isSuccess) {
            JSONUtil.obj2Json(JSONResult.ok("添加成功"), resp);
        } else {
            JSONUtil.obj2Json(JSONResult.error("添加失败"), resp);
        }
    }


//    http://localhost:8080/JavaWeb/user?method=selectByPage&page=1&limit=10&name=333&email=333&phone=333
    private void selectByPage(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("UserServlet.selectByPage");
        int page = Integer.parseInt(req.getParameter("page"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String beginDate = req.getParameter("beginDate");
        String endDate = req.getParameter("endDate");
        UserQuery userQuery = new UserQuery(page, limit, name, email, phone, DateUtil.parse(beginDate), DateUtil.parse(endDate));

        LayUITableResult layUITableResult = userService.selectByPage(userQuery);
        JSONUtil.obj2Json(layUITableResult, resp);
    }

    private void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("UserServlet.deleteById");
        String id = req.getParameter("id");
        boolean isSuccess = userService.deleteById(Integer.parseInt(id));
        //使用jsxa就不能返回方法，而是返回json格式的信息
        //删除完了之后应该重定向到查找所有界面  /hotel/user?method=selectAll
        // resp.sendRedirect(req.getContextPath() + "/user?method=selectAll");
        if (isSuccess) {
            JSONUtil.obj2Json(JSONResult.ok("删除成功"), resp);
        } else {
            JSONUtil.obj2Json(JSONResult.error("删除失败"), resp);
        }
    }
    private void deleteAll(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("UserServlet.deleteAll");
        //"14,15"
        String ids=req.getParameter("ids");
        String[] array = ids.split(",");
        Boolean isSuccess = userService.deleteAll(array);
        if (isSuccess) {
            JSONUtil.obj2Json(JSONResult.ok("删除成功"), resp);
        } else {
            JSONUtil.obj2Json(JSONResult.error("删除失败"), resp);
        }
    }
    private void selectAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UserServlet.selectAll");
        List<User> list = userService.selectAll();
        //把数据放到req这个域对象
        req.setAttribute("list", list);
        //转发到user_list.jsp页面进行展示
        req.getRequestDispatcher("/user/user_list.jsp").forward(req, resp);
    }

}
