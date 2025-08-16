package com.lms.controller;

import com.lms.dao.UserDAO;
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

@WebServlet(name = "AdminController", urlPatterns = {
    "/admin/dashboard",
    "/admin/faculties",
    "/admin/faculty/add",
    "/admin/faculty/edit",
    "/admin/faculty/delete"
})
public class AdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();
        try {
            switch (path) {
                case "/admin/dashboard":
                    showDashboard(request, response);
                    break;
                case "/admin/faculties":
                    showFaculties(request, response);
                    break;
                case "/admin/faculty/add":
                    showAddFacultyForm(request, response);
                    break;
                case "/admin/faculty/edit":
                    showEditFacultyForm(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();
        try {
            switch (path) {
                case "/admin/faculty/add":
                    addFaculty(request, response);
                    break;
                case "/admin/faculty/edit":
                    updateFaculty(request, response);
                    break;
                case "/admin/faculty/delete":
                    deleteFaculty(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
    }

    private void showFaculties(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        UserDAO userDAO = new UserDAO();
        List<User> faculties = userDAO.getAllFaculties();
        request.setAttribute("faculties", faculties);
        request.getRequestDispatcher("/WEB-INF/views/admin/faculty-list.jsp").forward(request, response);
    }

    private void showAddFacultyForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/admin/faculty-add.jsp").forward(request, response);
    }

    private void showEditFacultyForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        UserDAO userDAO = new UserDAO();
        User faculty = userDAO.getFacultyById(id);
        
        if (faculty != null) {
            request.setAttribute("faculty", faculty);
            request.getRequestDispatcher("/WEB-INF/views/admin/faculty-add.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Faculty not found");
            showFaculties(request, response);
        }
    }

    private void addFaculty(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        User faculty = new User();
        faculty.setEmail(request.getParameter("email"));
        faculty.setPassword(request.getParameter("password"));
        faculty.setName(request.getParameter("name"));
        faculty.setDepartment(request.getParameter("department"));
        
        UserDAO userDAO = new UserDAO();
        if (userDAO.addFaculty(faculty)) {
            request.setAttribute("success", "Faculty added successfully!");
            response.sendRedirect(request.getContextPath() + "/admin/faculties");
        } else {
            request.setAttribute("error", "Failed to add faculty. Email might be already in use.");
            request.setAttribute("faculty", faculty);
            request.getRequestDispatcher("/WEB-INF/views/admin/faculty-add.jsp").forward(request, response);
        }
    }

    private void updateFaculty(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        User faculty = new User();
        faculty.setId(Integer.parseInt(request.getParameter("id")));
        faculty.setEmail(request.getParameter("email"));
        faculty.setName(request.getParameter("name"));
        faculty.setDepartment(request.getParameter("department"));
        
        UserDAO userDAO = new UserDAO();
        if (userDAO.updateFaculty(faculty)) {
            request.setAttribute("success", "Faculty updated successfully!");
            response.sendRedirect(request.getContextPath() + "/admin/faculties");
        } else {
            request.setAttribute("error", "Failed to update faculty.");
            request.setAttribute("faculty", faculty);
            request.getRequestDispatcher("/WEB-INF/views/admin/faculty-add.jsp").forward(request, response);
        }
    }

    private void deleteFaculty(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        UserDAO userDAO = new UserDAO();
        if (userDAO.deleteFaculty(id)) {
            request.setAttribute("success", "Faculty deleted successfully!");
        } else {
            request.setAttribute("error", "Failed to delete faculty.");
        }
        showFaculties(request, response);
    }
}