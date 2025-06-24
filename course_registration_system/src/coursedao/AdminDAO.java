package coursedao;  // ✅ Correct package

import course.db.DBConnection;
import course.model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    // ✅ Admin Login Method
    public int login(String username, String password) throws Exception {
        String query = "SELECT admin_id FROM admin WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("admin_id"); // ✅ Login success
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // ❌ Login failed
    }
}
