<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Login - Leave Management System</title>
        <%@include file="../includes/head.jsp" %>
    </head>
    <body>
        <%@include file="../includes/navbar.jsp" %>
        
        <div class="container">
            <div class="card fade-in">
                <div class="card-header">
                    <h2><i class="fas fa-sign-in-alt"></i> Login to System</h2>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/login" method="POST">
                        <div class="form-group">
                            <label>User Type</label>
                            <select name="userType" class="form-control" required>
                                <option value="">Select User Type</option>
                                <option value="student" ${param.userType eq 'student' ? 'selected' : ''}>Student</option>
                                <option value="faculty" ${param.userType eq 'faculty' ? 'selected' : ''}>Faculty</option>
                                <option value="admin" ${param.userType eq 'admin' ? 'selected' : ''}>Admin</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" name="email" class="form-control" 
                                   placeholder="Enter your email" value="${param.email}" required>
                        </div>
                        <div class="form-group">
                            <label>Password</label>
                            <input type="password" name="password" class="form-control" 
                                   placeholder="Enter your password" required>
                        </div>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-sign-in-alt"></i> Login
                        </button>
                        <a href="${pageContext.request.contextPath}/register" class="btn btn-success">
                            <i class="fas fa-user-plus"></i> Register as Student
                        </a>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger mt-3">
                                <i class="fas fa-exclamation-triangle"></i> ${error}
                            </div>
                        </c:if>
                    </form>
                </div>
            </div>
        </div>
        
        <%@include file="../includes/footer.jsp" %>
        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>