package study.service;

import study.pojo.User;
import study.utils.LayUITableResult;

import java.util.List;

public interface IUserService {
    List<User> selectAll();
    LayUITableResult selectByPage(Integer page, Integer limit);
    Boolean deleteById(Integer id);
}
