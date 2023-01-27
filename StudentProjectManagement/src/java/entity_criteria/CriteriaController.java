/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_criteria;

import entity_iteration.Iteration;
import entity_subject.Subject;
import utils.Constant;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
public class CriteriaController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */    
    private CriteriaDao dao = new CriteriaDao();

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
        ArrayList<Subject> subjectList = dao.searchSubject();
        request.setAttribute("subjectLists", subjectList);
        ArrayList<Iteration> iterationList = dao.searchIteration();
        request.setAttribute("iterationLists", iterationList);
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        request.setAttribute("action", action);
        switch (action) {
            case "add":
                request.setAttribute("subjects", dao.searchSubject());
                request.getRequestDispatcher("/manager/evaluation_criteria/add-criteria.jsp").forward(request, response);
                return;
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                Criteria it = dao.getById(id);
                request.setAttribute("criteria", it);
                request.getRequestDispatcher("/manager/evaluation_criteria/edit-criteria.jsp").forward(request, response);
                return;
            case "changestatus":
                id = Integer.parseInt(request.getParameter("id"));
                Criteria cri = dao.getById(id);
                cri.setStatus(!cri.isStatus());
                dao.updateCriteria(cri);
                response.sendRedirect("criteria");
                return;

        }
        Integer subjectId = subjectList.get(0).getSubjectId();
        Integer iterationId = iterationList.get(0).getId();
        try {
            subjectId = Integer.valueOf(request.getParameter("subject-id"));
            iterationId = Integer.valueOf(request.getParameter("iteration-id"));
        } catch (Exception e) {

        }
        request.setAttribute("subjectId", subjectId);
        request.setAttribute("iterationId", iterationId);
        Boolean status = null;
        if (request.getParameter("status") != null && !request.getParameter("status").isEmpty()) {
            status = Boolean.parseBoolean(request.getParameter("status"));
        }

        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
        }
        int start = (page - 1) * 5;
        int end = start + 5;
        request.setAttribute("criterias", dao.search(subjectId, iterationId, status, start, end));
        String criteriaName = request.getParameter("criteria-name");
        if (criteriaName == null) {
            criteriaName = "";
        }
        request.setAttribute("criteriaName", criteriaName);

        request.setAttribute("subjectId", subjectId);
        request.setAttribute("iterationId", iterationId);
        int count = dao.count(subjectId, iterationId, status);
        int totalPage = (count - 1) / 5 + 1;
        request.setAttribute("totalPage", totalPage);
        StringBuffer uri = request.getRequestURL().append("?").append(request.getQueryString());
        uri.toString().replace("page=" + (page ), "");
        request.setAttribute("uri", uri.toString().replace("page=" + (page ), ""));
        request.setAttribute("page", page);
        start = page * 5;
        end = start + 5;

        request.getRequestDispatcher("/manager/evaluation_criteria/criteria-list.jsp").forward(request, response);

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
        String action = request.getParameter("action");
        ArrayList<Subject> subjectList = dao.searchSubject();
        request.setAttribute("subjectLists", subjectList);
        ArrayList<Iteration> iterationList = dao.searchIteration();
        request.setAttribute("iterationLists", iterationList);
        String criteriaName, description;
        Integer subjectId, iterationId, evalWeight = 0, maxLoc = 0;
        boolean isValid = true;
        Boolean status;
        Boolean isTeamEval;
        if (action.equals("add")) {
            criteriaName = request.getParameter("criteriaName").trim();
//            subjectId = Integer.parseInt(request.getParameter("subjectId"));
            iterationId = Integer.parseInt(request.getParameter("iterationId"));
            try {
                evalWeight= Integer.parseInt(request.getParameter("evalWeight"));
                if(evalWeight < 0 ||evalWeight> 100 ) {
                    request.setAttribute("msgEvalWeight", "Eval weight phai trong khoang 0->100");
                    isValid = false;
                }
            } catch (Exception ex) {
                request.setAttribute("msgEvalWeight", Constant.MSG_EMPTY);
                isValid = false;
            }
            try {
                maxLoc = Integer.parseInt(request.getParameter("maxLoc"));
                if(maxLoc < 0) {
                    request.setAttribute("msgMaxLoc", "Max loc phai lon hon 0");
                    isValid = false;
                }
            } catch (Exception e) {
                isValid = false;

                request.setAttribute("msgMaxLoc", Constant.MSG_EMPTY);

            }
            description = request.getParameter("description").trim();

            if (criteriaName.isEmpty()) {
                isValid = false;
                request.setAttribute("msgCriteriaName", Constant.MSG_EMPTY);
            }

            if (description.isEmpty()) {
                isValid = false;
                request.setAttribute("msgDescription", Constant.MSG_EMPTY);
            }
            isTeamEval = Boolean.parseBoolean(request.getParameter("isTeamEval"));
            status = Boolean.parseBoolean(request.getParameter("status"));
            Criteria criteria = new Criteria(0, iterationId, criteriaName, evalWeight, maxLoc, isTeamEval, status, description);
            request.setAttribute("criteria", criteria);
            if (isValid) {
                dao.addCriteria(criteria);
                response.sendRedirect("criteria");
                return;
            }

        }
        if (action.equals("edit")) {
            criteriaName = request.getParameter("criteriaName").trim();
//            subjectId = Integer.parseInt(request.getParameter("subjectId"));
            iterationId = Integer.parseInt(request.getParameter("iterationId"));
            try {
                evalWeight = Integer.parseInt(request.getParameter("evalWeight"));
                if(evalWeight <0 || evalWeight > 100 ) {
                    request.setAttribute("msgEvalWeight", "Eval weight phai trong khoang 0->100");
                    isValid = false;
                }
            } catch (Exception ex) {
                request.setAttribute("msgEvalWeight", Constant.MSG_EMPTY);
                isValid = false;
            }
            try {
                maxLoc = Integer.parseInt(request.getParameter("maxLoc"));
                if(maxLoc < 0) {
                    request.setAttribute("msgMaxLoc", "Max loc phai lon hon 0");
                    isValid = false;
                }
            } catch (Exception e) {
                isValid = false;

                request.setAttribute("msgMaxLoc", Constant.MSG_EMPTY);

            }
            description = request.getParameter("description").trim();

            if (criteriaName.isEmpty()) {
                isValid = false;
                request.setAttribute("msgCriteriaName", Constant.MSG_EMPTY);
            }

            if (description.isEmpty()) {
                isValid = false;
                request.setAttribute("msgDescription", Constant.MSG_EMPTY);
            }
            isTeamEval = Boolean.parseBoolean(request.getParameter("isTeamEval"));
            status = Boolean.parseBoolean(request.getParameter("status"));
            Criteria criteria = new Criteria(0, iterationId, criteriaName, evalWeight, maxLoc, isTeamEval, status, description);
            request.setAttribute("criteria", criteria);
            int id = Integer.parseInt(request.getParameter("id"));
            Criteria updateCriteria = new Criteria(id, iterationId, criteriaName, evalWeight, maxLoc, isTeamEval, status, description);
            request.setAttribute("criteria", updateCriteria);
            if (isValid) {
                dao.updateCriteria(updateCriteria);
                response.sendRedirect("criteria");
                return;
            }else {
                 request.getRequestDispatcher("/manager/evaluation_criteria/edit-criteria.jsp").forward(request, response);
                 return;
            }
                  

        }
        if (action.equals("changestatus")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Criteria t = dao.getById(id);
            t.setStatus(!t.isStatus());
            dao.updateCriteria(t);
            response.sendRedirect("criteria");
            return;
        }
        ArrayList<Subject> subjects = dao.searchSubject();
        request.setAttribute("subjectLists", subjects);
        ArrayList<Iteration> iterations = dao.searchIteration();
        request.setAttribute("iterationLists", iterations);
        request.setAttribute("subjects", dao.searchSubject());
        request.getRequestDispatcher("/manager/evaluation_criteria/add-criteria.jsp").forward(request, response);
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
