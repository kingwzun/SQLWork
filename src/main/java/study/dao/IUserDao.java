package study.dao;

import study.pojo.User;

import java.util.List;

public interface IUserDao {
    List<User> selectAll();
    List<User> selectByPage(Integer offset, Integer limit);
    Long selectTotalCount();
    Integer deleteById(Integer id);

    Integer deleteAll(Integer[] ids);
}
