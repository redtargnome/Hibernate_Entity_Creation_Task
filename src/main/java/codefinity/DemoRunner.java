package codefinity;

import codefinity.Service.EmployeeService;
import codefinity.model.Department;
import codefinity.model.Employee;
import codefinity.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@Profile("!test")
public class DemoRunner implements CommandLineRunner {

    private final EmployeeService employeeService;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DemoRunner(EmployeeService employeeService, DepartmentRepository departmentRepository) {
        this.employeeService = employeeService;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Create and save department first
        Department department = new Department("Engineering", "Building A");
        departmentRepository.save(department);
        
        // Create employees with the saved department reference
        Employee employee1 = new Employee("John Doe", "Senior Developer", 85000.0, LocalDate.of(2020, 1, 15), department);
        Employee employee2 = new Employee("Jane Smith", "Junior Developer", 65000.0, LocalDate.of(2023, 5, 10), department);
        
        // Add employees to department (sets both sides of bidirectional relationship)
        department.addEmployee(employee1);
        department.addEmployee(employee2);
        
        // Save department - cascade will save employees automatically
        departmentRepository.save(department);
        
        // Retrieve and print
        System.out.println("\n=== Employee List ===");
        employeeService.getAllEmployeesWithDepartments().forEach(emp -> {
            System.out.println("ID: " + emp.getId() + ", Name: " + emp.getName() + 
                             ", Position: " + emp.getPosition() + ", Salary: " + emp.getSalary() +
                             ", Department: " + (emp.getDepartment() != null ? emp.getDepartment().getName() : "None"));
        });
        
        // Test retrieval by ID
        System.out.println("\n=== Testing getById ===");
        Employee retrieved = employeeService.getById(1);
        System.out.println("Retrieved employee: " + retrieved.getName());
        
        // Test name retrieval
        System.out.println("\n=== Testing getEmployeeNameById ===");
        String name = employeeService.getEmployeeNameById(1);
        System.out.println("Employee name: " + name);
        
        System.out.println("\n=== Demo completed successfully! ===");
    }
}
