<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Faculty Dashboard - Leave Management System</title>
        <%@include file="../includes/head.jsp" %>
    </head>
    <body>
        <%@include file="../includes/navbar.jsp" %>
        
        <div class="container">
            <c:set var="currentPage" value="faculty-dashboard" scope="request"/>
            
            <div class="profile-section">
                <div class="profile-avatar">
                    <i class="fas fa-chalkboard-teacher"></i>
                </div>
                <h2>Faculty Dashboard</h2>
                <p>Welcome, ${user.name}</p>
            </div>
            
            <div class="dashboard-stats">
                <div class="stat-card">
                    <div class="stat-number" style="color: #2196F3;">${totalLeaves}</div>
                    <div>Total Applications</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" style="color: #FF9800;">${pendingLeaves}</div>
                    <div>Pending Review</div>
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
                    <h3><i class="fas fa-list"></i> Student Leave Applications</h3>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty leaves}">
                           <c:forEach items="${leaves}" var="leave">
    <div class="leave-item">
        <div style="display: flex; gap: 15px; align-items: flex-start; margin-bottom: 15px;">
            <img src="${pageContext.request.contextPath}/student/profile-pic?id=${leave.studentId}" 
                 alt="Profile" width="70" height="70" style="border-radius: 50%; object-fit: cover;"/>

            <div style="flex: 1;">
                <h4>${leave.studentName} - ${leave.leaveType}</h4>
                <p><strong>Roll Number:</strong> ${leave.rollNumber}</p>
                <p><strong>Applied on:</strong> ${leave.appliedDate}</p>
                <p><strong>Duration:</strong> ${leave.fromDate} to ${leave.toDate}</p>
                <p><strong>Reason:</strong> ${leave.reason}</p>
            </div>

            <span class="status-badge status-${leave.status}">${leave.status}</span>
        </div>
<c:if test="${leave.hasMedicalDoc}">
    <a href="${pageContext.request.contextPath}/leave/document?id=${leave.id}" 
       class="btn btn-sm btn-info" target="_blank">
       <i class="fas fa-file-medical"></i> View Medical Doc
    </a>
</c:if>

        <c:if test="${leave.status eq 'pending'}">
            <form action="${pageContext.request.contextPath}/faculty/leave/update-status" method="POST">
                <input type="hidden" name="leaveId" value="${leave.id}">
                <div class="form-group">
                    <label>Comments (Optional)</label>
                    <textarea name="comments" class="form-control" rows="2" 
                              placeholder="Add comments for the student..."></textarea>
                </div>
                <button type="submit" name="status" value="approved" class="btn btn-success">
                    <i class="fas fa-check"></i> Approve
                </button>
                <button type="submit" name="status" value="rejected" class="btn btn-danger">
                    <i class="fas fa-times"></i> Reject
                </button>
            </form>
        </c:if>


        <c:if test="${not empty leave.facultyComments && leave.status ne 'pending'}">
            <div style="margin-top: 10px; padding: 10px; background: #f0f0f0; border-radius: 5px;">
                <strong>Comments:</strong> ${leave.facultyComments}
            </div>
        </c:if>
    </div>
</c:forEach>

                        </c:when>
                        <c:otherwise>
                            <div style="text-align: center; color: #666; padding: 40px;">
                                <i class="fas fa-clipboard-list" style="font-size: 48px; margin-bottom: 15px;"></i>
                                <h3>No Leave Applications</h3>
                                <p>No student has applied for leave yet.</p>
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