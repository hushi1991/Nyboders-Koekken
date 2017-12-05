package dk.sjd.demo.Model.Repositories;

import dk.sjd.demo.Model.Entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class EmployeeRepository implements IEmployeeRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public ArrayList<Employee> readAll() {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM  employees");
        ArrayList<Employee> employees = new ArrayList<>();

        while(sqlRowSet.next()) {
            employees.add(new Employee(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getInt("totalHours")));
        }

        return employees;
    }

    @Override
    public void createEmployee(Employee employee){
        jdbc.update("INSERT INTO employees(name, totalHours) VALUES('" + employee.getName() +"', '"+ employee.getTotalHours() +"')");
    }
}
