/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_class;

import entity_setting.Setting;
import entity_subject.Subject;
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
 * @author Administrator
 */
public class ClassController extends HttpServlet {

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
        String action = request.getParameter("action");
        ClassDao classDao = new ClassDao();
        String mssError = "";
        if (user.getRoleId() == 2 && isLogined) {
            if (action == null) {
                try {
                    //L???y danh s??ch d??? li???u t??? DB    
                    List<Class> classes = classDao.getAllClassOfManager(user.getUserId());
                    //T???o c??c bi???n d??ng cho vi???c ph??n trang(Page l?? t???ng s??? trang, startIndex v?? endIndex d??ng ????? quy???t ?????nh xem d??? li???u trong list b??n tr??n s??? ???????c hi???n th??? t??? s??? m???y ?????n s??? m???y)
                    // ??? ????y ??ang set ????? 1 trang s??? hi???n th??? 5 b???n ghi
                    int page = (int) Math.ceil((double) classes.size() / 5);
                    int startIndex = 0;
                    int endIndex = 5;
                    if (endIndex >= classes.size()) {
                        endIndex = classes.size();
                    }
                    session.setAttribute("classes", classes);
                    request.setAttribute("startIndex", startIndex);
                    request.setAttribute("endIndex", endIndex);
                    request.setAttribute("pageNumb", page);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(ClassController.class.getName()).log(Level.SEVERE, null, ex);
                }
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/manager/class/list.jsp");
                dispatcher.forward(request, response);
            } else {
                try {
                    //L???y action ???????c truy???n t??? FeatureListController ????? xem update hay th??m m???i b???n ghi                                        
                    Class classs = null;
                    List<User> trainers = classDao.getAllTrainer();
                    List<Subject> subjects = classDao.getAllSubject(user.getUserId());
                    List<Setting> terms = classDao.getAllTerm();
                    switch (action) {
                        case "edit":
                            //L???y id c???a m???t feature t??? session g???i t??? FeatureListController
                            int classId = Integer.parseInt(request.getParameter("classId"));
                            classs = classDao.getClassById(classId);
                            break;
                        case "add":
                            //Add n??n kh??ng c???n l???y id m?? truy???n lu??n 1 feature m???i kh??ng c?? g?? c??? ????? tr??nh vi???c jsp b??o null feature      
                            classs = new Class();
                            classs.setStatus(true);
                            break;
                    }
                    session.setAttribute("classs", classs);
                    session.setAttribute("trainers", trainers);
                    session.setAttribute("subjects", subjects);
                    session.setAttribute("terms", terms);
                    request.setAttribute("action", action);
                    request.setAttribute("mssError", mssError);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(ClassController.class.getName()).log(Level.SEVERE, null, ex);
                }
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/manager/class/details.jsp");
                dispatcher.forward(request, response);
            }
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
        ClassDao classDao = new ClassDao();
        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();
        String mssError = "";
        // L???y action truy???n v??? t??? jsp
        String action = request.getParameter("action");
        int subjectId;
        Class classs = new Class();
        if (action.equals("add") || action.equals("edit")) {
            // L???y parameter truy???n v???
            String rawClassId = request.getParameter("classId");
            String rawClassCode = request.getParameter("classCode");
            String rawTrainerId = request.getParameter("trainerId");
            String rawSubjectId = request.getParameter("subjectId");
            String rawTermId = request.getParameter("termId");
            //Add v??o class
            if (rawClassId != null && !rawClassId.equals("0")) {
                classs.setClassId(Integer.parseInt(rawClassId));
            }
            if (rawClassCode != null && !rawClassCode.equals("")) {
                classs.setClassCode(rawClassCode);
            } else {
                mssError = "Please enter class code";
            }
            if (rawTrainerId != null && !rawTrainerId.equals("0")) {
                classs.setTrainerId(Integer.parseInt(rawTrainerId));
            }
            if (rawSubjectId != null && !rawSubjectId.equals("0")) {
                classs.setSubjectId(Integer.parseInt(rawSubjectId));
            }
            if (rawTermId != null && !rawTermId.equals("0")) {
                classs.setTermId(Integer.parseInt(rawTermId));
            }
            classs.setBlock5(Boolean.parseBoolean(request.getParameter("isBlock5")));
            classs.setStatus(Boolean.parseBoolean(request.getParameter("status")));
        }

        if (!mssError.equals("")) {
            session.setAttribute("classs", classs);
            request.setAttribute("mssError", mssError);
            request.setAttribute("action", action);
            RequestDispatcher dispatcher //
                    = request.getServletContext().getRequestDispatcher("/manager/class/details.jsp");
            dispatcher.forward(request, response);
        } else {
            switch (action) {
                case "add":
                try {
                    int status = classDao.addClass(classs);
                    if (status == 3) {
                        request.setAttribute("action", action);
                        session.setAttribute("changeResult", status);
                        request.setAttribute("mssError", mssError);
                        RequestDispatcher dispatcher //
                                = request.getServletContext().getRequestDispatcher("/manager/class/details.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        session.setAttribute("changeResult", status);
                        response.sendRedirect(contextPath + "/class");
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(ClassController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
                case "edit":
                try {
                    int status = classDao.updateClass(classs);
                    if (status == 3) {
                        request.setAttribute("action", action);
                        session.setAttribute("changeResult", status);
                        request.setAttribute("mssError", mssError);
                        RequestDispatcher dispatcher //
                                = request.getServletContext().getRequestDispatcher("/manager/class/details.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        session.setAttribute("changeResult", status);
                        response.sendRedirect(contextPath + "/class");
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(ClassController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
                case "change":
                    //D??ng ????? thay ?????i status c???a 1 b???n ghi
                    boolean classStatus = Boolean.parseBoolean(request.getParameter("classStatus"));
                    try {
                        subjectId = Integer.parseInt(request.getParameter("classId"));
                        int result = classDao.changeStatusClass(subjectId, classStatus);
                        session.setAttribute("changeResult", result);
                        response.sendRedirect(contextPath + "/class");
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(ClassController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "search":
//                try {
//                //L???y d??? li???u t??? jsp
//                String status = request.getParameter("searchStatus");
//                String authorId = request.getParameter("searchAuthor");
//                String tittle = request.getParameter("searchTitle").trim();
//                //Th???c hi???n vi???c t??m ki???m v?? tr??? v??? list ???? t??m ki???m theo status ho???c title
//                List<Class> subjects = classDao.searchClass(status, authorId, tittle);
//                List<User> authors = classDao.getAllTrainer();
//                int page = (int) Math.ceil((double) subjects.size() / 5);
//                int startIndex = 0;
//                int endIndex = 5;
//                if (endIndex >= subjects.size()) {
//                    endIndex = subjects.size();
//                }
//                request.setAttribute("authors", authors);
//                request.setAttribute("subjects", subjects);
//                request.setAttribute("startIndex", startIndex);
//                request.setAttribute("endIndex", endIndex);
//                request.setAttribute("pageNumb", page);
//                RequestDispatcher dispatcher //
//                        = request.getServletContext().getRequestDispatcher("/admin/subject/list.jsp");
//                dispatcher.forward(request, response);
//            } catch (SQLException | ClassNotFoundException ex) {
//                Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
//            }
                    break;
                case "paging":
                    //D??ng ????? th???c hi???n ph??n trang
                    List<Class> classes = (ArrayList<Class>) session.getAttribute("classes");
//                List<User> authors = (ArrayList<User>) session.getAttribute("authors");
                    int pageNumb = Integer.parseInt(request.getParameter("pageNumb"));
                    int pageChoose = Integer.parseInt(request.getParameter("pageChoose"));
                    int startIndex = (pageChoose - 1) * 5;
                    int endIndex = pageChoose * 5;
                    if (endIndex >= classes.size()) {
                        endIndex = classes.size();
                    }

                    session.setAttribute("classes", classes);
//                request.setAttribute("authors", authors);
                    request.setAttribute("startIndex", startIndex);
                    request.setAttribute("endIndex", endIndex);
                    request.setAttribute("pageNumb", pageNumb);
                    RequestDispatcher dispatcher //
                            = request.getServletContext().getRequestDispatcher("/manager/class/list.jsp");
                    dispatcher.forward(request, response);
                    break;
            }
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
