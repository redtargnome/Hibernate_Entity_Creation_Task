package codefinity.Service;

import codefinity.model.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee add(Employee employee);

    Employee getById(int id);

    Optional<Employee> getByIdOptional(int id);

    String getEmployeeNameById(int id);

    List<Employee> getAllEmployees();
    
    List<Employee> getAllEmployeesWithDepartments();

    Employee updateEmployee(int id, Employee employee);

    void deleteEmployee(int id);

    List<Employee> getEmployeesByDepartment(int departmentId);
}

