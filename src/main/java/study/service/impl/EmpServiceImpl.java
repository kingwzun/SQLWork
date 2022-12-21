package study.service.impl;

import study.dao.IEmpDao;
import study.dao.impl.EmpDaoImpl;
import study.pojo.Emp;
import study.pojo.query.EmpQuery;
import study.pojo.vo.EmpDeptVO;
import study.service.IEmpService;
import study.utils.LayUITableResult;

import java.util.List;

public class EmpServiceImpl implements IEmpService {
    private IEmpDao empDao = new EmpDaoImpl();

    @Override
    public List<Emp> selectAll() {
        return empDao.selectAll();
    }

    @Override
    public LayUITableResult selectByPage(EmpQuery empQuery) {
        //查询当前页的数据
//        int offset = (page - 1) * limit;
        List<EmpDeptVO> list = empDao.selectByPage(empQuery);
        //查询总的数量
        Long totalCount = empDao.selectTotalCount(empQuery);
        return LayUITableResult.ok(list, totalCount);
    }

    @Override
    public Boolean deleteById(Integer id) {
        Integer count = empDao.deleteById(id);
        return count == 1;
    }

    @Override
    public Boolean deleteAll(String[] array) {
        Integer[] ids=new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            ids[i] = Integer.parseInt(array[i]);
        }

        int count=empDao.deleteAll(ids);
        return count==array.length;
    }

    @Override
    public Boolean add(Emp emp) {
        int count=empDao.add(emp);
        return count==1;
    }

    @Override
    public Emp selectById(int parseInt) {
        return empDao.selectById(parseInt);

    }

    @Override
    public Boolean update(Emp emp) {
        int count=empDao.update(emp);
        return count==1;
    }


}
