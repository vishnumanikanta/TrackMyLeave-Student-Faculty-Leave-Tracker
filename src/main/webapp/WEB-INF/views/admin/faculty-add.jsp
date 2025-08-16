<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>${empty faculty ? 'Add' : 'Edit'} Faculty - Leave Management System</title>
        <%@include file="../includes/head.jsp" %>
    </head>
    <body>
        <%@include file="../includes/navbar.jsp" %>
        
        <div class="container">
            <c:set var="currentPage" value="faculty-list" scope="request"/>
            
            <div class="card fade-in">
                <div class="card-header">
                    <h2><i class="fas fa-user-plus"></i> ${empty faculty ? 'Add' : 'Edit'} Faculty</h2>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/admin/faculty/${empty faculty ? 'add' : 'edit'}" method="POST">
                        <c:if test="${not empty faculty}">
                            <input type="hidden" name="id" value="${faculty.id}">
                        </c:if>
                        <div class="form-group">
                            <label>Name</label>
                            <input type="text" name="name" class="form-control" 
                                   value="${empty faculty ? '' : faculty.name}" required>
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" name="email" class="form-control" 
                                   value="${empty faculty ? '' : faculty.email}" required>
                        </div>
                        <div class="form-group">
                            <label>Department</label>
                            <select name="department" class="form-control" required>
                                <option value="">Select Department</option>
                                <option value="Computer Science" ${not empty faculty && faculty.department eq 'Computer Science' ? 'selected' : ''}>Computer Science</option>
                                <option value="Electronics" ${not empty faculty && faculty.department eq 'Electronics' ? 'selected' : ''}>Electronics</option>
                                <option value="Mechanical" ${not empty faculty && faculty.department eq 'Mechanical' ? 'selected' : ''}>Mechanical</option>
                                <option value="Civil" ${not empty faculty && faculty.department eq 'Civil' ? 'selected' : ''}>Civil</option>
                            </select>
                        </div>
                        <c:if test="${empty faculty}">
                            <div class="form-group">
                                <label>Password</label>
                                <input type="password" name="password" class="form-control" required>
                            </div>
                        </c:if>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save"></i> ${empty faculty ? 'Add' : 'Update'} Faculty
                        </button>
                        <a href="${pageContext.request.contextPath}/admin/faculties" class="btn btn-secondary">
                            <i class="fas fa-arrow-left"></i> Back to List
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