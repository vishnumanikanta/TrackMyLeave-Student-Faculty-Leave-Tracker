package com.lms.util;

import com.lms.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthUtil {
    public static boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("user") != null;
    }

    public static User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null ? (User) session.getAttribute("user") : null;
    }

    public static boolean isStudent(HttpServletRequest request) {
        User user = getCurrentUser(request);
        return user != null && "student".equals(user.getUserType());
    }

    public static boolean isFaculty(HttpServletRequest request) {
        User user = getCurrentUser(request);
        return user != null && "faculty".equals(user.getUserType());
    }

    public static boolean isAdmin(HttpServletRequest request) {
        User user = getCurrentUser(request);
        return user != null && "admin".equals(user.getUserType());
    }

    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}