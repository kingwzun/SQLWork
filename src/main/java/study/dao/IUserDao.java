package study.dao;

import study.pojo.User;
import study.pojo.query.UserQuery;

import java.util.List;

public interface IUserDao {
    List<User> selectAll();
    List<User> selectByPage(UserQuery userQuery);
    Long selectTotalCount(UserQuery userQuery);
    Integer deleteById(Integer id);

    Integer deleteAll(Integer[] ids);

    Integer add(User user);

    User selectById(int parseInt);

    Integer update(User user);

    User selectByNameAndPassword(String name, String password);


}
