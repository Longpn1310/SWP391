<%-- 
    Document   : user-list
    Created on : Aug 13, 2022, 14:48:21 PM
    Author     : Administrator
--%>
<%@page import="entity_user.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity_setting.Setting"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage ="../../common/error/error.jsp"%>

<% List<User> userlist = (ArrayList<User>) request.getAttribute("userlist"); %>
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
                            <h1 class="h3 mb-0 text-gray-800">User List</h1>
                            <a href="addUser" style="float:right;text-align:right;" class="d-sm-inline-block btn btn-success btn-icon-split mr-4">
                                <span class="icon text-white-50">
                                    <i class="fas fa-plus"></i>
                                </span>
                                <span class="text">Add New User</span>
                            </a>
                        </div>
                        <!-- phần filer-->
                        <!--                        <select>
                                                    
                                                </select>-->

                        <form action="UserSearchController" method="get">
                            <input type="text" id="name" name="usearch" placeholder="search by roll number"><input type="submit" value = "search">
                        </form>

                        <!-- Content Row -->
                        <div class="row">

                            <div class="table-responsive mb-4 mr-5 ml-5">
                                <table class="table">                                   

                                    <tr>
                                        <th scope="col">#</th>

                                        <th scope="col">Roll Number</th>
                                        <th scope="col">User Name</th>
                                        <th scope="col">Email</th>                                        
                                        <th scope="col">Mobile</th>    
                                        <th scope="col">Avatar</th>
                                        <th scope="col">Role ID</th>
                                        <th scope="col">Status</th>
                                        <th scope="col">Action</th>
                                    </tr>


                                    <% for (User user : userlist) {%>
                                    <tr>  
                                        <td><%=user.getUserId()%></td>
                                        <td><%=user.getRollNumber()%></td>
                                        <td><%=user.getFullName()%></td>
                                        <td><%=user.getEmail()%></td>                                        
                                        <td><%=user.getMobile()%></td>    
                                        <td><%=user.getAvatarLink()%></td>
                                        <td><%=user.getRoleId()%></td>

                                        <%if (user.isStatus() == true) {%>
                                        <td style="color: green">Active</td>
                                        <%} else {%>
                                        <td style="color: red">Deactivate</td>
                                        <%}%>



                                        <td>
                                            
                                                <%if (user.isStatus() == true) {%>
                                                <a style="color: red" href="DeactivateUserController?uid=<%=user.getUserId()%>" class="d-sm-inline-block btn btn-success btn-icon-split mr-4">
                                                <span class="text">Deactivate</span>
                                                </a>
                                                <%} else  {%>
                                                <a href="ActivateUserController?uid=<%=user.getUserId()%>" class="d-sm-inline-block btn btn-success btn-icon-split mr-4">
                                                <span class="text">Activate</span>
                                                </a>
                                                <%}%> 

                                            
                                            <a href="#" onclick="showMess(<%=user.getUserId()%>)" >
                                                
                                                <span class="text">Delete</span>
                                            </a>
                                            
                                        </td>
                                        <td>
                                            <a href="userdetail?uid=<%=user.getUserId()%>" class="d-sm-inline-block btn btn-primary btn-icon-split mr-4">
                                                <span class="text">View</span>
                                                </a>
                                        </td>
                                    </tr>
                                    <%}%>

                                </table>
                            </div>
                            <script>
                                function showMess(id) {
                                    var option = confirm("are you sure?");
                                    if (option === true) {
                                        window.location.href = 'delete?uid=' + id;
                                    }
                                }
                            </script>

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

        <!-- Scroll to Top Button-->
        <a class="scroll-to-top rounded" href="#page-top">
            <i class="fas fa-angle-up"></i>
        </a>

        <!-- Logout Modal-->
        <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                    <div class="modal-footer">
                        <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                        <a class="btn btn-primary" href="login.html">Logout</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="js/common/sb-admin-2.min.js"></script>

    </body>
</html>
