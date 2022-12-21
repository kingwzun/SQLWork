package study.dao.impl;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import study.dao.IDeptDao;
import study.pojo.Dept;
import study.pojo.query.DeptQuery;
import study.pojo.vo.DeptCountVO;
import study.utils.JDBCUtil;

import java.util.ArrayList;
import java.util.List;

public class DeptDaoImpl implements IDeptDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());

    @Override
    public List<Dept> selectAll() {
        String sql = "select id,name,addr from dept";
        List<Dept> list = template.query(sql, new BeanPropertyRowMapper<Dept>(Dept.class));
        return list;
    }

    @Override
    public List<Dept> selectByPage(DeptQuery deptQuery) {
        String sql = "select id,name,addr from dept ";

        //查询参数
        List<Object> args = new ArrayList<>();
        String where = "where 1=1 ";
        if (!StringUtils.isEmpty(deptQuery.getName())) {
            where += "and name like ?";
            args.add("%" + deptQuery.getName() + "%");
        }
        if (!StringUtils.isEmpty(deptQuery.getAddr())) {
            where += "and addr=?";
            args.add(deptQuery.getAddr());
        }

        String limit = "";
        if (deptQuery != null) {
            int offset = (deptQuery.getPage() - 1) * deptQuery.getLimit();
            limit = "order by id desc limit " + offset + "," + deptQuery.getLimit();
        }

        List<Dept> list = template.query(sql + where + limit, new BeanPropertyRowMapper<Dept>(Dept.class), args.toArray());
        return list;
    }

    @Override
    public Long selectTotalCount(DeptQuery deptQuery) {
        // <where> <if></if> </where>
        //这三个搜索条件应该是有值才拼接上，没有值就不拼接
        String sql = "select count(*) from dept ";

        //查询参数
        List<Object> args = new ArrayList<>();
        String where = "where 1=1 ";
        if (!StringUtils.isEmpty(deptQuery.getName())) {
            where += "and name like ?";
            args.add("%" + deptQuery.getName() + "%");
        }
        if (!StringUtils.isEmpty(deptQuery.getAddr())) {
            where += "and addr=?";
            args.add(deptQuery.getAddr());
        }

        Long totalCount = template.queryForObject(sql + where, Long.class, args.toArray());
        return totalCount;
    }

    @Override
    public Integer deleteById(Integer id) {
        String sql = "delete from dept where id=?";
        int count = template.update(sql, id);
        return count;
    }

    @Override
    public Integer deleteAll(Integer[] ids) {
        //[14,15]
        // delete from dept where id in(?,?)
        String sql = "delete from dept where id in(";
        //sql += Stream.of(ids).map(item -> "?").collect(Collectors.joining(","));
        for (Integer id : ids) {
            sql += "?,";
        }
        //delete from dept where id in(?,?,
        sql = sql.substring(0, sql.length() - 1);
        sql += ")";
        int count = template.update(sql, ids);
        return count;
    }

    @Override
    public Integer add(Dept dept) {
        String sql = "insert into dept(name,addr) values(?,?)";
        int count = template.update(sql, dept.getName(), dept.getAddr());
        return count;
    }

    @Override
    public Dept selectById(int id) {
        String sql = "select id,name,addr from dept where id=?";
        // Dept dept = jdbcTdeptlate.queryForObject(sql, new BeanPropertyRowMapper<Dept>(Dept.class), id);
        List<Dept> list = template.query(sql, new BeanPropertyRowMapper<Dept>(Dept.class), id);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Integer update(Dept dept) {
        String sql = "update dept set name=?,addr=? where id=?";
        int count = template.update(sql, dept.getName(), dept.getAddr(), dept.getId());
        return count;
    }

    @Override
    public List<DeptCountVO> selectDeptCount() {
        String sql = "SELECT d.name,count(*) as value FROM emp AS e INNER JOIN dept AS d on e.dept_id=d.id GROUP BY d.id";
        List<DeptCountVO> list = template.query(sql, new BeanPropertyRowMapper<DeptCountVO>(DeptCountVO.class));
        return  list;
    }
}
