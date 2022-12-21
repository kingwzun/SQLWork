package study.controller;


import study.pojo.Dept;
import study.pojo.query.DeptQuery;
import study.service.IDeptService;
import study.service.impl.DeptServiceImpl;
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

@WebServlet("/dept")
public class DeptServlet extends HttpServlet {
    private IDeptService deptService = new DeptServiceImpl();

    //http://localhost:8080/hotel/dept?method=selectAll
    //http://localhost:8080/hotel/dept?method=deleteById&id=1
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
                deleteAll(req, resp);
                break;
            case "add":
                add(req, resp);
                break;
            case "getDeptUpdatePage":
                getDeptUpdatePage(req, resp);
                break;
            case "update":
                update(req, resp);
                break;
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("DeptServlet.update");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String addr = req.getParameter("addr");
        Dept dept = new Dept();
        dept.setId(Integer.parseInt(id));
        dept.setName(name);
        dept.setAddr(addr);

        boolean isSuccess = deptService.update(dept);
        if (isSuccess) {
            JSONUtil.obj2Json(JSONResult.ok("修改成功"), resp);
        } else {
            JSONUtil.obj2Json(JSONResult.error("修改失败"), resp);
        }
    }


    private void getDeptUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DeptServlet.getDeptUpdatePage");
        String id = req.getParameter("id");
        Dept dept  = deptService.selectById(Integer.parseInt(id));
        req.setAttribute("dept", dept);
        req.getRequestDispatcher("/dept/dept_update.jsp").forward(req, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("DeptServlet.add");
        String name = req.getParameter("name");
        String addr = req.getParameter("addr");
        Dept dept = new Dept();
        dept.setName(name);
        dept.setAddr(addr);

        boolean isSuccess = deptService.add(dept);
        if (isSuccess) {
            JSONUtil.obj2Json(JSONResult.ok("添加成功"), resp);
        } else {
            JSONUtil.obj2Json(JSONResult.error("添加失败"), resp);
        }
    }

    private void deleteAll(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("DeptServlet.deleteAll");
        //"14,15"
        String ids = req.getParameter("ids");
        String[] array = ids.split(",");
        boolean isSuccess = deptService.deleteAll(array);
        if (isSuccess) {
            JSONUtil.obj2Json(JSONResult.ok("批量删除成功"), resp);
        } else {
            JSONUtil.obj2Json(JSONResult.error("批量删除失败"), resp);
        }
    }

    //https://layui.itze.cn/demo/table/dept.json?page=1&limit=10
    ///dept?method=selectById&page=1&limit=10
    // /hotel/dept?method=selectByPage&page=1&limit=10&name=lisi&email=aaa&phone=2323
    private void selectByPage(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("DeptServlet.selectByPage");
        int page = Integer.parseInt(req.getParameter("page"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        String name = req.getParameter("name");
        String addr = req.getParameter("addr");
        DeptQuery deptQuery = new DeptQuery(page, limit, name, addr);

        LayUITableResult layUITableResult = deptService.selectByPage(deptQuery);
        JSONUtil.obj2Json(layUITableResult, resp);
    }

    private void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("DeptServlet.deleteById");
        String id = req.getParameter("id");
        boolean isSuccess = deptService.deleteById(Integer.parseInt(id));
        //删除完了之后应该重定向到查找所有界面  /hotel/dept?method=selectAll
        // resp.sendRedirect(req.getContextPath() + "/dept?method=selectAll");
        if (isSuccess) {
            JSONUtil.obj2Json(JSONResult.ok("删除成功"), resp);
        } else {
            JSONUtil.obj2Json(JSONResult.error("删除失败"), resp);
        }
    }

    private void selectAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DeptServlet.selectAll");
        List<Dept> list = deptService.selectAll();
        //把数据放到req这个域对象
        req.setAttribute("list", list);
        //转发到dept_list.jsp页面进行展示
        req.getRequestDispatcher("/dept/dept_list.jsp").forward(req, resp);
    }

}
