<%-- 
    Document   : list
    Created on : Aug 15, 2022, 6:38:52 PM
    Author     : trung
--%>

<%@page import="entity_function.Function"%>
<%@page import="entity_function.ClassSetting"%>
<%@page import="entity_team.Team"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage ="../../common/error/error.jsp"%>
<% List<ClassSetting> listClassSetting = (ArrayList<ClassSetting>) request.getAttribute("listClassSetting"); %>
<% List<Function> listFunction = (ArrayList<Function>) request.getAttribute("listFunction"); %>
<% List<Team> listTeam = (ArrayList<Team>) request.getAttribute("listTeam"); %>
<% int startIndex = (Integer) request.getAttribute("startIndex"); %>
<% int endIndex = (Integer) request.getAttribute("endIndex"); %>
<% int pageNumb = (Integer) request.getAttribute("pageNumb"); %>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Function List</title>

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
                        <div class="row mb-4">
                            <h1 class="h3 mb-0 text-gray-800 ml-5">List of function</h1>      
                            <c:remove var="message" scope="session" /> 
                        </div>

                        <!-- Content Row -->
                        <div class="row flex-row justify-content-between">

                            <!--Search bar-->
                            <form id="searchForm" action="${pageContext.request.contextPath}/function" method="POST">   
                                <input type="hidden" name="action" value="search">
                                <div class="form-group ml-5">                                    
                                    <select id="searchTeam" name="searchTeam" class="custom-select">
                                        <!--<option id="opAll" value="all" selected="selected">All Team</option>-->
                                        <% for (Team team : listTeam) {%>
                                        <option id="searchTeam" value="<%= team.getTeam_id()%>"  <%if ((request.getAttribute("searchTeam") != null) && request.getAttribute("searchTeam").equals(String.valueOf(team.getTeam_id()))) { %> selected <%}%>><%=team.getTeam_id()%></option>
                                        <%}%>                                                                                                                   
                                    </select>                                        
                                </div>
                                <div class="form-group ml-5">                                    
                                    <select id="searchStatus" name="searchStatus" class="custom-select">
                                        <option id="opAll" value="all" selected="selected">All Status</option>
                                        <% for (ClassSetting classSetting : listClassSetting) {%>
                                        <option id="searchStatus" value="<%= classSetting.getSettingId()%>" <%if ((request.getAttribute("searchStatus") != null) && request.getAttribute("searchStatus").equals(String.valueOf(classSetting.getSettingId()))) { %> selected <%}%> ><%= classSetting.getSettingTitle()%></option>
                                        <%}%>                                                                                                                   
                                    </select>                                        
                                </div>   
                                <div class="form-group ml-5">                                    
                                    <select id="searchSubmitStatus" name="searchSubmitStatus" class="custom-select">
                                        <option id="searchSubmitStatus" value="all" selected="selected" >All Submit Status</option>
                                        <option id="searchSubmitStatus" value="1" <%if ((request.getAttribute("searchSubmitStatus") != null) && request.getAttribute("searchSubmitStatus").equals(String.valueOf(1))) { %> selected <%} %>>Pending</option>
                                        <option id="searchSubmitStatus" value="2" <%if ((request.getAttribute("searchSubmitStatus") != null) && request.getAttribute("searchSubmitStatus").equals(String.valueOf(2))) { %> selected <%} %>>Committed</option>  
                                        <option id="searchSubmitStatus" value="3" <%if ((request.getAttribute("searchSubmitStatus") != null) && request.getAttribute("searchSubmitStatus").equals(String.valueOf(3))) { %> selected <%} %>>Submitted</option>
                                        <option id="searchSubmitStatus" value="4" <%if ((request.getAttribute("searchSubmitStatus") != null) && request.getAttribute("searchSubmitStatus").equals(String.valueOf(4))) { %> selected <%} %>>Rejected</option>   
                                        <option id="searchSubmitStatus" value="5" <%if ((request.getAttribute("searchSubmitStatus") != null) && request.getAttribute("searchSubmitStatus").equals(String.valueOf(5))) { %> selected <%}%>>Evaluated</option>  
                                    </select>                                        
                                </div>
                                <div class="form-group ml-5">           
                                    <!--<input type="hidden" name="user" value= "<%=request.getParameter("user")%>"/>-->     
                                    <% if (request.getAttribute("searchTitle") != null) {%>
                                    <input name="searchTitle" id="searchTitle" type="text" class="form-control" value="<%= request.getAttribute("searchTitle")%>" placeholder="Enter name or feature">    
                                    <%} else { %>
                                    <input name="searchTitle" id="searchTitle" type="text" class="form-control" placeholder="Enter name or feature">    
                                    <%} %>
                                    </a>                           
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

                            <!--impot button-->
