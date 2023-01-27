/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_tracking;

import entity_milestone.Milestone;
import entity_milestone.MilestoneDao;
import entity_team.Team;
import entity_team.TeamDao;
import entity_user.User;
import entity_user.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
public class TrackingController extends HttpServlet {

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
            out.println("<title>Servlet TrackingController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TrackingController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        int userId = 0, miId = 0;

        String action = request.getParameter("action");
        if ("detail".equals(action)) {
            miId = Integer.parseInt(request.getParameter("miId"));
            int assignee = Integer.parseInt(request.getParameter("assignee"));
            int functionId = Integer.parseInt(request.getParameter("functionId"));
            int assigner = Integer.parseInt(request.getParameter("assigner"));
            request.setAttribute("tracking", dao.get(assignee, assigner, miId, functionId));
            request.getRequestDispatcher("student/tracking/detail-tracking.jsp").forward(request, response);
            return;
        }
        try {
            ArrayList<Milestone> list = (ArrayList<Milestone>) dao.getAllMilestone();
            request.setAttribute("milestones", list);
            miId = list.get(0).getMilestoneId();
            miId = Integer.parseInt(request.getParameter("miId"));
        } catch (Exception ex) {
            Logger.getLogger(TrackingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("miId", miId);
        try {
            ArrayList<User> list = (ArrayList<User>) new UserDao().getAllUser();
            request.setAttribute("users", list);
            userId = list.get(0).getUserId();
            userId = Integer.parseInt(request.getParameter("userId"));
        } catch (Exception ex) {
            Logger.getLogger(TrackingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("userId", userId);
        ArrayList<Team> teams = new TeamDao().search(null, Boolean.TRUE, "", 0, 1000);
        request.setAttribute("teams", teams);
        int teamId = teams.get(0).getTeam_id();
        try {
            teamId = Integer.parseInt(request.getParameter("teamId"));
        } catch (Exception e) {
        }
        request.setAttribute("teamId", teamId);
        String name = request.getParameter("name");
        request.setAttribute("name", name);
        request.setAttribute("trackings", dao.getAll(userId, teamId, miId, name));

        request.getRequestDispatcher("student/tracking/tracking-list.jsp").forward(request, response);
    }

    private TrackingDao dao = new TrackingDao();

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
        String action = request.getParameter("action");
        if (action.equals("detail")) {
            int miId = Integer.parseInt(request.getParameter("miId"));
            int functionId = Integer.parseInt(request.getParameter("functionId"));
            String history = request.getParameter("history");
            int status = Integer.parseInt(request.getParameter("status"));
            dao.update(functionId, miId, history, status);
            response.sendRedirect("tracking-list");
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
