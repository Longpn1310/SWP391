<%-- 
    Document   : newjspclass_eval
    Created on : Aug 21, 2022, 8:03:08 PM
    Author     : HaiLong
--%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="entity_iteration.Iteration"%>
<%@page import="entity_evaluation.IterationEvaluation"%>
<%@page import="entity_evaluation.ClassEval"%>
<%@page import="entity_class.Class"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage ="../../common/error/error.jsp"%>
<% List<ClassEval> classEvals = (ArrayList<ClassEval>) session.getAttribute("classEvals"); %>
<% List<Class> classes = (ArrayList<Class>) session.getAttribute("classes"); %>
<% List<Iteration> iterations = (ArrayList<Iteration>) session.getAttribute("iteration"); %>
<% int startIndex = (Integer) request.getAttribute("startIndex"); %>
<% int endIndex = (Integer) request.getAttribute("endIndex"); %>
<% int pageNumb = (Integer) request.getAttribute("pageNumb"); %>
<% int selectedClass = (Integer) session.getAttribute("selectedClass");%>
<% DecimalFormat dfSharp = new DecimalFormat("0.0");%>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">        
        <title>Class evaluation</title>        
        <!-- Custom fonts for this template-->        
        <link href="vendor/fontawesome-free/css/all.css" rel="stylesheet" type="text/css"/>  
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

        <!-- Custom styles for this template-->        
        <link href="css/common/sb-admin-2.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/common/global.css" rel="stylesheet" type="text/css"/>
    </head>

    <body id="page-top">

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
                        <div class="row mb-4">
                            <h1 class="h3 mb-0 text-gray-800 ml-3">Class evaluation</h1>                                                        
                        </div>

                        <!--Search bar-->
                        <div class="row ml-3">
                            <form id="classForm" method="POST" action="${pageContext.request.contextPath}/class-eval">    
                                <input type="hidden" name="action" value="search"> 
                                <div class="row">                                                                                
                                    <div class="form-group">     
                                        <label for="classId">Class</label>
                                        <select id="classId" name="classId" class="custom-select" onchange="document.getElementById('classForm').submit();">                                                                                                  
                                            <% for (Class classs : classes) {%>                                            
                                            <%if (classs.getClassId() == selectedClass) {%>
                                            <option value="<%=classs.getClassId()%>" selected="selected"><%=classs.getClassCode()%></option>
                                            <%} else {%>
                                            <option value="<%=classs.getClassId()%>"><%=classs.getClassCode()%></option>
                                            <%}%>                                            
                                            <%}%>                                                                                                                   
                                        </select>                                        
                                    </div> 
                                </div>
                            </form>
                        </div>                        

                        <!--Table-->
                        <div class="row">
                            <div class="table-responsive mr-3 ml-3">                                
                                <table id="listTable" class="table">                                    
                                    <thead>
                                        <tr>
                                            <th onclick="sortTable(0)">Roll Number</th>   
                                            <th onclick="sortTable(1)">Student</th>   
                                            <th onclick="sortTable(2)">Team</th>
                                            <th onclick="sortTable(3)">Total</th>
                                            <th onclick="sortTable(4)">OG</th>
                                                <% for (Iteration iteration : iterations) {%>
                                            <th class="text-center"><%=iteration.getName()%></th>
                                                <%}%>
                                        </tr>
                                    </thead>                                    
                                    <tbody>
                                        <%
                                            for (int i = startIndex; i < endIndex; i++) {
                                                List<IterationEvaluation> list = classEvals.get(i).getIterationEvaluations();
                                        %>
                                        <tr>
                                            <td><%=classEvals.get(i).getRollNumber()%></td>                                            
                                            <td><%=classEvals.get(i).getFullName()%></td>
                                            <td><%=classEvals.get(i).getTeamName()%></td>
                                            <td><%=classEvals.get(i).getTotal()%></td>
                                            <td><%=classEvals.get(i).getOnGoing()%></td>   
                                            <%for (Iteration iteration : iterations) {%>
                                            <td>
                                                <div class="d-flex ">
                                                    <%float total = 0;
                                                        for (ClassEval classEval : classEvals) {
                                                            List<IterationEvaluation> ies = classEval.getIterationEvaluations();
                                                            for (IterationEvaluation ie : ies) {
                                                                if (ie.getIterationId() == iteration.getId() && ie.getUserId() == classEvals.get(i).getUserId()) {
                                                                            total += ie.getTotalGrade();
                                                                        }
                                                                    }
                                                                }%>
                                                                <span class="mt-2 mr-2"><%=dfSharp.format(total)%></span>
                                                    <form id="editForm<%=i%>" action="#" method="GET">                                                         
                                                        <a href="javascript:;" onclick="document.getElementById('editForm<%=i%>').submit();"
                                                           class="d-sm-inline-block btn btn-primary btn-icon-split">                                                        
                                                            <span class="text">Details</span>
                                                        </a>
                                                    </form>

                                                </div>
                                            </td>     
                                            <%}%>
                                        </tr>  
                                        <%}%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="row ml-3">                            
                            <div class="row flex-row">                                    
                                <%for (int i = 1; i <= pageNumb; i++) {%>
                                <form id="pagingForm<%=i%>" action="${pageContext.request.contextPath}/class-eval" method="POST">                                    
                                    <input type="hidden" name="action" value="paging">                                
                                    <input type="hidden" name="pageNumb" value="<%=pageNumb%>">                                                   
                                    <div class="form-group">                                    
                                        <input type="hidden" name="pageChoose" value="<%=i%>">
                                        <a href="javascript:;" onclick="document.getElementById('pagingForm<%=i%>').submit();"
                                           class="d-sm-inline-block btn btn-outline-primary btn-icon-split mr-2">                                        
                                            <span class="text"><%=i%></span>
                                        </a>                                            
                                    </div>                                
                                </form>
                                <%}%>
                            </div>                            
                        </div>
                    </div>
                    <!-- /.container-fluid -->

                </div>
                <!-- End of Main Content -->             

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
        <script src="js/common/sort-table.js" type="text/javascript"></script>
    </body>

</html>
