/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_evaluation.student_eval;

import entity_evaluation.EvaluationCriteria;
import entity_evaluation.MemberEvaluation;
import entity_evaluation.TeamEvaluation;
import entity_function.Function;
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
public class StudentEvaluationController extends HttpServlet {

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
        int evaluationId = Integer.parseInt(request.getParameter("evaluationId"));
        int teamId = Integer.parseInt(request.getParameter("teamId"));
        int milestoneId = Integer.parseInt(request.getParameter("milestoneId"));
        int totalLoc = 0;
        float bonus = Float.parseFloat(request.getParameter("bonus"));
        StudentEvaluationDao evaluationDao = new StudentEvaluationDao();
        if (user.getRoleId() == 3 && isLogined) {
            try {
                //Lấy danh sách dữ liệu từ DB    
                List<TeamEvaluation> teamEvaluations = evaluationDao.getAllTeamEvaluation(teamId, milestoneId);
                List<MemberEvaluation> memberEvaluations = evaluationDao.getAllMemberEvaluation(evaluationId);
                for (MemberEvaluation memberEvaluation : memberEvaluations) {
                    totalLoc += memberEvaluation.getTotalLoc();
                }
                List<Function> functions = evaluationDao.getTeamFunction(teamId);
                session.setAttribute("teamEvaluations", teamEvaluations);
                session.setAttribute("memberEvaluations", memberEvaluations);
                session.setAttribute("totalLoc", totalLoc);
                session.setAttribute("bonus", bonus);
                session.setAttribute("functions", functions);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(StudentEvaluationController.class.getName()).log(Level.SEVERE, null, ex);
            }
            RequestDispatcher dispatcher //
                    = request.getServletContext().getRequestDispatcher("/trainer/student-evaluation/student-evaluation.jsp");
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
        StudentEvaluationDao evaluationDao = new StudentEvaluationDao();
        List<TeamEvaluation> teamEvaluations = (ArrayList<TeamEvaluation>) session.getAttribute("teamEvaluations");
        List<MemberEvaluation> memberEvaluations = (ArrayList<MemberEvaluation>) session.getAttribute("memberEvaluations");
        for (int i = 0; i < teamEvaluations.size(); i++) {
            float teamGrade = Float.parseFloat(request.getParameter("teamGrade" + i));
            String teamComment = request.getParameter("teamComment" + i);
            int teamId = Integer.parseInt(request.getParameter("teamId" + i));
            try {
                int result = evaluationDao.addTeamEvaluation(teamGrade, teamComment, teamId);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(StudentEvaluationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < memberEvaluations.size(); i++) {
            float memberGrade = Float.parseFloat(request.getParameter("individualGrade"+ i));
            String memberComment = request.getParameter("individualComment" + i);
            int memberId = Integer.parseInt(request.getParameter("memberId"+ i));
            try {
                int result = evaluationDao.addMemberEvaluation(memberGrade, memberComment, memberId);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(StudentEvaluationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        RequestDispatcher dispatcher //
                = request.getServletContext().getRequestDispatcher("/trainer/student-evaluation/student-evaluation.jsp");
        dispatcher.forward(request, response);
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
