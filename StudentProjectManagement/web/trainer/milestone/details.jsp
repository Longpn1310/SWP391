<%-- 
    Document   : details
    Created on : Aug 16, 2022, 12:59:20 AM
    Author     : ADMIN
--%>

<%@page import="entity_iteration.Iteration"%>
<%@page import="entity_milestone.Milestone"%>
<%@page import="entity_class.Class"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage ="../../common/error/error.jsp"%>
<% Milestone milestone = (Milestone) session.getAttribute("milestone");%>
<% List<Class> classes = (ArrayList<Class>) session.getAttribute("classes"); %>
<% List<Iteration> iterations = (ArrayList<Iteration>) session.getAttribute("iterations"); %>
<% String action = (String) request.getAttribute("action");%>
<% String mssError = (String) request.getAttribute("mssError");%>
<% String selectedClass = (String) request.getAttribute("selectedClass");%>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Milestone Details</title>

        <!-- Custom fonts for this template-->
        <link href="vendor/fontawesome-free/css/all.css" rel="stylesheet" type="text/css"/>  
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="css/common/sb-admin-2.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/common/global.css" rel="stylesheet" type="text/css"/>
    </head>

    <body id="page-top" class="sidebar-toggled">

        <!-- Page Wrapper -->
        <div id="wrapper">

            <!-- Sidebar -->
            <jsp:include page="../../common/fragment/trainer/sidebar.jsp" />
            <!-- End of Sidebar -->

            <!-- Content Wrapper -->
            <div id="content-wrapper" class="d-flex flex-column">

                <!-- Main Content -->
                <div id="content">

                    <!-- Topbar -->
                    <jsp:include page="../../common/fragment/trainer/topbar.jsp" />
                    <!-- End of Topbar -->

                    <!-- Begin Page Content -->
                    <div class="container-fluid">

                        <!-- Page Heading -->
                        <div class="d-sm-flex align-items-center justify-content-between mb-4">
                            <%if (action.equals("add")) {%>
                            <h1 class="h3 mb-0 text-gray-800">Create Milestone</h1>
                            <%} else {%>
                            <h1 class="h3 mb-0 text-gray-800">Update Milestone</h1>
                            <%}%>               

                        </div>
                        <label style="color: red"><%=mssError%></label>
                        <form id="classForm" method="GET" action="${pageContext.request.contextPath}/milestone">    
                            <%if (action.equals("add")) {%>
                            <input type="hidden" name="action" value="add">
                            <%} else {%>
                            <input type="hidden" name="action" value="edit">       
                            <input type="hidden" name="milestoneId" value="<%=milestone.getMilestoneId()%>">
                            <%}%>                            
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="form-group">     
                                        <label for="classId">Class*</label>
                                        <select id="classId" name="classId" class="custom-select" onchange="document.getElementById('classForm').submit();">                                              
                                            <option value="all">Select a class</option>
                                            <% for (Class classs : classes) {%>   
                                            <% if (selectedClass != null) {
                                                    if (classs.getClassId() == Integer.parseInt(selectedClass)) {%>
                                            <option value="<%=classs.getClassId()%>" selected="selected"><%=classs.getClassCode()%></option>
                                            <%} else {%>
                                            <option value="<%=classs.getClassId()%>"><%=classs.getClassCode()%></option>
                                            <%}%>
                                            <%} else {%>
                                            <%if (classs.getClassId() == milestone.getClassId()) {%>
                                            <option value="<%=classs.getClassId()%>" selected="selected"><%=classs.getClassCode()%></option>
                                            <%} else {%>
                                            <option value="<%=classs.getClassId()%>"><%=classs.getClassCode()%></option>
                                            <%}%>
                                            <%}%>                                                                                       
                                            <%}%>                                                                                                                   
                                        </select>                                        
                                    </div> 
                                </div>
                            </div>
                        </form>

                        <form method="POST" action="${pageContext.request.contextPath}/milestone">
                            <%if (action.equals("add")) {%>
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="selectedClass" value="<%=selectedClass%>">
                            <%} else {%>
                            <input type="hidden" name="action" value="edit">                            
                            <input type="hidden" name="milestoneId" value="<%=milestone.getMilestoneId()%>">
                            <input type="hidden" name="selectedClass" value="<%=selectedClass%>">
                            <%}%>                            
                            <!-- Content Row -->
                            <div class="row">
                                <div class="col-lg-6">
                                    <%if (iterations != null) {%>                                    
                                    <div class="form-group">     
                                        <label for="iterationId">Iteration*</label>
                                        <select id="iterationId" name="iterationId" class="custom-select">                                            
                                            <% for (Iteration iteration : iterations) {%>
                                            <%if (milestone.getIterationId() == iteration.getId()) {%>
                                            <option value="<%=iteration.getId()%>" selected="selected"><%=iteration.getName()%></option>
                                            <%} else {%>
                                            <option value="<%=iteration.getId()%>"><%=iteration.getName()%></option>
                                            <%}%>                                            
                                            <%}%>                                                                                                                   
                                        </select>                                        
                                    </div>  
                                    <%}%>

                                    <div class="form-group">
                                        <label for="start_date">From Date</label>                  
                                        <%if (milestone.getFromDate() == null) {%>
                                        <input name="start_date" id="start_date" type="date" class="form-control" placeholder="yyyy-mm-dd">
                                        <%} else {%>
                                        <input name="start_date" id="start_date" type="date" class="form-control" placeholder="yyyy-mm-dd" value="<%= milestone.getFromDate()%>">                                        
                                        <%}%>                                        
                                    </div> 
                                    <div class="form-group">
                                        <label for="due_date">To Date</label>                                  
                                        <%if (milestone.getFromDate() == null) {%>
                                        <input name="due_date" id="due_date" type="date" class="form-control" placeholder="yyyy-mm-dd">
                                        <%} else {%>
                                        <input name="due_date" id="due_date" type="date" class="form-control" placeholder="yyyy-mm-dd" value="<%= milestone.getToDate()%>">
                                        <%}%>                                        
                                    </div>                                                                                                       
                                    <div class="form-group">     
                                        <label for="status">Status</label>
                                        <div class="d-flex">
                                            <div class="mt-2">
                                                <%if (milestone.isStatus() == true) {%>
                                                <input type="radio" id="status" name="status" value="true" checked="checked">                                                
                                                <%} else {%>
                                                <input type="radio" id="status" name="status" value="true">
                                                <%}%>
                                                <label for="status">Open</label>
                                            </div>
                                            <div class="ml-5 mt-2">
                                                <%if (milestone.isStatus() == false) {%>
                                                <input type="radio" id="status" name="status" value="false" checked="checked">                                                
                                                <%} else {%>
                                                <input type="radio" id="status" name="status" value="false">
                                                <%}%>
                                                <label for="status">Close</label>
                                            </div>
                                        </div>
                                    </div>                                     
                                    <div class="form-group">
                                        <label for="description">Description</label>                                        
                                        <%if (milestone.getDescription() == null) {%>
                                        <textarea name="description" id="description" rows="5" type="text" class="form-control" placeholder="Enter milestone description"></textarea>
                                        <%} else {%>
                                        <textarea name="description" id="description" rows="5" type="text" class="form-control" placeholder="Enter milestone description"><%= milestone.getDescription()%></textarea>                                        
                                        <%}%>                                        
                                    </div>

                                    <input type="hidden" name="milestoneId" value="<%= milestone.getMilestoneId()%>">
                                    <input type="hidden" name="action" value="<%= request.getAttribute("action")%>">
                                    <%if (action.equals("add")) {%>
                                    <button type="submit" class="btn btn-lg btn-success">Create Milestone</button>
                                    <%} else {%>
                                    <button type="submit" class="btn btn-lg btn-success">Update Milestone</button>
                                    <%}%>                                        

                                </div>
                            </div>
                        </form>

                    </div>
                    <!-- /.container-fluid -->

                </div>
                <!-- End of Main Content -->

            </div>
            <!-- End of Content Wrapper -->

        </div>
        <!-- End of Page Wrapper -->               

        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="js/common/sb-admin-2.min.js" type="text/javascript"></script>        
    </body>

</html>
