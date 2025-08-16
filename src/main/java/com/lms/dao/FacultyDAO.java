package com.lms.dao;

import com.lms.model.Faculty;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacultyDAO {
    public List<Faculty> getAllFacultiesWithDetails() throws SQLException {
        List<Faculty> faculties = new ArrayList<>();
        String sql = "SELECT u.id, u.name, u.email, u.department, f.designation " +
                     "FROM users u LEFT JOIN faculties f ON u.id = f.user_id " +
                     "WHERE u.user_type = 'faculty'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Faculty faculty = new Faculty();
                faculty.setUserId(rs.getInt("id"));
                faculty.setDepartment(rs.getString("department"));
                faculty.setDesignation(rs.getString("designation"));
                faculties.add(faculty);
            }
        }
        return faculties;
    }

    public boolean addFacultyDetails(Faculty faculty) throws SQLException {
        String sql = "INSERT INTO faculties (user_id, designation, department) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, faculty.getUserId());
            stmt.setString(2, faculty.getDesignation());
            stmt.setString(3, faculty.getDepartment());
            return stmt.executeUpdate() > 0;
        }
    }
}