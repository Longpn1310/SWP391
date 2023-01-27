<%-- 
    Document   : student-evaluation
    Created on : Aug 21, 2022, 1:35:05 PM
    Author     : ADMIN
--%>

<%@page import="entity_evaluation.MemberEvaluation"%>
<%@page import="entity_evaluation.TeamEvaluation"%>
<%@page import="entity_function.Function"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage ="../../common/error/error.jsp"%>
<% List<TeamEvaluation> teamEvaluations = (ArrayList<TeamEvaluation>) session.getAttribute("teamEvaluations");%>
<% List<MemberEvaluation> memberEvaluations = (ArrayList<MemberEvaluation>) session.getAttribute("memberEvaluations");%>
<% List<Function> functions = (ArrayList<Function>) session.getAttribute("functions");%>
<% int totalLoc = (Integer) session.getAttribute("totalLoc");%>
<% float bonus = (Float) session.getAttribute("bonus");%>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Student evaluation</title>

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
                            <h1 class="h3 mb-0 text-gray-800 ml-5">Student evaluation</h1>                                                        
                        </div>

                        <!-- Content Row -->
                        <div class="row">                            
                            <div class="card ml-5 w-100 mr-5">
                                <div class="card-header">
                                    <ul class="nav nav-tabs" id="myTab" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="false">Evaluations</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">LOC Evaluations</a>
                                        </li>                                        
                                    </ul>
                                </div>
                                <div class="card-body">                                    
                                    <div class="tab-content" id="myTabContent">
                                        <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">      

                                            <!--Form submit-->
                                            <form id="submitForm" method="POST" action="${pageContext.request.contextPath}/studentevaluation">                                                
                                                <!--Accordion 1-->
                                                <div id="accordion" class="mb-4">
                                                    <div class="card">
                                                        <div class="card-header" id="headingOne">
                                                            <h5 class="mb-0">
                                                                <a class="btn btn-link" data-toggle="collapse" href="#collapseOne" role="button" aria-expanded="false" aria-controls="collapseOne">
                                                                    Team evaluation
                                                                </a>                                                                
                                                            </h5>
                                                        </div>
                                                        <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">
                                                            <div class="card-body">
                                                                <div class="row">
                                                                    <div class="w-100 mr-4 ml-4">
                                                                        <%for (int i = 0; i < teamEvaluations.size(); i++) {%>                                                                        
                                                                        <div class="form-group">
                                                                            <div class="flex-row-reverse d-flex mb-1">   
                                                                                <input type="hidden" id="teamId<%=i%>" name="teamId<%=i%>" value="<%=teamEvaluations.get(i).getId()%>">           
                                                                                <input type="text" class="form-control col-1" id="teamGrade<%=i%>" name="teamGrade<%=i%>" value="<%=teamEvaluations.get(i).getGrade()%>" required="required">           
                                                                                <h4 for="teamGrade<%=i%>" class="mr-2 mt-1">Grade/10:</h4>
                                                                                <h3 for="teamComment<%=i%>" class="mr-auto"><%=teamEvaluations.get(i).getCriteriaName()%> (<%=teamEvaluations.get(i).getEvalWeight()%>%)</h3> 
                                                                            </div>
                                                                            <div class="flex-row d-flex">
                                                                                <textarea name="teamComment<%=i%>" id="teamComment<%=i%>" rows="2" type="text" class="form-control" placeholder="Grading comment for <%=teamEvaluations.get(i).getCriteriaName()%>"></textarea>                                                                     
                                                                            </div>
                                                                        </div>                                                                        
                                                                        <%}%>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>                                                
                                                </div>
                                                <!--End accordion 1-->

                                                <!--Accordion 2-->
                                                <div id="accordion2" class="mb-4">
                                                    <div class="card">
                                                        <div class="card-header" id="headingTwo">
                                                            <h5 class="mb-0">
                                                                <a class="btn btn-link" data-toggle="collapse" href="#collapseTwo" role="button" aria-expanded="false" aria-controls="collapseTwo">
                                                                    Individual evaluation
                                                                </a>                                                                
                                                            </h5>
                                                        </div>
                                                        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion2">
                                                            <div class="card-body">
                                                                <div class="row">
                                                                    <div class="w-100 mr-4 ml-4">
                                                                        <%for (int i = 0; i < memberEvaluations.size(); i++) {%>                                                                        
                                                                        <div class="form-group">
                                                                            <div class="flex-row-reverse d-flex mb-1">   
                                                                                <input type="hidden" id="teamId<%=i%>" name="memberId<%=i%>" value="<%=memberEvaluations.get(i).getId()%>">           
                                                                                <input type="text" class="form-control col-1" id="individualGrade<%=i%>" name="individualGrade<%=i%>" value="<%=memberEvaluations.get(i).getGrade()%>" required="required">           
                                                                                <h4 for="individualGrade<%=i%>" class="mr-2 mt-1">Grade/10:</h4>
                                                                                <h3 for="individualComment<%=i%>" class="mr-auto"><%=memberEvaluations.get(i).getCriteriaName()%> (<%=memberEvaluations.get(i).getEvalWeight()%>%)</h3> 
                                                                            </div>
                                                                            <div class="flex-row d-flex">
                                                                                <textarea name="individualComment<%=i%>" id="individualComment<%=i%>" rows="2" type="text" class="form-control " placeholder="Grading comment for <%=memberEvaluations.get(i).getCriteriaName()%>"></textarea>                                                                     
                                                                            </div>
                                                                        </div>                                                                        
                                                                        <%}%>                                                                        
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>                                                
                                                </div>  
                                                <!--End accordion 2-->

                                                <!--Accordion 3-->
                                                <div id="accordion3" >
                                                    <div class="card">
                                                        <div class="card-header" id="headingThree">
                                                            <h5 class="mb-0">
                                                                <a class="btn btn-link" data-toggle="collapse" href="#collapseThree" role="button" aria-expanded="false" aria-controls="collapseThree">
                                                                    LOC evaluation
                                                                </a>                                                                
                                                            </h5>
                                                        </div>
                                                        <div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#accordion3">
                                                            <div class="card-body">
                                                                <div class="row">
                                                                    <div class="w-100 mr-4 ml-4">   
                                                                        <h4 for="bonus" class="mr-2 mt-1">Total LOC: <%=totalLoc%></h4>
                                                                        <h4 for="bonus" class="mr-2 mt-1">Bonus:</h4>
                                                                        <input type="text" class="form-control col-1" id="bonus" name="bonus" value="<%=bonus%>" required="required">                                                                                           
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>                                                
                                                </div>
                                                <!--End accordion 3-->

                                                <a href="javascript:;" onclick="document.getElementById('submitForm').submit();"
                                                   class="d-sm-inline-block btn btn-primary btn-icon-split mr-4 mt-4">
                                                    <span class="icon text-white-50">
                                                        <i class="fas fa-save"></i>
                                                    </span>
                                                    <span class="text">Save</span>
                                                </a> 
                                            </form>
                                            <!--End form submit-->

                                        </div>
                                        <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">

                                            <!--Table-->
                                            <div class="table-responsive">                                
                                                <table class="table" id="listTable">                                    
                                                    <thead>
                                                        <tr>
                                                            <th onclick="sortTable(0)">Id</th>   
                                                            <th onclick="sortTable(1)">Function</th>
                                                            <th onclick="sortTable(2)">Feature</th>  
                                                            <th onclick="sortTable(3)">Complexity</th>      
                                                            <th onclick="sortTable(4)">Quality</th>      
                                                            <th onclick="sortTable(5)">LOC</th>                                                           
                                                            <th>Action</th>                                            
                                                        </tr>
                                                    </thead>

                                                    <tbody>
                                                        <%
                                                            for (Function function : functions) {
                                                        %>
                                                        <tr>
                                                            <td><%=function.getFunctionId()%></td>                                            
                                                            <td><%=function.getTitle()%></td>
                                                            <td><%=function.getFeature()%></td>    
                                                            <td></td>
                                                            <td></td>
                                                            <td></td>                                                            
                                                            <td>
                                                                <div class="inline_center">    
                                                                    <form id="editForm<%=function.getFunctionId()%>" action="${pageContext.request.contextPath}/locevaluation" method="GET">
                                                                        <input type="hidden" name="classId" value="<%= function.getFunctionId()%>">
                                                                        <input type="hidden" name="action" value="edit">                                                        
                                                                        <a href="javascript:;" onclick="document.getElementById('editForm<%=function.getFunctionId()%>').submit();"
                                                                           class="d-sm-inline-block btn btn-primary btn-icon-split mr-4">
                                                                            <span class="icon text-white-50">
                                                                                <i class="fas fa-pen"></i>
                                                                            </span>
                                                                            <span class="text">Update</span>
                                                                        </a>
                                                                    </form>                                                                                                                                                               
                                                                </div>
                                                            </td>                                            
                                                        </tr>                                        
                                                        <%}%>                                       
                                                    </tbody>
                                                </table>
                                            </div>
                                            <!--End of table-->
                                        </div>                                        
                                    </div>                                    
                                </div>
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
        <script src="vendor/jquery/jquery.min.js" type="text/javascript"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js" type="text/javascript"></script>

        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js" type="text/javascript"></script>  

        <!-- Custom scripts for all pages-->
        <script src="js/common/sb-admin-2.min.js" type="text/javascript"></script>                          
    </body>

</html>
