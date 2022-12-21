package study.service;

import study.pojo.User;
import study.pojo.query.UserQuery;
import study.utils.LayUITableResult;

import java.util.List;

public interface IUserService {
    List<User> selectAll();
    LayUITableResult selectByPage(UserQuery userQuery);
    Boolean deleteById(Integer id);

    Boolean deleteAll(String[] array);

    Boolean add(User user);

    User selectById(int parseInt);

    Boolean update(User user);

    User login(String name, String password);


}
