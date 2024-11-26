# **Banking Operations Service**
### This is a simple banking service that allows users to manage their accounts, transfer money, and accrue interest on their balance. The service supports basic operations such as user creation, updating contact information, and searching for users. It also includes scheduled tasks to update balances periodically.

## 👋Features
1. **User Management:**
   Create new users with unique login, phone, and email. Update and delete contact information.
2. **Account Management:**
   Each user has a single bank account with an initial balance. The balance is updated periodically with interest.
3. **Money Transfer:**
   Transfer money from the authenticated user's account to another user's account.
4. **Search API:**
   Search users by birth date, phone, name, or email with filtering, pagination, and sorting.
5. **Authentication:**
   Secure endpoints with JWT-based authentication.
6. **Scheduled Tasks:**
   Increase account balances by 5% every minute, capped at 207% of the initial deposit.

## 🔧Technologies Used:
1. _Java 17_
2. _Spring Boot 3_
3. _PostgreSQL_
4. _Maven_
5. _JWT for authentication_
6. _Swagger/OpenAPI for API documentation_
7. _Lombok for reducing boilerplate code_
8. _Docker for containerization_
9. _Passay for password validation_
10. _Flyway for db migrations (with test data)_
11. _JUnit and Mockito for testing_

## 📄Configuration:
1. [Swagger](https://editor.swagger.io/): http://localhost:8080/v3/api-docs

## 🚀Setup
### You can use app with [Postman requests collection](postman_requests.json)
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

swagger: http://localhost:8080/swagger-ui.html