<!-- Responsive Navbar -->
<style>
    /* Base Navbar Styling */
.navbar {
    background: #2c3e50;
    color: white;
    padding: 10px 20px;
    display: flex;
    flex-direction: column;
}

/* Content Wrapper for Flex Layout */
.navbar-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
}

/* Logo Styling */
.logo {
    font-size: 1.5rem;
    font-weight: bold;
    display: flex;
    align-items: center;
    gap: 8px;
}

.logo i {
    margin-right: 4px;
}

/* Hamburger Menu - Hidden by default (desktop) */
.hamburger {
    display: none;
    font-size: 1.5rem;
    cursor: pointer;
    color: white;
    align-items: center;
    gap: 6px;
}

/* Navigation Menu */
.nav-menu {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    gap: 15px;
    align-items: center;
}

/* Navigation Links */
.nav-item {
    color: white;
    text-decoration: none;
    padding: 8px 12px;
    border-radius: 4px;
    transition: background 0.2s ease;
}

.nav-item:hover,
.nav-item.active {
    background: #1abc9c;
}

/* Mobile Responsive Styling */
@media (max-width: 768px) {
    .nav-menu {
        display: none;
        flex-direction: column;
        width: 100%;
        margin-top: 10px;
    }

    .nav-menu.active {
        display: flex;
    }

    .hamburger {
        display: flex; /* Only show on mobile */
    }
}

    
</style>

<nav class="navbar">
    <div class="navbar-content">
  <div style="display: flex; align-items: center; justify-content: space-between; padding: 10px 20px;">
    <!-- Logo -->
    <div class="logo" style="font-size: 16px; display: flex; align-items: center; gap: 4px;">
        <i class="fas fa-graduation-cap"></i>
        <span>Leave Management System</span>
    </div>

    <!-- Hamburger -->
<div class="hamburger" onclick="document.querySelector('.nav-menu').classList.toggle('active')">
    <i class="fas fa-bars"></i>
    <span style="font-size: 14px;"></span>
</div>

</div>
<div class="nav-menu">
            <c:choose>
                <c:when test="${not empty user}">
                    <c:choose>
                        <c:when test="${user.userType eq 'student'}">
                            <a href="${pageContext.request.contextPath}/student/dashboard" 
                               class="nav-item ${currentPage eq 'student-dashboard' ? 'active' : ''}">
                                <i class="fas fa-tachometer-alt"></i> Dashboard
                            </a>
                            <a href="${pageContext.request.contextPath}/student/apply-leave" 
                               class="nav-item ${currentPage eq 'apply-leave' ? 'active' : ''}">
                                <i class="fas fa-plus-circle"></i> Apply Leave
                            </a>
                            <a href="${pageContext.request.contextPath}/student/my-leaves" 
                               class="nav-item ${currentPage eq 'my-leaves' ? 'active' : ''}">
                                <i class="fas fa-inbox"></i> My Leaves
                            </a>
                            <a href="${pageContext.request.contextPath}/student/profile" 
                               class="nav-item ${currentPage eq 'profile' ? 'active' : ''}">
                                <i class="fas fa-user"></i> Profile
                            </a>
                        </c:when>
                        <c:when test="${user.userType eq 'faculty'}">
                            <a href="${pageContext.request.contextPath}/faculty/dashboard" 
                               class="nav-item ${currentPage eq 'faculty-dashboard' ? 'active' : ''}">
                                <i class="fas fa-chalkboard-teacher"></i> Faculty Dashboard
                            </a>
                        </c:when>
                        <c:when test="${user.userType eq 'admin'}">
                            <a href="${pageContext.request.contextPath}/admin/dashboard" 
                               class="nav-item ${currentPage eq 'admin-dashboard' ? 'active' : ''}">
                                <i class="fas fa-tachometer-alt"></i> Dashboard
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/faculties" 
                               class="nav-item ${currentPage eq 'faculty-list' ? 'active' : ''}">
                                <i class="fas fa-users"></i> Manage Faculties
                            </a>
                        </c:when>
                    </c:choose>
                    <a href="${pageContext.request.contextPath}/logout" class="nav-item">
                        <i class="fas fa-sign-out-alt"></i> Logout
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login" 
                       class="nav-item ${currentPage eq 'login' ? 'active' : ''}">
                        <i class="fas fa-sign-in-alt"></i> Login
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

