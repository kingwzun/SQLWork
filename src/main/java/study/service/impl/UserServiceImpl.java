package study.service.impl;


import study.dao.IUserDao;
import study.dao.impl.UserDaoImpl;
import study.pojo.User;
import study.service.IUserService;
import study.utils.LayUITableResult;

import java.util.List;

public class UserServiceImpl implements IUserService {
    private IUserDao userDao = new UserDaoImpl();

    @Override
    public List<User> selectAll() {
        return userDao.selectAll();
    }

    @Override
    public LayUITableResult selectByPage(Integer page, Integer limit) {
        //查询当前页的数据
        int offset = (page - 1) * limit;
        List<User> list = userDao.selectByPage(offset, limit);
        //查询总的数量
        Long totalCount = userDao.selectTotalCount();
        return LayUITableResult.ok(list, totalCount);
    }

    @Override
    public Boolean deleteById(Integer id) {
        Integer count = userDao.deleteById(id);
        return count == 1 ? true : false;
    }
}
