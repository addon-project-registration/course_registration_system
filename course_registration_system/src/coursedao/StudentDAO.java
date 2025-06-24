package coursedao;

import course.db.DBConnection;
import course.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // ✅ LOGIN Method
    public int login(String email, String password) {
        String query = "SELECT student_id FROM student WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("student_id"); // ✅ Login success
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // ❌ Login failed
    }
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        String query = "SELECT * FROM student";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Student s = new Student(
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password")
                );
                studentList.add(s);
            }

        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch students: " + e.getMessage());
        }

        return studentList;
    }


    // ✅ REGISTER Method
    public boolean register(Student student) {
        String query = "INSERT INTO student (name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getPassword());

            int rows = stmt.executeUpdate();

            return rows > 0; // ✅ Registration success

        } catch (SQLException e) {
            System.out.println("❌ Registration failed: " + e.getMessage());
        }
        

        return false;
    }
}
