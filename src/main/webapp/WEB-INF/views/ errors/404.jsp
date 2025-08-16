<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>404 Not Found - Leave Management System</title>
        <%@include file="../includes/head.jsp" %>
    </head>
    <body>
        <div class="container" style="text-align: center; padding: 100px 0;">
            <div class="card" style="max-width: 600px; margin: 0 auto;">
                <div class="card-header" style="background-color: #2196F3; color: white;">
                    <h1><i class="fas fa-exclamation-circle"></i> 404 Not Found</h1>
                </div>
                <div class="card-body">
                    <h3>The page you requested could not be found</h3>
                    <p>Please check the URL or navigate back to the homepage.</p>
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">
                        <i class="fas fa-home"></i> Back to Home
                    </a>
                </div>
            </div>
        </div>
    </body>
</html>