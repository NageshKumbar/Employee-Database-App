import java.sql.*;
import java.util.Scanner;

public class EmployeeDatabaseApp {
    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employee_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Hema@342434";
    
    private Connection connection;
    private Scanner scanner;
    
    public EmployeeDatabaseApp() {
        scanner = new Scanner(System.in);
        connectToDatabase();
    }
    
    // Establish database connection
    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("✓ Connected to database successfully!\n");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
        }
    }
    
    // CREATE - Add new employee
    public void addEmployee() {
        try {
            System.out.println("\n=== Add New Employee ===");
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            
            System.out.print("Enter Department: ");
            String department = scanner.nextLine();
            
            System.out.print("Enter Salary: ");
            double salary = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            
            String sql = "INSERT INTO employees (name, email, department, salary) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, department);
            pstmt.setDouble(4, salary);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✓ Employee added successfully!");
            }
            
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
        }
    }
    
    // READ - View all employees
    public void viewAllEmployees() {
        try {
            System.out.println("\n=== Employee List ===");
            String sql = "SELECT * FROM employees";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            System.out.println("─────────────────────────────────────────────────────────────────────");
            System.out.printf("%-5s %-20s %-25s %-15s %-10s%n", 
                "ID", "Name", "Email", "Department", "Salary");
            System.out.println("─────────────────────────────────────────────────────────────────────");
            
            boolean hasRecords = false;
            while (rs.next()) {
                hasRecords = true;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String department = rs.getString("department");
                double salary = rs.getDouble("salary");
                
                System.out.printf("%-5d %-20s %-25s %-15s $%-9.2f%n", 
                    id, name, email, department, salary);
            }
            
            if (!hasRecords) {
                System.out.println("No employees found in database.");
            }
            
            System.out.println("─────────────────────────────────────────────────────────────────────");
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error viewing employees: " + e.getMessage());
        }
    }
    
    // READ - Search employee by ID
    public void searchEmployee() {
        try {
            System.out.println("\n=== Search Employee ===");
            System.out.print("Enter Employee ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            String sql = "SELECT * FROM employees WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                System.out.println("\nEmployee Details:");
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Department: " + rs.getString("department"));
                System.out.println("Salary: $" + rs.getDouble("salary"));
            } else {
                System.out.println("✗ Employee not found with ID: " + id);
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Error searching employee: " + e.getMessage());
        }
    }
    
    // UPDATE - Update employee information
    public void updateEmployee() {
        try {
            System.out.println("\n=== Update Employee ===");
            System.out.print("Enter Employee ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            // Check if employee exists
            String checkSql = "SELECT * FROM employees WHERE id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            
            if (!rs.next()) {
                System.out.println("✗ Employee not found with ID: " + id);
                rs.close();
                checkStmt.close();
                return;
            }
            
            rs.close();
            checkStmt.close();
            
            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Name");
            System.out.println("2. Email");
            System.out.println("3. Department");
            System.out.println("4. Salary");
            System.out.println("5. All fields");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            String sql = "";
            PreparedStatement pstmt = null;
            
            switch (choice) {
                case 1:
                    System.out.print("Enter new Name: ");
                    String name = scanner.nextLine();
                    sql = "UPDATE employees SET name = ? WHERE id = ?";
                    pstmt = connection.prepareStatement(sql);
                    pstmt.setString(1, name);
                    pstmt.setInt(2, id);
                    break;
                    
                case 2:
                    System.out.print("Enter new Email: ");
                    String email = scanner.nextLine();
                    sql = "UPDATE employees SET email = ? WHERE id = ?";
                    pstmt = connection.prepareStatement(sql);
                    pstmt.setString(1, email);
                    pstmt.setInt(2, id);
                    break;
                    
                case 3:
                    System.out.print("Enter new Department: ");
                    String department = scanner.nextLine();
                    sql = "UPDATE employees SET department = ? WHERE id = ?";
                    pstmt = connection.prepareStatement(sql);
                    pstmt.setString(1, department);
                    pstmt.setInt(2, id);
                    break;
                    
                case 4:
                    System.out.print("Enter new Salary: ");
                    double salary = scanner.nextDouble();
                    scanner.nextLine();
                    sql = "UPDATE employees SET salary = ? WHERE id = ?";
                    pstmt = connection.prepareStatement(sql);
                    pstmt.setDouble(1, salary);
                    pstmt.setInt(2, id);
                    break;
                    
                case 5:
                    System.out.print("Enter new Name: ");
                    name = scanner.nextLine();
                    System.out.print("Enter new Email: ");
                    email = scanner.nextLine();
                    System.out.print("Enter new Department: ");
                    department = scanner.nextLine();
                    System.out.print("Enter new Salary: ");
                    salary = scanner.nextDouble();
                    scanner.nextLine();
                    
                    sql = "UPDATE employees SET name = ?, email = ?, department = ?, salary = ? WHERE id = ?";
                    pstmt = connection.prepareStatement(sql);
                    pstmt.setString(1, name);
                    pstmt.setString(2, email);
                    pstmt.setString(3, department);
                    pstmt.setDouble(4, salary);
                    pstmt.setInt(5, id);
                    break;
                    
                default:
                    System.out.println("Invalid choice!");
                    return;
            }
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✓ Employee updated successfully!");
            }
            
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
        }
    }
    
    // DELETE - Delete employee
    public void deleteEmployee() {
        try {
            System.out.println("\n=== Delete Employee ===");
            System.out.print("Enter Employee ID to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            System.out.print("Are you sure? (yes/no): ");
            String confirm = scanner.nextLine();
            
            if (!confirm.equalsIgnoreCase("yes")) {
                System.out.println("Delete operation cancelled.");
                return;
            }
            
            String sql = "DELETE FROM employees WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✓ Employee deleted successfully!");
            } else {
                System.out.println("✗ Employee not found with ID: " + id);
            }
            
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
        }
    }
    
    // Display menu
    public void showMenu() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║   EMPLOYEE MANAGEMENT SYSTEM       ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println("1. Add New Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. Search Employee by ID");
        System.out.println("4. Update Employee");
        System.out.println("5. Delete Employee");
        System.out.println("6. Exit");
        System.out.print("\nEnter your choice: ");
    }
    
    // Run application
    public void run() {
        boolean running = true;
        
        while (running) {
            showMenu();
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                switch (choice) {
                    case 1:
                        addEmployee();
                        break;
                    case 2:
                        viewAllEmployees();
                        break;
                    case 3:
                        searchEmployee();
                        break;
                    case 4:
                        updateEmployee();
                        break;
                    case 5:
                        deleteEmployee();
                        break;
                    case 6:
                        running = false;
                        System.out.println("\nThank you for using Employee Management System!");
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // clear buffer
            }
        }
        
        closeConnection();
    }
    
    // Close database connection
    private void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
            scanner.close();
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        EmployeeDatabaseApp app = new EmployeeDatabaseApp();
        app.run();
    }
}