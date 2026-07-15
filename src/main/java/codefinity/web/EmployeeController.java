package codefinity.web;

import codefinity.Service.EmployeeService;
import codefinity.model.Department;
import codefinity.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final codefinity.repository.DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeController(EmployeeService employeeService, 
                               codefinity.repository.DepartmentRepository departmentRepository) {
        this.employeeService = employeeService;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee employee = employeeService.getById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/{id}/name")
    public ResponseEntity<String> getEmployeeNameById(@PathVariable int id) {
        String name = employeeService.getEmployeeNameById(id);
        return ResponseEntity.ok(name);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable int departmentId) {
        List<Employee> employees = employeeService.getEmployeesByDepartment(departmentId);
        return ResponseEntity.ok(employees);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDto employeeDto) {
        Department department = null;
        if (employeeDto.getDepartmentId() != null) {
            department = departmentRepository.findById(employeeDto.getDepartmentId())
                    .orElseThrow(() -> new NoSuchElementException("Department not found with id: " + employeeDto.getDepartmentId()));
        }
        
        Employee employee = new Employee(
                employeeDto.getName(),
                employeeDto.getPosition(),
                employeeDto.getSalary(),
                employeeDto.getHireDate(),
                department
        );
        
        Employee savedEmployee = employeeService.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable int id, 
            @RequestBody EmployeeDto employeeDto) {
        Employee employeeDetails = new Employee(
                employeeDto.getName(),
                employeeDto.getPosition(),
                employeeDto.getSalary(),
                employeeDto.getHireDate()
        );
        
        if (employeeDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(employeeDto.getDepartmentId())
                    .orElseThrow(() -> new NoSuchElementException("Department not found with id: " + employeeDto.getDepartmentId()));
            employeeDetails.setDepartment(department);
        }
        
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // DTO for request/response
    public static class EmployeeDto {
        private String name;
        private String position;
        private double salary;
        private LocalDate hireDate;
        private Integer departmentId;

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getPosition() { return position; }
        public void setPosition(String position) { this.position = position; }
        public double getSalary() { return salary; }
        public void setSalary(double salary) { this.salary = salary; }
        public LocalDate getHireDate() { return hireDate; }
        public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
        public Integer getDepartmentId() { return departmentId; }
        public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }
    }
}
