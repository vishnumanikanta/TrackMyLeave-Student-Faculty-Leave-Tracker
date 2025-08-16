<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin Dashboard - Leave Management System</title>
        <%@include file="../includes/head.jsp" %>
    </head>
    <body>
        <%@include file="../includes/navbar.jsp" %>
        
        <div class="container">
            <c:set var="currentPage" value="admin-dashboard" scope="request"/>
            
            <div class="profile-section">
                <div class="profile-avatar">
                    <i class="fas fa-user-shield"></i>
                </div>
                <h2>Admin Dashboard</h2>
                <p>Welcome, ${user.name}</p>
            </div>
            
            <div class="dashboard-stats">
                <div class="stat-card">
                    <div class="stat-number" style="color: #2196F3;">0</div>
                    <div>Total Students</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" style="color: #FF9800;">0</div>
                    <div>Pending Leaves</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" style="color: #4CAF50;">0</div>
                    <div>Approved Leaves</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" style="color: #F44336;">0</div>
                    <div>Rejected Leaves</div>
                </div>
            </div>
            
            <div class="card">
                <div class="card-header">
                    <h3><i class="fas fa-tasks"></i> Quick Actions</h3>
                </div>
                <div class="card-body">
                    <div class="grid" style="grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));">
                        <a href="${pageContext.request.contextPath}/admin/faculties" class="btn btn-primary">
                            <i class="fas fa-users"></i> Manage Faculties
                        </a>
                        <a href="#" class="btn btn-success">
                            <i class="fas fa-user-graduate"></i> Manage Students
                        </a>
                        <a href="#" class="btn btn-warning">
                            <i class="fas fa-cog"></i> System Settings
                        </a>
                        <a href="#" class="btn btn-danger">
                            <i class="fas fa-file-export"></i> Generate Reports
                        </a>
                    </div>
                </div>
            </div>
        </div>
        
        <%@include file="../includes/footer.jsp" %>
        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>