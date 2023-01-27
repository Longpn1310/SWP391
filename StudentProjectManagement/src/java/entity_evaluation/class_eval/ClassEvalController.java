/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_evaluation.class_eval;

import entity_evaluation.ClassEval;
import entity_class.Class;
import entity_evaluation.IterationEvaluation;
import entity_iteration.Iteration;
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
public class ClassEvalController extends HttpServlet {

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
        ClassEvalDao classEvalDao = new ClassEvalDao();
        if (user.getRoleId() == 3 && isLogined) {
            try {
                //Lấy danh sách dữ liệu từ DB    
                List<Class> classes = classEvalDao.getAllClass(user.getUserId());
                List<ClassEval> classEvals = classEvalDao.getClassEvals(classes.get(0).getClassId());
                List<Iteration> iterations = classEvalDao.getAllIteration(classes.get(0).getClassId());
                for (ClassEval classEval : classEvals) {
                    classEval.setIterationEvaluations(classEvalDao.getIterationEvaluation(classEval.getClassId(), classEval.getUserId()));
                }                
                int page = (int) Math.ceil((double) classEvals.size() / 5);
                int startIndex = 0;
                int endIndex = 5;
                if (endIndex >= classEvals.size()) {
                    endIndex = classEvals.size();
                }
                session.setAttribute("classEvals", classEvals);
                session.setAttribute("classes", classes);
                session.setAttribute("iteration", iterations);
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("endIndex", endIndex);
                request.setAttribute("pageNumb", page);
                session.setAttribute("selectedClass", classes.get(0).getClassId());
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ClassEvalController.class.getName()).log(Level.SEVERE, null, ex);
            }
            RequestDispatcher dispatcher //
                    = request.getServletContext().getRequestDispatcher("/trainer/class_eval/class_eval.jsp");
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
        ClassEvalDao classEvalDao = new ClassEvalDao();
        // Lấy action truyền về từ jsp
        String action = request.getParameter("action");
        switch (action) {
            case "search":
                try {
                //Lấy dữ liệu từ jsp
                int classId = Integer.parseInt(request.getParameter("classId"));
                //Thực hiện việc tìm kiếm và trả về list đã tìm kiếm theo status hoặc title
                List<ClassEval> classEvals = classEvalDao.getClassEvals(classId);
                for (ClassEval classEval : classEvals) {
                    classEval.setIterationEvaluations(classEvalDao.getIterationEvaluation(classEval.getClassId(), classEval.getUserId()));
                }
                int page = (int) Math.ceil((double) classEvals.size() / 5);
                int startIndex = 0;
                int endIndex = 5;
                if (endIndex >= classEvals.size()) {
                    endIndex = classEvals.size();
                }
                session.setAttribute("classEvals", classEvals);
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("endIndex", endIndex);
                request.setAttribute("pageNumb", page);
                session.setAttribute("selectedClass", classId);
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/trainer/class_eval/class_eval.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ClassEvalController.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case "paging":
                //Dùng để thực hiện phân trang
                List<ClassEval> classEvals = (ArrayList<ClassEval>) session.getAttribute("classEvals");
                int pageNumb = Integer.parseInt(request.getParameter("pageNumb"));
                int pageChoose = Integer.parseInt(request.getParameter("pageChoose"));
                int startIndex = (pageChoose - 1) * 5;
                int endIndex = pageChoose * 5;
                if (endIndex >= classEvals.size()) {
                    endIndex = classEvals.size();
                }
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("endIndex", endIndex);
                request.setAttribute("pageNumb", pageNumb);
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/trainer/class_eval/class_eval.jsp");
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
