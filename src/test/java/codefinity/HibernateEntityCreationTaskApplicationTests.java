package codefinity;

import codefinity.Service.EmployeeService;
import codefinity.model.Department;
import codefinity.model.Employee;
import codefinity.repository.DepartmentRepository;
import codefinity.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HibernateEntityCreationTaskApplicationTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeService employeeService;

    @Test
    void contextLoads() {
        // Test that Spring context loads successfully
        assertNotNull(employeeRepository);
        assertNotNull(departmentRepository);
        assertNotNull(employeeService);
    }

    @Test
    void testEmployeeEntityMapping() {
        // Create a department
        Department department = new Department("IT", "Floor 1");
        Department savedDepartment = departmentRepository.save(department);
        
        // Create an employee
        Employee employee = new Employee("Alice", "Software Engineer", 75000.0, LocalDate.of(2024, 1, 15), savedDepartment);
        savedDepartment.addEmployee(employee);
        Employee savedEmployee = employeeRepository.save(employee);
        
        // Clear and retrieve
        employeeRepository.flush();
        departmentRepository.flush();
        
        Employee retrievedEmployee = employeeRepository.findById(savedEmployee.getId()).orElse(null);
        
        assertNotNull(retrievedEmployee, "Employee was not retrieved, mapping may be incorrect");
        assertEquals("Alice", retrievedEmployee.getName());
        assertEquals("Software Engineer", retrievedEmployee.getPosition());
        assertEquals(75000.0, retrievedEmployee.getSalary());
        assertEquals(LocalDate.of(2024, 1, 15), retrievedEmployee.getHireDate());
        assertNotNull(retrievedEmployee.getDepartment(), "Department of the retrieved employee is null");
        assertEquals("IT", retrievedEmployee.getDepartment().getName());
    }

    @Test
    void testDepartmentEntityMapping() {
        Department department = new Department("HR", "Floor 2");
        Department savedDepartment = departmentRepository.save(department);
        
        departmentRepository.flush();
        
        Department retrievedDepartment = departmentRepository.findById(savedDepartment.getId()).orElse(null);
        
        assertNotNull(retrievedDepartment, "Department was not retrieved, mapping may be incorrect");
        assertEquals("HR", retrievedDepartment.getName());
        assertEquals("Floor 2", retrievedDepartment.getLocation());
    }

    @Test
    void testEmployeeService() {
        // Create a department
        Department department = new Department("Finance", "Floor 3");
        Department savedDepartment = departmentRepository.save(department);
        
        // Test add employee
        Employee employee = new Employee("Bob", "Accountant", 60000.0, LocalDate.of(2023, 3, 1), savedDepartment);
        Employee savedEmployee = employeeService.add(employee);
        
        assertNotNull(savedEmployee.getId());
        assertEquals("Bob", savedEmployee.getName());
        
        // Test getById
        Employee retrieved = employeeService.getById(savedEmployee.getId());
        assertNotNull(retrieved);
        assertEquals("Bob", retrieved.getName());
        
        // Test getEmployeeNameById
        String name = employeeService.getEmployeeNameById(savedEmployee.getId());
        assertEquals("Bob", name);
        
        // Test getAllEmployees
        List<Employee> allEmployees = employeeService.getAllEmployees();
        assertTrue(allEmployees.size() > 0);
    }

    @Test
    void testBidirectionalRelationship() {
        Department department = new Department("Marketing", "Floor 4");
        Department savedDepartment = departmentRepository.save(department);
        
        Employee employee1 = new Employee("Charlie", "Marketing Specialist", 65000.0, LocalDate.of(2023, 6, 15), savedDepartment);
        Employee employee2 = new Employee("Diana", "Marketing Manager", 85000.0, LocalDate.of(2022, 1, 10), savedDepartment);
        
        savedDepartment.addEmployee(employee1);
        savedDepartment.addEmployee(employee2);
        
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        
        departmentRepository.flush();
        
        // Retrieve department and check employees
        Department retrievedDepartment = departmentRepository.findById(savedDepartment.getId()).orElse(null);
        assertNotNull(retrievedDepartment);
        assertNotNull(retrievedDepartment.getEmployees());
        assertEquals(2, retrievedDepartment.getEmployees().size());
        
        // Check that employees belong to the department
        for (Employee emp : retrievedDepartment.getEmployees()) {
            assertEquals(retrievedDepartment.getId(), emp.getDepartment().getId());
        }
    }
}
