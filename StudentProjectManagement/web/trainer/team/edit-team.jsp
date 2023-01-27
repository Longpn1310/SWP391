<%-- 
    Document   : details
    Created on : Aug 8, 2022, 11:25:26 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="entity_setting.Setting"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage ="../../common/error/error.jsp"%>
<% String action = (String) request.getAttribute("action");%>

<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Setting Details</title>

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
                            <h1 class="h3 mb-0 text-gray-800">Update Team</h1>  
                        </div>
                        <form method="POST" action="${pageContext.request.contextPath}/team?id=${team.team_id}&action=edit">
                            <input type="hidden" name="action" value="edit">
                            <!-- Content Row -->
                            <div class="row">
                                   
                                <div class="col-lg-6">
                                    <div class="form-group">       
                                        <label>Team code</label>
                                        <input  name="teamCode"  value="${team.projectCode}" class="form-control" />
                                        <label style="color: red">${msgTeamCode}</label>
                                    </div>
                                </div>
                                    <div class="col-lg-6">
                                    <div class="form-group">       
                                        <label>Topic code</label>
                                        <input  name="topicCode"  value="${team.topicCode}" class="form-control" />
                                        <label style="color: red">${msgTopicCode}</label>
                                    </div>
                                </div>
                                    <div class="col-lg-6">
                                    <div class="form-group">       
                                        <label>Topic name</label>
                                        <input  name="topicName"  value="${team.topicName}" class="form-control" />
                                        <label style="color: red">${msgTopicName}</label>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="form-group">       
                                        <label >Class</label>
                                        <select  name="classId" class="custom-select">
                                            <c:forEach var="cl" items="${classLists}">
                                                <option value="${cl.classId}"
                                                     ${cl.classId == team.class_id?"selected":""}
                                                        >${cl.classCode}</option>

                                            </c:forEach>
                                        </select>     
                                    </div>
                                </div>
                                
                                
                                <div class="col-lg-6">
                                    <div class="form-group">       
                                        <label >Status</label>
                                        <select  name="status" class="custom-select">
                                            <option value="true" ${team.status ? "selected":""}>Active</option>
                                            <option value="false" ${!team.status ? "selected" :""}>Deactive</option>                                                                               
                                        </select>     
                                    </div>
                                </div>
                                
                            </div>
                           
                           
                            <div class="form-group">
                                <label for="description">Description</label>
                               
                                <textarea rows="5" type="text" class="form-control" name="description" id="description"  placeholder="Enter description">${team.getDescription()}</textarea>
                                    <label style="color: red">${msgDescription}</label>                          
                            </div>      
                            
                            <button type="submit" class="btn btn-lg btn-success">Submit</button>                             
                          
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
