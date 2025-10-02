# Employee Database Management System

A complete Java JDBC application for managing employee records with full CRUD operations.

## 📋 Features

- ✅ **Create**: Add new employees
- ✅ **Read**: View all employees or search by ID
- ✅ **Update**: Modify employee information
- ✅ **Delete**: Remove employees from database
- 🎨 Clean console interface
- 🔒 SQL injection protection with PreparedStatements
- ⚡ Connection management

## 🛠️ Prerequisites

### Required Software

1. **Java Development Kit (JDK) 8 or higher**
   ```bash
   java -version
   ```

2. **MySQL or PostgreSQL Database**
   - MySQL 5.7+ or PostgreSQL 12+

3. **JDBC Driver**
   - MySQL: Download from https://dev.mysql.com/downloads/connector/j/
   - PostgreSQL: Download from https://jdbc.postgresql.org/download/

4. **VS Code** (optional but recommended)
   - Extension: Extension Pack for Java

## 📦 Installation Steps

### Step 1: Set Up Database

#### For MySQL:
```bash
# Start MySQL service
# Windows: net start MySQL80
# Mac: brew services start mysql
# Linux: sudo systemctl start mysql

# Login to MySQL
mysql -u root -p

# Run the setup script
source setup.sql
```

#### For PostgreSQL:
```bash
# Start PostgreSQL service
# Windows: net start postgresql-x64-13
# Mac: brew services start postgresql
# Linux: sudo systemctl start postgresql

# Login to PostgreSQL
psql -U postgres

# Create database and run setup
\i setup.sql
```

### Step 2: Download JDBC Driver

#### MySQL:
1. Download MySQL Connector/J from https://dev.mysql.com/downloads/connector/j/
2. Extract `mysql-connector-java-x.x.xx.jar`

#### PostgreSQL:
1. Download from https://jdbc.postgresql.org/download/
2. Get `postgresql-x.x.x.jar`

### Step 3: Configure Database Connection

Edit `EmployeeDatabaseApp.java` and update these lines:

```java
// For MySQL
private static final String DB_URL = "jdbc:mysql://localhost:3306/employee_db";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";

// For PostgreSQL (if using PostgreSQL)
// private static final String DB_URL = "jdbc:postgresql://localhost:5432/employee_db";
// private static final String DB_USER = "postgres";
// private static final String DB_PASSWORD = "your_password";
```

### Step 4: Compile and Run

#### Using Command Line:

**MySQL:**
```bash
# Compile (include JDBC driver in classpath)
javac -cp ".;mysql-connector-java-8.x.xx.jar" EmployeeDatabaseApp.java

# Run
java -cp ".;mysql-connector-java-8.x.xx.jar" EmployeeDatabaseApp
```

**PostgreSQL:**
```bash
# Compile
javac -cp ".;postgresql-42.x.x.jar" EmployeeDatabaseApp.java

# Run
java -cp ".;postgresql-42.x.x.jar" EmployeeDatabaseApp
```

> **Note for Mac/Linux:** Use `:` instead of `;` in classpath
> ```bash
> javac -cp ".:mysql-connector-java-8.x.xx.jar" EmployeeDatabaseApp.java
> java -cp ".:mysql-connector-java-8.x.xx.jar" EmployeeDatabaseApp
> ```

#### Using VS Code:

1. Open the project folder in VS Code
2. Create a `lib` folder and place the JDBC driver JAR inside
3. Update `.vscode/settings.json`:
```json
{
    "java.project.referencedLibraries": [
        "lib/**/*.jar"
    ]
}
```
4. Press F5 or use "Run Java" button

## 🎮 Usage Guide

### Main Menu Options:

```
╔════════════════════════════════════╗
║   EMPLOYEE MANAGEMENT SYSTEM       ║
╚════════════════════════════════════╝
1. Add New Employee
2. View All Employees
3. Search Employee by ID
4. Update Employee
5. Delete Employee
6. Exit
```

### Example Operations:

#### 1. Add New Employee
```
Enter Name: Sarah Johnson
Enter Email: sarah.j@company.com
Enter Department: Finance
Enter Salary: 72000
✓ Employee added successfully!
```

#### 2. View All Employees
```
─────────────────────────────────────────────────────────────────────
ID    Name                 Email                     Department      Salary
─────────────────────────────────────────────────────────────────────
1     John Doe             john.doe@company.com      Engineering     $75000.00
2     Jane Smith           jane.smith@company.com    Marketing       $65000.00
...
```

#### 3. Search Employee
```
Enter Employee ID: 1

Employee Details:
ID: 1
Name: John Doe
Email: john.doe@company.com
Department: Engineering
Salary: $75000.00
```

#### 4. Update Employee
```
Enter Employee ID to update: 1

What would you like to update?
1. Name
2. Email
3. Department
4. Salary
5. All fields
Choice: 4
Enter new Salary: 80000
✓ Employee updated successfully!
```

#### 5. Delete Employee
```
Enter Employee ID to delete: 3
Are you sure? (yes/no): yes
✓ Employee deleted successfully!
```

## 🔧 Troubleshooting

### Common Issues:

#### 1. "ClassNotFoundException: com.mysql.cj.jdbc.Driver"
**Solution:** JDBC driver not in classpath
```bash
# Make sure to include the JAR in classpath when compiling and running
javac -cp ".;mysql-connector-java-8.x.xx.jar" EmployeeDatabaseApp.java
```

#### 2. "Access denied for user"
**Solution:** Check database credentials
```java
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_actual_password";
```

#### 3. "Communications link failure"
**Solution:** Database server not running
```bash
# MySQL
net start MySQL80  # Windows
brew services start mysql  # Mac

# PostgreSQL
net start postgresql-x64-13  # Windows
brew services start postgresql  # Mac
```

#### 4. "Unknown database 'employee_db'"
**Solution:** Run the setup SQL script
```bash
mysql -u root -p < setup.sql
```

## 📚 Key JDBC Concepts Used

### 1. Connection Management
```java
Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
```

### 2. PreparedStatement (prevents SQL injection)
```java
String sql = "INSERT INTO employees (name, email) VALUES (?, ?)";
PreparedStatement pstmt = connection.prepareStatement(sql);
pstmt.setString(1, name);
pstmt.setString(2, email);
```

### 3. ResultSet (retrieve data)
```java
ResultSet rs = stmt.executeQuery(sql);
while (rs.next()) {
    int id = rs.getInt("id");
    String name = rs.getString("name");
}
```

## 🎯 Learning Points

- ✅ Database connectivity with JDBC
- ✅ CRUD operations implementation
- ✅ PreparedStatement for security
- ✅ ResultSet navigation
- ✅ Exception handling
- ✅ Resource management (closing connections)
- ✅ User input validation

## 🚀 Enhancements You Can Add

1. **Password encryption** for sensitive data
2. **Pagination** for large datasets
3. **Export to CSV/Excel** functionality
4. **Advanced search** (by name, department, salary range)
5. **Connection pooling** for better performance
6. **Logging** with Log4j or SLF4J
7. **GUI interface** with JavaFX or Swing
8. **Unit tests** with JUnit

## 📖 Additional Resources

- [JDBC Tutorial](https://docs.oracle.com/javase/tutorial/jdbc/)
- [MySQL Connector/J Documentation](https://dev.mysql.com/doc/connector-j/en/)
- [PostgreSQL JDBC Documentation](https://jdbc.postgresql.org/documentation/)

---

**Happy Coding! 🎉**
