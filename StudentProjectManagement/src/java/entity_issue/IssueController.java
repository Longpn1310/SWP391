/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_issue;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import entity_user.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.http.client.fluent.Response;

/**
 *
 * @author trung
 */
public class IssueController extends HttpServlet {

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
        IssueDao issueDao = new IssueDao();
        try {
            List<Issue> listIssue = issueDao.getAllIssue();
            List<User> listUser = issueDao.getAllUser();                        
            int page = (int) Math.ceil((double) listIssue.size() / 5);
                int startIndex = 0;
                int endIndex = 5;
                if (endIndex >= listIssue.size()) {
                    endIndex = listIssue.size();
                }                                
                session.setAttribute("listUser", listUser);
                session.setAttribute("listIssue", listIssue);
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("endIndex", endIndex);
                request.setAttribute("pageNumb", page);
        } catch (SQLException ex) {
            Logger.getLogger(IssueController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IssueController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        RequestDispatcher dispatcher //
                = request.getServletContext().getRequestDispatcher("/student/issue/list.jsp");
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
        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();
        // Lấy action truyền về từ jsp
        String action = request.getParameter("action");
        RequestDispatcher dispatcher;
        IssueDao issueDao = new IssueDao();

        switch (action) {
            case "syncGitlab":
                try {
                    issueDao.syncIssue();
                    System.out.println("successsssssssssssssss");
                } catch (SQLException ex) {
                    Logger.getLogger(IssueController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(IssueController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(IssueController.class.getName()).log(Level.SEVERE, null, ex);
                } catch( IllegalArgumentException ex) {
                    
                    Logger.getLogger(IssueController.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.sendRedirect(contextPath + "/issue");
//                dispatcher = request.getServletContext().getRequestDispatcher("/student/issue/list.jsp");
//                dispatcher.forward(request, response);
                break;
            
            case "paging":
                List<Issue> listIssue = (ArrayList<Issue>) session.getAttribute("listIssue");
                int pageNumb = Integer.parseInt(request.getParameter("pageNumb"));
                int pageChoose = Integer.parseInt(request.getParameter("pageChoose"));
                int startIndex = (pageChoose - 1) * 5;
                int endIndex = pageChoose * 5;
                if (endIndex >= listIssue.size()) {
                    endIndex = listIssue.size();
                }                
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("endIndex", endIndex);
                request.setAttribute("pageNumb", pageNumb);
                dispatcher = request.getServletContext().getRequestDispatcher("/student/issue/list.jsp");
                dispatcher.forward(request, response);
                break;

        }
//         dispatcher //
//                    = request.getServletContext().getRequestDispatcher("/student/issue/list.jsp");
//            dispatcher.forward(request, response);
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
