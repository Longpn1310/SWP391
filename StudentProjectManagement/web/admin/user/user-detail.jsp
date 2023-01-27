<%-- 
    Document   : user-update
    Created on : Aug 13, 2022, 13:05:06 PM
    Author     : Administrator
--%>
<%@page import="entity_user.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity_setting.Setting"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" errorPage ="../../common/error/error.jsp"%>
<% User user = (User) request.getAttribute("user");%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Project Manager Dashboard</title>

        <!-- Custom fonts for this template-->
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="css/common/sb-admin-2.min.css" rel="stylesheet">

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
                        <div class="d-sm-flex align-items-center justify-content-between mb-4">

                            <h1 class="h3 mb-0 text-gray-800">User Detail </h1>

                        </div>
                        <!-- Content Row -->
                        <div class="row">

                            <div class="table-responsive mb-4 mr-5 ml-5">
                                <form action="userdetail" method="post">
                                    <table class="table">
                                        <tr>
                                            <td>ID</td>
                                            <td>
                                                <input class="input-group-text" type="text" name = "id" readonly="1" value="${user.userId}">
                                            </td>
                                            <td>Avatar</td>
                                            <td>
                                                <input type="text" value="<%=user.getAvatarLink()%>" name="avatar">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Roll Number</td>
                                            <td>
                                                <input type="text" value="<%=user.getRollNumber()%>" name = "rollnumber">
                                            </td>
                                            <td>User Name</td>
                                            <td>
                                                <input  type="text" value="<%=user.getFullName()%>" name = "username">
                                            </td>
                                        </tr>
                                        <tr>

                                        </tr>
                                        <tr>
                                            <td>Status</td>
                                            <td>
                                                <input type="radio" name = "status" value="1" <%=user.isStatus()==true?"checked":""%>>Activate
                                                <input type="radio" name = "status" value="0" <%=user.isStatus()==false?"checked":""%>>Deactivate
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Role Id</td>
                                            <td>
                                                <input class="input-group-text" type="number" value="<%=user.getRoleId()%>"name="roleId" readonly="1" >
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Email</td>
                                            <td>
                                                <input type="email" value="<%=user.getEmail()%>" name="email" >
                                            </td>
                                            <td>Password</td>
                                            <td>
                                                <input type="password" value="<%=user.getPassword()%>" name="password" >
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Phone Number</td>
                                            <td>
                                                <input type="number" value="<%=user.getMobile()%>" name="mobile" >
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </div>


                        </div>
                        <!-- /.container-fluid -->




                    </div>
                </div>
                <!-- End of Main Content -->

                <!-- Footer -->

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
        <script src="js/common/sb-admin-2.min.js"></script>

    </body>
</html>
