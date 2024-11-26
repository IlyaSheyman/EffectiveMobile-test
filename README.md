# **Task Management Service**
### This is a task management service that allows administrators to create, update, and assign tasks while users can view and update their assigned tasks. The service supports features such as role-based access control, task filtering, and comment management. It includes secure authentication and detailed API documentation for seamless integration.

## 👋Features
1.	**Task Management:**
Administrators can create, update, and delete tasks, assign them to users, and set priorities and statuses.
2.	**User-Specific Tasks:**
Users can view tasks assigned to them, filter tasks by status or priority, and update task statuses as needed.
3.	**Role-Based Access Control:**
Leverages role-based permissions to ensure only administrators can manage tasks, while regular users can only interact with their own tasks.
4.	**Comments on Tasks:**
Add, view, and manage comments on tasks, allowing users to collaborate and track progress.
5.	**Search and Filtering:**
Retrieve tasks created by administrators with filters for status, priority, assignee, and more, along with pagination and sorting.
6.	**Authentication and Authorization:**
Secure all endpoints using JWT-based authentication, ensuring user-specific access to resources.
7.	**Admin Privileges:**
Grant ADMIN roles to authorized users through a secure endpoint, enabling full task management capabilities.
8.	**Detailed API Documentation:**
Interactive Swagger UI documentation for all endpoints, enabling easy testing and integration.

## 🔧Technologies Used:
1. _Java 17_
2. _Spring Boot 3_
3. _PostgreSQL_
4. _Maven_
5. _JWT for authentication_
6. _Swagger and Javadoc for API documentation_
7. _Lombok for reducing boilerplate code_
8. _Docker for containerization_
9. _Flyway for db migrations (with test data)_
10. _JUnit and Mockito for testing_

## 📄Configuration:
1. Swagger: http://localhost:8080/swagger-ui.html

## 🚀Setup
### You can use app with [Postman requests collection](postman_requests)
### 1. Install Docker & Maven
- [Download Docker Desktop](https://www.docker.com/products/docker-desktop/) and follow the installation instructions.
- Download Maven from [Apache Maven official website](https://maven.apache.org/index.html)
### 2. Running with Docker Compose
1. Clone the repository to your computer:
- `git clone <https://github.com/IlyaSheyman/EffectiveMobile-test.git>`

2. Go to the project root directory:
- `cd EffectiveMobile-test`

3. Run Maven package
- `mvn clean package`

4. Run Docker Compose:
- `docker-compose up --build`

5. After successful launch, you will be able to access your application at:
- http://localhost:8080 for Main Service
