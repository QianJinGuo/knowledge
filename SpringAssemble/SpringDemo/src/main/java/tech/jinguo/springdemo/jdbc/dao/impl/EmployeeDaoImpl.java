package tech.jinguo.springdemo.jdbc.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tech.jinguo.springdemo.jdbc.bean.Employee;
import tech.jinguo.springdemo.jdbc.dao.EmployeeDao;

import java.util.List;

@Repository("employeeDao")
public class EmployeeDaoImpl implements EmployeeDao {
    @Autowired
    @Qualifier(value = "jdbcTemplate")
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Employee> getAll() {
        String sql = "SELECT * from employee";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        return jdbcTemplate.query(sql, rowMapper);
    }
}
