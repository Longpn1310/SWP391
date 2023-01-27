<%-- 
    Document   : list
    Created on : Aug 8, 2022, 11:25:11 PM
    Author     : ADMIN
--%>

<%@page import="entity_setting.Setting"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage ="../../common/error/error.jsp"%>
<% List<Setting> settings = (ArrayList<Setting>) request.getAttribute("settings"); %>
<% List<Setting> settingTypes = (ArrayList<Setting>) request.getAttribute("settingTypes"); %>
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
        <title>Setting List</title>        
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
                        <div class="row mb-4">
                            <h1 class="h3 mb-0 text-gray-800 ml-5">List of setting</h1>                                                        
                        </div>

                        <!-- Content Row -->
                        <div class="row flex-row justify-content-between">

                            <!--Search bar-->
                            <form id="searchForm" action="${pageContext.request.contextPath}/setting" method="POST">   
                                <input type="hidden" name="action" value="search">
                                <div class="form-group ml-5">                                    
                                    <select id="searchSettingType" name="searchSettingType" class="custom-select">
                                        <option id="opAll" value="all" selected="selected">All setting types</option>
                                        <% for (Setting settingType : settingTypes) {%>
                                        <option id="settingType" value="<%=settingType.getTypeId()%>"><%=settingType.getTypeTitle()%></option>
                                        <%}%>                                                                                                                   
                                    </select>                                        
                                </div>                                
                                <div class="form-group ml-5">                                    
                                    <select id="searchStatus" name="searchStatus" class="custom-select">
                                        <option id="opAll" value="all" selected="selected">All status</option>
                                        <option id="opOpen" value="true">Active</option>
                                        <option id="opClosed" value="false">Inactive</option>                                        
                                    </select>                                        
                                </div>
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

                            <!--Add button-->
                            <form id="addForm" action="${pageContext.request.contextPath}/setting" method="GET">                                    
                                <input type="hidden" name="action" value="add">                                
                                <a href="javascript:;" onclick="document.getElementById('addForm').submit();"
                                   class="d-sm-inline-block btn btn-primary btn-icon-split mr-5">
                                    <span class="icon text-white-50">
                                        <i class="fas fa-plus"></i>
                                    </span>
                                    <span class="text">New setting</span>
                                </a>                                
                            </form>
                        </div>

                        <!--Table-->
                        <div class="row">                                                        
                            <div class="table-responsive mr-5 ml-5">                                
                                <table class="table">                                    
                                    <thead>
                                        <tr>
                                            <th scope="col">Id</th>   
                                            <th scope="col">Type</th>
                                            <th scope="col">Title</th>                                            
                                            <th scope="col">Value</th>
                                            <th scope="col">Display Order</th>
                                            <th scope="col">Status</th>
                                            <th></th>                                            
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <%
                                            for (int i = startIndex; i < endIndex; i++) {
                                        %>
                                        <tr>
                                            <td><%=settings.get(i).getId()%></td>                                            
                                            <td><%=settings.get(i).getTypeTitle()%></td>
                                            <td><%=settings.get(i).getTitle()%></td>
                                            <td><%=settings.get(i).getValue()%></td> 
                                            <td><%=settings.get(i).getOrder()%></td>                                                                                           
                                            <%if (settings.get(i).isStatus() == true) {%>
                                            <td>Active</td>
                                            <%} else {%>                                                 
                                            <td>Inactive</td>                                            
                                            <%}%>
                                            <td>
                                                <div class="inline_center">    
                                                    <form id="editForm<%=settings.get(i).getId()%>" action="${pageContext.request.contextPath}/setting" method="GET">
                                                        <input type="hidden" name="settingId" value="<%= settings.get(i).getId()%>">
                                                        <input type="hidden" name="action" value="edit">                                                        
                                                        <a href="javascript:;" onclick="document.getElementById('editForm<%=settings.get(i).getId()%>').submit();"
                                                           class="d-sm-inline-block btn btn-primary btn-icon-split mr-4">
                                                            <span class="icon text-white-50">
                                                                <i class="fas fa-pen"></i>
                                                            </span>
                                                            <span class="text">Edit</span>
                                                        </a>
                                                    </form>                                                                                                                                                               

                                                    <form id="deactiveForm<%=settings.get(i).getId()%>" action="${pageContext.request.contextPath}/setting" method="POST">
                                                        <input type="hidden" name="settingId" value="<%= settings.get(i).getId()%>">
                                                        <input type="hidden" name="settingStatus" value="<%= settings.get(i).isStatus()%>">
                                                        <input type="hidden" name="action" value="change">
                                                        <%if (settings.get(i).isStatus() == true) {%>
                                                        <a href="javascript:;" onclick="document.getElementById('deactiveForm<%=settings.get(i).getId()%>').submit();"
                                                           class="d-sm-inline-block btn btn-danger btn-icon-split">
                                                            <span class="icon text-white-50">
                                                                <i class="fas fa-undo"></i>
                                                            </span>
                                                            <span class="text">Deactivate</span>
                                                        </a>
                                                        <%} else {%>                                                            
                                                        <a href="javascript:;" onclick="document.getElementById('deactiveForm<%=settings.get(i).getId()%>').submit();"
                                                           class="d-sm-inline-block btn btn-success btn-icon-split">
                                                            <span class="icon text-white-50">
                                                                <i class="fas fa-undo"></i>
                                                            </span>
                                                            <span class="text">Activate</span>
                                                        </a>                                                        
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
                                <form id="pagingForm<%=i%>" action="${pageContext.request.contextPath}/setting" method="POST">                                    
                                    <input type="hidden" name="action" value="paging">                                
                                    <input type="hidden" name="pageNumb" value="<%=pageNumb%>">           
                                    <%session.setAttribute("listofSettings", settings);%>
                                    <%session.setAttribute("settingTypes", settingTypes);%>
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
        <!-- End of Page Wrapper -->        
        <input type="hidden" id="showAlert" value='<%= (Integer) session.getAttribute("changeResult")%>' />
        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        
        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->                
        <script src="js/common/sb-admin-2.min.js" type="text/javascript"></script>          
        <script src="js/common/showChangeStatusAlert.js" type="text/javascript"></script>
        <%session.setAttribute("changeResult", null);%>
    </body>

</html>