<!--                            <form id="importForm" action="${pageContext.request.contextPath}/function" method="POST">                                    
                                <input type="hidden" name="action" value="import">                                
                                                                <a href="javascript:;" onclick="document.getElementById('importForm').submit();"
                                                                   class="d-sm-inline-block btn btn-primary btn-icon-split mr-5">
                                                                    <span class="icon text-white-50">
                                                                        <i class="fas fa-plus"></i>
                                                                    </span>
                                                                    <span class="text">New Function</span>
                                                                </a>     
                                <input class="d-sm-inline-block btn btn-primary btn-icon-split mr-5" type="file" name="file" onchange="document.getElementById('importForm').submit();"/>
                            </form>        -->

                            <!--Add button-->
                            <form id="addForm" action="${pageContext.request.contextPath}/function" method="GET">                                    
                                <input type="hidden" name="action" value="add">                                
                                <a href="javascript:;" onclick="document.getElementById('addForm').submit();"
                                   class="d-sm-inline-block btn btn-primary btn-icon-split mr-5">
                                    <span class="icon text-white-50">
                                        <i class="fas fa-plus"></i>
                                    </span>
                                    <span class="text">New Function</span>
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
                                            <th onclick="sortTable(1)">Function</th>
                                            <th onclick="sortTable(2)">Feature</th>                                            
                                            <th onclick="sortTable(3)">Priority</th>                                            
                                            <th onclick="sortTable(4)">Status</th>                                       
                                            <th onclick="sortTable(5)">Submit Status</th>
                                            <th></th>                                            
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <%
                                            for (int i = startIndex; i < endIndex; i++) {
                                        %>
                                        <tr>
                                            <td><%=listFunction.get(i).getFunctionId()%></td>                                            
                                            <td><%=listFunction.get(i).getTitle()%></td>
                                            <td><%=listFunction.get(i).getFeature()%></td>
                                            <td><%=listFunction.get(i).getPriority()%></td>
                                            <td>
                                                <%for (ClassSetting classSetting : listClassSetting) {
                                                        if (classSetting.getSettingId() == listFunction.get(i).getStatusId()) {%>
                                                <%=classSetting.getSettingTitle()%>
                                                <%}
                                                    }%>                                                
                                            </td>                                                                                            
                                            <%if (listFunction.get(i).getSubmitStatus() == 1) {%>
                                            <td>Pending</td>
                                            <%} else if (listFunction.get(i).getSubmitStatus() == 2) {%>                                                 
                                            <td>Committed</td>                                            
                                            <%} else if (listFunction.get(i).getSubmitStatus() == 3) {%>                                                 
                                            <td>Submitted</td>                                            
                                            <%} else if (listFunction.get(i).getSubmitStatus() == 4) {%>                                                 
                                            <td>Rejected</td>                                            
                                            <%} else if (listFunction.get(i).getSubmitStatus() == 5) {%>                                                 
                                            <td>Evaluated</td>                                            
                                            <%}%>
                                            <td>
                                                <div class="inline_center">    
                                                    <form id="editForm<%=listFunction.get(i).getFunctionId()%>" action="${pageContext.request.contextPath}/function" method="GET">
                                                        <input type="hidden" name="functionId" value="<%= listFunction.get(i).getFunctionId()%>">
                                                        <input type="hidden" name="action" value="edit">                                                        
                                                        <a href="javascript:;" onclick="document.getElementById('editForm<%=listFunction.get(i).getFunctionId()%>').submit();"
                                                           class="d-sm-inline-block btn btn-primary btn-icon-split mr-4">
                                                            <span class="icon text-white-50">
                                                                <i class="fas fa-pen"></i>
                                                            </span>
                                                            <span class="text">Edit</span>
                                                        </a>
                                                    </form>                                                                                                                                                               

                                                    <form id="editStatusForm<%=listFunction.get(i).getFunctionId()%>" action="${pageContext.request.contextPath}/function" method="POST">
                                                        <input type="hidden" name="functionId" value="<%= listFunction.get(i).getFunctionId()%>">
                                                        <input type="hidden" name="action" value="change">
                                                        <select id="changeStatus" name="changeStatus" onchange="document.getElementById('editStatusForm<%=listFunction.get(i).getFunctionId()%>').submit();" class="custom-select">
                                                            <option id="opAll" value="all" selected="selected">Update Status</option>
                                                            <% for (ClassSetting classSetting : listClassSetting) {%>
                                                            <option id="changeStatus" value="<%= classSetting.getSettingId()%>" <%if ((request.getAttribute("changeStatus") != null) && request.getAttribute("changeStatus").equals(String.valueOf(classSetting.getSettingId()))) { %> selected <%}%> ><%= classSetting.getSettingTitle()%></option>
                                                            <%}%>                                                                                                                   
                                                        </select>                                                        
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
                                <form id="pagingForm<%=i%>" action="${pageContext.request.contextPath}/function" method="POST">                                    
                                    <input type="hidden" name="action" value="paging">                                
                                    <input type="hidden" name="pageNumb" value="<%=pageNumb%>">           
                                    <%session.setAttribute("listFunction", listFunction);%>
                                    <%session.setAttribute("listTeam", listTeam);%>
                                    <%session.setAttribute("listClassSetting", listClassSetting);%>
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

        <!-- End of Page Wrapper -->        
        <input type="hidden" id="showAlert" value='<%= (Integer) session.getAttribute("changeResult")%>' />
        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js" type="text/javascript"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js" type="text/javascript"></script>

        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js" type="text/javascript"></script>  

        <!-- Custom scripts for all pages-->
        <script src="js/common/sb-admin-2.min.js" type="text/javascript"></script>                  
        <script src="js/common/sort-table.js" type="text/javascript"></script>
        <script src="js/common/open-modal.js" type="text/javascript"></script>
        <%session.setAttribute(
                    "changeResult", null);%>
    </body>

</html>