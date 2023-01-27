/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_user;

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
import entity_user.User;

/**
 *
 * @author trung
 */
public class UserProfileController extends HttpServlet {

    UserProfileDao userProfileDao = new UserProfileDao();

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
        HttpSession session = request.getSession();
//            User user = userProfileDAO.getUserProfile("e2bs.cutung@gmail.com");
//        User user = (User) session.getAttribute("user1");
        String email = (String) session.getAttribute("email");

        try {
            User user = new UserProfileDao().getUserByEmail(email);
            String mobile = user.getMobile();
            request.setAttribute("user", user);
            request.setAttribute("mobile", mobile);
            String role = "";
            String status = "";
            if (user.getRoleId() == 1) {
                role = "Admin";
            } else if (user.getRoleId() == 2) {
                role = "Manager";
            } else if (user.getRoleId() == 3) {
                role = "Trainer";
            } else {
                role = "Student";
            };
            if (!user.isStatus()) {
                status = "Inactive";
            } else {
                status = "Active";
            };
            request.setAttribute("status", status);
            request.setAttribute("role", role);
            request.setAttribute("link", "http://localhost:8080${pageContext.request.contextPath}/userProfile");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }

        RequestDispatcher dispatcher
                = request.getServletContext().getRequestDispatcher("/common/userProfile/userProfile.jsp");
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
            String mobileRegex = "[0-9]+";
            String rollNumber = request.getParameter("rollNumber");
            String fullName = request.getParameter("fullName");
            String mobile = request.getParameter("mobile");
            String link = request.getParameter("link");
//            String status = request.getParameter("status");
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("email");
            User updatedUser = new UserProfileDao().getUserByEmail(email);
            String role = "";
            String status = "";
            if (updatedUser.getRoleId() == 1) {
                role = "Admin";
            } else if (updatedUser.getRoleId() == 2) {
                role = "Manager";
            } else if (updatedUser.getRoleId() == 3) {
                role = "Trainer";
            } else {
                role = "Student";
            };
            if (!updatedUser.isStatus()) {
                status = "Inactive";
            } else {
                status = "Active";
            };
            if (!mobile.equals("") && !mobile.matches(mobileRegex)) {
                request.setAttribute("mobile", mobile);
                request.setAttribute("role", role);
                request.setAttribute("status", status);
                request.setAttribute("user", updatedUser);
                request.setAttribute("link", link);
                request.setAttribute("err", "Wrong syntax mobile!");

                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/common/userProfile/userProfile.jsp");
                dispatcher.forward(request, response);
            } else {

//            request.setAttribute("user", updatedUser);
 request.setAttribute("mobile", mobile);
                request.setAttribute("role", role);
                request.setAttribute("status", status);
                request.setAttribute("user", updatedUser);
                request.setAttribute("link", link);
                updatedUser.setRollNumber(rollNumber);
                updatedUser.setFullName(fullName);
                updatedUser.setMobile(mobile);
                userProfileDao.updateUserProfile(updatedUser);
//                        String contextPath = request.getContextPath();
//        response.sendRedirect(contextPath + "/userProfile"); 
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/common/userProfile/userProfile.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try{
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        RequestDispatcher dispatcher //
//                = request.getServletContext().getRequestDispatcher("/common/userProfile/userProfile.jsp");
//        dispatcher.forward(request, response);
//        String contextPath = request.getContextPath();
//        response.sendRedirect(contextPath + "/userProfile");    
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
