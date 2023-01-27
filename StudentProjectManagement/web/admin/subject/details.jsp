<%-- 
    Document   : details
    Created on : Aug 14, 2022, 2:17:58 AM
    Author     : ADMIN
--%>

<%@page import="entity_subject.Subject"%>
<%@page import="entity_user.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage ="../../common/error/error.jsp"%>
<% String action = (String) request.getAttribute("action");%>
<% List<User> authors = (ArrayList<User>) request.getAttribute("authors"); %>
<% Subject subject = (Subject) request.getAttribute("subject");%>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Subject Details</title>

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
            <jsp:include page="../../common/fragment/admin/sidebar.jsp" />
            <!-- End of Sidebar -->

            <!-- Content Wrapper -->
            <div id="content-wrapper" class="d-flex flex-column">

                <!-- Main Content -->
                <div id="content">

                    <!-- Topbar -->
                    <jsp:include page="../../common/fragment/admin/topbar.jsp" />
                    <!-- End of Topbar -->

                    <!-- Begin Page Content -->
                    <div class="container-fluid">

                        <!-- Page Heading -->
                        <div class="d-sm-flex align-items-center justify-content-between mb-4">
                            <%if (action.equals("add")) {%>
                            <h1 class="h3 mb-0 text-gray-800">Add new subject</h1>  
                            <%} else {%>
                            <h1 class="h3 mb-0 text-gray-800">Edit subject</h1>
                            <%}%>                  
                        </div>
                        <form method="POST" action="${pageContext.request.contextPath}/subject">
                            <%if (action.equals("add")) {%>
                            <input type="hidden" name="action" value="add">
                            <%} else {%>
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="subjectId" value="<%=subject.getSubjectId()%>">
                            <%}%>
                            <!-- Content Row -->                            
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label for="subjectCode">Subject code*</label>
                                        <%if (action.equals("add")) {%>
                                        <input type="text" class="form-control" name="subjectCode" id="subjectCode" placeholder="Enter subject code" required="required">
                                        <%} else {%>
                                        <input type="text" class="form-control" name="subjectCode" id="subjectCode" placeholder="Enter subject code" value="<%=subject.getSubjectCode()%>" required="required">
                                        <%}%>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label for="subjectName">Subject name*</label>
                                        <%if (action.equals("add")) {%>
                                        <input type="text" class="form-control" name="subjectName" id="subjectName" placeholder="Enter value" required="required"> 
                                        <%} else {%>
                                        <input type="text" class="form-control" name="subjectName" id="subjectName" placeholder="Enter value" value="<%=subject.getSubjectName()%>" required="required">  
                                        <%}%>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="form-group">       
                                        <label for="authorId">Manager*</label>
                                        <select id="authorId" name="authorId" class="custom-select">                                            
                                            <% for (User author : authors) {%>
                                            <%if (author.getUserId() == subject.getManagerId()) {%>
                                            <option id="authorId" value="<%=author.getUserId()%>" selected><%=author.getRollNumber()%> - <%=author.getFullName()%></option>
                                            <%} else {%>
                                            <option id="authorId" value="<%=author.getUserId()%>"><%=author.getRollNumber()%> - <%=author.getFullName()%></option>  
                                            <%}%>                                            
                                            <%}%>                                                                                                                   
                                        </select>                                        
                                    </div>
                                </div>                                
                            </div>
                            <div class="row">
                                <div class="col-lg-6">                                   
                                    <div class="form-group">     
                                        <label for="status">Status</label>
                                        <div class="d-flex">
                                            <div>
                                                <%if (subject.isStatus() == true) {%>
                                                <input type="radio" id="status" name="status" value="true" checked="checked">                                                
                                                <%} else {%>
                                                <input type="radio" id="status" name="status" value="true">
                                                <%}%>
                                                <label for="status">Active</label>
                                            </div>
                                            <div class="ml-3">
                                                <%if (subject.isStatus() == false) {%>
                                                <input type="radio" id="status" name="status" value="false" checked="checked">                                                
                                                <%} else {%>
                                                <input type="radio" id="status" name="status" value="false">
                                                <%}%>
                                                <label for="status">Inactive</label>
                                            </div>
                                        </div>
                                    </div>                                    
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label for="description">Description</label>
                                        <%if (action.equals("add")) {%>
                                        <textarea rows="5" type="text" class="form-control" name="description" id="description" placeholder="Enter description"></textarea>
                                        <%} else {%>
                                        <textarea rows="5" type="text" class="form-control" name="description" id="description" placeholder="Enter description"><%=subject.getDescription()%></textarea>
                                        <%}%>                                
                                    </div>
                                </div>
                            </div>
                            <%if (action.equals("add")) {%>
                            <button type="submit" class="btn btn-lg btn-success">Add new subject</button>                             
                            <%} else {%>
                            <button type="submit" class="btn btn-lg btn-success">Edit subject</button>                             
                            <%}%>                                                           
                        </form>

                    </div>
                    <!-- /.container-fluid -->

                </div>
                <!-- End of Main Content -->

            </div>
            <!-- End of Content Wrapper -->

        </div>
        <!-- End of Page Wrapper -->

        <!-- Scroll to Top Button-->

        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="js/common/sb-admin-2.min.js" type="text/javascript"></script>       
    </body>

</html>
