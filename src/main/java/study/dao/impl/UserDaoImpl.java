package study.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import study.dao.IUserDao;
import study.pojo.User;
import study.pojo.query.UserQuery;
import study.utils.JDBCUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDaoImpl implements IUserDao {
    private  JdbcTemplate template =new JdbcTemplate(JDBCUtil.getDataSource());
    @Override
    public List<User> selectAll() {
        String sql = "select id,name,type,password,email,phone,status,gmt_create,gmt_modified from user ";
        List<User> list = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return list;
    }

    @Override
    public List<User> selectByPage(UserQuery userQuery) {
        String sql = "select id,name,type,password,email,phone,status,gmt_create,gmt_modified from user ";
        //where name=? and email=? and phone=?
        String where="where 1=1 ";//1=1不起作用，目的是为了消除拼接中and的影响
        List<Object> args=new ArrayList<>();
        if(!StringUtils.isEmpty(userQuery.getName())){
            where+="and name like ? ";
            args.add("%"+userQuery.getName()+"%");
        }
        if(!StringUtils.isEmpty(userQuery.getEmail())){
            where+="and email=? ";
            args.add(userQuery.getEmail());
        }
        if(!StringUtils.isEmpty(userQuery.getPhone())){
            where+="and phone=? ";
            args.add(userQuery.getPhone());
        }
        if (userQuery.getType() != null) {
            where += "and type=? ";
            args.add(userQuery.getType());
        }
        if (userQuery.getBeginDate() != null && userQuery.getEndDate() != null) {
            where += "and gmt_create between ? and ? ";
            args.add(userQuery.getBeginDate());
            args.add(userQuery.getEndDate());
        }
        //order by id desc limit ? , ?
        String limit="";
        if(userQuery!=null){
            int offset = (userQuery.getPage() - 1) * userQuery.getLimit();
            limit = "order by id desc limit " + offset + "," + userQuery.getLimit();
        }

        List<User> list = template.query(sql+where+limit, new BeanPropertyRowMapper<User>(User.class), args.toArray());
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list;
    }

    @Override
    public Long selectTotalCount(UserQuery userQuery) {
        // <where> <if></if> </where>
        //这三个搜索条件应该是有值才拼接上，没有值就不拼接
        //sql = select count(*) from user where name=? and email=? and phone=?
        String sql = "select count(*) from user ";

        List<Object> args=new ArrayList<>();
        String where="where 1=1 ";//1=1不起作用，目的是为了消除拼接中and的影响
        if(!StringUtils.isEmpty(userQuery.getName())){
            where+="and name like ? ";
            args.add("%"+userQuery.getName()+"%");
        }
        if(!StringUtils.isEmpty(userQuery.getEmail())){
            where+="and email=? ";
            args.add(userQuery.getEmail());
        }
        if(!StringUtils.isEmpty(userQuery.getPhone())){
            where+="and phone=? ";
            args.add(userQuery.getPhone());
        }
        if (userQuery.getType() != null) {
            where += "and type=?";
            args.add(userQuery.getType());
        }
        if (userQuery.getBeginDate() != null && userQuery.getEndDate() != null) {
            where += "and gmt_create between ? and ? ";
            args.add(userQuery.getBeginDate());
            args.add(userQuery.getEndDate());
        }
        Long totalCount = template.queryForObject(sql+where, Long.class ,args.toArray());
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
        String sql = "insert into user(name,password,email,phone,avatar,type) values(?,?,?,?,?,?)";
        return template.update(sql, user.getName(), user.getPassword(), user.getEmail(), user.getPhone(), user.getAvatar(),user.getType());
    }
    @Override
    public User selectById(int id) {
        String sql = "select id,name,password,email,phone from user where id=?";
        // User user = jdbcTemplate.queryForObject(sql, User.class, id);
        List<User> list = template.query(sql, new BeanPropertyRowMapper<User>(User.class), id);
        return list.get(0);
    }

    @Override
    public Integer update(User user) {
        String sql = "update user set name=?,password=?,email=?,phone=?,avatar=? where id=?";
        int count = template.update(sql, user.getName(), user.getPassword(), user.getEmail(), user.getPhone(), user.getAvatar(), user.getId());
        return count;
    }

    @Override
    public User selectByNameAndPassword(String name, String password) {
        String sql = "select id,name,password,email,phone,type from user where name=? and password=?";
        List<User> user = template.query(sql, new BeanPropertyRowMapper<User>(User.class),name,password);
        if(CollectionUtils.isEmpty(user)){
            return null;
        }
        return user.get(0);
//        解决方法2：捕获异常
//        User user = null;
//        try{
//            user=template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class), name,password);
//        }catch (EmptyResultDataAccessException e){
//            e.printStackTrace();
//        }
    }
}
