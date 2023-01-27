<%-- 
    Document   : userProfile
    Created on : Aug 12, 2022, 5:04:32 PM
    Author     : trung
--%>

<%@page import="entity_user.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    User user = (User) request.getAttribute("user");
%>
<%
    String role = (String) request.getAttribute("role");
%>
<%
    String status = (String) request.getAttribute("status");
%>
<%
    String mobile = (String) request.getAttribute("mobile");
%>
<!DOCTYPE html>
<html>
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>User Profile</title>

        <!-- Custom fonts for this template-->
        <link href="vendor/fontawesome-free/css/all.css" rel="stylesheet" type="text/css"/>  
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="css/common/sb-admin-2.min.css" rel="stylesheet" type="text/css"/>  
        <link href="css/settingcss.css" rel="stylesheet" type="text/css"/>
    </head>
    <body id="page-top">

        <!-- Page Wrapper -->
        <div id="wrapper">         

            <jsp:include page="../../common/fragment/student/sidebar.jsp" />

            <!-- Content Wrapper -->
            <div id="content-wrapper" class="d-flex flex-column">

                <!-- Main Content -->
                <div id="content">
                    <!-- Topbar -->                    
                    <jsp:include page="../../common/fragment/student/topbar.jsp" />
                    <!-- End of Topbar -->

                    <div class="container-fluid">


                        <!-- Content Row -->
                        <div class="row mb-4">
                            <div class="col-lg-12 mb-4">

                                <div class="card mb-2">
                                    <!-- Card Header -->
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-info">User Profile</h6>
                                    </div>
                                    <div class="card-body">
                                        <!--enctype="multipart/form-data"-->
                                        <form class="user" action="${pageContext.request.contextPath}/userProfile" method="POST" >
                                            <div class="form-group row">
                                                <div class="col-sm-6 mb-3 mb-sm-0" >
                                                    <strong>Email:</strong>
                                                    <input name="email"  type="text" class="form-control" placeholder="Email" disabled="disabled" style="margin-bottom: 15px" value="<%=user.getEmail()%>" >
                                                </div>
                                                <div class="col-sm-6 mb-3 mb-sm-0" >
                                                    <strong>Full Name:</strong>
                                                    <input id="fullName" name="fullName"  type="text" class="form-control" placeholder="Full name" style="margin-bottom: 15px"  value="<%=user.getFullName()%>" >
                                                </div> 

                                                <div class="col-sm-6 mb-3 mb-sm-0" >
                                                    <strong>Roll Number:</strong>
                                                    <input id="rollNumber" name="rollNumber"  type="text" class="form-control" placeholder="Roll number"  style="margin-bottom: 15px" value="<%=user.getRollNumber()%>" >
                                                </div> 
                                                <div class="col-sm-6 mb-3 mb-sm-0" >
                                                    <strong>Mobile:</strong>
                                                    <input id="mobile" name="mobile"  type="text" class="form-control" placeholder="Mobile" style="margin-bottom: 15px" value="<%=mobile%>" >
                                                </div> 
                                                <div class="col-sm-6 mb-3 mb-sm-0" >
                                                    <strong>Profile Link: </strong>
                                                    <input name="link" id="link" type="text" class="form-control" placeholder="Profile Link" style="margin-bottom: 15px" value="<%= request.getAttribute("link") %>" >
                                                </div>
                                                <div class="col-sm-6 mb-3 mb-sm-0" >
                                                    <strong>User Name:</strong>
                                                    <input name="userName"  type="text" class="form-control" placeholder="userName" style="margin-bottom: 15px"  value="<%=user.getFullName()%>" >
                                                </div> 

                                                <div class="col-sm-6 mb-3 mb-sm-0" >
                                                    <strong>Role: </strong>
                                                    <input name="role" id="role" type="text" class="form-control" placeholder="Role" disabled="disabled"  style="margin-bottom: 15px" value="<%=role%>" >
                                                </div> 
                                                <div class="col-sm-6 mb-3 mb-sm-0" >
                                                    <strong>Status: </strong>
                                                    <input name="status" id="status" type="text" class="form-control" placeholder="Status" disabled="disabled" style="margin-bottom: 15px" value="<%=status%>" >
                                                </div> 
                                                <div class="col-sm-6 mb-3 mb-sm-0" >
                                                    <input name="userId"  type="text" class="form-control" placeholder="User Id" style="display: none" value="${user.getUserId()}" >
                                                </div>
                                                <div class="col-sm-12 mb-6 mb-sm-0" >
                                                    <h5 style="color: red;text-align: center; justify-content: center;">${err}</h5>
                                                </div>
                                                
                                                <button class="btn btn-primary btn-user btn-block" style="margin-top: 30px" type="submit">Submit</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                            </div>
                        </div>
                        <!-- Page Heading -->

                    </div>
                    <!--</form>-->                  
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
    <script src="vendor/jquery/jquery.min.js" type="text/javascript"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js" type="text/javascript"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js" type="text/javascript"></script>  

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js" type="text/javascript"></script>  
</body>
</html>
