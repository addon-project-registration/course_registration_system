package coursedao;

import course.db.DBConnection;
import course.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    // ✅ 1. Add new course (Admin use)
    public boolean addCourse(Course course) {
        String query = "INSERT INTO course (course_name, capacity) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, course.getCourseName());
            stmt.setInt(2, course.getCapacity());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error adding course: " + e.getMessage());
        }
        return false;
    }

    // ✅ 2. View all available courses
    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();
        String query = "SELECT * FROM course";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Course c = new Course(
                    rs.getInt("course_id"),
                    rs.getString("course_name"),
                    rs.getInt("capacity")
                );
                courseList.add(c);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching courses: " + e.getMessage());
        }
        return courseList;
    }

    // ✅ 3. Delete course (Admin use)
    public boolean deleteCourse(int courseId) {
        String query = "DELETE FROM course WHERE course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, courseId);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error deleting course: " + e.getMessage());
        }
        return false;
    }
}
