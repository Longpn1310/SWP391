/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_subject;

import entity_setting.Setting;
import entity_setting.SettingController;
import entity_setting.SettingDao;
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
public class SubjectController extends HttpServlet {

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
            SubjectDao subjectDao = new SubjectDao();
            try {
                //Lấy danh sách dữ liệu từ DB    
                List<Subject> subjects = subjectDao.getAllSubject();
                List<User> authors = subjectDao.getAllAuthor();
                //Tạo các biến dùng cho việc phân trang(Page là tổng số trang, startIndex và endIndex dùng để quyết định xem dữ liệu trong list bên trên sẽ được hiển thị từ số mấy đến số mấy)
                // Ở đây đang set để 1 trang sẽ hiển thị 5 bản ghi
                int page = (int) Math.ceil((double) subjects.size() / 5);
                int startIndex = 0;
                int endIndex = 5;
                if (endIndex >= subjects.size()) {
                    endIndex = subjects.size();
                }
                request.setAttribute("subjects", subjects);
                request.setAttribute("authors", authors);
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("endIndex", endIndex);
                request.setAttribute("pageNumb", page);
                request.setAttribute("pageNumb", page);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
            }
            RequestDispatcher dispatcher //
                    = request.getServletContext().getRequestDispatcher("/admin/subject/list.jsp");
            dispatcher.forward(request, response);
        } else {
            try {
                //Lấy action được truyền từ FeatureListController để xem update hay thêm mới bản ghi
                HttpSession session = request.getSession();
                SubjectDao subjectDao = new SubjectDao();
                Subject subject = null;
                List<User> authors = subjectDao.getAllAuthor();
                switch (action) {
                    case "edit":
                        //Lấy id của một feature từ session gửi từ FeatureListController
                        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
                        subject = subjectDao.getSubjectById(subjectId);
                        if (subject.getDescription() == null) {
                            subject.setDescription("");
                        }
                        break;
                    case "add":
                        //Add nên không cần lấy id mà truyền luôn 1 feature mới không có gì cả để tránh việc jsp báo null feature      
                        subject = new Subject();
                        subject.setStatus(true);
                        break;
                }
                request.setAttribute("subject", subject);
                request.setAttribute("authors", authors);
                request.setAttribute("action", action);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
            }
            RequestDispatcher dispatcher //
                    = request.getServletContext().getRequestDispatcher("/admin/subject/details.jsp");
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
        SubjectDao subjectDao = new SubjectDao();
        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();
        // Lấy action truyền về từ jsp
        String action = request.getParameter("action");
        int subjectId;
        switch (action) {
            case "add":
                try {
                //Lấy dữ liệu của Feature được nhập từ jsp
                Subject subject = new Subject();
                subject.setSubjectCode(request.getParameter("subjectCode"));
                subject.setSubjectName(request.getParameter("subjectName"));
                subject.setManagerId(Integer.parseInt(request.getParameter("authorId")));
                subject.setStatus(Boolean.parseBoolean(request.getParameter("status")));
                subject.setDescription(request.getParameter("description"));
                // Thực hiện thêm mới 1 feature
                int status = subjectDao.addSubject(subject);
                session.setAttribute("changeResult", status);
                response.sendRedirect(contextPath + "/subject");
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case "edit":
                try {
                //Lấy dữ liệu của Feature được nhập từ jsp
                Subject subject = new Subject();
                subject.setSubjectId(Integer.parseInt(request.getParameter("subjectId")));
                subject.setSubjectCode(request.getParameter("subjectCode"));
                subject.setSubjectName(request.getParameter("subjectName"));
                subject.setManagerId(Integer.parseInt(request.getParameter("authorId")));
                subject.setStatus(Boolean.parseBoolean(request.getParameter("status")));
                subject.setDescription(request.getParameter("description"));
                //Thực hiện update feature có dữ liệu như trên
                int status = subjectDao.updateSubject(subject);
                session.setAttribute("changeResult", status);
                response.sendRedirect(contextPath + "/subject");
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case "change":
                //Dùng để thay đổi status của 1 bản ghi
                boolean subjectStatus = Boolean.parseBoolean(request.getParameter("subjectStatus"));
                try {
                    subjectId = Integer.parseInt(request.getParameter("subjectId"));
                    int result = subjectDao.changeStatusSubject(subjectId, subjectStatus);
                    session.setAttribute("changeResult", result);
                    response.sendRedirect(contextPath + "/subject");
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "search":
                try {
                //Lấy dữ liệu từ jsp
                String status = request.getParameter("searchStatus");
                String authorId = request.getParameter("searchAuthor");
                String tittle = request.getParameter("searchTitle").trim();
                //Thực hiện việc tìm kiếm và trả về list đã tìm kiếm theo status hoặc title
                List<Subject> subjects = subjectDao.searchSubject(status, authorId, tittle);
                List<User> authors = subjectDao.getAllAuthor();
                int page = (int) Math.ceil((double) subjects.size() / 5);
                int startIndex = 0;
                int endIndex = 5;
                if (endIndex >= subjects.size()) {
                    endIndex = subjects.size();
                }
                request.setAttribute("authors", authors);
                request.setAttribute("subjects", subjects);
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("endIndex", endIndex);
                request.setAttribute("pageNumb", page);
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/admin/subject/list.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case "paging":
                //Dùng để thực hiện phân trang
                List<Subject> subjects = (ArrayList<Subject>) session.getAttribute("listofSubject");
                List<User> authors = (ArrayList<User>) session.getAttribute("authors");
                int pageNumb = Integer.parseInt(request.getParameter("pageNumb"));
                int pageChoose = Integer.parseInt(request.getParameter("pageChoose"));
                int startIndex = (pageChoose - 1) * 5;
                int endIndex = pageChoose * 5;
                if (endIndex >= subjects.size()) {
                    endIndex = subjects.size();
                }

                request.setAttribute("subjects", subjects);
                request.setAttribute("authors", authors);
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("endIndex", endIndex);
                request.setAttribute("pageNumb", pageNumb);
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/admin/subject/list.jsp");
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
