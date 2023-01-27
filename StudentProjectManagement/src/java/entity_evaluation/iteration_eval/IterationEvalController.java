/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_evaluation.iteration_eval;

import entity_team.Team;
import entity_class.Class;
import entity_evaluation.IterationEvaluation;
import entity_milestone.Milestone;
import entity_user.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
 * @author ADMIN
 */
public class IterationEvalController extends HttpServlet {

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
        User user = (User) session.getAttribute("user1");
        boolean isLogined = (boolean) session.getAttribute("logined");
        IterationEvalDao iterationEvalDao = new IterationEvalDao();
        if (user.getRoleId() == 3 && isLogined) {
            try {
                //Lấy danh sách dữ liệu từ DB    
                List<Class> classes = iterationEvalDao.getAllClass(user.getUserId());
                List<Team> teams = iterationEvalDao.getAllTeam(classes.get(0).getClassId());
                List<Milestone> milestones = iterationEvalDao.getAllMilestone(classes.get(0).getClassId());
                List<IterationEvaluation> iterationEvaluations
                        = iterationEvalDao.getAllIterationEvaluation(classes.get(0).getClassId(), 0, milestones.get(0).getMilestoneId());
                iterationEvaluations = iterationEvalDao.getAllTeamEvaluation(iterationEvaluations);
                iterationEvaluations = iterationEvalDao.getAllMemberEvaluation(iterationEvaluations);
                int page = (int) Math.ceil((double) iterationEvaluations.size() / 5);
                int startIndex = 0;
                int endIndex = 5;
                if (endIndex >= iterationEvaluations.size()) {
                    endIndex = iterationEvaluations.size();
                }
                session.setAttribute("iterationEvaluations", iterationEvaluations);
                session.setAttribute("classes", classes);
                session.setAttribute("teams", teams);
                session.setAttribute("milestones", milestones);
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("endIndex", endIndex);
                request.setAttribute("pageNumb", page);
                session.setAttribute("selectedClass", classes.get(0).getClassId());
                session.setAttribute("selectedTeam", 0);
                session.setAttribute("selectedMilestone", milestones.get(0).getMilestoneId());
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(IterationEvalController.class.getName()).log(Level.SEVERE, null, ex);
            }
            RequestDispatcher dispatcher //
                    = request.getServletContext().getRequestDispatcher("/trainer/iteration-evaluation/iteration-evaluation.jsp");
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
        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();
        IterationEvalDao iterationEvalDao = new IterationEvalDao();
        // Lấy action truyền về từ jsp
        String action = request.getParameter("action");
        switch (action) {
            case "search":
                try {
                //Lấy dữ liệu từ jsp
                int classId = Integer.parseInt(request.getParameter("classId"));
                int teamId = Integer.parseInt(request.getParameter("teamId"));
                int milestoneId = Integer.parseInt(request.getParameter("milestoneId"));
                //Thực hiện việc tìm kiếm và trả về list đã tìm kiếm theo status hoặc title
                List<IterationEvaluation> iterationEvaluations
                        = iterationEvalDao.getAllIterationEvaluation(classId, teamId, milestoneId);
                iterationEvaluations = iterationEvalDao.getAllTeamEvaluation(iterationEvaluations);
                iterationEvaluations = iterationEvalDao.getAllMemberEvaluation(iterationEvaluations);
                int page = (int) Math.ceil((double) iterationEvaluations.size() / 5);
                int startIndex = 0;
                int endIndex = 5;
                if (endIndex >= iterationEvaluations.size()) {
                    endIndex = iterationEvaluations.size();
                }
                session.setAttribute("iterationEvaluations", iterationEvaluations);
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("endIndex", endIndex);
                request.setAttribute("pageNumb", page);
                session.setAttribute("selectedClass", classId);
                session.setAttribute("selectedTeam", teamId);
                session.setAttribute("selectedMilestone", milestoneId);
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/trainer/iteration-evaluation/iteration-evaluation.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(IterationEvalController.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case "paging":
                //Dùng để thực hiện phân trang
                List<IterationEvaluation> iterationEvaluations = (ArrayList<IterationEvaluation>) session.getAttribute("iterationEvaluations");
                int pageNumb = Integer.parseInt(request.getParameter("pageNumb"));
                int pageChoose = Integer.parseInt(request.getParameter("pageChoose"));
                int startIndex = (pageChoose - 1) * 5;
                int endIndex = pageChoose * 5;
                if (endIndex >= iterationEvaluations.size()) {
                    endIndex = iterationEvaluations.size();
                }
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("endIndex", endIndex);
                request.setAttribute("pageNumb", pageNumb);
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/trainer/iteration-evaluation/iteration-evaluation.jsp");
                dispatcher.forward(request, response);
                break;
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
