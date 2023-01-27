<%-- 
    Document   : list
    Created on : Aug 19, 2022, 11:09:45 AM
    Author     : trung
--%>

<%@page import="entity_user.User"%>
<%@page import="entity_issue.Issue"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%-- 
    Document   : list
    Created on : Aug 14, 2022, 2:17:51 AM
    Author     : ADMIN
--%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage ="../../common/error/error.jsp"%>
<% List<Issue> listIssue = (ArrayList<Issue>) session.getAttribute("listIssue"); %>
<% List<User> listUser = (ArrayList<User>) session.getAttribute("listUser"); %>
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

        <title>Issue List</title>

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
                            <h1 class="h3 mb-0 text-gray-800 ml-5">List of issues</h1>                                                        
                        </div>

                        <!-- Content Row -->
                        <div class="row flex-row justify-content-between">

                            <!--Search bar-->
                            <form id="searchForm" action="${pageContext.request.contextPath}/issue" method="POST">   
                                <input type="hidden" name="action" value="search">
<!--                                <div class="form-group ml-5">                                    
                                    <select id="searchTeam" name="searchTeam" class="custom-select">
                                        <option id="opAll" value="all" selected="selected">All Team</option>                                       <                                                                                                                 
                                    </select>                                        
                                </div>-->
                                <div class="form-group ml-5">                                       
                                    <input name="searchTitle" id="searchTitle" type="text" class="form-control" placeholder="Enter title">                                                                    
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

                            <!--Sync button-->
                            <form id="syncGitlab" action="${pageContext.request.contextPath}/issue" method="POST">                                    
                                <input type="hidden" name="action" value="syncGitlab">                                
                                <a href="javascript:;" onclick="document.getElementById('syncGitlab').submit();"
                                   class="d-sm-inline-block btn btn-primary btn-icon-split mr-5">
                                    <span class="icon text-white-50">
                                        <i class="fas fa-plus"></i>
                                    </span>
                                    <span class="text">Sync Gitlab</span>
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
                                            <th onclick="sortTable(1)">Title</th>
                                            <th onclick="sortTable(2)">Type</th>                                            
                                            <th onclick="sortTable(3)">Functions</th>                                            
                                            <th onclick="sortTable(4)">Milestone</th>
                                            <th onclick="sortTable(5)">Assigner</th>                                            
                                            <th onclick="sortTable(6)">Assignee</th>                                            
                                            <th onclick="sortTable(7)">Status</th>
                                            <th></th>                                            
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <%
                                            for (int i = startIndex; i < endIndex; i++) {
                                        %>
                                        <tr>
                                            <td><%=listIssue.get(i).getIssueId()%></td>                                            
                                            <td><%=listIssue.get(i).getTitle()%></td>
                                            <td><%=listIssue.get(i).getTypeId()%></td>
                                            <td><%=listIssue.get(i).getFunctionIds()%></td>                                            
                                            <td><%=listIssue.get(i).getMilestoneId()%></td>
                                            <td>
                                                <%for (User user : listUser) {
                                                        if (listIssue.get(i).getAssignerId() == user.getUserId()) {%>
                                                <%=user.getFullName()%>
                                                <%}
                                                    }%>                                                
                                            </td>  
                                            <td>
                                                <%for (User user : listUser) {
                                                        if (listIssue.get(i).getAssigneeId() == user.getUserId()) {%>
                                                <%=user.getFullName()%>
                                                <%}
                                                    }%>                                                
                                            </td>                                          
                                            <td><%=listIssue.get(i).getStatusId()%></td>

                                            <td>
                                                <div class="inline_center">    
                                                    <input type="hidden" name="issueId" value="<%= listIssue.get(i).getIssueId()%>">
                                                    <input type="hidden" name="action" value="edit">                                                        
                                                    <a href="<%= listIssue.get(i).getUrl()%>" target="_blank"
                                                       class="d-sm-inline-block btn btn-primary btn-icon-split mr-4">
                                                        <span class="icon text-white-50">
                                                            <i class="fas fa-pen"></i>
                                                        </span>
                                                        <span class="text">View</span>
                                                    </a>                                                         
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
                                <form id="pagingForm<%=i%>" action="${pageContext.request.contextPath}/issue" method="POST">                                    
                                    <input type="hidden" name="action" value="paging">                                
                                    <input type="hidden" name="pageNumb" id="pageNumb" value="<%=pageNumb%>">                                               

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

