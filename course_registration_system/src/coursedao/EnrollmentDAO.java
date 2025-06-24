package coursedao;



import course.db.DBConnection;
import java.sql.*;

public class EnrollmentDAO {

    // ✅ 1. Enroll in a course
    public boolean enroll(int studentId, int courseId) {
        String checkQuery = "SELECT COUNT(*) FROM enrollment WHERE student_id = ? AND course_id = ?";
        String insertQuery = "INSERT INTO enrollment (student_id, course_id, status) VALUES (?, ?, 'enrolled')";

        try (Connection conn = DBConnection.getConnection()) {

            // Check if already enrolled
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, studentId);
            checkStmt.setInt(2, courseId);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            if (count > 0) {
                System.out.println("⚠️ You are already enrolled in this course.");
                return false;
            }

            // Enroll
            PreparedStatement stmt = conn.prepareStatement(insertQuery);
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Enrollment error: " + e.getMessage());
        }

        return false;
    }
 // 🔍 View enrolled courses for a given student (by Admin)
    public void viewStudentEnrollments(int studentId) {
        String query = """
            SELECT c.course_id, c.course_name
            FROM enrollment e
            JOIN course c ON e.course_id = c.course_id
            WHERE e.student_id = ? AND e.status = 'enrolled'
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("📘 Enrolled Courses:");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("→ " + rs.getInt("course_id") + " - " + rs.getString("course_name"));
            }

            if (!found) {
                System.out.println("⚠️ No courses enrolled by this student.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch enrollments: " + e.getMessage());
        }
    }

    // ✅ 2. Drop a course
    public boolean drop(int studentId, int courseId) {
        String query = "DELETE FROM enrollment WHERE student_id = ? AND course_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Drop error: " + e.getMessage());
        }

        return false;
    }

    // ✅ 3. View enrolled courses
    public void viewEnrolledCourses(int studentId) {
        String query = """
            SELECT c.course_id, c.course_name
            FROM enrollment e
            JOIN course c ON e.course_id = c.course_id
            WHERE e.student_id = ? AND e.status = 'enrolled'
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("📚 Your Enrolled Courses:");
            boolean hasCourses = false;
            while (rs.next()) {
                hasCourses = true;
                System.out.println(rs.getInt("course_id") + " - " + rs.getString("course_name"));
            }

            if (!hasCourses) {
                System.out.println("⚠️ You are not enrolled in any courses.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error showing courses: " + e.getMessage());
        }
    }
}
