<%-- 
    Document   : class_dashboard
    Created on : Aug 20, 2022, 8:55:53 PM
    Author     : HaiLong
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="entity_setting.Setting"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
        <link href="css/common/global.css" rel="stylesheet" type="text/css"/>
        <link href="css/common/sb-admin-2.min.css" rel="stylesheet" type="text/css"/>
    </head>

    <body id="page-top">

        <!-- Page Wrapper -->
        <div id="wrapper">

            <!-- Sidebar -->                                    
            <jsp:include page="../common/fragment/trainer/sidebar.jsp" />
            <!-- End of Sidebar -->

            <!-- Content Wrapper -->
            <div id="content-wrapper" class="d-flex flex-column">

                <!-- Main Content -->
                <div id="content">

                    <!-- Topbar -->      
                    <jsp:include page="../common/fragment/trainer/topbar.jsp" />
                    <!-- End of Topbar -->

                    <!-- Begin Page Content -->
                    <div class="container-fluid">                        
                        <!-- Page Heading -->
                        <div class="row mb-4">
                            <h1 class="h3 mb-0 text-gray-800 ml-5">Class Dashboard</h1>                                                        
                        </div>

                        <!-- Content Row -->
                        <div class="row flex-row justify-content-between">

                            <!--Search bar-->


                        </div>

                        <!--Table-->
                        <div class="row">                                                        
                            <div class="table-responsive mr-5 ml-5 col-5">                                
                                <table class="table">                                    
                                    <thead>
                                        <tr>
                                            <th scope="col">Team</th>   
                                            <th scope="col">Total</th>
                                            <th scope="col">Team base</th>                                            
                                            <th scope="col">Individual based</th>                                    
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${teamGrades}" var="teamGrade">
                                            <tr>
                                                <td>${teamGrade.teamCode}</td>
                                                <td>${teamGrade.teamBase + teamGrade.individualBase}</td>
                                                <td>${teamGrade.teamBase}</td>
                                                <td>${teamGrade.individualBase}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>


                                </table>
                            </div>
                            <div class="table-responsive mr-5 ml-5 col-4">                                
                                <table class="table">                                    
                                    <thead>
                                        <tr>
                                            <th scope="col">Issue</th>   
                                            <th scope="col">Team</th>
                                            <th scope="col">Count</th>                                            

                                        </tr>
                                    </thead>
                                    <c:forEach items="${teamissues}" var="ts">
                                        <tr> <td>${ts.issue}</td>
                                            <td>${ts.teamCode}</td>
                                            <td>${ts.count}</td>
                                        </tr>
                                    </c:forEach>


                                </table>
                            </div>
                        </div>        
                        <div class="row">
                            <div class="table-responsive mr-5 ml-5 ">                                
                                <table class="table">                                    
                                    <thead>
                                        <tr>
                                            <th scope="col">Team</th>   
                                            <th scope="col">Total</th>
                                            <th scope="col">Pending</th> 
                                            <th scope="col">Planded</th> 
                                            <th scope="col">Submitted</th>
                                            <th scope="col">Rejected</th>
                                            <th scope="col">Evaluation</th>
                                        </tr>
                                    </thead>
                                    <c:forEach items = "${map}" var="item">
                                        <tr>
                                            <td>${item.key}</td>

                                            <td>${item.value.getTotal()}</td>
                                            <td>${item.value.pending}</td>
                                            <td>${item.value.planded}</td>
                                            <td>${item.value.submitted}</td>
                                            <td>${item.value.rejected}</td>
                                            <td>${item.value.evaluation}</td>
                                        </tr>
                                    </c:forEach>
                                    <tr>

                                    </tr>


                                </table>
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
