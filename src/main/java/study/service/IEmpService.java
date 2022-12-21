package study.service;

import study.pojo.Emp;
import study.pojo.query.EmpQuery;
import study.utils.LayUITableResult;

import java.util.List;

public interface IEmpService {
    List<Emp> selectAll();
    LayUITableResult selectByPage(EmpQuery empQuery);
    Boolean deleteById(Integer id);

    Boolean deleteAll(String[] array);

    Boolean add(Emp emp);

    Emp selectById(int parseInt);

    Boolean update(Emp emp);




}
