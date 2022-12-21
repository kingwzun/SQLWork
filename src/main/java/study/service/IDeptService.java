package study.service;


import study.pojo.Dept;
import study.pojo.query.DeptQuery;
import study.utils.JSONResult;
import study.utils.LayUITableResult;

import java.util.List;

public interface IDeptService {
    List<Dept> selectAll();
    LayUITableResult selectByPage(DeptQuery deptQuery);
    Boolean deleteById(Integer id);
    Boolean deleteAll(String[] array);
    Boolean add(Dept dept);
    Dept selectById(int id);
    Boolean update(Dept dept);
    JSONResult selectDeptCount();
}
