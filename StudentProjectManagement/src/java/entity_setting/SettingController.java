/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_setting;

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
public class SettingController extends HttpServlet {

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
        String action = request.getParameter("action");
        if (action == null) {
            try {
                SettingDao settingDao = new SettingDao();
                //Lấy danh sách dữ liệu từ DB    
                List<Setting> settings = settingDao.getAllSetting();
                for (Setting setting : settings) {
                    if (setting.getValue() == null) {
                        setting.setValue("");
                    }
                }
                List<Setting> settingTypes = settingDao.getAllSettingType();
                //Tạo các biến dùng cho việc phân trang(Page là tổng số trang, startIndex và endIndex dùng để quyết định xem dữ liệu trong list bên trên sẽ được hiển thị từ số mấy đến số mấy)
                // Ở đây đang set để 1 trang sẽ hiển thị 5 bản ghi
                int page = (int) Math.ceil((double) settings.size() / 5);
                int startIndex = 0;
                int endIndex = 5;
                if (endIndex >= settings.size()) {
                    endIndex = settings.size();
                }
                request.setAttribute("settings", settings);
                request.setAttribute("settingTypes", settingTypes);
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("endIndex", endIndex);
                request.setAttribute("pageNumb", page);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SettingController.class.getName()).log(Level.SEVERE, null, ex);
            }
            RequestDispatcher dispatcher //
                    = request.getServletContext().getRequestDispatcher("/admin/setting/list.jsp");
            dispatcher.forward(request, response);
        } else {
            try {
                //Lấy action được truyền từ FeatureListController để xem update hay thêm mới bản ghi                                
                SettingDao settingDao = new SettingDao();
                Setting setting = null;
                List<Setting> settingTypes = settingDao.getAllSettingType();
                switch (action) {
                    case "edit":
                        //Lấy id của một feature từ session gửi từ FeatureListController
                        int settingId = Integer.parseInt(request.getParameter("settingId"));
                        setting = settingDao.getSettingById(settingId);
                        //Lấy dữ liệu của Feature với id ở trên        
                        if (setting.getDescription() == null) {
                            setting.setDescription("");
                        }
                        if (setting.getValue() == null) {
                            setting.setValue("");
                        }
                        request.setAttribute("setting", setting);
                        request.setAttribute("settingTypes", settingTypes);
                        break;
                    case "add":
                        //Add nên không cần lấy id mà truyền luôn 1 feature mới không có gì cả để tránh việc jsp báo null feature      
                        setting = new Setting();
                        setting.setStatus(true);
                        request.setAttribute("setting", setting);
                        request.setAttribute("settingTypes", settingTypes);
                        break;
                }
                request.setAttribute("action", action);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SettingController.class.getName()).log(Level.SEVERE, null, ex);
            }
            RequestDispatcher dispatcher //
                    = request.getServletContext().getRequestDispatcher("/admin/setting/details.jsp");
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
        SettingDao settingDao = new SettingDao();
        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();
        // Lấy action truyền về từ jsp
        String action = request.getParameter("action");
        //Lấy dữ liệu của Feature được nhập từ jsp        
        int settingId;
        switch (action) {
            case "add":
                try {
                Setting setting = new Setting();                
                setting.setTypeId(Integer.parseInt(request.getParameter("settingType")));
                setting.setTitle(request.getParameter("title"));
                setting.setValue(request.getParameter("value"));
                setting.setDescription(request.getParameter("description"));
                setting.setOrder(Integer.parseInt(request.getParameter("orderNumb")));
                setting.setStatus(Boolean.parseBoolean(request.getParameter("status")));
                // Thực hiện thêm mới 1 feature
                int status = settingDao.addSetting(setting);
                session.setAttribute("changeResult", status);
                response.sendRedirect(contextPath + "/setting");
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SettingController.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case "edit":
                try {
                Setting setting = new Setting();
                setting.setId(Integer.parseInt(request.getParameter("settingId")));
                setting.setTypeId(Integer.parseInt(request.getParameter("settingType")));
                setting.setTitle(request.getParameter("title"));
                setting.setValue(request.getParameter("value"));
                setting.setDescription(request.getParameter("description"));
                setting.setOrder(Integer.parseInt(request.getParameter("orderNumb")));
                setting.setStatus(Boolean.parseBoolean(request.getParameter("status")));
                //Thực hiện update feature có dữ liệu như trên
                int status = settingDao.updateSetting(setting);
                session.setAttribute("changeResult", status);
                response.sendRedirect(contextPath + "/setting");
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SettingController.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case "change":
                //Dùng để thay đổi status của 1 bản ghi
                boolean settingStatus = Boolean.parseBoolean(request.getParameter("settingStatus"));
                try {
                    settingId = Integer.parseInt(request.getParameter("settingId"));
                    int result = settingDao.changeStatusSetting(settingId, settingStatus);
                    session.setAttribute("changeResult", result);
                    response.sendRedirect(contextPath + "/setting");
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(SettingController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "search":
                try {
                //Lấy dữ liệu từ jsp
                String status = request.getParameter("searchStatus");
                String settingType = request.getParameter("searchSettingType");
                String tittle = request.getParameter("searchTitle").trim();
                //Thực hiện việc tìm kiếm và trả về list đã tìm kiếm theo status hoặc title
                List<Setting> settings = settingDao.searchSetting(tittle, status, settingType);
                List<Setting> settingTypes = settingDao.getAllSettingType();
                int page = (int) Math.ceil((double) settings.size() / 5);
                int startIndex = 0;
                int endIndex = 5;
                if (endIndex >= settings.size()) {
                    endIndex = settings.size();
                }
                request.setAttribute("settingTypes", settingTypes);
                request.setAttribute("settings", settings);
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("endIndex", endIndex);
                request.setAttribute("pageNumb", page);
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/admin/setting/list.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SettingController.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case "paging":
                //Dùng để thực hiện phân trang
                List<Setting> settings = (ArrayList<Setting>) session.getAttribute("listofSettings");
                List<Setting> settingTypes = (ArrayList<Setting>) session.getAttribute("settingTypes");
                int pageNumb = Integer.parseInt(request.getParameter("pageNumb"));
                int pageChoose = Integer.parseInt(request.getParameter("pageChoose"));
                int startIndex = (pageChoose - 1) * 5;
                int endIndex = pageChoose * 5;
                if (endIndex >= settings.size()) {
                    endIndex = settings.size();
                }

                request.setAttribute("settings", settings);
                request.setAttribute("settingTypes", settingTypes);
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("endIndex", endIndex);
                request.setAttribute("pageNumb", pageNumb);
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/admin/setting/list.jsp");
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
