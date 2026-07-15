package codefinity.Service.impl;

import codefinity.Service.EmployeeService;
import codefinity.model.Department;
import codefinity.model.Employee;
import codefinity.repository.DepartmentRepository;
import codefinity.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public Employee add(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public Employee getById(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot get employee by ID: " + id));
    }

    @Override
    public Optional<Employee> getByIdOptional(int id) {
        return employeeRepository.findById(id);
    }

    @Override
    public String getEmployeeNameById(int id) {
        Employee employee = getById(id);
        String employeeName = employee.getName();
        if (employeeName != null) {
            return employeeName;
        } else {
            throw new NullPointerException("The employee's name is null, " +
                    "or there is no name for employee with ID " + id);
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    @Override
    @Transactional
    public List<Employee> getAllEmployeesWithDepartments() {
        return employeeRepository.findAllWithDepartments();
    }

    @Override
    @Transactional
    public Employee updateEmployee(int id, Employee employeeDetails) {
        Employee employee = getById(id);
        employee.setName(employeeDetails.getName());
        employee.setPosition(employeeDetails.getPosition());
        employee.setSalary(employeeDetails.getSalary());
        employee.setHireDate(employeeDetails.getHireDate());
        
        if (employeeDetails.getDepartment() != null) {
            Department department = departmentRepository.findById(employeeDetails.getDepartment().getId())
                    .orElseThrow(() -> new NoSuchElementException("Department not found"));
            employee.setDepartment(department);
        }
        
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void deleteEmployee(int id) {
        Employee employee = getById(id);
        employeeRepository.delete(employee);
    }

    @Override
    public List<Employee> getEmployeesByDepartment(int departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department not found with id: " + departmentId));
        return employeeRepository.findByDepartment(department);
    }
}
