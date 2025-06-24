package course.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/course_registration_system";
    private static final String USER = "root"; // 🔁 Replace with your MySQL username
    private static final String PASSWORD = "protectmyacnt"; // 🔁 Replace with your MySQL password

    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found.");
            e.printStackTrace();
        }

        // Establish connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
