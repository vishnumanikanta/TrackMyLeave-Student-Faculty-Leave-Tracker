<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Manage Faculties - Leave Management System</title>
        <%@include file="../includes/head.jsp" %>
    </head>
    <body>
        <%@include file="../includes/navbar.jsp" %>
        
        <div class="container">
            <c:set var="currentPage" value="faculty-list" scope="request"/>
            
            <div class="card fade-in">
                <div class="card-header">
                    <div style="display: flex; justify-content: space-between; align-items: center;">
                        <h2><i class="fas fa-users"></i> Manage Faculties</h2>
                        <a href="${pageContext.request.contextPath}/admin/faculty/add" class="btn btn-primary">
                            <i class="fas fa-plus"></i> Add Faculty
                        </a>
                    </div>
                </div>
                <div class="card-body">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">
                            <i class="fas fa-exclamation-triangle"></i> ${error}
                        </div>
                    </c:if>
                    <c:if test="${not empty success}">
                        <div class="alert alert-success">
                            <i class="fas fa-check-circle"></i> ${success}
                        </div>
                    </c:if>
                    
                    <table class="table" style="width: 100%; border-collapse: collapse;">
                        <thead>
                            <tr style="background-color: #f8f9fa;">
                                <th style="padding: 12px; text-align: left; border-bottom: 1px solid #ddd;">Name</th>
                                <th style="padding: 12px; text-align: left; border-bottom: 1px solid #ddd;">Email</th>
                                <th style="padding: 12px; text-align: left; border-bottom: 1px solid #ddd;">Department</th>
                                <th style="padding: 12px; text-align: left; border-bottom: 1px solid #ddd;">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${faculties}" var="faculty">
                                <tr style="border-bottom: 1px solid #ddd;">
                                    <td style="padding: 12px;">${faculty.name}</td>
                                    <td style="padding: 12px;">${faculty.email}</td>
                                    <td style="padding: 12px;">${faculty.department}</td>
                                    <td style="padding: 12px;">
                                        <a href="${pageContext.request.contextPath}/admin/faculty/edit?id=${faculty.id}" 
                                           class="btn btn-warning btn-sm">
                                            <i class="fas fa-edit"></i> Edit
                                        </a>
                                        <form action="${pageContext.request.contextPath}/admin/faculty/delete" method="POST" 
                                              style="display: inline-block;">
                                            <input type="hidden" name="id" value="${faculty.id}">
                                            <button type="submit" class="btn btn-danger btn-sm" 
                                                    onclick="return confirm('Are you sure you want to delete this faculty?')">
                                                <i class="fas fa-trash"></i> Delete
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        
        <%@include file="../includes/footer.jsp" %>
        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>