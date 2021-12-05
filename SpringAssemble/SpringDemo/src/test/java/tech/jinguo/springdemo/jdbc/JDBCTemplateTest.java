package tech.jinguo.springdemo.jdbc;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import tech.jinguo.springdemo.jdbc.bean.Employee;
import tech.jinguo.springdemo.jdbc.config.JDBCConfig;
import tech.jinguo.springdemo.jdbc.dao.EmployeeDao;

import java.util.ArrayList;
import java.util.List;

public class JDBCTemplateTest {
    ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:beans-jdbc.xml");
    ApplicationContext annotionContext = new AnnotationConfigApplicationContext(JDBCConfig.class);
    @Autowired
    EmployeeDao employeeDao;

    /**
     * 测试更新,xml方式获取jdbcTemplate
     */
    @Test
    public void testJDBCTemplate() {
        JdbcTemplate jdbcTemplate = ioc.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "update employee set last_name = ? where last_name = ?";
        jdbcTemplate.update(sql,"LeiJun","HanZong");
    }

    /**
     * 测试更新，注解方式获取jdbcTemplate
     */
    @Test
    public void testAnotionJDBCTemplate() {
        String sql = "update employee set last_name = ? where last_name = ?";
        JdbcTemplate jdbcTemplate = (JdbcTemplate) annotionContext.getBean("annotationJdbcTemplate");
        jdbcTemplate.update(sql,"LeiJun2","LeiJun");
    }

    /**
     * 测试批量插入
     */
    @Test
    public void testBatchUpdate() {
        JdbcTemplate jdbcTemplate = ioc.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "INSERT INTO employee(last_name,email,salary) VALUES (?,?,?)";
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{"zhangsan","123@jinguo.tech",888});
        list.add(new Object[]{"lisi","456@jinguo.tech",999});
        jdbcTemplate.batchUpdate(sql,list);
    }

    @Test
    public void testGetSingleValue(){
        String sql = "SELECT COUNT(*) FROM employee";
        JdbcTemplate jdbcTemplate = ioc.getBean("jdbcTemplate", JdbcTemplate.class);
        //调用JDBCTemplate的queryForObject方法
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class);
        System.out.println(count);
    }

    @Test
    public void testGetOne(){
        JdbcTemplate jdbcTemplate = ioc.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "SELECT * from employee WHERE id = ?";
        //映射器，下划线和驼峰映射
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        Employee employee= jdbcTemplate.queryForObject(sql,rowMapper,1);
        System.out.println(employee);
    }

    @Test
    public void testAll(){
        JdbcTemplate jdbcTemplate = ioc.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "SELECT * from employee";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        List<Employee> list = jdbcTemplate.query(sql, rowMapper);
        list.forEach(System.out::println);
    }

    @Test
    public void testGetAll(){
        EmployeeDao employeeDao = ioc.getBean("employeeDao", EmployeeDao.class);
        employeeDao.getAll().forEach(System.out::println);
    }

}
