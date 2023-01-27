/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import entity_user.UserDao;
import entity_user.User;
import utils.EncryptPassword;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
public class LoginController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher //
                = request.getServletContext().getRequestDispatcher("/common/login/login.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            boolean isEmpty = false;
            String uemail = request.getParameter("email");
            String upassword = request.getParameter("password");
            if (uemail.trim().isEmpty()) {
                request.setAttribute("msgErrorUsername", "Email must not blank");
                isEmpty = true;

            }
            if (upassword.trim().isEmpty()) {
                request.setAttribute("msgErrorPassword", "password must not blank");
                isEmpty = true;

            }
            

            request.setAttribute("uemail", uemail);
            if(isEmpty) {
                request.getRequestDispatcher("/common/login/login.jsp").forward(request, response);
                return;
            }
            UserDao udao = new UserDao();
            User user = udao.getUserByEmail(uemail);
            if (user == null) {
                request.setAttribute("msgErrorUsername", "Email not found");
                request.getRequestDispatcher("/common/login/login.jsp").forward(request, response);
                return;
            } else if (user.getPassword().equals(EncryptPassword.encrypt(upassword)) == false) {
                request.setAttribute("msgErrorPassword", "Password not match");
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/common/login/login.jsp");
                dispatcher.forward(request, response);
                return;
            }
            HttpSession session = request.getSession();
            try {
                if (user.getEmail().equals(uemail) && user.getPassword().equals(EncryptPassword.encrypt(upassword))) {

                    session.setAttribute("user1", user);
                    session.setAttribute("email", user.getEmail());
                    session.setAttribute("role", user.getRoleId());
                    String contextPath = request.getContextPath();
                    session.setAttribute("logined", true);

                    switch (user.getRoleId()) {
                        case 1:
                            response.sendRedirect(contextPath + "/setting");
                            break;
                        case 2:
                            response.sendRedirect(contextPath + "/subject-setting");
                            break;
                        case 3:
                            response.sendRedirect(contextPath + "/class-setting");
                            break;
                        case 4:
                            response.sendRedirect(contextPath + "/function");
                            break;                        
                    }
                } else {
                    session.setAttribute("logined", null);
                }
            } catch (NullPointerException nuEx) {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/login?");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
