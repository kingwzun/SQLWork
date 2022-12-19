package study.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import study.dao.IUserDao;
import study.pojo.User;
import study.utils.JDBCUtil;;

import java.util.List;

public class UserDaoImpl implements IUserDao {
    private  JdbcTemplate template =new JdbcTemplate(JDBCUtil.getDataSource());
    @Override
    public List<User> selectAll() {
        String sql = "select id,name,password,email,phone from user";
        List<User> list = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return list;
    }

    @Override
    public List<User> selectByPage(Integer offset, Integer limit) {
        String sql = "select id,name,password,email,phone from user limit ?,?";
        List<User> list = template.query(sql, new BeanPropertyRowMapper<User>(User.class), offset, limit);
        return list;
    }

    @Override
    public Long selectTotalCount() {
        String sql = "select count(*) from user";
        Long totalCount = template.queryForObject(sql, Long.class);
        return totalCount;
    }

    @Override
    public Integer deleteById(Integer id) {
        String sql = "delete from user where id=?";
        int count = template.update(sql, id);
        return count;
    }
}
