# Hibernate_Entity_Creation_Task

This task will consist of 2 parts.

## Setting up the connection:

Your task is to **create the necessary database in MySQL** using regular SQL queries. The project expects tables named `departments` and `employees`.

Then, you need to **connect your database to the project**. The database configuration is in `src/main/resources/application.properties` (not hibernate.cfg.xml). You need to update the following properties:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/testDatabase
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

I recommend using the database name `testDatabase` for testing your solution. The application uses Hibernate's `update` mode, which will create the tables automatically on startup.

You need to fill in the `username` (typically `root`) and your MySQL `password`.

*Don't worry, I won't see your password :).*

## Creating the entity class:

The `Department` and `Employee` classes are already configured as entity classes with all necessary JPA annotations. The relationship between them is bidirectional (OneToMany from Department to Employee, ManyToOne from Employee to Department).

You can use **Project Lombok** library to reduce boilerplate, or manually write getters, setters, and constructors as currently done.

After you complete the setup, **run the integration tests** with `mvn test`, and they will verify the correctness of your configuration.

## Running the application:

To run the demo with sample data, execute `mvn spring-boot:run`. The DemoRunner will populate the database with departments and employees.