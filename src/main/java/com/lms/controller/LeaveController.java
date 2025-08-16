package com.lms.controller;

import com.lms.dao.LeaveDAO;
import com.lms.model.Leave;
import com.lms.model.User;
import com.lms.util.AuthUtil;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LeaveController", urlPatterns = {"/leave/details"})
public class LeaveController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            int leaveId = Integer.parseInt(request.getParameter("id"));
            LeaveDAO leaveDAO = new LeaveDAO();
            Leave leave = leaveDAO.getLeaveById(leaveId);
            
            if (leave != null) {
                request.setAttribute("leave", leave);
                User user = AuthUtil.getCurrentUser(request);
                
                if (AuthUtil.isStudent(request) && leave.getStudentId() == user.getId()) {
                    request.getRequestDispatcher("/WEB-INF/views/student/leave-details.jsp").forward(request, response);
                } else if (AuthUtil.isFaculty(request) && leave.getFacultyId() == user.getId()) {
                    request.getRequestDispatcher("/WEB-INF/views/faculty/leave-details.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission to view this leave");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Leave not found");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid leave ID");
        }
    }
}