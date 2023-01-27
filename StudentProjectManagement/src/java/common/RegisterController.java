/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import utils.EncryptPassword;
import entity_user.User;
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
import utils.SendEmail;

/**
 *
 * @author trung
 */
public class RegisterController extends HttpServlet {

    private RegisterDao registerDao = new RegisterDao();

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
        String action = request.getParameter("action");
        String paramValue = request.getParameter("success");
        HttpSession session = request.getSession();
        
        if(paramValue == null) {
            request.setAttribute("email", "");
            request.setAttribute("fullName", "");
            request.setAttribute("password", "");
            request.setAttribute("confirmPassword", "");
            request.setAttribute("mobile", "");
            
            RequestDispatcher dispatcher //
                = request.getServletContext().getRequestDispatcher("/common/register/register.jsp");
        dispatcher.forward(request, response);
        } else {
            
                
            try {
                String password = (String) session.getAttribute("password");
            String email = (String) session.getAttribute("email");
            String fullName = (String) session.getAttribute("fullName");
            String mobile = (String) session.getAttribute("mobile");
            
            User registerUser = new User();
                registerUser.setFullName(fullName);
                registerUser.setEmail(email);
                registerUser.setPassword(EncryptPassword.encrypt(password));
                registerUser.setMobile(mobile);
                
                registerDao.registerUser(registerUser);
                
                
             RequestDispatcher dispatcher //
                = request.getServletContext().getRequestDispatcher("/common/register/success.jsp");
        dispatcher.forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            String mobileRegex = "[0-9]+";
            String action = request.getParameter("action");

            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String mobile = request.getParameter("mobile");

                    HttpSession session = request.getSession();
//            if (action.equals("verified")) {
                if (!password.equals(confirmPassword)) {
                    request.setAttribute("err", "New password not match with confirm password");
                    request.setAttribute("email", email);
                    request.setAttribute("fullName", fullName);
                    request.setAttribute("password", password);
                    request.setAttribute("confirmPassword", confirmPassword);
                    request.setAttribute("mobile", mobile);
                    RequestDispatcher dispatcher //
                            = request.getServletContext().getRequestDispatcher("/common/register/register.jsp");
                    dispatcher.forward(request, response);
                } else if (registerDao.checkEmailExist(email)) {
                    request.setAttribute("err", "Email already exist!");
                    request.setAttribute("email", email);
                    request.setAttribute("fullName", fullName);
                    request.setAttribute("password", password);
                    request.setAttribute("confirmPassword", confirmPassword);
                    request.setAttribute("mobile", mobile);
                    RequestDispatcher dispatcher //
                            = request.getServletContext().getRequestDispatcher("/common/register/register.jsp");
                    dispatcher.forward(request, response);
                } else if (!email.matches(emailRegex)) {
                    request.setAttribute("email", email);
                    request.setAttribute("fullName", fullName);
                    request.setAttribute("password", password);
                    request.setAttribute("confirmPassword", confirmPassword);
                    request.setAttribute("mobile", mobile);
                    request.setAttribute("err", "Wrong syntax email!");
                    RequestDispatcher dispatcher //
                            = request.getServletContext().getRequestDispatcher("/common/register/register.jsp");
                    dispatcher.forward(request, response);
                } else if (!mobile.matches(mobileRegex) && !mobile.equals("")) {
                    request.setAttribute("email", email);
                    request.setAttribute("fullName", fullName);
                    request.setAttribute("password", password);
                    request.setAttribute("confirmPassword", confirmPassword);
                    request.setAttribute("mobile", mobile);
                    request.setAttribute("err", "Wrong syntax mobile!");
                    RequestDispatcher dispatcher //
                            = request.getServletContext().getRequestDispatcher("/common/register/register.jsp");
                    dispatcher.forward(request, response);
                } else if (mobile.length() < 10) {
                    request.setAttribute("email", email);
                    request.setAttribute("fullName", fullName);
                    request.setAttribute("password", password);
                    request.setAttribute("confirmPassword", confirmPassword);
                    request.setAttribute("mobile", mobile);
                    request.setAttribute("err", "Mobile number must have at least 10 digit");
                    RequestDispatcher dispatcher //
                            = request.getServletContext().getRequestDispatcher("/common/register/register.jsp");
                    dispatcher.forward(request, response);
                } else {
                    request.setAttribute("email", email);
                    request.setAttribute("fullName", fullName);
                    request.setAttribute("password", password);
                    request.setAttribute("confirmPassword", confirmPassword);
                    request.setAttribute("mobile", mobile);
                    session.setAttribute("email", email);
                    session.setAttribute("fullName", fullName);
                    session.setAttribute("password", password);
                    session.setAttribute("confirmPassword", confirmPassword);
                    session.setAttribute("mobile", mobile);
                    session.setAttribute("success", "Verify email was sent to your email!");
                    SendEmail.send(email, "Verify email!", "http://localhost:8080/StudentProjectManagement/register?success=true");
                    request.setAttribute("action", "success");
//                    String contextPath = request.getContextPath();
//                    response.sendRedirect(contextPath + "/login");
                    RequestDispatcher dispatcher //
                            = request.getServletContext().getRequestDispatcher("/common/register/register.jsp");
                    dispatcher.forward(request, response);
                }
//            } 
//            else {
//
//                User registerUser = new User();
//                registerUser.setFullName(request.getParameter("fullName"));
//                registerUser.setEmail(request.getParameter("email"));
//                registerUser.setPassword(EncryptPassword.encrypt(request.getParameter("password")));
//                registerUser.setMobile(mobile);
//
//                registerDao.registerUser(registerUser);
//                RequestDispatcher dispatcher //
//                        = request.getServletContext().getRequestDispatcher("/common/register/success.jsp");
//                dispatcher.forward(request, response);
//            }

        } catch (SQLException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
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
