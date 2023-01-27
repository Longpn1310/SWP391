/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_milestone;

import entity_user.User;
import entity_class.Class;
import entity_iteration.Iteration;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class MilestoneController extends HttpServlet {

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
        String mssError = "";
        if (user.getRoleId() == 3 && isLogined) {
            String action = request.getParameter("action");
            if (action == null) {
                MilestoneDao milestoneDao = new MilestoneDao();
                try {
                    List<Milestone> milestones = milestoneDao.getAllMilestoneTrainer(user.getUserId());
                    List<Class> classes = milestoneDao.getAllClass(user.getUserId());
                    int page = (int) Math.ceil((double) milestones.size() / 5);
                    int startIndex = 0;
                    int endIndex = 5;
                    if (endIndex >= milestones.size()) {
                        endIndex = milestones.size();
                    }
                    session.setAttribute("milestones", milestones);
                    session.setAttribute("classes", classes);
                    session.setAttribute("startIndex", startIndex);
                    session.setAttribute("endIndex", endIndex);
                    session.setAttribute("pageNumb", page);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(MilestoneController.class.getName()).log(Level.SEVERE, null, ex);
                }
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/trainer/milestone/list.jsp");
                dispatcher.forward(request, response);
            } else {
                try {
                    MilestoneDao milestoneDao = new MilestoneDao();
                    Milestone milestone = null;
                    List<Iteration> iterations = null;
                    String classId = request.getParameter("classId");
                    if (classId != null && !classId.equals("all")) {
                        iterations = milestoneDao.getIterationsByClassId(Integer.parseInt(classId));
                        request.setAttribute("selectedClass", classId);
                    }
                    switch (action) {
                        case "edit":
                            int milestoneId = Integer.parseInt(request.getParameter("milestoneId"));
                            milestone = milestoneDao.getMilestoneById(milestoneId);
                            if (milestone.getDescription() == null) {
                                milestone.setDescription("");
                            }
                            break;
                        case "add":
                            milestone = new Milestone();
                            milestone.setStatus(true);
                            break;
                    }
                    session.setAttribute("milestone", milestone);
                    session.setAttribute("iterations", iterations);
                    request.setAttribute("action", action);
                    request.setAttribute("mssError", mssError);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(MilestoneController.class.getName()).log(Level.SEVERE, null, ex);
                }
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/trainer/milestone/details.jsp");
                dispatcher.forward(request, response);
            }
        }
        if (user.getRoleId() == 4 && isLogined) {
            MilestoneDao milestoneDao = new MilestoneDao();
            try {
                List<Milestone> milestones = milestoneDao.getAllMilestoneTrainer(user.getUserId());
                List<Class> classes = milestoneDao.getAllClass(user.getUserId());
                int page = (int) Math.ceil((double) milestones.size() / 5);
                int startIndex = 0;
                int endIndex = 5;
                if (endIndex >= milestones.size()) {
                    endIndex = milestones.size();
                }
                session.setAttribute("milestones", milestones);
                session.setAttribute("classes", classes);
                session.setAttribute("startIndex", startIndex);
                session.setAttribute("endIndex", endIndex);
                session.setAttribute("pageNumb", page);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(MilestoneController.class.getName()).log(Level.SEVERE, null, ex);
            }
            RequestDispatcher dispatcher //
                    = request.getServletContext().getRequestDispatcher("/trainer/milestone/list.jsp");
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
        try {
            MilestoneDao milestoneDao = new MilestoneDao();
            HttpSession session = request.getSession();
            String contextPath = request.getContextPath();
            String error = "";
            // Lấy action truyền về từ jsp        
            String action = request.getParameter("action");
            User user = (User) session.getAttribute("user1");
            Milestone milestone = new Milestone();
            if (action.equals("add") || action.equals("edit")) {
                //Lấy dữ liệu của Milestone được nhập từ jsp
                String mId = request.getParameter("milestoneId");
                String iterationId = request.getParameter("iterationId");
                String cId = request.getParameter("selectedClass");
                String rawFromDate = request.getParameter("start_date");
                String rawToDate = request.getParameter("due_date");
                milestone.setStatus(Boolean.parseBoolean(request.getParameter("status")));
                milestone.setDescription(request.getParameter("description"));
                //Validate dữ liệu
                if (!mId.equals("0")) {
                    milestone.setMilestoneId(Integer.parseInt(mId));
                }
                if (!cId.equals("") && !cId.equals("null")) {
                    milestone.setClassId(Integer.parseInt(cId));
                } else {
                    error = "Please choose a class";
                    request.setAttribute("action", action);
                    request.setAttribute("mssError", error);
                    RequestDispatcher dispatcher //
                            = request.getServletContext().getRequestDispatcher("/trainer/milestone/details.jsp");
                    dispatcher.forward(request, response);
                }
                if (iterationId != null) {
                    milestone.setIterationId(Integer.parseInt(iterationId));
                }
                if (!rawFromDate.equals("")) {
                    Date fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(rawFromDate);
                    milestone.setFromDate(fromDate);
                }
                if (!rawToDate.equals("")) {
                    Date toDate = new SimpleDateFormat("yyyy-MM-dd").parse(rawToDate);
                    milestone.setToDate(toDate);
                }                
            }
            if (error.equals("")) {
                switch (action) {
                    case "add": {
                        try {
                            int status = milestoneDao.addMilestone(milestone);
                            session.setAttribute("changeResult", status);
                            response.sendRedirect(contextPath + "/milestone");
                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(MilestoneController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }
                    case "edit": {
                        try {
                            int status = milestoneDao.updateMilestone(milestone);
                            session.setAttribute("changeResult", status);
                            response.sendRedirect(contextPath + "/milestone");
                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(MilestoneController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }
                    case "change": {
                        //Dùng để thay đổi status của 1 bản ghi
                        boolean milestoneStatus = Boolean.parseBoolean(request.getParameter("milestoneStatus"));
                        try {
                            int milestoneId = Integer.parseInt(request.getParameter("milestoneId"));
                            int result = milestoneDao.changeStatusMilestone(milestoneId, milestoneStatus);
                            session.setAttribute("changeResult", result);
                            response.sendRedirect(contextPath + "/milestone");
                        } catch (SQLException | ClassNotFoundException ex) {
                            Logger.getLogger(MilestoneController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }
                    case "search": {
                        try {
                            //Lấy dữ liệu từ jsp
                            String status = request.getParameter("searchStatus");
                            String classId = request.getParameter("searchClass");
                            //Thực hiện việc tìm kiếm và trả về list đã tìm kiếm theo status hoặc title
                            List<Milestone> milestones = milestoneDao.searchMilestone(user.getUserId(), status, classId);
                            int page = (int) Math.ceil((double) milestones.size() / 5);
                            int startIndex = 0;
                            int endIndex = 5;
                            if (endIndex >= milestones.size()) {
                                endIndex = milestones.size();
                            }
                            if (!status.equals("all")) {
                                request.setAttribute("searchStatus", status);
                            }

                            if (!classId.equals("all")) {
                                request.setAttribute("classId", classId);
                            }
                            session.setAttribute("milestones", milestones);
                            session.setAttribute("startIndex", startIndex);
                            session.setAttribute("endIndex", endIndex);
                            session.setAttribute("pageNumb", page);
                            RequestDispatcher dispatcher //
                                    = request.getServletContext().getRequestDispatcher("/trainer/milestone/list.jsp");
                            dispatcher.forward(request, response);
                        } catch (SQLException | ClassNotFoundException ex) {
                            Logger.getLogger(MilestoneController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }
                    case "paging": {
                        //Dùng để thực hiện phân trang
                        List<Milestone> milestones = (ArrayList<Milestone>) session.getAttribute("milestones");
                        int pageNumb = (int) session.getAttribute("pageNumb");
                        int pageChoose = Integer.parseInt(request.getParameter("pageChoose"));
                        int startIndex = (pageChoose - 1) * 5;
                        int endIndex = pageChoose * 5;
                        if (endIndex >= milestones.size()) {
                            endIndex = milestones.size();
                        }
                        session.setAttribute("startIndex", startIndex);
                        session.setAttribute("endIndex", endIndex);
                        session.setAttribute("pageNumb", pageNumb);
                        RequestDispatcher dispatcher //
                                = request.getServletContext().getRequestDispatcher("/trainer/milestone/list.jsp");
                        dispatcher.forward(request, response);
                        break;
                    }
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(MilestoneController.class.getName()).log(Level.SEVERE, null, ex);
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
