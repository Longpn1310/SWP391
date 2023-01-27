/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_team;

import utils.Constant;
import entity_class.Class;
import entity_iteration.IterationDao;
import entity_user.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
public class TeamController extends HttpServlet {

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
            out.println("<title>Servlet TeamController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TeamController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    private TeamDao dao = new TeamDao();

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
        int userId = 1;
        try {
            HttpSession sesson = request.getSession();
            User user = (User) sesson.getAttribute("user1");
            userId = user.getUserId();
        } catch (Exception e) {
        }
        ArrayList<Class> classList = dao.search(userId);
        request.setAttribute("classLists", classList);
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        request.setAttribute("action", action);
        switch (action) {
            case "add":
                request.setAttribute("subjects", new IterationDao().search());
                request.getRequestDispatcher("/trainer/team/add-team.jsp").forward(request, response);
                return;
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                Team it = dao.getById(id);
                request.setAttribute("team", it);
                request.getRequestDispatcher("/trainer/team/edit-team.jsp").forward(request, response);
                return;
            case "changestatus":
                id = Integer.parseInt(request.getParameter("id"));
                Team tea = dao.getById(id);
                tea.setStatus(!tea.isStatus());
                dao.updateTeam(tea);
                response.sendRedirect("team");
                return;

        }
        Integer classId = classList.get(0).getClassId();
        try {
            classId = Integer.valueOf(request.getParameter("class-id"));
        } catch (Exception e) {
        }
        request.setAttribute("classId", classId);
        Boolean status = null;
        if (request.getParameter("status") != null && !request.getParameter("status").isEmpty()) {
            status = Boolean.parseBoolean(request.getParameter("status"));
        }
        String teamCode = request.getParameter("team-code");
        request.setAttribute("teamCode", teamCode);
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
        }
       
        int start = (page - 1) * 5;
        int end = start + 5;
        request.setAttribute("teams", dao.search(classId, status, teamCode, start, end));
        String projectCode = request.getParameter("project-code");
        if (projectCode == null) {
            projectCode = "";
        }
        request.setAttribute("projectCode", projectCode);

        request.setAttribute("classId", classId);
        int count = dao.count(classId, status, teamCode);
        int totalPage = (count-1)/5+1;
        request.setAttribute("totalPage", totalPage);
        StringBuffer uri = request.getRequestURL().append("?").append(request.getQueryString());
        uri.toString().replace("page=" + (page + 1), "");
        request.setAttribute("uri", uri.toString().replace("page=" + page , ""));
        request.setAttribute("page", page);
        start = page * 5;
        end = start + 5;

//        int count = dao.count(projectCode, classId, status);
//        int totalPage = (count -1)/5 + 1;
////        int endPage = 
//        request.setAttribute("totalPage", totalPage);
//        ArrayList<Team> list = dao.search(projectCode, classId, status, start, end, sort);
//        request.setAttribute("list", list);
        request.getRequestDispatcher("/trainer/team/team-list.jsp").forward(request, response);
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
        int userId = 1;
        try {
            HttpSession sesson = request.getSession();
            User user = (User) sesson.getAttribute("user");
            userId = user.getUserId();
        } catch (Exception e) {
        }
        ArrayList<Class> classList = dao.search(userId);
        request.setAttribute("classLists", classList);
        String action = request.getParameter("action");
        String teamCode, topicCode, topicName, description;
        Integer classId;
        boolean isValid = true;
        Boolean status;
        if (action.equals("add")) {
            teamCode = request.getParameter("teamCode").trim();
            classId = Integer.parseInt(request.getParameter("classId"));
            topicCode = request.getParameter("topicCode").trim();
            topicName = request.getParameter("topicName").trim();
            description = request.getParameter("description").trim();

            if (teamCode.isEmpty()) {
                isValid = false;
                request.setAttribute("msgTeamCode", Constant.MSG_EMPTY);
            }else if(dao.getByTeamCode(teamCode) != null) {
                isValid = false;
                request.setAttribute("msgTeamCode","Team code đã tồn tại");
            }
            if (topicCode.isEmpty()) {
                isValid = false;
                request.setAttribute("msgTopicCode", Constant.MSG_EMPTY);
            }
            if (topicName.isEmpty()) {
                isValid = false;
                request.setAttribute("msgTopicName", Constant.MSG_EMPTY);
            }
            if (description.isEmpty()) {
                isValid = false;
                request.setAttribute("msgDescription", Constant.MSG_EMPTY);
            }
            status = Boolean.parseBoolean(request.getParameter("status"));
            Team team = new Team(0, classId, teamCode, topicCode, topicName, status, description);
            request.setAttribute("team", team);
            if (isValid) {
                dao.addTeam(team);
                response.sendRedirect("team");
                return;
            }

        }
        if (action.equals("edit")) {
            teamCode = request.getParameter("teamCode").trim();
            classId = Integer.parseInt(request.getParameter("classId"));
            topicCode = request.getParameter("topicCode").trim();
            topicName = request.getParameter("topicName").trim();
            description = request.getParameter("description").trim();

            isValid = true;

            if (teamCode.isEmpty()) {
                isValid = false;
                request.setAttribute("msgTeamCode", Constant.MSG_EMPTY);
            }
            if (topicCode.isEmpty()) {
                isValid = false;
                request.setAttribute("msgTopicCode", Constant.MSG_EMPTY);
            }
            if (topicName.isEmpty()) {
                isValid = false;
                request.setAttribute("msgTopicName", Constant.MSG_EMPTY);
            }
            if (description.isEmpty()) {
                isValid = false;
                request.setAttribute("msgDescription", Constant.MSG_EMPTY);
            }
            status = Boolean.parseBoolean(request.getParameter("status"));
            int id = Integer.parseInt(request.getParameter("id"));
            Team updateTeam = new Team(id, classId, teamCode, topicCode, topicName, status, description);
            request.setAttribute("team", updateTeam);
            if (isValid) {
                dao.updateTeam(updateTeam);
                response.sendRedirect("team");
                return;
            }else 
                            request.getRequestDispatcher("/trainer/team/edit-team.jsp").forward(request, response);

        }
        if(action.equals("changestatus")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Team t = dao.getById(id);
            t.setStatus(!t.isStatus());
            dao.updateTeam(t);
            response.sendRedirect("team");
                return;
        }
            request.setAttribute("subjects", new IterationDao().search());
            request.getRequestDispatcher("/trainer/team/add-team.jsp").forward(request, response);
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
