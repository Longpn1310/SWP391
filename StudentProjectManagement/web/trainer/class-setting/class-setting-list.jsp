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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
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
                    <div class="container-fluid">                        
                        <!-- Page Heading -->
                        <div class="row mb-4">
                            <h1 class="h3 mb-0 text-gray-800 ml-5">List of class setting</h1>                                                        
                        </div>

                        <!-- Content Row -->
                        <div class="row flex-row justify-content-between">

                            <!--Search bar-->
                            <form id="searchForm" action="${pageContext.request.contextPath}/class-setting" method="Get">   
                                <input type="hidden" name="action" value="search">
                                <div class="form-group ml-5">                                    
                                    <select id="searchSettingType" name="typeId" class="custom-select">
                                        <option id="opAll" value="" selected="selected">All types</option>
                                        <c:forEach items="${settings}" var="setting">
                                            <option   value="${setting.id}"
                                                      ${setting.id == typeId ? "selected" : ""}
                                                      >${setting.title}</option>
                                        </c:forEach>                                                                                           
                                    </select>                                        
                                </div>                                
                                <div class="form-group ml-5">                                    
                                    <select id="searchStatus" name="status" class="custom-select">
                                        <option id="opAll" value="" >All status</option>
                                        <option id="opOpen" value="true" ${status ? "selected" : "" }>Active</option>
                                        <option id="opClosed" value="false" ${!status  ? "selected" : "" }>Inactive</option>                                        
                                    </select>                                        
                                </div>
                                <div class="form-group ml-5">                                       
                                    <input value="${settingValue}" name="settingValue" id="searchTitle" type="text" class="form-control" placeholder="Enter setting value">                                                                    
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
                            <form id="addForm" action="${pageContext.request.contextPath}/class-setting" method="GET">                                    
                                <input type="hidden" name="action" value="add">                                
                                <a href="javascript:;" onclick="document.getElementById('addForm').submit();"
                                   class="d-sm-inline-block btn btn-primary btn-icon-split mr-5">
                                    <span class="icon text-white-50">
                                        <i class="fas fa-plus"></i>
                                    </span>
                                    <span class="text">New class setting</span>
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
                                        <c:forEach items="${classSettings}" var="cs">
                                            <tr>
                                                <td>${cs.getSettingId()}</td>
                                                <td>${cs.getTypeName()}</td>
                                                <td>${cs.getSettingTitle()}</td>
                                                <td>${cs.getSettingValue()}</td>
                                                <td>${cs.getDisplayOrder()}</td>
                                                <td>
                                                    <a class="d-sm-inline-block btn btn-icon-split
                                                       ${cs.isStatus()?"btn-success": "btn-danger"}
                                                       ">
                                                        <span class="text">${cs.isStatus()?"Active":"Deactive"}</span>
                                                    </a>
                                                </td>
                                                <td>
                                                    <a href="class-setting?id=${cs.getSettingId()}&action=edit"
                                                       class="d-sm-inline-block btn btn-primary btn-icon-split mr-4">
                                                        <span class="icon text-white-50">
                                                            <i class="fas fa-pen"></i>
                                                        </span>
                                                        <span class="text">Edit</span>
                                                    </a>  
                                                    <c:set var="url" value="class-setting?id=${cs.getSettingId()}&status=${cs.isStatus()}&action=changestatus"/>   
                                                    <a href="javascript:;" class="d-sm-inline-block btn btn-icon-split
                                                       ${cs.isStatus()?"btn-danger": "btn-success"}
                                                       " onclick="changeStatus('${url}')"
                                                       >
                                                        <span class="icon text-white-50">
                                                            <i class="fas fa-undo"></i>
                                                        </span>
                                                        <span class="text">${cs.isStatus()?"Deactive":"Active"}</span>
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
                                <c:forEach begin="${1}" end="${totalPage}" var = "p">
                                    <div class="form-group">                                    
                                        <!--<input type="hidden" name="pageChoose" value="">-->
                                        <a href="${uri}?page=${p}"
                                           class="d-sm-inline-block btn btn-outline-primary btn-icon-split mr-2">                                        
                                            <span class="text">${p}</span>
                                        </a>                                            
                                    </div>   
                                </c:forEach>

                            </div>
                        </div>                        
                    </div>
                    <!-- /.container-fluid -->

                </div>
                <!-- End of Main Content -->
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
                                <a class="btn btn-primary" id="urlChange" >Change</a>
                            </div>
                        </div>
                    </div>
                </div>
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
        <script>
                                                           function changeStatus(url) {
//                                        console.log(url)
//                                        var doIt = confirm('Do you want to change status the record?');
//                                        if (doIt) {
//                                            window.location.href = url;
//                                        } 
    $('#urlChange').attr("href", url);
                                                               $('#myModal').modal('show');
                                                           }
        </script>
        <!-- Custom scripts for all pages-->                
        <script src="js/common/sb-admin-2.min.js" type="text/javascript"></script>          
        <script src="js/common/showChangeStatusAlert.js" type="text/javascript"></script>
        <%session.setAttribute("changeResult", null);%>
    </body>

</html>
