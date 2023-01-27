<%-- 
    Document   : home
    Created on : Aug 26, 2022, 11:48:11 PM
    Author     : HaiLong
--%>
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
    <style>
        #content {
            position: relative;
    height: calc(100vh - 4.5em);
    margin-left: -15px;
    margin-right: -15px;
    background-attachment: scroll,fixed;
    background-position: center center;
    -webkit-background-size: cover;
    background-size: cover;
    color: #fff;
    background-image: url(/theme/image.php/trema/theme/1597744132/frontpage/overlay),url(//cmshn.fpt.edu.vn/pluginfile.php/1/theme_trema/frontpagebanner/1597744132/sanhAlpha.jpg);
        }
    </style>
    <body id="page-top">

        <!-- Page Wrapper -->
        <div id="wrapper">

            <!-- Sidebar -->                                    
            <jsp:include page="sidebar.jsp" />
            <!-- End of Sidebar -->

            <!-- Content Wrapper -->
            <div id="content-wrapper" class="d-flex flex-column">

                <!-- Main Content -->
                <div id="content">

                    <!-- Topbar -->                    
                    <jsp:include page="topbar.jsp" />
                    <!-- End of Topbar -->

                    <!-- Begin Page Content -->
                    <div class="container-fluid">                        
                        <!-- Page Heading -->
                      
                     
                        <!--Table-->
                               
                                    
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
