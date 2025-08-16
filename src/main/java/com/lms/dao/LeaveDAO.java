package com.lms.dao;

import com.lms.model.Leave;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class LeaveDAO {
	public boolean applyLeave(Leave leave) throws SQLException {
	    String sql = "INSERT INTO leave_applications (student_id, faculty_id, leave_type, from_date, to_date, reason, medical_doc) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?)";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, leave.getStudentId());
	        stmt.setInt(2, leave.getFacultyId());
	        stmt.setString(3, leave.getLeaveType());
	        stmt.setDate(4, leave.getFromDate());
	        stmt.setDate(5, leave.getToDate());
	        stmt.setString(6, leave.getReason());
	        if (leave.getMedicalDoc() != null) {
	            stmt.setBlob(7, leave.getMedicalDoc());
	        } else {
	            stmt.setNull(7, Types.BLOB);
	        }
	        return stmt.executeUpdate() > 0;
	    }
	}


    public List<Leave> getLeavesByStudentId(int studentId) throws SQLException {
        List<Leave> leaves = new ArrayList<>();
        String sql = "SELECT la.*, u.name as faculty_name " +
                     "FROM leave_applications la " +
                     "JOIN users u ON la.faculty_id = u.id " +
                     "WHERE la.student_id = ? " +
                     "ORDER BY la.applied_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Leave leave = new Leave();
                    leave.setId(rs.getInt("id"));
                    leave.setLeaveType(rs.getString("leave_type"));
                    leave.setFromDate(rs.getDate("from_date"));
                    leave.setToDate(rs.getDate("to_date"));
                    leave.setReason(rs.getString("reason"));
                    leave.setStatus(rs.getString("status"));
                    leave.setFacultyComments(rs.getString("faculty_comments"));
                    leave.setAppliedDate(rs.getDate("applied_date"));
                    leave.setReviewedDate(rs.getDate("reviewed_date"));
                    leave.setFacultyName(rs.getString("faculty_name"));
                    leave.setHasMedicalDoc(rs.getBlob("medical_doc") != null);

                    leaves.add(leave);
                }
            }
        }
        return leaves;
    }

    public List<Leave> getLeavesByFacultyId(int facultyId) throws SQLException {
        List<Leave> leaves = new ArrayList<>();
        String sql = "SELECT la.*, u.name AS student_name, u.roll_number, u.id AS student_id " +
                     "FROM leave_applications la " +
                     "JOIN users u ON la.student_id = u.id " +
                     "WHERE la.faculty_id = ? ORDER BY la.applied_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, facultyId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Leave leave = new Leave();
                    leave.setId(rs.getInt("id"));
                    leave.setStudentId(rs.getInt("student_id"));
                    leave.setFacultyId(rs.getInt("faculty_id"));
                    leave.setLeaveType(rs.getString("leave_type"));
                    leave.setFromDate(rs.getDate("from_date"));
                    leave.setToDate(rs.getDate("to_date"));
                    leave.setReason(rs.getString("reason"));
                    leave.setStatus(rs.getString("status"));
                    leave.setAppliedDate(rs.getDate("applied_date"));
                    leave.setFacultyComments(rs.getString("faculty_comments"));
                    leave.setHasMedicalDoc(rs.getBlob("medical_doc") != null);

                    leave.setStudentName(rs.getString("student_name"));
                    leave.setRollNumber(rs.getString("roll_number"));

                    leaves.add(leave);
                }
            }
        }
        return leaves;
    }

    public boolean updateLeaveStatus(int leaveId, String status, String comments, int facultyId) throws SQLException {
        String sql = "UPDATE leave_applications SET status = ?, faculty_comments = ?, reviewed_date = CURRENT_TIMESTAMP " +
                     "WHERE id = ? AND faculty_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, comments);
            stmt.setInt(3, leaveId);
            stmt.setInt(4, facultyId);
            return stmt.executeUpdate() > 0;
        }
    }

    public Leave getLeaveById(int leaveId) throws SQLException {
        String sql = "SELECT la.*, u.name as faculty_name, s.name as student_name, s.roll_number " +
                     "FROM leave_applications la " +
                     "JOIN users u ON la.faculty_id = u.id " +
                     "JOIN users s ON la.student_id = s.id " +
                     "WHERE la.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, leaveId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Leave leave = new Leave();
                    leave.setId(rs.getInt("id"));
                    leave.setStudentId(rs.getInt("student_id"));
                    leave.setFacultyId(rs.getInt("faculty_id"));
                    leave.setLeaveType(rs.getString("leave_type"));
                    leave.setFromDate(rs.getDate("from_date"));
                    leave.setToDate(rs.getDate("to_date"));
                    leave.setReason(rs.getString("reason"));
                    leave.setStatus(rs.getString("status"));
                    leave.setFacultyComments(rs.getString("faculty_comments"));
                    leave.setAppliedDate(rs.getDate("applied_date"));
                    leave.setReviewedDate(rs.getDate("reviewed_date"));
                    leave.setStudentName(rs.getString("student_name"));
                    leave.setFacultyName(rs.getString("faculty_name"));
                    leave.setRollNumber(rs.getString("roll_number"));
                    
                    return leave;
                }
            }
        }
        return null;
    }

    public int getLeaveCountByStatus(int studentId, String status) throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM leave_applications WHERE student_id = ? AND status = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setString(2, status);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        }
        return 0;
    }
    public InputStream getMedicalDocByLeaveId(int id) throws SQLException {
        String sql = "SELECT medical_doc FROM leave_applications WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBinaryStream("medical_doc");
            }
        }
        return null;
    }

}