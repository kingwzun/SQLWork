package study.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import study.dao.IEmpDao;
import study.pojo.Emp;
import study.pojo.query.EmpQuery;
import study.utils.JDBCUtil;

import java.util.ArrayList;
import java.util.List;

public class EmpDaoImpl implements IEmpDao {
    private  JdbcTemplate template =new JdbcTemplate(JDBCUtil.getDataSource());
    @Override
    public List<Emp> selectAll() {
        String sql = "select id,name,dept_id,email,phone from emp ";
        List<Emp> list = template.query(sql, new BeanPropertyRowMapper<Emp>(Emp.class));
        return list;
    }

    @Override
    public List<Emp> selectByPage(EmpQuery empQuery) {
        String sql = "select id,name,dept_id,email,phone from emp ";

        //where name=? and email=? and phone=?
        String where="where 1=1 ";//1=1不起作用，目的是为了消除拼接中and的影响
        List<Object> args=new ArrayList<>();
        if(!StringUtils.isEmpty(empQuery.getName())){
            where+="and name like ? ";
            args.add("%"+empQuery.getName()+"%");
        }
        if(!StringUtils.isEmpty(empQuery.getEmail())){
            where+="and email=? ";
            args.add(empQuery.getEmail());
        }
        if(!StringUtils.isEmpty(empQuery.getPhone())){
            where+="and phone=? ";
            args.add(empQuery.getPhone());
        }
        //order by id desc limit ? , ?
        String limit="";
        if(empQuery!=null){
            int offset = (empQuery.getPage() - 1) * empQuery.getLimit();
            limit = "order by id desc limit " + offset + "," + empQuery.getLimit();
        }

        List<Emp> list = template.query(sql+where+limit, new BeanPropertyRowMapper<Emp>(Emp.class), args.toArray());
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list;
    }

    @Override
    public Long selectTotalCount(EmpQuery empQuery) {
        // <where> <if></if> </where>
        //这三个搜索条件应该是有值才拼接上，没有值就不拼接
        //sql = select count(*) from emp where name=? and email=? and phone=?
        String sql = "select count(*) from emp ";

        List<Object> args=new ArrayList<>();
        String where="where 1=1 ";//1=1不起作用，目的是为了消除拼接中and的影响
        if(!StringUtils.isEmpty(empQuery.getName())){
            where+="and name like ? ";
            args.add("%"+empQuery.getName()+"%");
        }
        if(!StringUtils.isEmpty(empQuery.getEmail())){
            where+="and email=? ";
            args.add(empQuery.getEmail());
        }
        if(!StringUtils.isEmpty(empQuery.getPhone())){
            where+="and phone=? ";
            args.add(empQuery.getPhone());
        }
        Long totalCount = template.queryForObject(sql+where, Long.class ,args.toArray());
        return totalCount;
    }

    @Override
    public Integer deleteById(Integer id) {
        String sql = "delete from emp where id=?";
        return template.update(sql, id);
    }

    @Override
    public Integer deleteAll(Integer[] ids) {
        //[14,15]
        // delete from emp where id in(?,?);
        String sql = "delete from emp where id in(";

        //拼接？和，如果采用高级内置方法写就是：sql+= Stream.of(ids).map(item->"?").collect(Collectors.joining(","));
        //拼接？和，
        for (Integer id : ids) {
            sql += "?,";
        }
        //delete from emp where id in(?,?,
        sql = sql.substring(0, sql.length() - 1);

        sql += ")";
        return template.update(sql,ids);
    }

    @Override
    public Integer add(Emp emp) {
        String sql = "insert into emp(name,dept_id,email,phone) values(?,?,?,?)";
        return template.update(sql, emp.getName(), emp.getDeptId(), emp.getEmail(), emp.getPhone());
    }
    @Override
    public Emp selectById(int id) {
        String sql = "select id,name,dept_id,email,phone from emp where id=?";
        // Emp emp = jdbcTemplate.queryForObject(sql, Emp.class, id);
        List<Emp> list = template.query(sql, new BeanPropertyRowMapper<Emp>(Emp.class), id);
        return list.get(0);
    }

    @Override
    public Integer update(Emp emp) {
        String sql = "update emp set name=?,dept_id=?,email=?,phone=? where id=?";
        int count = template.update(sql, emp.getName(), emp.getDeptId(), emp.getEmail(), emp.getPhone(), emp.getId());
        return count;
    }


}
