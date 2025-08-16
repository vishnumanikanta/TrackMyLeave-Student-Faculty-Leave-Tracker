<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>403 Forbidden - Leave Management System</title>
        <%@include file="../includes/head.jsp" %>
    </head>
    <body>
        <div class="container" style="text-align: center; padding: 100px 0;">
            <div class="card" style="max-width: 600px; margin: 0 auto;">
                <div class="card-header" style="background-color: #f44336; color: white;">
                    <h1><i class="fas fa-ban"></i> 403 Forbidden</h1>
                </div>
                <div class="card-body">
                    <h3>You don't have permission to access this page</h3>
                    <p>Please contact the administrator if you believe this is an error.</p>
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">
                        <i class="fas fa-sign-in-alt"></i> Back to Login
                    </a>
                </div>
            </div>
        </div>
    </body>
</html>