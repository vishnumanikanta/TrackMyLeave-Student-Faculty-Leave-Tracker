<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Register - Leave Management System</title>
        <%@include file="../includes/head.jsp" %>
    </head>
    <body>
        <%@include file="../includes/navbar.jsp" %>
        
        <div class="container">
            <div class="card fade-in">
                <div class="card-header">
                    <h2><i class="fas fa-user-plus"></i> Student Registration</h2>
                </div>
                <div class="card-body">
                 <form action="${pageContext.request.contextPath}/register" method="POST" enctype="multipart/form-data">
                       <div class="form-group">
        <label>Profile Picture (optional)</label>
        <input type="file" name="profilePic" class="form-control" accept="image/*">
    </div>
                        <div class="form-group">
                            <label>Full Name</label>
                            <input type="text" name="name" class="form-control" 
                                   placeholder="Enter your full name" value="${param.name}" required>
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" name="email" class="form-control" 
                                   placeholder="Enter your email" value="${param.email}" required>
                        </div>
                        <div class="form-group">
                            <label>Password</label>
                            <input type="password" name="password" class="form-control" 
                                   placeholder="Create a password" required>
                        </div>
                        <div class="form-group">
                            <label>Roll Number</label>
                            <input type="text" name="rollNumber" class="form-control" 
                                   placeholder="Enter your roll number" value="${param.rollNumber}" required>
                        </div>
                        <div class="form-group">
                            <label>Department</label>
                            <select name="department" class="form-control" required>
                                <option value="">Select Department</option>
                                <option value="Computer Science" ${param.department eq 'Computer Science' ? 'selected' : ''}>Computer Science</option>
                                <option value="Electronics" ${param.department eq 'Electronics' ? 'selected' : ''}>Electronics</option>
                                <option value="Mechanical" ${param.department eq 'Mechanical' ? 'selected' : ''}>Mechanical</option>
                                <option value="Civil" ${param.department eq 'Civil' ? 'selected' : ''}>Civil</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Phone</label>
                            <input type="tel" name="phone" class="form-control" 
                                   placeholder="Enter your phone number" value="${param.phone}">
                        </div>
                        
                        <div class="form-group">
                            <label>Address</label>
                            <textarea name="address" class="form-control" rows="3" 
                                      placeholder="Enter your address">${param.address}</textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-user-plus"></i> Register
                        </button>
                        <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary">
                            <i class="fas fa-sign-in-alt"></i> Back to Login
                        </a>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger mt-3">
                                <i class="fas fa-exclamation-triangle"></i> ${error}
                            </div>
                        </c:if>
                        <c:if test="${not empty success}">
                            <div class="alert alert-success mt-3">
                                <i class="fas fa-check-circle"></i> ${success}
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