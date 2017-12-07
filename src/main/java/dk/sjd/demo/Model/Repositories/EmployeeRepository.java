package dk.sjd.demo.Model.Repositories;

import dk.sjd.demo.Model.Entities.Employee;
import dk.sjd.demo.Model.Entities.Shift;
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

            SqlRowSet sqlRowSet1 = jdbc.queryForRowSet("SELECT sum(hours) AS totalHours FROM shifts WHERE name='" + sqlRowSet.getString("name") + "'");
            while(sqlRowSet1.next()){
                employees.add(new Employee(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet1.getInt("totalHours")));
            }
        }

        return employees;
    }

    @Override
    public void createEmployee(Employee employee){
        jdbc.update("INSERT INTO employees(name, totalHours) VALUES('" + employee.getName() +"', '"+ employee.getTotalHours() +"')");
    }

    @Override
    public void deleteEmployee(int id) {

        jdbc.update("DELETE FROM employees WHERE id=" + id + "");
    }

    @Override
    public Employee readSpecificEmployee(int id) {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM employees WHERE id =" + id + "");

        if (sqlRowSet.next()){
            return new Employee(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getInt("totalHours"));
        }
        return new Employee();
    }

    /*
    public void calculateHours(Employee employee){

        jdbc.update("SELECT sum(hours) FROM shifts WHERE name='Joachim Stougaard'");
    }

    */

}
