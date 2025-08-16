package com.lms.dao;

import com.lms.model.User;

import ch.qos.logback.core.db.dialect.DBUtil;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public User authenticate(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setEmail(rs.getString("email"));
                    user.setName(rs.getString("name"));
                    user.setUserType(rs.getString("user_type"));
                    user.setRollNumber(rs.getString("roll_number"));
                    user.setDepartment(rs.getString("department"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                    return user;
                }
            }
        }
        return null;
    }
    public boolean emailExists(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    public boolean registerStudentWithPic(User user) throws SQLException {
        String sql = "INSERT INTO users (email, password, name, user_type, roll_number, department, phone, address, profile_pic) " +
                     "VALUES (?, ?, ?, 'student', ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getRollNumber());
            stmt.setString(5, user.getDepartment());
            stmt.setString(6, user.getPhone());
            stmt.setString(7, user.getAddress());

            if (user.getProfilePic() != null) {
                stmt.setBlob(8, user.getProfilePic());
            } else {
                stmt.setNull(8, Types.BLOB);
            }

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean registerStudent(User user) {
        String sql = "INSERT INTO users (email, password, name, phone, user_type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getUserType());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProfile(User user) throws SQLException {
        String sql = "UPDATE users SET name=?, phone=?, address=?, profile_pic=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPhone());
            stmt.setString(3, user.getAddress());

            if (user.getProfilePic() != null) {
                stmt.setBlob(4, user.getProfilePic());
            } else {
                stmt.setNull(4, Types.BLOB);
            }

            stmt.setInt(5, user.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public InputStream getProfilePicById(int userId) throws SQLException {
        String sql = "SELECT profile_pic FROM users WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBinaryStream("profile_pic");
            }
        }
        return null;
    }

    public List<User> getAllFaculties() throws SQLException {
        List<User> faculties = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE user_type = 'faculty'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setDepartment(rs.getString("department"));
                faculties.add(user);
            }
        }
        return faculties;
    }

    public boolean addFaculty(User user) throws SQLException {
        String sql = "INSERT INTO users (email, password, name, user_type, department) " +
                     "VALUES (?, ?, ?, 'faculty', ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getDepartment());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteFaculty(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ? AND user_type = 'faculty'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public User getFacultyById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ? AND user_type = 'faculty'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setEmail(rs.getString("email"));
                    user.setName(rs.getString("name"));
                    user.setDepartment(rs.getString("department"));
                    return user;
                }
            }
        }
        return null;
    }

    public boolean updateFaculty(User user) throws SQLException {
        String sql = "UPDATE users SET email = ?, name = ?, department = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getDepartment());
            stmt.setInt(4, user.getId());
            return stmt.executeUpdate() > 0;
        }
    }
}