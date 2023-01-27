<%-- 
    Document   : iteration-list
    Created on : Aug 16, 2022, 5:39:23 PM
    Author     : admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Project Manager Dashboard</title>

        <!--         Custom fonts for this template-->
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet"> 
        <link href="css/common/sb-admin-2.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/common/global.css" rel="stylesheet" type="text/css"/>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>

    <body id="page-top">

        <!-- Page Wrapper -->
        <div id="wrapper">

            <!--Sidebar -->            
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
                            <h1 class="h3 mb-0 text-gray-800 ml-5">Team List</h1>
                        </div>

                        <!-- Content Row -->
                        <div class="row flex-row justify-content-between">

                            <!-- Search bar-->

                            <form id="searchForm" action="${pageContext.request.contextPath}/team" method="get">   
                                <input type="hidden" name="action" value="">
                                <div class="form-group ml-5">                                    
                                    <select id="searchSettingType" name="class-id" class="custom-select">
                                        <c:forEach items="${classLists}" var="c">
                                            <option value="${c.classId}"
                                                    ${c.classId==classId ? "selected": ""}

                                                    >${c.classCode}</option>
                                        </c:forEach>
                                    </select>                                        
                                </div>                                
                                <div class="form-group ml-5">                                    
                                    <select id="searchStatus" name="status" class="custom-select">
                                        <option id="opAll" value="">All status</option>
                                        <option id="opOpen" value="true" ${status == 1 ? "selected":""}>Inactive</option>
                                        <option id="opClosed" value="false" ${status == 0 ? "selected":""}>Active</option>                                        
                                    </select>                                        
                                </div>
                                <div class="form-group ml-5">                                       
                                    <input name="team-code" value="${teamCode}" id="searchTitle" type="text" class="form-control" placeholder="Enter name">                                                                    
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
                            <form id="addForm" action="team" method="GET">                                    
                                <input type="hidden" name="action" value="add">                                
                                <a href="javascript:;" onclick="document.getElementById('addForm').submit();"
                                   class="d-sm-inline-block btn btn-primary btn-icon-split mr-5">
                                    <span class="icon text-white-50">
                                        <i class="fas fa-plus"></i>
                                    </span>
                                    <span class="text">New Team</span>
                                </a>                                
                            </form>

                        </div>


                        <!--Table-->
                        <div class="row">                                                        

                            <div class="table-responsive mb-4 mr-5 ml-5">
                                <table class="table">                                   

                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Team Code</th>
                                        <th scope="col">Topic Code</th>
                                        <th scope="col">Topic Name</th>
                                        <th scope="col">Description</th>
                                        <th scope="col">Status</th>

                                        <th scope="col">Action</th>
                                    </tr>


                                    <c:forEach var="tea" items="${teams}">
                                        <tr>
                                            <td>${tea.team_id}</td>
                                            <td>${tea.projectCode}</td>
                                            <td>${tea.topicCode}</td>
                                            <td>${tea.topicName}</td>
                                            <td>${tea.description}</td>
                                            <td>${tea.status?"Active":"Deactive"}</td>
                                            <td>

                                                <a href="team?id=${tea.team_id}&action=edit"
                                                   class="d-sm-inline-block btn btn-primary btn-icon-split mr-4">
                                                    <span class="icon text-white-50">
                                                        <i class="fas fa-pen"></i>
                                                    </span>
                                                    <span class="text">Edit</span>
                                                </a>      

                                                <c:set var="url" value="team?id=${tea.team_id}&status=${tea.status}&action=changestatus"/>   
                                                <a onclick="changeStatus('${url}')"
                                                   class="d-sm-inline-block btn btn-icon-split ${tea.status?"btn-danger":"btn-success"}">
                                                    <span class="icon text-white-50">
                                                        <i class="fas fa-undo"></i>
                                                    </span>
                                                    <span class="text">${tea.status? "Deactive":"Active"}</span>
                                                </a>     

                                            </td>
                                        </tr>
                                    </c:forEach>

                                </table>

                            </div>
                            <div class="ml-5">
                                <div class="row flex-row">     

                                    <div class="form-group">  
                                        <c:forEach begin="${1}" end="${totalPage}" var="p">
                                            <a href="${uri}&page=${p}" 
                                               class="d-sm-inline-block btn btn-outline-primary btn-icon-split mr-2 ${p==page?"active":""}">                                        
                                                <span class="text">${p}</span>
                                            </a>       
                                        </c:forEach>

                                    </div> 
                                </div>
                            </div>        

                        </div>

                        <!-- /.container-fluid -->
                    </div>

                    <!-- End of Main Content -->
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
        <!-- Trigger the modal with a button -->
        <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Open Modal</button>

        <!-- Modal -->
        <div class="modal fade" id="myModal" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Confirm</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>

                    </div>
                    <div class="modal-body">
                        <p>Do you want to change status?</p>
                    </div>
                    <div class="modal-footer">
                        <a class="btn btn-primary" id="urlChangeStatus">Change</a>
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
        <script src="js/common/sb-admin-2.min.js" type="text/javascript"></script>        
        <script>
                                                    function changeStatus(url) {
                                                        $('#urlChangeStatus').attr("href", url);
                                                        $("#myModal").modal('show');
                                                    }
        </script>
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->                
        <script src="js/common/sb-admin-2.min.js" type="text/javascript"></script>          
        <script src="js/common/showChangeStatusAlert.js" type="text/javascript"></script>
    </body>
</html>
