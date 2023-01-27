/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_class_setting;

import entity_setting.Setting;
import entity_user.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HaiLong
 */
public class ClassSettingController extends HttpServlet {

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
            out.println("<title>Servlet ClassSettingController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ClassSettingController at " + request.getContextPath() + "</h1>");
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
    private ClassSettingDao dao = new ClassSettingDao();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("settings",  dao.getAllSetting());
        } catch (SQLException ex) {
            Logger.getLogger(ClassSettingController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassSettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String action = request.getParameter("action");
        try {
            int trainerId=3;
            HttpSession session = request.getSession();
            User u = (User) session.getAttribute("user");
            if(u != null) trainerId = u.getUserId();
            ArrayList<entity_class.Class> list = dao.getAllClass(trainerId);
            request.setAttribute("classes", list);
        } catch (Exception ex) {
            Logger.getLogger(ClassSettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if("add".equals(action)) {
            request.getRequestDispatcher("/trainer/class-setting/class-setting-add.jsp").forward(request, response);
            return;
        }
        if("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("cs", dao.getById(id));
            request.getRequestDispatcher("/trainer/class-setting/class-setting-edit.jsp").forward(request, response);
            return;
        }
        if("changestatus".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            ClassSetting cs =  dao.getById(id);
            cs.setStatus(!cs.isStatus());
            dao.update(cs);
            response.sendRedirect("class-setting");
        }
        String settingTitle = request.getParameter("settingTitle");
        if("".equals(settingTitle)) settingTitle = null;
        Integer typeId=null;
        try {
            typeId = Integer.parseInt(request.getParameter("typeId"));
        
        } catch (Exception e) {
        }
        request.setAttribute("typeId", typeId);
        String settingValue = request.getParameter("settingValue");
        if(settingValue == null) settingValue = "";
        Boolean status = null;
        try {
            if(request.getParameter("status") != null && !request.getParameter("status").equals(""))
            status = Boolean.valueOf(request.getParameter("status"));
        } catch (Exception e) {
        }
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
        }
        String uri = request.getRequestURI()+ request.getQueryString();
        uri.replace("page="+page,"");
        request.setAttribute("settingTitle", settingTitle);
        request.setAttribute("settingValue", settingValue);
        request.setAttribute("status", status);
        request.setAttribute("totalPage", (dao.count(settingTitle, settingValue, status)-1)/5+1);
        request.setAttribute("classSettings", dao.search(typeId, settingValue, status, page, "display_order"));
        request.getRequestDispatcher("/trainer/class-setting/class-setting-list.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    String getTitle(int typeId) {
        try {
            for(Setting c : dao.getAllSetting()) {
                if(c.getId() == typeId) {
                    return c.getTitle();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassSettingController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassSettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";

    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int trainerId=3;
            HttpSession session = request.getSession();
            User u = (User) session.getAttribute("user");
            if(u != null) trainerId = u.getUserId();
            ArrayList<entity_class.Class> list = dao.getAllClass(trainerId);
            request.setAttribute("classes", list);
        } catch (Exception ex) {
            Logger.getLogger(ClassSettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String action = request.getParameter("action");
        if("add".equals(action)) {
            String settingValue = request.getParameter("settingValue");
            int displayOrder = Integer.parseInt(request.getParameter("displayOrder"));
            int classId = Integer.parseInt(request.getParameter("classId"));
            int typeId = Integer.parseInt(request.getParameter("typeId"));
            Boolean status = Boolean.valueOf(request.getParameter("status"));
            String description = request.getParameter("description");
            ClassSetting cs = ClassSetting.builder()
                    .displayOrder(displayOrder)
                    .settingValue(settingValue)
                    .classId(classId)
                    .typeId(typeId)
                    .status(status)
                    .settingTitle(getTitle(typeId))
                    .description(description)
                    .build();
            dao.add(cs);
            response.sendRedirect("class-setting");
        }
        if("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String settingValue = request.getParameter("settingValue");
            int displayOrder = Integer.parseInt(request.getParameter("displayOrder"));
            int classId = Integer.parseInt(request.getParameter("classId"));
            int typeId = Integer.parseInt(request.getParameter("typeId"));
            Boolean status = Boolean.valueOf(request.getParameter("status"));
            String description = request.getParameter("description");
            ClassSetting cs = ClassSetting.builder()
                    .displayOrder(displayOrder)
                    .settingValue(settingValue)
                    .classId(classId)
                    .typeId(typeId)
                    .status(status)
                    .settingTitle(getTitle(typeId))
                    .description(description)
                    .settingId(id)
                    .build();
            dao.update(cs);
            response.sendRedirect("class-setting");
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
