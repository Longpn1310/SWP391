<%-- 
    Document   : detail
    Created on : Aug 16, 2022, 10:20:31 AM
    Author     : trung
--%>

<%@page import="entity_function.ClassSetting"%>
<%@page import="entity_team.Team"%>
<%@page import="entity_function.Function"%>"
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage ="../../common/error/error.jsp"%>
<% String action = (String) request.getAttribute("action");%>
<% List<String> listFeature = (ArrayList<String>) request.getAttribute("listFeature");%>
<% List<ClassSetting> listClassSetting = (ArrayList<ClassSetting>) request.getAttribute("listClassSetting"); %>
<% Function function = (Function) request.getAttribute("function"); %>
<% List<Team> listTeam = (ArrayList<Team>) request.getAttribute("listTeam"); %>
<% String submitStatus = (String) request.getAttribute("submitStatus"); %>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Function Details</title>

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
            <jsp:include page="../../common/fragment/student/sidebar.jsp" />
            <!-- End of Sidebar -->

            <!-- Content Wrapper -->
            <div id="content-wrapper" class="d-flex flex-column">

                <!-- Main Content -->
                <div id="content">

                    <!-- Topbar -->
                    <jsp:include page="../../common/fragment/student/topbar.jsp" />
                    <!-- End of Topbar -->

                    <!-- Begin Page Content -->
                    <div class="container-fluid">

                        <!-- Page Heading -->
                        <div class="d-sm-flex align-items-center justify-content-between mb-4">
                            <%if (action.equals("add")) {%>
                            <h1 class="h3 mb-0 text-gray-800">Add new function</h1>  
                            <%} else {%>
                            <h1 class="h3 mb-0 text-gray-800">Edit function</h1>
                            <%}%>                  
                        </div>
                        <form method="POST" action="${pageContext.request.contextPath}/function">
                            <%if (action.equals("add")) {%>
                            <input type="hidden" name="action" value="add">
                            <%} else {%>
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="functionId" value="<%=function.getFunctionId()%>">
                            <%}%>
                            <!-- Content Row -->                            
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label for="functionTitle">Function title*</label>
                                        <%if (action.equals("add")) {%>
                                        <input type="text" class="form-control" name="functionTitle" id="functionTitle" placeholder="Enter function title" required="required">
                                        <%} else {%>
                                        <input type="text" class="form-control" name="functionTitle" id="functionTitle" placeholder="Enter function title" value="<%=function.getTitle()%>" required="required">
                                        <%}%>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label for="functionFeature">Function feature*</label>
                                        <%if (action.equals("add")) {%>

                                        <div class="form-group ml-6">                                    
                                            <select id="functionFeature" name="functionFeature" contenteditable="true" class="custom-select" ">
                                                <% for (String feature : listFeature) {%>
                                                <option id="functionFeature" value="<%= feature%>"><%= feature%></option>
                                                <% } %>
                                            </select>                                        
                                        </div>
                                        <%} else {%>

                                        <div class="form-group ml-6">                                    
                                            <select id="functionFeature" name="functionFeature" class="custom-select" value="<%= function.getFeature()%>">
                                                <% for (String feature : listFeature) {%>
                                                <option id="functionFeature" value="<%= feature%>" <%if (feature.equals(String.valueOf(function.getFeature())) ) { %> selected <%}%>><%= feature%></option>
                                                <% } %>
                                            </select>                                        
                                        </div>
                                        <%}%>
                                    </div>
                                </div>
                            </div>   

                            <div class="row">
                                <div class="col-lg-3">

                                    <div class="form-group">
                                        <label for="priority">Priority*</label>
                                        <%if (action.equals("add")) {%>
                                        <input type="text" class="form-control" name="priority" id="priority" required="required">
                                        <%} else {%>
                                        <input type="text" class="form-control" name="priority" id="priority" value="<%= function.getPriority()%>" required="required">
                                        <%}%>
                                    </div>
                                </div>
                                <div class="col-lg-3">
                                    <div class="form-group">
                                        <label for="status">Status*:</label>
                                        <%if (action.equals("add")) {%>

                                        <div class="form-group ml-6">                                    
                                            <select id="status" name="status" class="custom-select" ">
                                                <% for (ClassSetting classSetting : listClassSetting) {%>
                                                <option id="status" value="<%= classSetting.getSettingId()%>" ><%= classSetting.getSettingTitle()%></option>
                                                <% } %>
                                            </select>                                        
                                        </div>
                                        <%} else {%>

                                        <div class="form-group ml-6">                                    
                                            <select id="status" name="status" class="custom-select" value="<%= function.getStatusId()%>">
                                                <% for (ClassSetting classSetting : listClassSetting) {%>
                                                <option id="status" value="<%= classSetting.getSettingId()%>" <%if (classSetting.getSettingId() == function.getStatusId()) { %> selected <%}%>><%= classSetting.getSettingTitle()%></option>
                                                <% } %>
                                            </select>                                        
                                        </div>
                                        <%}%>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-lg-3">
                                    <div class="form-group">
                                        <label for="submitStatus">Submit status*:</label>
                                        <%if (action.equals("add")) {%>
                                        <input type="text" class="form-control" name="submitStatus" id="submitStatus" readonly="readonly" value="<%=submitStatus%>" required="required">
                                        <%} else {%>
                                        <input type="text" class="form-control" name="submitStatus" id="submitStatus" readonly="readonly" value="<%=submitStatus%>" required="required">
                                        <%}%>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label for="description">Description</label>
                                        <%if (action.equals("add")) {%>
                                        <textarea rows="5" type="text" class="form-control" name="description" id="description" placeholder="Enter description" re></textarea>
                                        <%} else {%>
                                        <textarea rows="5" type="text" class="form-control" name="description" id="description" placeholder="Enter description"><%=function.getDescription()%></textarea>
                                        <%}%>                                
                                    </div>
                                </div>
                            </div>
                            <%if (action.equals("add")) {%>
                            <button type="submit" class="btn btn-lg btn-success">Add new function</button>                             
                            <%} else {%>
                            <button type="submit" class="btn btn-lg btn-success">Edit function  </button>                             
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
