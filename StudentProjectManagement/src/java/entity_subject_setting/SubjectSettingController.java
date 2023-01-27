/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_subject_setting;

import entity_setting.SettingDao;
import entity_subject.Subject;
import utils.Constant;
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
 * @author HaiLong
 */
public class SubjectSettingController extends HttpServlet {

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
            out.println("<title>Servlet SubjectSettingController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SubjectSettingController at " + request.getContextPath() + "</h1>");
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
    private SubjectSettingDao dao = new SubjectSettingDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Subject> subjects = dao.getAll();
        int subjectId = subjects.get(0).getSubjectId();
        try {
            subjectId = Integer.valueOf(request.getParameter("subjectId"));
        } catch (Exception e) {
        }
        String action = request.getParameter("action");
        if("changestatus".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            SubjectSetting s = dao.getById(id);
            s.setStatus(1-s.getStatus());
            dao.update(s);
            response.sendRedirect("subject-setting");
                return;
        }
        request.setAttribute("subjects", subjects);
        if ("add".equals(action)) {
            try {
                request.setAttribute("settings", new SettingDao().getAllSetting());
            } catch (SQLException ex) {
                Logger.getLogger(SubjectSettingController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SubjectSettingController.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.getRequestDispatcher("/manager/subject_setting/subject-setting-add.jsp").forward(request, response);
            return;
        }
        if("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            SubjectSetting s = dao.getById(id);
            request.setAttribute("subjectSetting", s);
            try {
                request.setAttribute("settings", new SettingDao().getAllSetting());
            } catch (SQLException ex) {
                Logger.getLogger(SubjectSettingController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SubjectSettingController.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.getRequestDispatcher("/manager/subject_setting/subject-setting-edit.jsp").forward(request, response);
            return;
        }
        request.setAttribute("subjectId", subjectId);
        Boolean status = null;
        try {
            if (request.getParameter("status").equals("all") == false) {
                status = Boolean.valueOf(request.getParameter("status"));
            }
        } catch (Exception e) {
        }
        request.setAttribute("status", status);
        Integer typeId = null;
        String type = request.getParameter("type");
        if (Constant.TYPE_SUBJECT_COMPLEXITY.equals(type)) {
            typeId = 2;
        }
        if (Constant.TYPE_SUBJECT_QUANTITY.equals(type)) {
            typeId = 1;
        }
        String settingTitle = request.getParameter("setting-title");
        if (settingTitle == null) {
            settingTitle = "";
        }
        request.setAttribute("subjects", subjects);
        request.setAttribute("list", dao.search(subjectId, typeId, status, settingTitle, 0));
        request.getRequestDispatcher("/manager/subject_setting/subject-setting-list.jsp").forward(request, response);
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
        ArrayList<Subject> subjects = dao.getAll();
        request.setAttribute("subjects", subjects);
        String action = request.getParameter("action");
        
        Integer subjectId=subjects.get(0).getSubjectId(), displayOrder=0, status=1, typeId=1;
        String title, value, description;
        boolean isValid = true;
        try {
                request.setAttribute("settings", new SettingDao().getAllSetting());
            } catch (SQLException ex) {
                Logger.getLogger(SubjectSettingController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SubjectSettingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        if ("add".equals(action)) {
            try {
                subjectId = Integer.parseInt(request.getParameter("subjectId"));

            } catch (Exception e) {
                isValid = false;
            }
            typeId= Integer.parseInt(request.getParameter("type"));
            try {
                displayOrder = Integer.parseInt(request.getParameter("displayOrder"));
            } catch (Exception e) {
                isValid = false;
            }
            status = Integer.parseInt(request.getParameter("status"));
            
            title = request.getParameter("title");
            if(title.trim().isEmpty()) {
                isValid = false;
                request.setAttribute("msgTitle", Constant.MSG_EMPTY);
            }
            value = request.getParameter("value");
            if(value.trim().isEmpty()) {
                isValid = false;
                request.setAttribute("msgValue", Constant.MSG_EMPTY);
            }
            description = request.getParameter("description");
            if(description.trim().isEmpty()) {
                isValid = false;
                request.setAttribute("msgDescription", Constant.MSG_EMPTY);
            }
            SubjectSetting ss = new SubjectSetting(0, subjectId, typeId , title, value, displayOrder, status, description, title);
            
            if(isValid) {
                dao.add(ss);
                response.sendRedirect("subject-setting");
                return;
            }
            request.setAttribute("subjectSetting", ss);
                        
            request.getRequestDispatcher("/manager/subject_setting/subject-setting-add.jsp").forward(request, response);
            
            
        }
        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                subjectId = Integer.parseInt(request.getParameter("subjectId"));

            } catch (Exception e) {
                isValid = false;
            }
            typeId= Integer.parseInt(request.getParameter("type"));
            try {
                displayOrder = Integer.parseInt(request.getParameter("displayOrder"));
            } catch (Exception e) {
                isValid = false;
            }
            status = Integer.parseInt(request.getParameter("status"));
            
            title = request.getParameter("title");
            if(title.trim().isEmpty()) {
                isValid = false;
                request.setAttribute("msgTitle", Constant.MSG_EMPTY);
            }
            value = request.getParameter("value");
            if(value.trim().isEmpty()) {
                isValid = false;
                request.setAttribute("msgValue", Constant.MSG_EMPTY);
            }
            description = request.getParameter("description");
            if(description.trim().isEmpty()) {
                isValid = false;
                request.setAttribute("msgDescription", Constant.MSG_EMPTY);
            }
            SubjectSetting ss = new SubjectSetting(id, subjectId, typeId , title, value, displayOrder, status, description, title);
            
            if(isValid) {
                dao.update(ss);
                response.sendRedirect("subject-setting");
                return;
            }
            request.setAttribute("subjectSetting", ss);
                        
            request.getRequestDispatcher("/manager/subject_setting/subject-setting-edit.jsp").forward(request, response);
            
            
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
