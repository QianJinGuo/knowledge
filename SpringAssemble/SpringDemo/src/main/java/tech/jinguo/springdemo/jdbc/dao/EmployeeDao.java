package tech.jinguo.springdemo.jdbc.dao;

import tech.jinguo.springdemo.jdbc.bean.Employee;

import java.util.List;

public interface EmployeeDao {
    public List<Employee> getAll();
}
