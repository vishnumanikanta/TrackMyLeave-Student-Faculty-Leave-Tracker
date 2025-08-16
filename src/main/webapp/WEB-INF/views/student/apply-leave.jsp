<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Apply Leave - Leave Management System</title>
        <%@include file="../includes/head.jsp" %>
    </head>
    <body>
        <%@include file="../includes/navbar.jsp" %>
        
        <div class="container">
            <c:set var="currentPage" value="apply-leave" scope="request"/>
            
            <div class="card fade-in">
                <div class="card-header">
                    <h2><i class="fas fa-plus-circle"></i> Apply for Leave</h2>
                </div>
                <div class="card-body">
                   <form action="${pageContext.request.contextPath}/student/apply-leave/submit" method="POST" enctype="multipart/form-data">
                        <div class="form-group">
                            <label>Leave Type</label>
                            <select name="leaveType" class="form-control" required>
                                <option value="">Select Leave Type</option>
                                <option value="Sick Leave">Sick Leave</option>
                                <option value="Personal Leave">Personal Leave</option>
                                <option value="Emergency Leave">Emergency Leave</option>
                                <option value="Medical Leave">Medical Leave</option>
                                <option value="Family Function">Family Function</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>From Date</label>
                            <input type="date" name="fromDate" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label>To Date</label>
                            <input type="date" name="toDate" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label>Reason</label>
                            <textarea name="reason" class="form-control" rows="4" 
                                      placeholder="Explain the reason for your leave..." required></textarea>
                        </div>
                         <div class="form-group">
        <label>Upload Medical Document (optional)</label>
        <input type="file" name="medicalDoc" class="form-control" accept="image/*,.pdf">
    </div>
                        <div class="form-group">
                            <label>Faculty/Department</label>
                            <select name="facultyId" class="form-control" required>
                                <option value="">Select Faculty</option>
                                <c:forEach items="${faculties}" var="faculty">
                                    <option value="${faculty.id}">${faculty.name} - ${faculty.department}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-paper-plane"></i> Submit Leave Application
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
        <script>
            // Ensure toDate is not before fromDate
            document.querySelector('input[name="fromDate"]').addEventListener('change', function() {
                const fromDate = this.value;
                const toDateInput = document.querySelector('input[name="toDate"]');
                if (fromDate && toDateInput.value && fromDate > toDateInput.value) {
                    toDateInput.value = '';
                }
                toDateInput.min = fromDate;
            });
        </script>
    </body>
</html>