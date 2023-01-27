<%-- 
    Document   : iteration-evaluation
    Created on : Aug 23, 2022, 7:20:38 PM
    Author     : ADMIN
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="entity_milestone.Milestone"%>
<%@page import="entity_team.Team"%>
<%@page import="entity_evaluation.IterationEvaluation"%>
<%@page import="entity_class.Class"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage ="../../common/error/error.jsp"%>
<% List<IterationEvaluation> evaluations = (ArrayList<IterationEvaluation>) session.getAttribute("iterationEvaluations"); %>
<% List<Team> teams = (ArrayList<Team>) session.getAttribute("teams"); %>
<% List<Milestone> milestones = (ArrayList<Milestone>) session.getAttribute("milestones"); %>
<% List<Class> classes = (ArrayList<Class>) session.getAttribute("classes"); %>
<% int startIndex = (Integer) request.getAttribute("startIndex"); %>
<% int endIndex = (Integer) request.getAttribute("endIndex"); %>
<% int pageNumb = (Integer) request.getAttribute("pageNumb"); %>
<% int selectedClass = (Integer) session.getAttribute("selectedClass");%>
<% int selectedTeam = (Integer) session.getAttribute("selectedTeam");%>
<% int selectedMilestone = (Integer) session.getAttribute("selectedMilestone");%>
<% DecimalFormat dfZero = new DecimalFormat("0.00");%>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">        
        <title>Iteration evaluation</title>        
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
                            <h1 class="h3 mb-0 text-gray-800 ml-3">Iteration evaluation</h1>                                                        
                        </div>

                        <!--Search bar-->   
                        <form id="searchForm" method="POST" action="${pageContext.request.contextPath}/iteration-eval">
                            <input type="hidden" name="action" value="search">                             
                            <!--Class-->                                                                                                                                              
                            <div class="form-group mr-3">     
                                <label for="classId">Class</label>
                                <select id="classId" name="classId" class="custom-select" onchange="document.getElementById('searchForm').submit();">                                                                                                  
                                    <% for (Class classs : classes) {%>                                            
                                    <%if (classs.getClassId() == selectedClass) {%>
                                    <option value="<%=classs.getClassId()%>" selected="selected"><%=classs.getClassCode()%></option>
                                    <%} else {%>
                                    <option value="<%=classs.getClassId()%>"><%=classs.getClassCode()%></option>
                                    <%}%>                                            
                                    <%}%>                                                                                                                   
                                </select>                                        
                            </div>                                 
                            <!--Team-->                            
                            <div class="form-group mr-3">     
                                <label for="teamId">Team</label>
                                <select id="teamId" name="teamId" class="custom-select" onchange="document.getElementById('searchForm').submit();">
                                    <option value="0" selected="selected">All teams</option>
                                    <% for (Team team : teams) {%>        
                                    <%if (selectedTeam != 0) {%>
                                    <%if (team.getTeam_id() == selectedTeam) {%>
                                    <option value="<%=team.getTeam_id()%>" selected="selected"><%=team.getProjectCode()%></option>
                                    <%} else {%>
                                    <option value="<%=team.getTeam_id()%>"><%=team.getProjectCode()%></option>
                                    <%}%>
                                    <%} else {%>    
                                    <option value="<%=team.getTeam_id()%>"><%=team.getProjectCode()%></option>
                                    <%}%>
                                    <%}%>                                                                                                                   
                                </select>                                        
                            </div>                                 
                            <!--Milestone-->                                                                                                                     
                            <div class="form-group">     
                                <label for="milestoneId">Milestone</label>
                                <select id="milestoneId" name="milestoneId" class="custom-select" onchange="document.getElementById('searchForm').submit();">                                                                                                  
                                    <% for (Milestone milestone : milestones) {%>                                            
                                    <%if (milestone.getMilestoneId() == selectedMilestone) {%>
                                    <option value="<%=milestone.getMilestoneId()%>" selected="selected"><%=milestone.getMilestoneName()%></option>
                                    <%} else {%>
                                    <option value="<%=milestone.getMilestoneId()%>"><%=milestone.getMilestoneName()%></option>
                                    <%}%>                                            
                                    <%}%>                                                                                                                   
                                </select>                                        
                            </div>                                                             
                        </form>
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
                                            <th onclick="sortTable(4)">Team Eval</th>
                                            <th onclick="sortTable(5)">Individual Eval</th>
                                            <th onclick="sortTable(6)">Loc Eval</th>
                                            <th onclick="sortTable(7)">Bonus</th>
                                            <th>Edit</th>                                                
                                        </tr>
                                    </thead>                                    
                                    <tbody>
                                        <%
                                            for (int i = startIndex; i < endIndex; i++) {
                                        %>
                                        <tr>
                                            <td><%=evaluations.get(i).getRollNumber()%></td>                                            
                                            <td><%=evaluations.get(i).getFullName()%></td>
                                            <td><%=evaluations.get(i).getTeamName()%></td>
                                            <td><%=dfZero.format(evaluations.get(i).getTotalGrade())%></td>
                                            <td><%=dfZero.format(evaluations.get(i).getTeamEvaluation())%></td>
                                            <td><%=dfZero.format(evaluations.get(i).getMemberEvaluation())%></td>
                                            <td><%=evaluations.get(i).getLocEvaluation()%></td>
                                            <td><%=dfZero.format(evaluations.get(i).getBonus())%></td>
                                            <td>
                                                <div class="d-flex">                                                    
                                                    <form id="editForm<%=i%>" action="${pageContext.request.contextPath}/studentevaluation" method="GET">
                                                        <input type="hidden" name="action" value="iterEdit">                                                                                                            
                                                        <input type="hidden" name="evaluationId" value="<%=evaluations.get(i).getEvaluationId()%>"> 
                                                        <input type="hidden" name="milestoneId" value="<%=evaluations.get(i).getMilestoneId()%>">
                                                        <input type="hidden" name="bonus" value="<%=evaluations.get(i).getBonus()%>">
                                                        <input type="hidden" name="userId" value="<%=evaluations.get(i).getUserId()%>">
                                                        <input type="hidden" name="teamId" value="<%=evaluations.get(i).getTeamId()%>">
                                                        <a href="javascript:;" onclick="document.getElementById('editForm<%=i%>').submit();"
                                                           class="d-sm-inline-block btn btn-primary btn-icon-split">                                                        
                                                            <span class="text">Details</span>
                                                        </a>
                                                    </form>
                                                </div>
                                            </td>                                            
                                        </tr>                                        
                                        <%}%>                                           
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="row ml-3">                            
                            <div class="row flex-row">                                    
                                <%for (int i = 1; i <= pageNumb; i++) {%>
                                <form id="pagingForm<%=i%>" action="${pageContext.request.contextPath}/iteration-eval" method="POST">                                    
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
