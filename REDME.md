🐞 Issue Tracker Backend (Spring Boot)

A role-based Issue Tracking System backend built using Spring Boot, designed to manage issues, teams, 
comments, files, and users efficiently. This project follows clean architecture, RESTful APIs, and 
JWT-based security, and is fully documented with Swagger (OpenAPI).



🚀 Tech Stack Used

* Java 21
* Spring Boot
* Spring Security (JWT Authentication)
* Spring Data JPA (Hibernate)
* PostgreSQL
* Swagger / OpenAPI 
* Lombok
* Maven



🔐 Security & Authentication

* JWT-based authentication

* Role-based authorization

* Supported roles:

    * `ADMIN`
    * `MANAGER`
    * `DEVELOPER`

* Method-level security using `@PreAuthorize`

* Bearer token required for secured APIs



📦 Core Modules & Functionalities

 👤 User Management

* User registration & login
* Role-based access control
* Fetch users (admin only)


🐞 Issue Management

# Features:

* Create a new issue
* Update issue details
* Delete issue (Admin / Manager)
* Filter issues by status, priority, assignee, etc.
* Assign issues to users

# Key APIs:

* `POST /api/issues` – Create issue
* `PUT /api/issues/{id}` – Update issue
* `DELETE /api/issues/{id}` – Delete issue
* `POST /api/issues/filter` – Filter issues
* `PUT /api/issues/{issueId}/assign/{userId}` – Assign issue



💬 Comment Management

# Features:

* Add comment to an issue
* View comments by issue
* Update own comment only
* Delete own comment only

# Security Rules:

* Only comment owner can update/delete
* Admin, Manager, Developer roles supported

# Key APIs:

* `POST /api/comments` – Add comment
* `GET /api/comments/issue/{issueId}` – Get comments
* `PUT /api/comments/{commentId}` – Update comment
* `DELETE /api/comments/{commentId}` – Delete comment



👥 Team Management

# Features:

* Create team
* Update team
* Add member to team
* Remove member from team

# Key APIs:

* `POST /api/teams` – Create team
* `PUT /api/teams/{teamId}` – Update team
* `POST /api/teams/{teamId}/members` – Add member
* `DELETE /api/teams/{teamId}/members/{userId}` – Remove member



📎 File Management

# Features:

* Upload files linked to issues
* Store metadata in database

# Key APIs:

* `POST /api/files/upload/{issueId}` – Upload file



🧾 API Response Structure

All APIs follow a **standard response format**:

```json
{
  "success": true,
  "message": "User updated",
  "data": {
    "id": 6,
    "username": "DEV",
    "name": "DEV101",
    "email": "dev@gmail.com",
    "password": "$2a$10$b5p.ZwLCMR22W6yjm/AK1uz7IFM.AySEIscxF7Xkkp0HoTthCIaBu",
    "enabled": true,
    "role": "DEVELOPER"
  },
  "timestamp": "2026-02-05T17:30:45.2189068"
}
```


📄 Swagger Documentation

Swagger UI is enabled for easy API testing and documentation.

📍 Swagger URL:


http://localhost:9092/swagger-ui/index.html


📍 OpenAPI Docs:


http://localhost:9092/v3/api-docs




🗄 Database Configuration (PostgreSQL)

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/issuetracker
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```



 🧪 API Testing

* APIs tested using Swagger UI
* JWT token passed via Authorization header:

```
Authorization: Bearer <JWT_TOKEN>
```



🧠 Project Highlights

* Clean layered architecture (Controller → Service → Repository)
* DTO-based request & response handling
* Custom exception handling
* Secure role-based access
* Swagger-integrated documentation
* Easily extendable for future features



🔮 Future Enhancements

* Issue status workflow (OPEN → IN_PROGRESS → DONE)
* Notifications (email / in-app)
* Comment attachments
* Pagination & sorting
* Soft delete support



🧪 How to Run the Project:

Clone the repository
git clone : 



👩‍💻 Author

Chetana Mahant
Backend Developer | Java | Spring Boot

---

This project demonstrates real-world backend development practices using Spring Boot and modern security standards.

