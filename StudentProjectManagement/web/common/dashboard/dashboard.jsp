<%-- 
    Document   : dashboard
    Created on : Aug 10, 2022, 11:38:50 PM
    Author     : ADMIN
--%>

<%@page import="entity_user.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage ="../error/error.jsp"%>
<%
    User user = (User) request.getAttribute("user");
%>
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
        <link href="css/common/sb-admin-2.min.css" rel="stylesheet" type="text/css"/>

    </head>
    <style>
        .text-small {
            font-size: 0.9rem;
        }

        a {
            color: inherit;
            text-decoration: none;
            transition: all 0.3s;
        }

        a:hover, a:focus {
            text-decoration: none;
        }

        .form-control {
            background: #212529;
            border-color: #545454;
        }

        .form-control:focus {
            background: #212529;
        }

        footer {
            background: #212529;
        }


        /* ==========================================
            CUSTOM UTILS CLASSES
          ========================================== */
        body, html {
            height: 100%;
        }
        .info {
            margin-bottom: 20px
        }
    </style>
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
                    </div>
                </div>
                <!-- End of Main Content -->
            </div>
            <!-- End of Content Wrapper -->

        </div>
        <!-- End of Page Wrapper -->        
        <footer class="w-100 py-4 flex-shrink-0">
            <div class="container py-4">
                <div class="row gy-4 gx-5">
                    <div class="col-lg-6 col-md-8">
                        <h5 class="h1 text-white">Địa chỉ và thông tin liên hệ</h5>
                        <p class="small text-muted">Khu Giáo dục và Đào tạo – Khu Công nghệ cao Hòa Lạc – Km29 Đại lộ Thăng Long, H. Thạch Thất, TP. Hà Nội</p>
                        <p class="small text-muted">Điện thoại: 024 7300 1866</p>
                        <p class="small text-muted">Email: daihocfpt@fpt.edu.vn</p>
                    </div>

                    <div class="col-lg-4 col-md-6">
                        <h5 class="text-white mb-3">Liên hệ hỗ trợ và nhận thông tin</h5>
                        <form action="#">
                            <div class="row">
                                <div class="info col-md-6">
                                    <input class="form-control" type="text" placeholder="Họ và tên" aria-label="Recipient's username" aria-describedby="button-addon2">
                                </div>
                                <div class="info col-md-6">
                                    <input class="form-control" type="text" placeholder="Loại liên hệ" aria-label="Recipient's username" aria-describedby="button-addon2">
                                </div><br>
                                <div class="info col-md-6">
                                    <input class="form-control" type="text" placeholder="Số điện thoại" aria-label="Recipient's username" aria-describedby="button-addon2">
                                </div>
                                <div class="info col-md-6">
                                    <input class="form-control" type="text" placeholder="Email" aria-label="Recipient's username" aria-describedby="button-addon2">
                                </div><br>
                                <div class="info col-md-12">
                                    <textarea class="form-control" type="text" placeholder="Lời nhắn" aria-label="Recipient's username" aria-describedby="button-addon2"></textarea>
                                </div>
                                <button class="btn btn-primary info col-6">Gửi yêu cầu</button>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </footer>
        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="js/common/sb-admin-2.min.js" type="text/javascript"></script>     

    </body>
</html>

