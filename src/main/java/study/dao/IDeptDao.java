package study.dao;

import study.pojo.Dept;
import study.pojo.query.DeptQuery;

import java.util.List;

public interface IDeptDao {
    List<Dept> selectAll();
    List<Dept> selectByPage(DeptQuery deptQuery);
    Long selectTotalCount(DeptQuery deptQuery);
    Integer deleteById(Integer id);
    Integer deleteAll(Integer[] ids);
    Integer add(Dept dept);
    Dept selectById(int id);
    Integer update(Dept dept);
}
