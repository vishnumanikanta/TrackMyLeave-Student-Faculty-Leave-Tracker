package com.lms.controller;

import com.lms.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/student/profile-pic")
public class ProfilePicServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        UserDAO userDAO = new UserDAO();

        try (InputStream input = userDAO.getProfilePicById(userId)) {
            if (input != null) {
                response.setContentType("image/jpeg");
                OutputStream out = response.getOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                out.flush();
            } else {
                response.sendRedirect(request.getContextPath() + "/images/default-avatar.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
