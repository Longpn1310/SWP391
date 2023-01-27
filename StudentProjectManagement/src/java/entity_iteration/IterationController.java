/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_iteration;

import entity_subject.Subject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name = "IterationController", urlPatterns = {"/iteration"})
public class IterationController extends HttpServlet {

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
            out.println("<title>Servlet IterationController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet IterationController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private IterationDao dao = new IterationDao();

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
        ArrayList<Subject> subjects = dao.search();
        request.setAttribute("subjects", subjects);
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        String sort = request.getParameter("sort");
        if (sort == null) {
            sort = "iteration_id";
        }
        request.setAttribute("sort", sort);

        request.setAttribute("action", action);
        switch (action) {
            case "add":
                Iteration a = new Iteration();
                a.setOnGoing(true);
                a.setStatus(true);
                request.setAttribute("iteration", a);
                request.getRequestDispatcher("/manager/iteration/add-iteration.jsp").forward(request, response);
                return;
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                Iteration it = dao.getById(id);
                request.setAttribute("iteration", it);
                request.getRequestDispatcher("/manager/iteration/edit-iteration.jsp").forward(request, response);
                return;
            case "changestatus":
                id = Integer.parseInt(request.getParameter("id"));
                Iteration ite = dao.getById(id);
                ite.setStatus(!ite.isStatus());
                dao.updateIteration(ite);
                response.sendRedirect("iteration");
                return;

        }

        request.setAttribute("subjects", subjects);
        String iterationName = request.getParameter("iteration-name");
        if (iterationName == null) {
            iterationName = "";
        }
        request.setAttribute("iterationName", iterationName);
        int status = 1;
        try {
            if (request.getParameter("status").equals("false")) {
                status = 0;
            }

        } catch (Exception e) {
        }
        request.setAttribute("status", status);
        int subjectId = subjects.get(0).getSubjectId();
        try {
            subjectId = Integer.parseInt(request.getParameter("subject-id"));

        } catch (Exception e) {
        }
        request.setAttribute("subjectId", subjectId);
        int page = 0;
        int start = 0, end = 5;
        try {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        } catch (Exception e) {
        }

        StringBuffer uri = request.getRequestURL().append("?").append(request.getQueryString());
        uri.toString().replace("page=" + (page + 1), "");
        request.setAttribute("uri", uri.toString().replace("page=" + (page + 1), ""));
        request.setAttribute("page", page);
        start = page * 5;
        end = start + 5;

        int count = dao.count(iterationName, subjectId, status);
        int totalPage = (count - 1) / 5 + 1;
//        int endPage = 
        request.setAttribute("totalPage", totalPage);
        ArrayList<Iteration> list = dao.search(iterationName, subjectId, status, start, end, sort);
        request.setAttribute("list", list);
        request.getRequestDispatcher("/manager/iteration/iteration-list.jsp").forward(request, response);

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
        ArrayList<Subject> subjects = dao.search();
        request.setAttribute("subjects", subjects);
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        request.setAttribute("action", action);
        String iterationName, desc;
        int evalWeight = -1, subjectId = -1, id = -1;
        boolean onGoing, status;
        Iteration iteration;
        boolean isValid = true;
        switch (action) {
            case "add":
                iterationName = request.getParameter("iteration-name");
                if (iterationName.trim().isEmpty()) {
                    isValid = false;
                    request.setAttribute("msgIterationName", "Iteration name khong duoc de trong");
                }
                try {
                    evalWeight = Integer.parseInt(request.getParameter("eval-weight"));
                    if (evalWeight > 100 || evalWeight < 1) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    isValid = false;
                    request.setAttribute("msgEvalweight", "Eval weight nam trong khoang 1->100");
                }
                subjectId = Integer.parseInt(request.getParameter("subject-id"));
                onGoing = Boolean.valueOf(request.getParameter("on-going"));
                status = Boolean.valueOf(request.getParameter("status"));
                desc = request.getParameter("description");
                if (desc.trim().isEmpty()) {
                    isValid = false;
                    request.setAttribute("msgDesc", "Description khong duoc de trong");
                }
                iteration = new Iteration(subjectId, subjectId, iterationName, evalWeight, onGoing, desc, status);
                if (isValid) {
                    dao.add(iteration);
                    request.setAttribute("action", "");
                    response.sendRedirect("iteration");
                } else {
                    request.setAttribute("iteration", iteration);
                    request.getRequestDispatcher("/manager/iteration/add-iteration.jsp").forward(request, response);
                }
                break;
            case "edit":
                id = Integer.parseInt(request.getParameter("id"));
                iterationName = request.getParameter("iteration-name");
                if (iterationName.trim().isEmpty()) {
                    isValid = false;
                    request.setAttribute("msgIterationName", "Iteration name khong duoc de trong");
                }
                try {
                    evalWeight = Integer.parseInt(request.getParameter("eval-weight"));
                    if (evalWeight > 100 || evalWeight < 1) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    isValid = false;
                    request.setAttribute("msgEvalweight", "Eval weight nam trong khoang 1->100");
                }
                subjectId = Integer.parseInt(request.getParameter("subject-id"));
                onGoing = Boolean.valueOf(request.getParameter("on-going"));
                status = Boolean.valueOf(request.getParameter("status"));
                desc = request.getParameter("description");
                if (desc.trim().isEmpty()) {
                    isValid = false;
                    request.setAttribute("msgDesc", "Description khong duoc de trong");
                }
               
                iteration = new Iteration(id, subjectId, iterationName, evalWeight, onGoing, desc, status);
                if (isValid) {
                    dao.updateIteration(iteration);
                    request.setAttribute("action", "");
                    response.sendRedirect("iteration");
                } else {
                    request.setAttribute("iteration", iteration);
                    request.getRequestDispatcher("/manager/iteration/edit-iteration.jsp").forward(request, response);
                }
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
