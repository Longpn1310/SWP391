/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity_user.User;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import utils.SendEmail;
import utils.EncryptPassword;

/**
 *
 * @author trung
 */
public class ResetPasswordController extends HttpServlet {

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
        String paramValue = request.getParameter("user");
        HttpSession session = request.getSession();
        try {
            if (paramValue == null) {
                request.setAttribute("email", "");
                RequestDispatcher dispatcher
                        = request.getServletContext().getRequestDispatcher("/common/resetPassword/resetPassword.jsp");
                dispatcher.forward(request, response);
            } else if (((String) session.getAttribute("expired")).equals("")) {
                RequestDispatcher dispatcher
                        = request.getServletContext().getRequestDispatcher("/common/resetPassword/expired.jsp");
                dispatcher.forward(request, response);
            } else {
//                String expired = (String) session.getAttribute("expired");
                request.setAttribute("user", paramValue);
                request.setAttribute("password", "");
                request.setAttribute("confirmPassword", "");
                RequestDispatcher dispatcher
                        = request.getServletContext().getRequestDispatcher("/common/resetPassword/setNewPassword.jsp");
                dispatcher.forward(request, response);
            }
        } catch (NullPointerException e) {
            RequestDispatcher dispatcher
                    = request.getServletContext().getRequestDispatcher("/common/resetPassword/expired.jsp");
            dispatcher.forward(request, response);
        }

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
//        String action = request.getParameter("action");
        ResetPasswordDao resetPasswordDao = new ResetPasswordDao();
        String contextPath = request.getContextPath();
        String paramValue = request.getParameter("user");

//        switch (action) {
//            case "inputEmail":
        if (paramValue == null) {
            String email = request.getParameter("email");
            boolean valid = true;
            try {
                User user = resetPasswordDao.getUserByEmail(email);
                if (user == null) {
                    request.setAttribute("email", email);
                    request.setAttribute("err", "Email not found in system");
                    valid = false;
                } else {
//                    String params = EncryptPassword.encrypt(Integer.toString(user.getUserId()));
                    HttpSession session = request.getSession();
                    session.setAttribute("expired", "false");
                    session.setMaxInactiveInterval(10);
                    int params = user.getUserId();
                    request.setAttribute("email", email);
                    SendEmail.send(email, "Reset password", "http://localhost:8080/StudentProjectManagement/resetPassword?user=" + params);
                    request.setAttribute("msg", "Email was sent.");
                    request.setAttribute("user", Integer.toString(user.getUserId()));

                }
            } catch (SQLException ex) {
                Logger.getLogger(ResetPasswordController.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ResetPasswordController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            RequestDispatcher dispatcher //
                    = request.getServletContext().getRequestDispatcher("/common/resetPassword/resetPassword.jsp");
            dispatcher.forward(request, response);

        } else {
            try {

                User user = resetPasswordDao.getUserById(Integer.parseInt(paramValue));
                String password = request.getParameter("password");
                String confirmPassword = request.getParameter("confirmPassword");
                if (!password.equals(confirmPassword)) {
                    request.setAttribute("password", password);
                    request.setAttribute("confirmPassword", confirmPassword);
                    request.setAttribute("err", "Confirm password not correct.");
                    RequestDispatcher dispatcher //
                            = request.getServletContext().getRequestDispatcher("/common/resetPassword/setNewPassword.jsp");
                    dispatcher.forward(request, response);
                } else {
                    resetPasswordDao.changePassword(Integer.parseInt(paramValue), EncryptPassword.encrypt(password));
                    request.setAttribute("msg", "Reset password success. Click here to login.");
                    RequestDispatcher dispatcher //
                            = request.getServletContext().getRequestDispatcher("/common/resetPassword/setNewPassword.jsp");
                    dispatcher.forward(request, response);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ResetPasswordController.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ResetPasswordController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

//        }
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
