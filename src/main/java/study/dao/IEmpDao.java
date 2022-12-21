package study.dao;

import study.pojo.Emp;
import study.pojo.query.EmpQuery;
import study.pojo.vo.EmpDeptVO;
import java.util.List;

public interface IEmpDao {
    List<Emp> selectAll();
    List<EmpDeptVO> selectByPage(EmpQuery empQuery);
    Long selectTotalCount(EmpQuery empQuery);
    Integer deleteById(Integer id);

    Integer deleteAll(Integer[] ids);

    Integer add(Emp emp);

    Emp selectById(int parseInt);

    Integer update(Emp emp);




}
