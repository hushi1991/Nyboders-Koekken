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

    //fields
    @Autowired
    private JdbcTemplate jdbc;

    //metoder
    //readAll laver en ArrayList med alle de objekter af den korrekte type der hentes fra DB, og returnere denne.
    @Override
    public ArrayList<Employee> readAll() {
        //Alt fra tabellen "employees" vælges og sættes i et SqlRowSet
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM  employees");
        ArrayList<Employee> employees = new ArrayList<>();

        //Så længe der er noget i SqlRowSettet bliver der fyldt på ArrayListen. Særligt bliver der i denne while-loop udregnet summen af 'hours'
        //fra "shifts" tabellen og indlæst i Employee-feltet 'totalhours' baseret på deres navn.
        while(sqlRowSet.next()) {

            SqlRowSet sqlRowSet1 = jdbc.queryForRowSet("SELECT sum(hours) AS totalHours FROM shifts WHERE name='" + sqlRowSet.getString("name") + "'");

            while(sqlRowSet1.next()){
                employees.add(new Employee(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet1.getDouble("totalHours")));
            }
        }

        return employees;
    }

    //Det Employee objekt der gives i parametret bliver skabt i DB.
    @Override
    public void createEmployee(Employee employee){
        jdbc.update("INSERT INTO employees(name, totalHours) VALUES('" + employee.getName() +"', '"+ employee.getTotalHours() +"')");
    }

    //Det valgte id findes og fjernes samt den Employee det tilhører fra DB
    @Override
    public void deleteEmployee(int id) {
        jdbc.update("DELETE FROM employees WHERE id=" + id + "");
    }

    //En specifik Employee bliver fundet og returneret baseret på det id der gives i parametret


    @Override
    public Employee readSpecificEmployee(int id) {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM employees WHERE id =" + id + "");

        if (sqlRowSet.next()){
            return new Employee(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getDouble("totalHours"));
        }
        return new Employee();
    }
}
