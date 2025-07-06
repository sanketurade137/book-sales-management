# 📚 Book Sales Management System

A full-stack Spring Boot application that allows admins to manage books and users to view, search, and purchase books. The application uses an H2 database, follows the MVC pattern, and features session-based and role-based login functionality for Admin and User access.

---

## 📌 Features

### 👨‍💼 Admin Can:
- Add, edit, and delete books
- View all books
- View purchase records from all users
- Access dashboard stats (total books & purchases)

### 👤 User Can:
- Register and log in
- View and search books
- Buy books
- View their purchase history

### 🔐 Additional Highlights:
- Session-based login/logout with role checks
- H2 file-based database for easy local testing
- Confirmation modals for Delete, Buy, and Logout actions
- Input validations to restrict invalid data (e.g., title can’t be numeric)

---

## 🧑‍💻 Tech Stack Used

| Layer           | Technology                    |
|----------------|-------------------------------|
| Frontend        | Thymeleaf, HTML5, Bootstrap 5 |
| Backend         | Java, Spring Boot             |
| Database        | H2 (file-based)               |
| Build Tool      | Maven                         |
| API Protocol    | REST                          |
| IDE             | IntelliJ IDEA                 |
| Version Control | Git, GitHub                   |

---

## 📂 Project Structure

```plaintext
book-sales-management/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── accenture/
│       │           └── booksales/
│       │               ├── controller/
│       │               │   ├── AuthController.java
│       │               │   ├── BookController.java
│       │               │   └── BookViewController.java
│       │               ├── model/
│       │               │   ├── AppUser.java
│       │               │   ├── Book.java
│       │               │   └── Purchase.java
│       │               ├── repository/
│       │               │   ├── AppUserRepository.java
│       │               │   ├── BookRepository.java
│       │               │   └── PurchaseRepository.java
│       │               └── BookSalesManagementApplication.java
│       └── resources/
│           ├── static/
│           ├── templates/
│           │   ├── admin-dashboard.html
│           │   ├── book-form.html
│           │   ├── book-list.html
│           │   ├── buy-success.html
│           │   ├── login.html
│           │   ├── purchase-history.html
│           │   ├── purchase-list.html
│           │   ├── register.html
│           └── application.properties
├── pom.xml
└── README.md

```

## 🧪 API Endpoints

| Method | Endpoint                     | Description                  |
| ------ | ---------------------------- | ---------------------------- |
| GET    | `/api/books`                 | Get list of all books        |
| POST   | `/api/books`                 | Add a new book               |
| GET    | `/api/books/search?title=`   | Search books by title        |
| DELETE | `/api/books/{id}`            | Delete a book                |
| GET    | `/books/buy/{id}`            | User buys a book             |
| GET    | `/books/purchases`           | View purchase history (user) |
| GET    | `/admin/purchases`           | View all purchases (admin)   |
| POST   | `/login`, `/register`        | Login or register user       |
| GET    | `/books/view`, `/books/user` | View books (admin/user view) |



---

## ⚙️ Setup & Installation

### ✅ Prerequisites:
- Java 17+
- Maven
- IntelliJ IDEA or VS Code

### 🚀 Steps:

1. Clone the repository:
   https://github.com/sanketurade137/book-sales-management.git

2. Open the project in IntelliJ IDEA or VS Code
Wait for Maven to automatically download the required dependencies.

3. Configure the database in application.properties
Located at: src/main/resources/application.properties

```spring.datasource.url=jdbc:h2:file:./data/bookdb
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

4. Run the application: 

``` 
./mvnw spring-boot:run
``` 

5. Access via:

http://localhost:8080/login

http://localhost:8080/h2-console (for DB access)


## ✅ Testing
✔️ Manual testing of all user and admin flows (login, book add/edit/delete, and purchase).

✔️ Form validation tested via browser form submissions.

✔️ Verified H2 database using both UI and H2 console.

✔️ API endpoints (GET, POST, DELETE) tested using Postman.

## 🚀 Future Enhancements

🔐 Add email verification during registration.

📄 Enable invoice/PDF generation after book purchase.

📊 Export purchase history as CSV or Excel file.

🔒 Implement password encryption using Spring Security.

📚 Add pagination and sorting on the book list page.

☁️ Deploy project on a cloud platform (e.g., Render or Heroku).


## 🙋 Contributor
Sanket Urade – PADA Intern at Accenture (Solo project)

## 📃 License

This project was developed as part of an internship project at Accenture. It is intended for educational and reference purposes only.