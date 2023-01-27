/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

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
import entity_user.User;
import utils.EncryptPassword;

/**
 *
 * @author trung
 */
public class ChangePasswordController extends HttpServlet {

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
//        processRequest(request, response);
        boolean valid = false;
        request.setAttribute("valid", valid);
        request.setAttribute("old-password", "");
        request.setAttribute("password", "");
        request.setAttribute("confirmPassword", "");
        RequestDispatcher dispatcher //
                = request.getServletContext().getRequestDispatcher("/common/changePassword/changePassword.jsp");
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
        String email = request.getParameter("email");
        if (email == null) {
            HttpSession session = request.getSession();
            email = (String) session.getAttribute("email");
        }
        String oldPassword = request.getParameter("old-password");
        String newPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        ChangePasswordDao changePasswordDao = new ChangePasswordDao();
        boolean valid = true;

        try {
            User user = changePasswordDao.getUserByEmail(email);
            if (user == null) {
                request.setAttribute("old-password", oldPassword);
                request.setAttribute("password", newPassword);
                request.setAttribute("confirmPassword", confirmPassword);
                request.setAttribute("err", "Email not found in system");
                valid = false;
                request.setAttribute("valid", valid);
            } else if (!user.getPassword().equals(EncryptPassword.encrypt(oldPassword))) {
                request.setAttribute("old-password", oldPassword);
                request.setAttribute("password", newPassword);
                request.setAttribute("confirmPassword", confirmPassword);
                request.setAttribute("err", "Old password not correct!");
                valid = false;
                request.setAttribute("valid", valid);
            } else if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("old-password", oldPassword);
                request.setAttribute("password", newPassword);
                request.setAttribute("confirmPassword", confirmPassword);
                request.setAttribute("err", "Confirm password not correct.");
                valid = false;
                request.setAttribute("valid", valid);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChangePasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChangePasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (valid) {
            try {
                ChangePasswordDao.changePassword(email, EncryptPassword.encrypt(newPassword));
                request.setAttribute("msg", "Change password success. Click to this link to login.");
                request.setAttribute("valid", valid);
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/common/changePassword/changePassword.jsp");
                dispatcher.forward(request, response);
                HttpSession session = request.getSession();
//                session.removeAttribute("user1");
            } catch (SQLException ex) {
                Logger.getLogger(ChangePasswordController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ChangePasswordController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            RequestDispatcher dispatcher //
                    = request.getServletContext().getRequestDispatcher("/common/changePassword/changePassword.jsp");
            dispatcher.forward(request, response);
        }

//        if (!valid) {
//            request.setAttribute("email", email);
//        } else {
//            request.setAttribute("msg", "Change password success");
//            HttpSession session = request.getSession();
//            session.removeAttribute("user1");
//            try {
//                ChangePasswordDao.changePassword(email, EncryptPassword.encrypt(newPassword));
//            } catch (SQLException ex) {
//                Logger.getLogger(ChangePasswordController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(ChangePasswordController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
//                String contextPath = request.getContextPath();
//                response.sendRedirect(contextPath + "/setting");
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
