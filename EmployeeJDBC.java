import java.sql.*;

abstract class Employee {
    protected int id;
    protected String name;
    protected double salary;
    
    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
    
    abstract void displayInfo();
}

class Manager extends Employee {
    public Manager(int id, String name, double salary) {
        super(id, name, salary);
    }
    
    void displayInfo() {
        System.out.println("Manager - ID: " + id + ", Name: " + name + ", Salary: " + salary);
    }
}

class Developer extends Employee {
    public Developer(int id, String name, double salary) {
        super(id, name, salary);
    }
    
    void displayInfo() {
        System.out.println("Developer - ID: " + id + ", Name: " + name + ", Salary: " + salary);
    }
}

class EmployeeDAO {
    static Employee createEmployee(String type, int id, String name, double salary) {
        if (type.equals("Manager")) return new Manager(id, name, salary);
        if (type.equals("Developer")) return new Developer(id, name, salary);
        return null;
    }
    
    static void fetchEmployees() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "Adityaraj16@");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name, salary, type FROM employees");
            
            while (rs.next()) {
                Employee emp = createEmployee(rs.getString("type"), rs.getInt("id"), 
                                           rs.getString("name"), rs.getDouble("salary"));
                if (emp != null) emp.displayInfo();
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

public class EmployeeJDBC {
    public static void main(String[] args) {
        EmployeeDAO.fetchEmployees();
    }
}