<%-- 
    Document   : list
    Created on : Aug 14, 2022, 4:44:32 AM
    Author     : ADMIN
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entity_user.User"%>
<%@page import="entity_milestone.Milestone"%>
<%@page import="entity_class.Class"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage ="../../common/error/error.jsp"%>
<% List<Milestone> milestones = (ArrayList<Milestone>) session.getAttribute("milestones");%>
<% List<Class> classes = (ArrayList<Class>) session.getAttribute("classes"); %>
<% int startIndex = (Integer) session.getAttribute("startIndex"); %>
<% int endIndex = (Integer) session.getAttribute("endIndex"); %>
<% int pageNumb = (Integer) session.getAttribute("pageNumb"); %>
<% User user = (User) session.getAttribute("user1");%>
<% String searchStatus = (String) request.getAttribute("searchStatus");%>
<% String searchClassId = (String) request.getAttribute("classId");%>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Milestone List</title>

        <!-- Custom fonts for this template-->
        <link href="vendor/fontawesome-free/css/all.css" rel="stylesheet" type="text/css"/>  
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="css/common/sb-admin-2.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/common/global.css" rel="stylesheet" type="text/css"/>       
    </head>

    <body id="page-top">
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
                    <%if (user.getRoleId() == 3) {%>
                    <div class="container-fluid">

                        <!-- Page Heading -->
                        <div class="row mb-4">
                            <h1 class="h3 mb-0 text-gray-800 ml-5">List of milestone</h1>                                                        
                        </div>

                        <!-- Content Row -->
                        <div class="row flex-row justify-content-between">

                            <!--Search bar-->                                                                
                            <form id="searchForm" action="${pageContext.request.contextPath}/milestone" method="POST">   
                                <input type="hidden" name="action" value="search">         
                                <div class="form-group ml-5">                                    
                                    <select id="searchClass" name="searchClass" class="custom-select">
                                        <option id="opAll" value="all">All classes</option>                                        
                                        <% for (Class classe : classes) {%>
                                        <%if (searchClassId != null) {
                                                if (Integer.parseInt(searchClassId) == classe.getClassId()) {%>
                                        <option id="searchClass" value="<%=classe.getClassId()%>" selected="selected"><%=classe.getClassCode()%></option>
                                        <%} else {%>
                                        <option id="searchClass" value="<%=classe.getClassId()%>"><%=classe.getClassCode()%></option>
                                        <%}%>
                                        <%} else {%>
                                        <option id="searchClass" value="<%=classe.getClassId()%>"><%=classe.getClassCode()%></option>
                                        <%}
                                        %>
                                        <%}%>                                                                                                                   
                                    </select>                                        
                                </div>
                                <div class="form-group ml-5">                                    
                                    <select id="searchStatus" name="searchStatus" class="custom-select">
                                        <option id="opAll" value="all">All status</option>
                                        <%if (searchStatus != null) {
                                                if (Boolean.parseBoolean(searchStatus) == true) {%>
                                        <option id="opOpen" value="true" selected="selected" >Open</option>
                                        <option id="opClosed" value="false" >Closed</option> 
                                        <%} else {%>
                                        <option id="opOpen" value="true" selected="selected">Open</option>
                                        <option id="opClosed" value="false" selected="selected">Closed</option> 
                                        <%}%>
                                        <%} else {%>
                                        <option id="opOpen" value="true">Open</option>
                                        <option id="opClosed" value="false">Closed</option>    
                                        <%}%>                                        
                                    </select>                                        
                                </div>                                
                                <div class="form-group ml-5">                                           
                                    <a href="javascript:;" onclick="document.getElementById('searchForm').submit();"
                                       class="btn btn-primary btn-icon-split mr-4">
                                        <span class="icon text-white-50">
                                            <i class="fas fa-search"></i>
                                        </span>
                                        <span class="text">Search</span>
                                    </a>                           
                                </div>
                            </form>

                            <!--Add button-->
                            <form id="addForm" action="${pageContext.request.contextPath}/milestone" method="GET">                                    
                                <input type="hidden" name="action" value="add">                                
                                <a href="javascript:;" onclick="document.getElementById('addForm').submit();"
                                   class="d-sm-inline-block btn btn-primary btn-icon-split mr-5">
                                    <span class="icon text-white-50">
                                        <i class="fas fa-plus"></i>
                                    </span>
                                    <span class="text">New milestone</span>
                                </a>                                
                            </form>

                        </div>

                        <!--Table-->                        
                        <div class="row">                                                        
                            <div class="table-responsive mr-5 ml-5">                                
                                <table class="table" id="listTable">                                    
                                    <thead>
                                        <tr>
                                            <th onclick="sortTable(0)">Id</th>
                                            <th onclick="sortTable(1)">Iteration</th>
                                            <th onclick="sortTable(2)">From date</th>
                                            <th onclick="sortTable(3)">To date</th>                                                                                      
                                            <th onclick="sortTable(4)">Status</th>
                                            <th></th>                                            
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <%
                                            for (int i = startIndex; i < endIndex; i++) {
                                        %>
                                        <tr>
                                            <td><%=milestones.get(i).getMilestoneId()%></td>
                                            <td><%=milestones.get(i).getIterationName()%></td>
                                            <%SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");%>
                                            <%if (milestones.get(i).getFromDate() != null) {%>
                                            <td><%=formatter.format(milestones.get(i).getFromDate())%></td>
                                            <%} else {%>
                                            <td></td>
                                            <%}%>
                                            <%if (milestones.get(i).getToDate() != null) {%>
                                            <td><%=formatter.format(milestones.get(i).getToDate())%></td>
                                            <%} else {%>
                                            <td></td>
                                            <%}%>



                                            <%if (milestones.get(i).isStatus() == true) {%>
                                            <td>Open</td>
                                            <%} else {
                                                if (milestones.get(i).isStatus() == false) {%>
                                            <td>Closed</td>
                                            <%} else {%>
                                            <td>Cancelled</td>
                                            <%}
                                                }%>
                                            <td>

                                                <div class="inline_center">    

                                                    <form id="editForm<%=milestones.get(i).getMilestoneId()%>" action="${pageContext.request.contextPath}/milestone" method="GET">
                                                        <input type="hidden" name="milestoneId" value="<%= milestones.get(i).getMilestoneId()%>">
                                                        <input type="hidden" name="classId" value="<%= milestones.get(i).getClassId()%>">
                                                        <input type="hidden" name="action" value="edit">                                                        
                                                        <a href="javascript:;" onclick="document.getElementById('editForm<%=milestones.get(i).getMilestoneId()%>').submit();"
                                                           class="d-sm-inline-block btn btn-primary btn-icon-split mr-4">
                                                            <span class="icon text-white-50">
                                                                <i class="fas fa-pen"></i>
                                                            </span>
                                                            <span class="text">Edit</span>
                                                        </a>
                                                    </form>                                                                                                                                                               

                                                    <form id="deactiveForm<%=milestones.get(i).getMilestoneId()%>" action="${pageContext.request.contextPath}/milestone" method="POST">
                                                        <input type="hidden" name="milestoneId" value="<%= milestones.get(i).getMilestoneId()%>">
                                                        <input type="hidden" name="milestoneStatus" value="<%= milestones.get(i).isStatus()%>">
                                                        <input type="hidden" name="action" value="change">
                                                        <%if (milestones.get(i).isStatus() == true) {%>
                                                        <a href="javascript:;" onclick="document.getElementById('deactiveForm<%=milestones.get(i).getMilestoneId()%>').submit();"
                                                           class="d-sm-inline-block btn btn-danger btn-icon-split">
                                                            <span class="icon text-white-50">
                                                                <i class="fas fa-undo"></i>
                                                            </span>
                                                            <span class="text">Close</span>
                                                        </a>
                                                        <%} else {
                                                            if (milestones.get(i).isStatus() == false) {%>
                                                        <a href="javascript:;" onclick="document.getElementById('deactiveForm<%=milestones.get(i).getMilestoneId()%>').submit();"
                                                           class="d-sm-inline-block btn btn-success btn-icon-split">
                                                            <span class="icon text-white-50">
                                                                <i class="fas fa-undo"></i>
                                                            </span>
                                                            <span class="text">Re-open</span>
                                                        </a>
                                                        <%}%> 
                                                        <%}%>                                                           
                                                    </form>                                                        
                                                </div>
                                            </td>                                            
                                        </tr>
                                        <%}%>                                       
                                    </tbody>
                                </table>
                            </div>
                        </div>    
                        <div class="ml-5">
                            <div class="row flex-row">                                    
                                <%for (int i = 1; i <= pageNumb; i++) {%>
                                <form id="pagingForm<%=i%>" action="${pageContext.request.contextPath}/milestone" method="POST">                                    
                                    <input type="hidden" name="action" value="paging">                                                                                                                
                                    <div class="form-group">                                    
                                        <input type="hidden" name="pageChoose" value="<%=i%>">
                                        <a href="javascript:;" onclick="document.getElementById('pagingForm<%=i%>').submit();"
                                           class="d-sm-inline-block btn btn-outline-primary btn-icon-split mr-2">                                        
                                            <span class="text"><%=i%></span>
                                        </a>                                            
                                    </div>                                
                                </form>
                                <%}%>
                            </div>
                        </div> 
                    </div>
                    <%}%>
                    <%if (user.getRoleId() == 4) {%>
                    <!-- Begin Page Content -->
                    <div class="container-fluid">

                        <!-- Page Heading -->
                        <div class="row mb-4">
                            <h1 class="h3 mb-0 text-gray-800 ml-5">List of milestone</h1>                                                        
                        </div>                        

                        <!--Table-->                        
                        <div class="row">                                                        
                            <div class="table-responsive mr-5 ml-5">                                
                                <table class="table" id="listTable">                                    
                                    <thead>
                                        <tr>
                                            <th onclick="sortTable(0)">Id</th>
                                            <th onclick="sortTable(1)">Iteration</th>
                                            <th onclick="sortTable(2)">From date</th>
                                            <th onclick="sortTable(3)">To date</th>                                                                                      
                                            <th onclick="sortTable(4)">Status</th>                                                          
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <%
                                            for (int i = startIndex; i < endIndex; i++) {
                                        %>
                                        <tr>
                                            <td><%=milestones.get(i).getMilestoneId()%></td>
                                            <td><%=milestones.get(i).getIterationName()%></td>
                                            <td><%=milestones.get(i).getFromDate()%></td>
                                            <td><%=milestones.get(i).getToDate()%></td>                                            
                                            <%if (milestones.get(i).isStatus() == true) {%>
                                            <td>Open</td>
                                            <%} else {
                                                if (milestones.get(i).isStatus() == false) {%>
                                            <td>Closed</td>
                                            <%} else {%>
                                            <td>Cancelled</td>
                                            <%}
                                                }%>                                                                                    
                                        </tr>
                                        <%}%>                                       
                                    </tbody>
                                </table>
                            </div>
                        </div>    
                        <div class="ml-5">
                            <div class="row flex-row">                                    
                                <%for (int i = 1; i <= pageNumb; i++) {%>
                                <form id="pagingForm<%=i%>" action="${pageContext.request.contextPath}/milestone" method="POST">                                    
                                    <input type="hidden" name="action" value="paging">                                
                                    <input type="hidden" name="pageNumb" value="<%=pageNumb%>">                                               
                                    <div class="form-group">                                    
                                        <input type="hidden" name="pageChoose" value="<%=i%>">
                                        <a href="javascript:;" onclick="document.getElementById('pagingForm<%=i%>').submit();"
                                           class="d-sm-inline-block btn btn-outline-primary btn-icon-split mr-2">                                        
                                            <span class="text"><%=i%></span>
                                        </a>                                            
                                    </div>                                
                                </form>
                                <%}%>
                            </div>
                        </div> 
                    </div>
                    <%}%>                    
                    <!-- /.container-fluid -->
                </div>
                <!-- End of Main Content -->

                <!-- Footer -->
                <footer class="sticky-footer bg-white">
                    <div class="container my-auto">          
                    </div>
                </footer>
                <!-- End of Footer -->

            </div>
            <!-- End of Content Wrapper -->

        </div>
        <!-- End of Page Wrapper -->       
        <!-- Alert Modal-->
        <div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Message:</h5>
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <%if (session.getAttribute("changeResult") != null) {
                                if ((Integer) session.getAttribute("changeResult") == 1) {%>
                        Change success
                        <%} else {
                            if ((Integer) session.getAttribute("changeResult") == 0) {%>
                        Change failed
                        <%} else {
                            if ((Integer) session.getAttribute("changeResult") == 3) {%>
                        Data exist
                        <%} else {%>
                        aaaa
                        <%}
                                    }
                                }
                            }%>                        
                    </div>
                    <div class="modal-footer">                        
                        <button class="btn btn-primary" type="button" data-dismiss="modal">Ok</button>                        
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="showAlert" value='<%= (Integer) session.getAttribute("changeResult")%>' />

        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js" type="text/javascript"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js" type="text/javascript"></script>

        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js" type="text/javascript"></script>  

        <!-- Custom scripts for all pages-->
        <script src="js/common/sb-admin-2.min.js" type="text/javascript"></script>
        <script src="js/common/open-modal.js" type="text/javascript"></script>        
        <script src="js/common/sort-table.js" type="text/javascript"></script>
        <%session.setAttribute("changeResult", null);%>
    </body>

</html>
