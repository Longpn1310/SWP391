<%-- 
    Document   : Login
    Created on : May 18, 2022, 2:29:31 PM
    Author     : admin
--%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- MATERIAL DESIGN ICONIC FONT -->
        <link rel="stylesheet" href="fonts/login/material-design-iconic-font/css/material-design-iconic-font.min.css">

        <!-- STYLE CSS -->         
        <link rel="stylesheet" href="css/login/style.css">
    </head>

    <body id="page-top">


        <div class="wrapper">
            <div class="image-holder">                
                <!--<img src="images/login/fpt.jpg"  alt="">-->
            </div>
            <div class="form-inner">
                <form action="${pageContext.request.contextPath}/login" method="POST">
                    <p class="text-danger">${mess}</p>
                    <div class="form-header">
                        <h3>Have an account?</h3>                        
                        <img src="images/login/sign-up.png" alt="" class="sign-up-icon">
                    </div>

                    <!-- Email input -->
                    <div class="form-group">
                        <label for="email">E-mail:</label>
                        <input name="email" type="email" id="email" class="form-control" data-validation="email" 
                               placeholder="Enter your email address" value="${uemail}">
                        <label style="color: red">${msgErrorUsername}</label>
                    </div>

                    <!-- Password input -->
                    <div class="form-group" >
                        <label for="">Password:</label>
                        <input name="password" type="password" id="password" class="form-control"
                               data-validation="length" data-validation-length="min8" placeholder="Enter your password" >
                        <label style="color: red">${msgErrorPassword}</label>
                    </div>                                        
                    <!-- Forgot Password -->
                    <div class="form-group" >                                
                        <a href="${pageContext.request.contextPath}/resetPassword" class="form-text">Forgot password?</a>
                    </div>
                    <p class="">Don't have an account? 
                        <a href="${pageContext.request.contextPath}/register" class="link-danger">Register</a>
                    </p>
                    <!-- Sign in button -->
                    <button type="submit" value="Login" >Sign in</button>                     
                    <div class="socials">
                        <p>Sign in with social platforms</p>

                        <!--     ???ng link https://accounts.google.com/o/oauth2/...force
                             dùng ?? g?i h?p tho?i ??ng nh?p và cài ??t URL chuy?n h??ng-->

                        <!--      Sau khi b?n ch?n tài kho?n ?? ??ng nh?p, google s? g?i m?t ?o?n mã (code) 
                            t?i url ?http://localhost:8080/AccessGoogle/login-google? ?? server c?a b?n x? lý.-->
                        <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/StudentProjectManagement/login-google&response_type=code
                           &client_id=571220986659-t6e8dm1j1jsf5h1o48s04qhnfca7sjle.apps.googleusercontent.com&approval_prompt=force" class="socials-icon">
                            <i class="zmdi zmdi-google" ></i>
                        </a>
                    </div>
                </form>
            </div>

        </div>                    
        <script src="js/login/jquery-3.3.1.min.js" type="text/javascript"></script>
        <script src="js/login/jquery.form-validator.min.js" type="text/javascript"></script>
        <script src="js/login/main.js" type="text/javascript"></script>    
    </body>

</html>
