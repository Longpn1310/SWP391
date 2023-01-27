/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import entity_user.User;
import entity_user.UserDao;
import utils.GooglePojo;
import utils.GoogleUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.Constants;
import utils.EncryptPassword;
import utils.SendEmail;

/**
 *
 * @author admin
 */
@WebServlet(name = "LoginGoogle", urlPatterns = {"/login-google"})
public class LoginGoogle extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginGoogle</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginGoogle at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private static final long serialVersionUID = 1L;

    //LoginGoogleServlet.java sẽ nhận đoạn mã trả về từ google,
    //đổi mã đó thành access-token rồi dùng access-token để truy cập các 
    //thông tin trong tài khoản google như email, name, id…
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            RequestDispatcher dis = request.getRequestDispatcher("login.jsp");
            dis.forward(request, response);
        } else {
            String accessToken = GoogleUtils.getToken(code);
            GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
            request.setAttribute("id", googlePojo.getId());
            request.setAttribute("name", googlePojo.getName());
            request.setAttribute("email", googlePojo.getEmail());
            String email = googlePojo.getEmail();
            try {
                User user = new UserDao().getUserByEmail(email);
                if (user == null) {
                    String password = Constants.generatePassword();
                    new RegisterDao().registerUser(new User(0, "", "", email, EncryptPassword.encrypt(password), "", "", 4, true, ""));
                    SendEmail.send(email, "Password", "Tafi khoan cua ban da duoc tao voi password la: " + password);
                    response.sendRedirect("login");
                    return;
                }
                HttpSession session = request.getSession();
                session.setAttribute("user1", user);
                switch (user.getRoleId()) {
                        case 1:
                            response.sendRedirect("setting");
                            break;
                        case 2:
                            response.sendRedirect("subject-setting");
                            break;
                        case 3:
                            response.sendRedirect("class-setting");
                            break;
                        case 4:
                            response.sendRedirect("function");
                            break;                        
                    }
            } catch (Exception ex) {
                request.setAttribute("error", "Tài khoản chưa được đăng ký.Hãy đăng ký với tài khoản với mail này.");
                request.setAttribute("email", email);
                request.getRequestDispatcher("common/register/register.jsp").forward(request, response);

            } 
            
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
