package study.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import study.dao.IUserDao;
import study.pojo.User;
import study.utils.JDBCUtil;

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
        String sql = "select id,name,password,email,phone from user order by id desc limit ?,?";
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
        return template.update(sql, id);
    }

    @Override
    public Integer deleteAll(Integer[] ids) {
        //[14,15]
        // delete from user where id in(?,?);
        String sql = "delete from user where id in(";

        //拼接？和，如果采用高级内置方法写就是：sql+= Stream.of(ids).map(item->"?").collect(Collectors.joining(","));
        //拼接？和，
        for (Integer id : ids) {
            sql += "?,";
        }
        //delete from user where id in(?,?,
        sql = sql.substring(0, sql.length() - 1);

        sql += ")";
        return template.update(sql,ids);
    }

    @Override
    public Integer add(User user) {
        String sql = "insert into user(name,password,email,phone,avatar) values(?,?,?,?,?)";
        return template.update(sql, user.getName(), user.getPassword(), user.getEmail(), user.getPhone(), user.getAvatar());
    }
}
