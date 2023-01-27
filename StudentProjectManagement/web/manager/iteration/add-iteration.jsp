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
                        <div class="d-sm-flex align-items-center justify-content-between mb-4">
                            <%if (action.equals("add")) {%>
                            <h1 class="h3 mb-0 text-gray-800">Add new Iteration</h1>  
                            <%} else {%>
                            <h1 class="h3 mb-0 text-gray-800">Edit Iteration</h1>
                            <%}%>                  
                        </div>
                        <form method="POST" action="${pageContext.request.contextPath}/iteration">
                            <%if (action.equals("add")) {%>
                            <input type="hidden" name="action" value="add">
                            <%} else {%>
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="settingId">
                            <%}%>
                            <!-- Content Row -->
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="form-group">       
                                        <label for="settingType">Iteration name <span style="color: red">*</span></label>
                                        <input value="${iteration.name}" id="settingType" name="iteration-name" class="form-control" />
                                        <label style="color:red">${msgIterationName}</label>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="form-group">       
                                        <label >Eval weight <span style="color: red">*</span></label>
                                        <input  name="eval-weight" type="number" value="${iteration.evalWeight}" class="form-control" />
                                        <label style="color: red">${msgEvalweight}</label>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="form-group">       
                                        <label >Subject</label>
                                        <select  name="subject-id" class="custom-select">
                                            <c:forEach var="subject" items="${subjects}">
                                                <option value="${subject.subjectId}"
                                                     ${subject.subjectId == iteration.subjectId ? "selected":""}    
                                                        >${subject.subjectName}</option>

                                            </c:forEach>
                                        </select>     
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="form-group">       
                                        <label >On-going</label>
                                        <div >
                                            <input type="radio"  name="on-going" ${iteration.onGoing ? "checked" : ""} value="true" />
                                            <label for="option1" style="margin-right: 10px">Active</label>

                                            <input type="radio"  name="on-going" ${!iteration.onGoing ? "checked" : ""} value="false" />
                                            <label for="option2">Inactive</label>

                                         
                                        </div>    
                                    </div>
                                </div>
                                
                                <div class="col-lg-6">
                                    <div class="form-group">       
                                        <label >Status</label>
                                        <div >
                                            <input type="radio"  name="status" ${iteration.status ? "checked" : ""} value="true" />
                                            <label for="option1" style="margin-right: 10px">Active</label>

                                            <input type="radio"  name="status" ${!iteration.status ? "checked" : ""} value="false" />
                                            <label for="option2">Inactive</label>

                                         
                                        </div>
                                    </div>
                                </div>
                                
                            </div>
                           
                           
                            <div class="form-group">
                                <label for="description">Description <span style="color: red">*</span></label>
                               
                                <textarea rows="5" type="text" class="form-control" name="description" id="description"  placeholder="Enter description">${iteration.getDescription()}</textarea>
                                       <label style="color:red">${msgDesc}</label>                       
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
