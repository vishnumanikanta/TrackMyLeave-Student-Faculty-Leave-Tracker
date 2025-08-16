package com.lms.controller;

import com.lms.dao.LeaveDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
@WebServlet("/leave/document")
public class MedicalDocServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int leaveId = Integer.parseInt(request.getParameter("id"));
        LeaveDAO dao = new LeaveDAO();

        try (InputStream in = dao.getMedicalDocByLeaveId(leaveId)) {
            if (in != null) {
                // Use image/jpeg or image/png based on your upload
                response.setContentType("image/jpeg"); // or "image/png" if needed

                OutputStream out = response.getOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                out.flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No document found");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
