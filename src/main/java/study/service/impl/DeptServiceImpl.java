package study.service.impl;


import study.dao.IDeptDao;
import study.dao.impl.DeptDaoImpl;
import study.pojo.Dept;
import study.pojo.query.DeptQuery;
import study.pojo.vo.DeptCountVO;
import study.service.IDeptService;
import study.utils.JSONResult;
import study.utils.LayUITableResult;

import java.util.List;

public class DeptServiceImpl implements IDeptService {
    private IDeptDao deptDao = new DeptDaoImpl();

    @Override
    public List<Dept> selectAll() {
        return deptDao.selectAll();
    }

    @Override
    public LayUITableResult selectByPage(DeptQuery deptQuery) {
        //查询当前页的数据
        List<Dept> list = deptDao.selectByPage(deptQuery);
        //查询总的数量
        Long totalCount = deptDao.selectTotalCount(deptQuery);
        return LayUITableResult.ok(list, totalCount);
    }

    @Override
    public Boolean deleteById(Integer id) {
        Integer count = deptDao.deleteById(id);
        //return count == 1 ? true : false;
        return count == 1;
    }

    @Override
    public Boolean deleteAll(String[] array) {
        Integer[] ids = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            ids[i] = Integer.parseInt(array[i]);
        }

        int count = deptDao.deleteAll(ids);
        return count == array.length;
    }

    @Override
    public Boolean add(Dept dept) {
        int count = deptDao.add(dept);
        return count == 1;
    }

    @Override
    public Dept selectById(int id) {
        return deptDao.selectById(id);
    }

    @Override
    public Boolean update(Dept dept) {
        int count = deptDao.update(dept);
        return count == 1;
    }

    @Override
    public JSONResult selectDeptCount() {
        List<DeptCountVO> list =deptDao.selectDeptCount();
        return JSONResult.ok(list);
    }
}
