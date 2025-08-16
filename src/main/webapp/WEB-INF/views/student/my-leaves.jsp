<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>My Leaves - Leave Management System</title>
        <%@include file="../includes/head.jsp" %>
    </head>
    <body>
        <%@include file="../includes/navbar.jsp" %>
        
        <div class="container">
            <c:set var="currentPage" value="my-leaves" scope="request"/>
            
            <div class="card fade-in">
                <div class="card-header">
                    <h2><i class="fas fa-inbox"></i> My Leave Applications</h2>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty leaves}">
                            <c:forEach items="${leaves}" var="leave">
                                <div class="leave-item">
                                    <div style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 15px;">
                                        <div style="flex: 1;">
                                            <h4>${leave.leaveType}</h4>
                                            <p><strong>Applied on:</strong> ${leave.appliedDate}</p>
                                            <p><strong>Duration:</strong> ${leave.fromDate} to ${leave.toDate}</p>
                                            <p><strong>Faculty:</strong> ${leave.facultyName}</p>
                                            <p><strong>Reason:</strong> ${leave.reason}</p>
                                            <c:if test="${not empty leave.facultyComments}">
                                                <div style="margin-top: 10px; padding: 10px; background: #f0f0f0; border-radius: 5px;">
                                                    <strong>Faculty Comments:</strong> ${leave.facultyComments}
                                                </div>
                                            </c:if>
                                        </div>
                                        <span class="status-badge status-${leave.status}">${leave.status}</span>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div style="text-align: center; color: #666; padding: 40px;">
                                <i class="fas fa-inbox" style="font-size: 48px; margin-bottom: 15px;"></i>
                                <h3>No Leave Applications</h3>
                                <p>You haven't applied for any leaves yet.</p>
                                <a href="${pageContext.request.contextPath}/student/apply-leave" class="btn btn-primary">
                                    <i class="fas fa-plus"></i> Apply for Leave
                                </a>
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