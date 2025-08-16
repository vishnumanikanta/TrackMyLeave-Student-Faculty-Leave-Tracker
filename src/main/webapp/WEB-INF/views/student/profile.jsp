<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>My Profile - Leave Management System</title>
        <%@include file="../includes/head.jsp" %>
    </head>
    <body>
        <%@include file="../includes/navbar.jsp" %>
        
        <div class="container">
            <c:set var="currentPage" value="profile" scope="request"/>
            
            <div class="card fade-in">
                <div class="card-header">
                    <h2><i class="fas fa-user"></i> My Profile</h2>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/student/profile" method="POST" enctype="multipart/form-data">

                        <div class="form-group">
                            <label>Profile Picture</label><br>
                            <img src="${pageContext.request.contextPath}/student/profile-pic?id=${user.id}" 
     alt="Profile Picture" width="150" height="150" class="img-thumbnail mb-2"/>

                            <input type="file" name="profilePic" class="form-control mt-2">
                        </div>
                        <div class="form-group">
                            <label>Name</label>
                            <input type="text" name="name" class="form-control" value="${user.name}" required>
                        </div>
                        <div class="form-group">
                            <label>Roll Number</label>
                            <input type="text" class="form-control" value="${user.rollNumber}" readonly>
                        </div>
                        <div class="form-group">
                            <label>Department</label>
                            <input type="text" class="form-control" value="${user.department}" readonly>
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" class="form-control" value="${user.email}" readonly>
                        </div>
                        <div class="form-group">
                            <label>Phone</label>
                            <input type="tel" name="phone" class="form-control" value="${user.phone}">
                        </div>
                        <div class="form-group">
                            <label>Address</label>
                            <textarea name="address" class="form-control" rows="3">${user.address}</textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save"></i> Update Profile
                        </button>
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
