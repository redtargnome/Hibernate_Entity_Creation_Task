package codefinity.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ApiInfoController {

    @GetMapping
    public String getApiInfo() {
        return ""
                + "<html>"
                + "<head><title>Hibernate Entity Creation Task API</title></head>"
                + "<body>"
                + "<h1>Hibernate Entity Creation Task API</h1>"
                + "<h2>Available Endpoints:</h2>"
                + "<ul>"
                + "<li><strong>Employees:</strong>"
                + "<ul>"
                + "<li>GET <a href='/api/employees'>/api/employees</a> - List all employees</li>"
                + "<li>GET /api/employees/{id} - Get employee by ID</li>"
                + "<li>GET /api/employees/{id}/name - Get employee name by ID</li>"
                + "<li>GET /api/employees/department/{departmentId} - Get employees by department</li>"
                + "<li>POST /api/employees - Create new employee</li>"
                + "<li>PUT /api/employees/{id} - Update employee</li>"
                + "<li>DELETE /api/employees/{id} - Delete employee</li>"
                + "</ul>"
                + "<li><strong>Departments:</strong>"
                + "<ul>"
                + "<li>GET <a href='/api/departments'>/api/departments</a> - List all departments</li>"
                + "<li>GET /api/departments/{id} - Get department by ID</li>"
                + "<li>GET /api/departments/name/{name} - Get department by name</li>"
                + "<li>POST /api/departments - Create new department</li>"
                + "<li>PUT /api/departments/{id} - Update department</li>"
                + "<li>DELETE /api/departments/{id} - Delete department</li>"
                + "</ul>"
                + "</ul>"
                + "</body>"
                + "</html>";
    }
}
