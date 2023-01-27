<%-- 
    Document   : subject-setting-list
    Created on : Aug 14, 2022, 4:32:59 PM
    Author     : HaiLong
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="entity_setting.Setting"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage ="../../common/error/error.jsp"%>
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
            <jsp:include page="../../common/fragment/manager/sidebar.jsp" />
            <!-- End of Sidebar -->

            <!-- Content Wrapper -->
            <div id="content-wrapper" class="d-flex flex-column">

                <!-- Main Content -->
                <div id="content">

                    <!-- Topbar -->                    
                    <jsp:include page="../../common/fragment/manager/topbar.jsp" />
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
                            <form id="searchForm" action="${pageContext.request.contextPath}/subject-setting" method="GET">   
                                <input type="hidden" name="action" value="search">
                                <div class="form-group ml-5">                                    
                                    <select id="searchSettingType" name="subjectId" class="custom-select">
                                        <c:forEach items="${subjects}" var="subject">
                                            <option ${subjectId == subject.subjectId?"selected": ""}  value="${subject.subjectId}">${subject.subjectName}</option>
                                        </c:forEach>
                                        
                                    </select>                                        
                                </div> 
                                <div class="form-group ml-5">                                    
                                    <select id="searchSettingType" name="type" class="custom-select">
                                        <option id="opAll" value="all" selected="selected">All setting types</option>
                                        <option  value="Complexity">Complexity</option>
                                        <option  value="Quantity">Quantity</option>                                                                                                 
                                    </select>                                        
                                </div>                                
                                <div class="form-group ml-5">                                    
                                    <select id="searchStatus" name="status" class="custom-select">
                                        <option id="opAll" ${status==null?"selected":""} value="all" selected="selected">All status</option>
                                        <option id="opOpen" ${status==true?"selected":""} value="true">Active</option>
                                        <option id="opClosed" ${status==false?"selected":""} value="false">Inactive</option>                                        
                                    </select>                                        
                                </div>
                                <div class="form-group ml-5">                                       
                                    <input name="searchTitle" id="searchTitle" type="text" class="form-control" placeholder="Enter setting name">                                                                    
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
                            <form id="addForm" action="${pageContext.request.contextPath}/subject-setting" method="GET">                                    
                                <input type="hidden" name="action" value="add">                                
                                <a href="javascript:;" onclick="document.getElementById('addForm').submit();"
                                   class="d-sm-inline-block btn btn-primary btn-icon-split mr-5">
                                    <span class="icon text-white-50">
                                        <i class="fas fa-plus"></i>
                                    </span>
                                    <span class="text">New subject setting</span>
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
                                            <th>Action</th>                                            
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <c:forEach items="${list}" var="ss">
                                            <tr>
                                        <td>${ss.settingId}</td>
                                        <td>${ss.typeId}</td>
                                        <td>${ss.settingTitle}</td>
                                        <td>${ss.settingValue}</td>
                                        <td>${ss.displayOrder}</td>
                                        <td>${ss.status}</td>
                                        <td>
                                            <a href="subject-setting?action=edit&id=${ss.settingId}">Edit</a>
                                            <c:set var="url" value="subject-setting?id=${ss.settingId}&status=${ss.status}&action=changestatus"/>   
                                                <a onclick="changeStatus('${url}')"
                                                    class="d-sm-inline-block btn btn-primary btn-icon-split">
                                                    <span class="icon text-white-50">
                                                                <i class="fas fa-undo"></i>
                                                            </span>
                                                    <span class="text">Change Status</span>
                                                </a>  
                                        </td>
                                            </tr>
                                        </c:forEach>
                                                                     
                                    </tbody>
                                </table>
                            </div>
                        </div>                         
                        <div class="ml-5">
                            <div class="row flex-row">                                    
                            
                              
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
<div class="modal" id="myModal" role="dialog">
                    <div class="modal-dialog modal-sm">
                        <div class="modal-content">
                            <div class="modal-header">
                                
                                <h4 class="modal-title">Confirm</h4>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>
                            <div class="modal-body">
                                <p>Do you want to change status?</p>
                            </div>
                            <div class="modal-footer">
                                <a class="btn btn-primary" id="urlChangeStatus" >Change</a>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
        <!-- End of Page Wrapper -->        
        <input type="hidden" id="showAlert" value='<%= (Integer) session.getAttribute("changeResult")%>' />
        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script>
                                                     function changeStatus(url) {
//                                                console.log(url)
//                                                var doIt = confirm('Do you want to change status the record?');
//                                                if (doIt) {
//                                                    window.location.href = url;
//                                                }
console.log(url)
                                                        $('#urlChangeStatus').attr("href", url);
                                                        $("#myModal").modal('show');
                                                    }
        </script>
        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->                
        <script src="js/common/sb-admin-2.min.js" type="text/javascript"></script>          
        <script src="js/common/showChangeStatusAlert.js" type="text/javascript"></script>
        <%session.setAttribute("changeResult", null);%>
    </body>

</html>
