package dk.sjd.demo.Model.Repositories;

import dk.sjd.demo.Model.Entities.Employee;

public interface IEmployeeRepository extends ICrudRepository {

    public void createEmployee(Employee employee);

    public Employee readSpecificEmployee(int id);

    public void deleteEmployee(int id);
}
