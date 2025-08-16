<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Student Dashboard - Leave Management System</title>
        <%@include file="../includes/head.jsp" %>
    </head>
    <body>
        <%@include file="../includes/navbar.jsp" %>
        
        <div class="container">
            <c:set var="currentPage" value="student-dashboard" scope="request"/>
            
          <div class="profile-section" style="position: relative; padding-top: 20px; text-align: center;">

    <!-- Profile Picture -->
    <div style="
        width: 120px;
        height: 120px;
        margin: 0 auto 10px auto;
        overflow: hidden;
        border-radius: 50%;
        background: #f0f0f0;
        border: 2px solid #ccc;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    ">
        <img src="${pageContext.request.contextPath}/student/profile-pic?id=${user.id}" 
             alt="Profile Picture" 
             style="
                width: 100%;
                height: 100%;
                object-fit: cover;
                border-radius: 50%;
             ">
    </div>

    <!-- Name and Details -->
    <h2 style="font-size: 1.6rem; margin: 0;">Welcome, ${user.name}</h2>
    <p style="font-size: 1rem; color: #555;">${user.department} - ${user.rollNumber}</p>

</div>

            <div class="dashboard-stats">
                <div class="stat-card">
                    <div class="stat-number" style="color: #2196F3;">${totalLeaves}</div>
                    <div>Total Leaves</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" style="color: #FF9800;">${pendingLeaves}</div>
                    <div>Pending</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" style="color: #4CAF50;">${approvedLeaves}</div>
                    <div>Approved</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" style="color: #F44336;">${rejectedLeaves}</div>
                    <div>Rejected</div>
                </div>
            </div>
            
            <div class="card">
                <div class="card-header">
                    <h3><i class="fas fa-clock"></i> Recent Leave Applications</h3>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty recentLeaves}">
                            <c:forEach items="${recentLeaves}" var="leave">
                                <div class="leave-item">
                                    <div style="display: flex; justify-content: space-between; align-items: center;">
                                        <div>
                                            <h4>${leave.leaveType}</h4>
                                            <p><strong>From:</strong> ${leave.fromDate} <strong>To:</strong> ${leave.toDate}</p>
                                            <p><strong>Reason:</strong> ${leave.reason}</p>
                                        </div>
                                        <span class="status-badge status-${leave.status}">${leave.status}</span>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div style="text-align: center; color: #666;">
                                <i class="fas fa-inbox"></i> No leave applications yet
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        
        <%@include file="../includes/footer.jsp" %>
        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>