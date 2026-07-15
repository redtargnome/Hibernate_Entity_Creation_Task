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
import java.util.List;

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
        // Create 5 departments
        Department engineering = new Department("Engineering", "Building A, Floor 1");
        Department marketing = new Department("Marketing", "Building A, Floor 2");
        Department hr = new Department("Human Resources", "Building B, Floor 1");
        Department finance = new Department("Finance", "Building B, Floor 2");
        Department operations = new Department("Operations", "Building C, Floor 1");
        
        // Save all departments
        departmentRepository.saveAll(List.of(engineering, marketing, hr, finance, operations));
        
        // Create 5 employees for Engineering
        Employee emp1 = new Employee("John Doe", "Senior Software Engineer", 95000.0, LocalDate.of(2018, 3, 15), engineering);
        Employee emp2 = new Employee("Jane Smith", "Junior Developer", 70000.0, LocalDate.of(2023, 5, 10), engineering);
        engineering.addEmployee(emp1);
        engineering.addEmployee(emp2);
        
        // Create 5 employees for Marketing
        Employee emp3 = new Employee("Michael Johnson", "Marketing Manager", 85000.0, LocalDate.of(2020, 1, 20), marketing);
        Employee emp4 = new Employee("Sarah Williams", "Digital Marketing Specialist", 65000.0, LocalDate.of(2022, 7, 1), marketing);
        Employee emp5 = new Employee("David Brown", "Content Creator", 60000.0, LocalDate.of(2023, 2, 15), marketing);
        marketing.addEmployee(emp3);
        marketing.addEmployee(emp4);
        marketing.addEmployee(emp5);
        
        // Create 5 employees for HR
        Employee emp6 = new Employee("Lisa Davis", "HR Director", 90000.0, LocalDate.of(2019, 6, 1), hr);
        Employee emp7 = new Employee("Robert Wilson", "Recruiter", 70000.0, LocalDate.of(2021, 11, 10), hr);
        hr.addEmployee(emp6);
        hr.addEmployee(emp7);
        
        // Create 5 employees for Finance
        Employee emp8 = new Employee("James Taylor", "Financial Analyst", 75000.0, LocalDate.of(2021, 4, 1), finance);
        Employee emp9 = new Employee("Jennifer Martinez", "Accountant", 72000.0, LocalDate.of(2020, 9, 15), finance);
        finance.addEmployee(emp8);
        finance.addEmployee(emp9);
        
        // Create 5 employees for Operations
        Employee emp10 = new Employee("Thomas Anderson", "Operations Manager", 88000.0, LocalDate.of(2017, 8, 1), operations);
        Employee emp11 = new Employee("Emily Clark", "Logistics Coordinator", 68000.0, LocalDate.of(2022, 3, 20), operations);
        operations.addEmployee(emp10);
        operations.addEmployee(emp11);
        
        // Save all departments with their employees (cascade will save employees)
        departmentRepository.saveAll(List.of(engineering, marketing, hr, finance, operations));
        
        // Retrieve and print all departments
        System.out.println("\n=== Department List ===");
        departmentRepository.findAll().forEach(dept -> {
            System.out.println("Department ID: " + dept.getId() + ", Name: " + dept.getName() + 
                             ", Location: " + dept.getLocation() + 
                             ", Employees: " + dept.getEmployees().size());
        });
        
        // Retrieve and print all employees
        System.out.println("\n=== Employee List ===");
        employeeService.getAllEmployeesWithDepartments().forEach(emp -> {
            System.out.println("ID: " + emp.getId() + ", Name: " + emp.getName() + 
                             ", Position: " + emp.getPosition() + ", Salary: $" + emp.getSalary() +
                             ", Hire Date: " + emp.getHireDate() +
                             ", Department: " + (emp.getDepartment() != null ? emp.getDepartment().getName() : "None"));
        });
        
        // Print summary statistics
        System.out.println("\n=== Summary Statistics ===");
        System.out.println("Total Departments: " + departmentRepository.count());
        System.out.println("Total Employees: " + employeeService.getAllEmployees().size());
        
        // Test retrieval by ID
        System.out.println("\n=== Testing getById ===");
        Employee retrieved = employeeService.getById(1);
        System.out.println("Retrieved employee: " + retrieved.getName() + " from " + retrieved.getDepartment().getName());
        
        // Test name retrieval
        System.out.println("\n=== Testing getEmployeeNameById ===");
        String name = employeeService.getEmployeeNameById(1);
        System.out.println("Employee name: " + name);
        
        // Test department retrieval
        System.out.println("\n=== Testing Department Retrieval ===");
        Department retrievedDept = departmentRepository.findById(1).orElse(null);
        if (retrievedDept != null) {
            System.out.println("Department: " + retrievedDept.getName() + " has " + retrievedDept.getEmployees().size() + " employees");
        }
        
        System.out.println("\n=== Demo completed successfully! ===");
    }
}
