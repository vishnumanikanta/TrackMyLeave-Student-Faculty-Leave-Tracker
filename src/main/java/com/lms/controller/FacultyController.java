package com.lms.controller;

import com.lms.dao.LeaveDAO;
import com.lms.model.Leave;
import com.lms.model.User;
import com.lms.util.AuthUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "FacultyController", urlPatterns = {
    "/faculty/dashboard",
    "/faculty/leave/update-status"
})
public class FacultyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtil.isFaculty(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();
        try {
            if ("/faculty/dashboard".equals(path)) {
                showDashboard(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtil.isFaculty(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();
        if ("/faculty/leave/update-status".equals(path)) {
            try {
                updateLeaveStatus(request, response);
            } catch (SQLException e) {
                throw new ServletException("Database error occurred", e);
            }
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        User user = AuthUtil.getCurrentUser(request);
        LeaveDAO leaveDAO = new LeaveDAO();
        
        List<Leave> allLeaves = leaveDAO.getLeavesByFacultyId(user.getId());
        List<Leave> pendingLeaves = allLeaves.stream()
                .filter(leave -> "pending".equals(leave.getStatus()))
                .toList();
        List<Leave> approvedLeaves = allLeaves.stream()
                .filter(leave -> "approved".equals(leave.getStatus()))
                .toList();
        List<Leave> rejectedLeaves = allLeaves.stream()
                .filter(leave -> "rejected".equals(leave.getStatus()))
                .toList();
        
        request.setAttribute("totalLeaves", allLeaves.size());
        request.setAttribute("pendingLeaves", pendingLeaves.size());
        request.setAttribute("approvedLeaves", approvedLeaves.size());
        request.setAttribute("rejectedLeaves", rejectedLeaves.size());
        request.setAttribute("leaves", allLeaves);
        
        request.getRequestDispatcher("/WEB-INF/views/faculty/dashboard.jsp").forward(request, response);
    }

    private void updateLeaveStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int leaveId = Integer.parseInt(request.getParameter("leaveId"));
        String status = request.getParameter("status");
        String comments = request.getParameter("comments");
        User user = AuthUtil.getCurrentUser(request);
        
        LeaveDAO leaveDAO = new LeaveDAO();
        if (leaveDAO.updateLeaveStatus(leaveId, status, comments, user.getId())) {
            request.setAttribute("success", "Leave status updated successfully!");
        } else {
            request.setAttribute("error", "Failed to update leave status. Please try again.");
        }
        
        showDashboard(request, response);
    }
}