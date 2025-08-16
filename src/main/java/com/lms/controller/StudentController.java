package com.lms.controller;

import com.lms.dao.LeaveDAO;
import com.lms.dao.UserDAO;
import com.lms.model.Leave;
import com.lms.model.User;
import com.lms.util.AuthUtil;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "StudentController", urlPatterns = {
    "/student/dashboard",
    "/student/apply-leave",
    "/student/my-leaves",
    "/student/profile",
    "/student/apply-leave/submit"
})
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class StudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtil.isStudent(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();
        try {
            switch (path) {
                case "/student/dashboard":
                    showDashboard(request, response);
                    break;
                case "/student/apply-leave":
                    showApplyLeaveForm(request, response);
                    break;
                case "/student/my-leaves":
                    showMyLeaves(request, response);
                    break;
                case "/student/profile":
                    showProfile(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtil.isStudent(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();
        if ("/student/apply-leave/submit".equals(path)) {
            try {
                submitLeaveApplication(request, response);
            } catch (SQLException e) {
                throw new ServletException("Database error occurred", e);
            }
        } else if ("/student/profile".equals(path)) {
            try {
                updateProfile(request, response);
            } catch (SQLException e) {
                throw new ServletException("Database error occurred", e);
            }
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        User user = AuthUtil.getCurrentUser(request);
        LeaveDAO leaveDAO = new LeaveDAO();
        
        int totalLeaves = leaveDAO.getLeaveCountByStatus(user.getId(), null);
        int pendingLeaves = leaveDAO.getLeaveCountByStatus(user.getId(), "pending");
        int approvedLeaves = leaveDAO.getLeaveCountByStatus(user.getId(), "approved");
        int rejectedLeaves = leaveDAO.getLeaveCountByStatus(user.getId(), "rejected");
        
        List<Leave> recentLeaves = leaveDAO.getLeavesByStudentId(user.getId());
        recentLeaves = recentLeaves.size() > 3 ? recentLeaves.subList(0, 3) : recentLeaves;
        
        request.setAttribute("totalLeaves", totalLeaves);
        request.setAttribute("pendingLeaves", pendingLeaves);
        request.setAttribute("approvedLeaves", approvedLeaves);
        request.setAttribute("rejectedLeaves", rejectedLeaves);
        request.setAttribute("recentLeaves", recentLeaves);
        
        request.getRequestDispatcher("/WEB-INF/views/student/dashboard.jsp").forward(request, response);
    }

    private void showApplyLeaveForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        UserDAO userDAO = new UserDAO();
        List<User> faculties = userDAO.getAllFaculties();
        request.setAttribute("faculties", faculties);
        request.getRequestDispatcher("/WEB-INF/views/student/apply-leave.jsp").forward(request, response);
    }

    private void showMyLeaves(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        User user = AuthUtil.getCurrentUser(request);
        LeaveDAO leaveDAO = new LeaveDAO();
        List<Leave> leaves = leaveDAO.getLeavesByStudentId(user.getId());
        request.setAttribute("leaves", leaves);
        request.getRequestDispatcher("/WEB-INF/views/student/my-leaves.jsp").forward(request, response);
    }

    private void showProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/student/profile.jsp").forward(request, response);
    }

    private void submitLeaveApplication(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        User user = AuthUtil.getCurrentUser(request);
        Leave leave = new Leave();
        leave.setStudentId(user.getId());
        leave.setFacultyId(Integer.parseInt(request.getParameter("facultyId")));
        leave.setLeaveType(request.getParameter("leaveType"));
        leave.setFromDate(Date.valueOf(request.getParameter("fromDate")));
        leave.setToDate(Date.valueOf(request.getParameter("toDate")));
        leave.setReason(request.getParameter("reason"));

        Part filePart = request.getPart("medicalDoc");
        if (filePart != null && filePart.getSize() > 0) {
            leave.setMedicalDoc(filePart.getInputStream());
        }

        LeaveDAO leaveDAO = new LeaveDAO();
        if (leaveDAO.applyLeave(leave)) {
            request.setAttribute("success", "Leave application submitted successfully!");
        } else {
            request.setAttribute("error", "Failed to submit leave application.");
        }

        showApplyLeaveForm(request, response);
    }

    private void updateProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        User user = AuthUtil.getCurrentUser(request);

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        Part filePart = request.getPart("profilePic");

        user.setName(name);
        user.setPhone(phone);
        user.setAddress(address);

        if (filePart != null && filePart.getSize() > 0) {
            user.setProfilePic(filePart.getInputStream());
        }

        UserDAO userDAO = new UserDAO();
        if (userDAO.updateProfile(user)) {
            request.getSession().setAttribute("user", user);
            request.setAttribute("success", "Profile updated successfully!");
        } else {
            request.setAttribute("error", "Failed to update profile.");
        }

        showProfile(request, response);
    }

}