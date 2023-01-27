<%-- 
    Document   : details
    Created on : Aug 20, 2022, 12:38:58 AM
    Author     : ADMIN
--%>

<%@page import="entity_setting.Setting"%>
<%@page import="entity_user.User"%>
<%@page import="entity_subject.Subject"%>
<%@page import="entity_class.Class"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage ="../../common/error/error.jsp"%>
<% Class classs = (Class) session.getAttribute("classs");%>
<% List<User> trainers = (ArrayList<User>) session.getAttribute("trainers");%>
<% List<Subject> subjects = (ArrayList<Subject>) session.getAttribute("subjects");%>
<% List<Setting> terms = (ArrayList<Setting>) session.getAttribute("terms");%>
<% String action = (String) request.getAttribute("action");%>
<% String mssError = (String) request.getAttribute("mssError");%>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Class Details</title>

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
                            <h1 class="h3 mb-0 text-gray-800">Add new class</h1>  
                            <%} else {%>
                            <h1 class="h3 mb-0 text-gray-800">Edit class</h1>
                            <%}%>                  
                        </div>
                        
                        <label style="color: red"><%=mssError%></label>
                        
                        <form id="submitForm" method="POST" action="${pageContext.request.contextPath}/class">
                            <%if (action.equals("add")) {%>
                            <input type="hidden" name="action" value="add">
                            <%} else {%>
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="classId" value="<%=classs.getClassId()%>">
                            <%}%>
                            <!-- Content Row -->    
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="form-group">       
                                        <label for="subjectId">Subject*</label>
                                        <select id="subjectId" name="subjectId" class="custom-select">                                            
                                            <% for (Subject subject : subjects) {%>
                                            <%if (subject.getSubjectId() == classs.getSubjectId()) {%>
                                            <option value="<%=subject.getSubjectId()%>" selected><%=subject.getSubjectCode()%></option>
                                            <%} else {%>
                                            <option value="<%=subject.getSubjectId()%>"><%=subject.getSubjectCode()%></option>
                                            <%}%>                                            
                                            <%}%>                                                                                                                   
                                        </select>                                        
                                    </div>
                                </div>                                
                            </div>
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label for="classCode">Class code*</label>
                                        <%if (classs.getClassCode() == null) {%>
                                        <input type="text" class="form-control" name="classCode" id="classCode" placeholder="Enter class code" required="required">
                                        <%} else {%>
                                        <input type="text" class="form-control" name="classCode" id="classCode" placeholder="Enter class code" value="<%=classs.getClassCode()%>" required="required">
                                        <%}%>
                                    </div>
                                </div>
                            </div>                            
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="form-group">       
                                        <label for="termId">Term*</label>
                                        <select id="termId" name="termId" class="custom-select">                                            
                                            <% for (Setting term : terms) {%>
                                            <%if (term.getId() == classs.getTermId()) {%>
                                            <option value="<%=term.getId()%>" selected><%=term.getTitle()%></option>
                                            <%} else {%>
                                            <option value="<%=term.getId()%>"><%=term.getTitle()%></option>
                                            <%}%>                                            
                                            <%}%>                                                                                                                   
                                        </select>                                        
                                    </div>
                                </div>                                
                            </div>
                            <div class="row">
                                <div class="col-lg-6">                                   
                                    <div class="form-group">     
                                        <label for="isBlock5">Block5 Class</label>
                                        <div class="d-flex">
                                            <div>
                                                <%if (classs.isBlock5() == true) {%>
                                                <input type="radio" id="isBlock5" name="isBlock5" value="true" checked="checked">                                                
                                                <%} else {%>
                                                <input type="radio" id="isBlock5" name="isBlock5" value="true">
                                                <%}%>
                                                <label for="status">True</label>
                                            </div>
                                            <div class="ml-3">
                                                <%if (classs.isBlock5() == false) {%>
                                                <input type="radio" id="isBlock5" name="isBlock5" value="false" checked="checked">                                                
                                                <%} else {%>
                                                <input type="radio" id="isBlock5" name="isBlock5" value="false">
                                                <%}%>
                                                <label for="status">False</label>
                                            </div>
                                        </div>
                                    </div>                                    
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="form-group">       
                                        <label for="trainerId">Trainer*</label>
                                        <select id="trainerId" name="trainerId" class="custom-select">                                            
                                            <% for (User trainer : trainers) {%>
                                            <%if (trainer.getUserId() == classs.getTrainerId()) {%>
                                            <option value="<%=trainer.getUserId()%>" selected><%=trainer.getRollNumber()%> - <%=trainer.getFullName()%></option>
                                            <%} else {%>
                                            <option value="<%=trainer.getUserId()%>"><%=trainer.getRollNumber()%> - <%=trainer.getFullName()%></option>  
                                            <%}%>                                            
                                            <%}%>                                                                                                                   
                                        </select>                                        
                                    </div>
                                </div>                                
                            </div>
                            <div class="row">
                                <div class="col-lg-6">                                   
                                    <div class="form-group">     
                                        <label for="status">Status</label>
                                        <div class="d-flex">
                                            <div>
                                                <%if (classs.isStatus() == true) {%>
                                                <input type="radio" id="status" name="status" value="true" checked="checked">                                                
                                                <%} else {%>
                                                <input type="radio" id="status" name="status" value="true">
                                                <%}%>
                                                <label for="status">Active</label>
                                            </div>
                                            <div class="ml-3">
                                                <%if (classs.isStatus() == false) {%>
                                                <input type="radio" id="status" name="status" value="false" checked="checked">                                                
                                                <%} else {%>
                                                <input type="radio" id="status" name="status" value="false">
                                                <%}%>
                                                <label for="status">Inactive</label>
                                            </div>
                                        </div>
                                    </div>                                    
                                </div>
                            </div>                            
                            <%if (action.equals("add")) {%>
                            <a href="#" data-toggle="modal" data-target="#confirmModal"
                               class="btn btn-primary">                                
                                <span class="text">Add new class</span>
                            </a>                                               
                            <%} else {%>
                            <a href="#" data-toggle="modal" data-target="#confirmModal"
                               class="btn btn-primary">                                
                                <span class="text">Edit class</span>
                            </a>                                                        
                            <%}%>                                                           
                        </form>

                    </div>
                    <!-- /.container-fluid -->

                </div>
                <!-- End of Main Content -->

            </div>
            <!-- End of Content Wrapper -->

        </div>
        <!-- End of Page Wrapper -->

        <!-- Alert Modal-->
        <div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Message:</h5>
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <%if (session.getAttribute("changeResult") != null) {
                                if ((Integer) session.getAttribute("changeResult") == 3) {%>
                        Data exist
                        <%} else {%>
                        aaaa
                        <%}%>                                   
                        <%}%>                        
                    </div>
                    <div class="modal-footer">                        
                        <button class="btn btn-primary" type="button" data-dismiss="modal">Ok</button>                        
                    </div>
                </div>
            </div>
        </div>       
        <!--End of Alert Modal-->

        <!-- Confirm Modal-->
        <div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Are you sure you want to do it?</h5>
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <%if (action.equals("add")) {%>
                    <div class="modal-body">Click confirm to add new class.</div>
                    <%} else {%>
                    <div class="modal-body">Click confirm to edit this class.</div>
                    <%}%>

                    <div class="modal-footer">
                        <button class="btn btn-danger" type="button" data-dismiss="modal">Cancel</button>
                        <a class="btn btn-success" href="javascript:;" onclick="document.getElementById('submitForm').submit();">Confirm</a>
                    </div>
                </div>
            </div>
        </div>
        <!-- End of Confirm Modal-->

        <input type="hidden" id="showAlert" value='<%= (Integer) session.getAttribute("changeResult")%>' />
        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="js/common/sb-admin-2.min.js" type="text/javascript"></script>      
        <script src="js/common/open-modal.js" type="text/javascript"></script>
        <%session.setAttribute("changeResult", null);%>
    </body>

</html>
