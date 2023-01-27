<%-- 
    Document   : details
    Created on : Aug 8, 2022, 11:25:26 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>User Register</title>

        <!-- Custom fonts for this template-->
        <link href="vendor/fontawesome-free/css/all.css" rel="stylesheet" type="text/css"/>  
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

        <!-- Custom styles for this template-->        
        <link href="css/common/sb-admin-2.min.css" rel="stylesheet" type="text/css"/>          
        <link href="css/common/global.css" rel="stylesheet" type="text/css"/>        

    </head>

    <body class="bg-gradient-primary">

        <div class="container">
            <h1 style="text-align: center">${error}</h1>
            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-0">
                    <!-- Nested Row within Card Body -->
                    <div class="row">
                        <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
                        <div class="col-lg-7">
                            <div class="p-5">
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4">Create an Account!</h1>
                                </div>
                                <form class="user" autocomplete="off" action="${pageContext.request.contextPath}/register" name="" id="register" method="POST">
                                    <div class="row flex-row">
                                        <div class="form-group col-sm-12">                                        
                                            <label for="email">Email: </label>                                            
                                            <input value="${email}" name="email" id="fullName" type="text" class="form-control" placeholder="Enter your email" required="required" autofocus="autofocus">                                                                                        
                                        </div>
                                        <div class="form-group col-sm-6">
                                            <label for="fullName">Full name:</label>          
                                            <input name="fullName" id="password" type="text" class="form-control" placeholder="Enter your full name" required="required" value="<%= request.getAttribute("fullName")%>" >                                                                                                                                                                          
                                        </div>  
                                        <div class="form-group col-sm-6 mb-3 mb-sm-0">                                        
                                            <label for="mobile">Mobile: </label>                                            
                                            <input name="mobile" id="mobile" type="text" class="form-control" placeholder="Enter your mobile" value="<%= request.getAttribute("mobile")%>" >                                                                                        
                                        </div>  
                                    </div>
                                    <div class="row flex-row">
                                        <div class="form-group col-sm-6 mb-3 mb-sm-0">                                        
                                            <label for="password">Password :</label>          
                                            <input name="password" id="password" type="password" class="form-control" value="<%= request.getAttribute("password")%>" placeholder="Enter your password" required="required" >                                                                                                                                                                          
                                        </div>
                                        <div class="form-group col-sm-6">
                                            <label for="confirmPassword">Confirm password :</label>          
                                            <input name="confirmPassword" id="confirmPassword" type="password" class="form-control" value="<%= request.getAttribute("confirmPassword")%>" placeholder="Enter your password" required="required" >                                                                                                                                                                          
                                        </div>       
                                    </div>
                                    <h5 style="color: red;text-align: center">${err}</h5>
                                <h5 style="color: green;text-align: center">${success}</h5>
                                     <input type="hidden" name="action" value="verified">
                                    <button class="btn btn-primary btn-user btn-block" type="submit"><i class="fas fa-user-plus"></i> Register</button>
                                </form>
                                
                                <hr>
                            </div>
                        </div>
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

    </body>

</html>
