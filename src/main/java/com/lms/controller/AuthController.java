package com.lms.controller;

import com.lms.dao.UserDAO;
import com.lms.model.User;
import com.lms.util.AuthUtil;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet(name = "AuthController", urlPatterns = {"/login", "/register", "/logout"})

@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class AuthController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/login":
                if (AuthUtil.isLoggedIn(request)) {
                    redirectBasedOnUserType(request, response);
                } else {
                    request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
                }
                break;
            case "/register":
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                break;
            case "/logout":
                AuthUtil.logout(request);
                response.sendRedirect(request.getContextPath() + "/login");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/login":
                handleLogin(request, response);
                break;
            case "/register":
                handleRegister(request, response);
                break;
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.authenticate(email, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                redirectBasedOnUserType(request, response);
            } else {
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = new User();
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));
        user.setRollNumber(request.getParameter("rollNumber"));
        user.setDepartment(request.getParameter("department"));
        user.setPhone(request.getParameter("phone"));
        user.setAddress(request.getParameter("address"));

        // ✅ Handle uploaded image
        Part filePart = request.getPart("profilePic");
        if (filePart != null && filePart.getSize() > 0) {
            user.setProfilePic(filePart.getInputStream());
        }

        try {
            UserDAO userDAO = new UserDAO();

            // Check for existing email
            if (userDAO.emailExists(user.getEmail())) {
                request.setAttribute("error", "Email already registered. Please use another.");
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                return;
            }

            // Save with profile pic
            if (userDAO.registerStudentWithPic(user)) {
                request.setAttribute("success", "Registration successful! Please login.");
                request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }

    private void redirectBasedOnUserType(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        User user = AuthUtil.getCurrentUser(request);
        if (user != null) {
            switch (user.getUserType()) {
                case "student":
                    response.sendRedirect(request.getContextPath() + "/student/dashboard");
                    break;
                case "faculty":
                    response.sendRedirect(request.getContextPath() + "/faculty/dashboard");
                    break;
                case "admin":
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/login");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}