<%-- 
    Document   : sidebar
    Created on : Jun 20, 2022, 5:44:30 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
    </head>
    <body>
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">
                <div class="sidebar-brand-icon">
                    <i class="fas fa-user-clock"></i>
                </div>
                <div class="sidebar-brand-text mx-3">Project Manager</div>
            </a>
            <!-- Divider -->
            <hr class="sidebar-divider my-0">

            <!--Nav Item - Dashboard--> 
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/class-dashboard">
                    <i class="fas fa-fw fa-tachometer-alt"></i>
                    <span>Dashboard</span></a>
            </li>            
            
            <!-- Divider -->
            <hr class="sidebar-divider my-0">                       

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/subject-setting">
                    <i class="fas fa-fw fa-tasks"></i>
                    <span>Subject setting list</span></a>
            </li> 

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/iteration">
                    <i class="fas fa-fw fa-tasks"></i>
                    <span>Iteration list</span></a>
            </li> 

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/criteria">
                    <i class="fas fa-fw fa-tasks"></i>
                    <span>Criteria list</span></a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/class">
                    <i class="fas fa-fw fa-tasks"></i>
                    <span>Class list</span></a>
            </li>                                                           

            <!-- Divider -->
            <hr class="sidebar-divider d-none d-md-block">

            <!-- Sidebar Toggler (Sidebar) -->
            <div class="text-center d-none d-md-inline">
                <button class="rounded-circle border-0" id="sidebarToggle"></button>
            </div>

        </ul>        
    </body>
</html>
