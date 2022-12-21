package study.controller;


import study.pojo.Emp;
import study.pojo.query.EmpQuery;
import study.service.IEmpService;
import study.service.impl.EmpServiceImpl;
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

@WebServlet("/emp")
public class EmpServlet extends HttpServlet {
    private IEmpService empService = new EmpServiceImpl();

    //http://localhost:8080/hotel/emp?method=selectAll
    //http://localhost:8080/hotel/emp?method=deleteById&id=1
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
            case "getEmpUpdatePage":
                getEmpUpdatePage(req, resp);
                break;
            case "update":
                update(req,resp);
                break;
        }
    }

    private void getEmpUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EmpServlet.getEmpUpdatePage");
        String id = req.getParameter("id");
        Emp emp  = empService.selectById(Integer.parseInt(id));
        req.setAttribute("emp", emp);
        req.getRequestDispatcher("/emp/emp_update.jsp").forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("EmpServlet.update");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        Integer deptId =Integer.parseInt(req.getParameter("deptId")) ;
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        Emp emp = new Emp();
        emp.setId(Integer.parseInt(id));
        emp.setName(name);
        emp.setDeptId(deptId);
        emp.setEmail(email);
        emp.setPhone(phone);


        boolean isSuccess = empService.update(emp);
        if (isSuccess) {
            JSONUtil.obj2Json(JSONResult.ok("修改成功"), resp);
        } else {
            JSONUtil.obj2Json(JSONResult.error("修改失败"), resp);
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("EmpServlet.add");
        String name=req.getParameter("name");
        Integer deptId =Integer.parseInt(req.getParameter("deptId")) ;
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        Emp emp=new Emp();
        emp.setName(name);
        emp.setDeptId(deptId);
        emp.setEmail(email);
        emp.setPhone(phone);

        Boolean isSuccess = empService.add(emp);
        if (isSuccess) {
            JSONUtil.obj2Json(JSONResult.ok("添加成功"), resp);
        } else {
            JSONUtil.obj2Json(JSONResult.error("添加失败"), resp);
        }
    }


//    http://localhost:8080/JavaWeb/emp?method=selectByPage&page=1&limit=10&name=333&email=333&phone=333
    private void selectByPage(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("EmpServlet.selectByPage");
        int page = Integer.parseInt(req.getParameter("page"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        EmpQuery empQuery=new EmpQuery(page,limit,name,email,phone);

        LayUITableResult layUITableResult = empService.selectByPage(empQuery);
        JSONUtil.obj2Json(layUITableResult, resp);
    }

    private void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("EmpServlet.deleteById");
        String id = req.getParameter("id");
        boolean isSuccess = empService.deleteById(Integer.parseInt(id));
        //使用jsxa就不能返回方法，而是返回json格式的信息
        //删除完了之后应该重定向到查找所有界面  /hotel/emp?method=selectAll
        // resp.sendRedirect(req.getContextPath() + "/emp?method=selectAll");
        if (isSuccess) {
            JSONUtil.obj2Json(JSONResult.ok("删除成功"), resp);
        } else {
            JSONUtil.obj2Json(JSONResult.error("删除失败"), resp);
        }
    }
    private void deleteAll(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("EmpServlet.deleteAll");
        //"14,15"
        String ids=req.getParameter("ids");
        String[] array = ids.split(",");
        Boolean isSuccess = empService.deleteAll(array);
        if (isSuccess) {
            JSONUtil.obj2Json(JSONResult.ok("删除成功"), resp);
        } else {
            JSONUtil.obj2Json(JSONResult.error("删除失败"), resp);
        }
    }
    private void selectAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EmpServlet.selectAll");
        List<Emp> list = empService.selectAll();
        //把数据放到req这个域对象
        req.setAttribute("list", list);
        //转发到emp_list.jsp页面进行展示
        req.getRequestDispatcher("/emp/emp_list.jsp").forward(req, resp);
    }

}
