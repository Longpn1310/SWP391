<%-- 
    Document   : changePassword
    Created on : Aug 11, 2022, 3:09:20 AM
    Author     : trung
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% boolean valid = (boolean) request.getAttribute("valid"); %>
<style>
    .messageContainer {
        height: 30px;
        padding-bottom: 30px;
    }   
    title {
        color: #3a3b45!important;
        margin-bottom: 20px;
    }
    .inputField {
        margin-bottom: 20px;
    }
    .containerDiv {
        width: 100%;
        height: 450px;
    }
    

</style>

<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Project Manager - Forgot Password</title>

        <!-- Custom fonts for this template-->
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="css/common/sb-admin-2.min.css" rel="stylesheet">

    </head>

    <body class="bg-gradient-primary">

        <div class="container" style="height: 100%;">

            <!-- Outer Row -->
            <div class="row justify-content-center justify-content-center " style="height: 100%;">

                <div class="col-xl-10 col-lg-12 col-md-9">

                    <div class="card o-hidden border-0 shadow-lg my-5">
                        <div class="card-body p-0">
                            <!-- Nested Row within Card Body -->
                            <div class="row" style="height: 450px;">

                                <div class="col-lg-6 d-none d-lg-block bg-password-image"></div>
                                <div class="col-lg-6">
                                    <div class="p-5">
                                        <div class="text-center" >
                                            <h4 class="title">Change password</h4>
                                        </div>

                                        <div class="messageContainer" style="padding-bottom: 30px;">
                                            <p style=" color: red;">${err}</p>
                                            <p class="mb-4" ><a style="color: green;text-align: center; font-weight: 700;" href="login">${msg}</a></p>
                                        </div>
                                        <form class="user" action="${pageContext.request.contextPath}/changePassword"  method="post" name="" id="changePassword">
                                            <% if (valid == false) { %>
                                            <div class="form-group"  style="margin-bottom: 20px;">
                                                <input type="password" class="form-control form-control-user" id="old-password" name="old-password" value="<%= request.getAttribute("old-password")%>" required placeholder="Enter Current Password...">
                                            </div>
                                            <div class="form-group " style="margin-bottom: 20px;">
                                                <input type="password" class="form-control form-control-user" id="password" name="password" value="<%= request.getAttribute("password")%>" required placeholder="Enter New Password...">
                                            </div>
                                            <div class="form-group" style="margin-bottom: 20px;">
                                                <input type="password" class="form-control form-control-user" id="confirmPassword" name="confirmPassword" value="<%= request.getAttribute("confirmPassword")%>" required placeholder="Enter Confirm Password...">
                                            </div>

                                            <button type="submit" id="submitButton" class="btn btn-primary btn-user btn-block">
                                                <span>Change Password</span>

                                            </button>
                                            <%}%>
                                        </form>

                                    </div>
                                </div>
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
        <script src="js/sb-admin-2.min.js"></script>

    </body>

</html>



<!--<div class="mainDiv">
    <div class="cardStyle">
        <form action="${pageContext.request.contextPath}/changePassword"  method="post" name="" id="changePassword">

            <img src="" id="signupLogo"/>

            <h2 class="formTitle">
                Change password
            </h2>
            <h5 style="color: red;text-align: center">${err}</h5>
            <h5 style="color: green;text-align: center"><a style="color: green;text-align: center" href="login">${msg}</a></h5>
            
<% if (valid == false) { %>
<div class="inputDiv">
    <label class="inputLabel" for="old-password">Old Password</label>
    <input type="password" id="old-password" name="old-password" required>
</div>
<div class="inputDiv">
    <label class="inputLabel" for="password">New Password</label>
    <input type="password" id="password" name="password" required>
</div>

<div class="inputDiv">
    <label class="inputLabel" for="confirmPassword">Confirm Password</label>
    <input type="password" id="confirmPassword" name="confirmPassword">
</div>

<div class="buttonWrapper">
    <button type="submit" id="submitButton" class="submitButton pure-button pure-button-primary">
        <span>Continue</span>

    </button>
</div>
<% }%>

</form>
</div>
</div>-->
