/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_function;

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
import entity_team.Team;

/**
 *
 * @author trung
 */
public class FunctionController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//   public static ArrayList readExcelFile() throws IOException, InvalidFormatException {
//
//        ArrayList cellArrayLisstHolder = new ArrayList();
//        try
//        {
//            FileInputStream file = new FileInputStream("exampleData.xlsx");
// 
//            //Create Workbook instance holding reference to .xlsx file
//            XSSFWorkbook workbook = new XSSFWorkbook(file);
// 
//            //Get first/desired sheet from the workbook
//            XSSFSheet sheet = workbook.getSheetAt(0);
// 
//            //Iterate through each rows one by one
//            Iterator<Row> rowIterator = sheet.iterator();
//            while (rowIterator.hasNext()) 
//            {
//                Row row = rowIterator.next();
//                //For each row, iterate through all the columns
//                Iterator<Cell> cellIterator = row.cellIterator();
//                 
//                ArrayList cellStoreArrayList = new ArrayList();
//                while (cellIterator.hasNext()) 
//                {
//                    Cell cell = cellIterator.next();
//                    //Check the cell type and format accordingly
//                   cellStoreArrayList.add(cell);
//                    CellType cellType = cell.getCellType();
//                    if (cellType == CellType.STRING) {
//                        System.out.print(cell.getNumericCellValue() + "t");
//                            break;
//                    } else if (cellType == CellType.NUMERIC) {
//                        System.out.print(cell.getStringCellValue() + "t");
//                            break;
//                    }
//                }
//            cellArrayLisstHolder.add(cellStoreArrayList);
//                System.out.println("");
//            }
//            file.close();
//        } 
//        catch (Exception e) 
//        {
//            e.printStackTrace();
//        }
//        return cellArrayLisstHolder;
//
////        ArrayList cellArrayLisstHolder = new ArrayList();
////        try {
////            FileInputStream inputStream = new FileInputStream(fileName);
////            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName));
////            HSSFWorkbook wb = new HSSFWorkbook(fs);
////            HSSFSheet sheet = wb.getSheetAt(0);
////
////            Iterator rows = sheet.rowIterator();
////            while (rows.hasNext()) {
////                HSSFRow row = (HSSFRow) rows.next();
////
////                Iterator cells = row.cellIterator();
////
////                ArrayList cellStoreArrayList = new ArrayList();
////                while (cells.hasNext()) {
////                    HSSFCell cell = (HSSFCell) cells.next();
////                    cellStoreArrayList.add(cell);
////                    CellType cellType = cell.getCellType();
////                    if (cellType == CellType.STRING) {
////                        HSSFRichTextString strData = cell.getRichStringCellValue();
////                        System.out.println("String data=" + strData.getString());
////                    } else if (cellType == CellType.NUMERIC) {
////                        double data = cell.getNumericCellValue();
////                        System.out.println("Numeric data=" + data);
////                    }
////                }
////                cellArrayLisstHolder.add(cellStoreArrayList);
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        return cellArrayLisstHolder;
//    };
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
//        processRequest(request, response
        String action = request.getParameter("action");
        if (action == null) {
            FunctionDao functionDao = new FunctionDao();
            try {
                List<Function> listFunction = functionDao.getFunctionsByTeamId(1);
                List<Team> listTeam = functionDao.getAllTeam();
                List<ClassSetting> listClassSetting = functionDao.getAllStatus();
                int page = (int) Math.ceil((double) listFunction.size() / 5);
                int startIndex = 0;
                int endIndex = 5;
                if (endIndex >= listFunction.size()) {
                    endIndex = listFunction.size();
                }
                request.setAttribute("listFunction", listFunction);
                request.setAttribute("listTeam", listTeam);
                request.setAttribute("listClassSetting", listClassSetting);
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("endIndex", endIndex);
                request.setAttribute("pageNumb", page);
            } catch (SQLException ex) {
                Logger.getLogger(FunctionController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FunctionController.class.getName()).log(Level.SEVERE, null, ex);
            }

            RequestDispatcher dispatcher //
                    = request.getServletContext().getRequestDispatcher("/student/function/list.jsp");
            dispatcher.forward(request, response);
        } else {
//                HttpSession session = request.getSession();
            FunctionDao functionDao = new FunctionDao();
            try {

                List<Team> listTeam = functionDao.getAllTeam();
                List<ClassSetting> listClassSetting = functionDao.getAllStatus();
                List<String> listFeature = functionDao.getAllFeature();
                Function function = null;
                String submitStatus = "Pending";
                switch (action) {
                    case "edit":
                        //L???y id c???a m???t feature t??? session g???i t??? FeatureListController
                        int functionId = Integer.parseInt(request.getParameter("functionId"));
                        function = functionDao.getFunctionById(functionId);
                        if (function.getDescription() == null) {
                            function.setDescription("");
                        }
                        if(function.getSubmitStatus() == 1){
                            submitStatus = "Pending";
                        } else if (function.getSubmitStatus() == 2) {
                            submitStatus = "Committed";
                        } else if (function.getSubmitStatus() == 3) {
                            submitStatus = "Submitted";
                        } else if (function.getSubmitStatus() == 4) {
                            submitStatus = "Rejected";
                        } else if (function.getSubmitStatus() == 5) {
                            submitStatus = "Evaluated";
                        }

                        break;
                    case "add":
                        //Add n??n kh??ng c???n l???y id m?? truy???n lu??n 1 feature m???i kh??ng c?? g?? c??? ????? tr??nh vi???c jsp b??o null feature      
                        function = new Function();
                        break;
                }
                request.setAttribute("listFeature", listFeature);
                request.setAttribute("listTeam", listTeam);
                request.setAttribute("listClassSetting", listClassSetting);
                request.setAttribute("submitStatus", submitStatus);
                request.setAttribute("function", function);
                request.setAttribute("action", action);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(FunctionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            RequestDispatcher dispatcher //
                    = request.getServletContext().getRequestDispatcher("/student/function/details.jsp");
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

        FunctionDao functionDao = new FunctionDao();
        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();

        String action = request.getParameter("action");
        RequestDispatcher dispatcher;
        switch (action) {
            case "search":
                try {
                //L???y d??? li???u t??? jsp
                String team = request.getParameter("searchTeam");
                String status = request.getParameter("searchStatus");
                String submitStatus = request.getParameter("searchSubmitStatus");
                String tittle = request.getParameter("searchTitle").trim();
                //Th???c hi???n vi???c t??m ki???m v?? tr??? v??? list ???? t??m ki???m theo status ho???c title
                List<Function> functions = functionDao.searchFunction(team, status, submitStatus, tittle);
                List<Team> listTeam = (ArrayList<Team>) session.getAttribute("listTeam");
                List<ClassSetting> listClassSetting = (ArrayList<ClassSetting>) session.getAttribute("listClassSetting");
                int page = (int) Math.ceil((double) functions.size() / 5);
                int startIndex = 0;
                int endIndex = 5;
                if (endIndex >= functions.size()) {
                    endIndex = functions.size();
                }
                request.setAttribute("listFunction", functions);
                request.setAttribute("listTeam", listTeam);
                request.setAttribute("listClassSetting", listClassSetting);
                request.setAttribute("searchTeam", team);
                request.setAttribute("searchTitle", tittle);
                request.setAttribute("searchStatus", status);
                request.setAttribute("searchSubmitStatus", submitStatus);
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("endIndex", endIndex);
                request.setAttribute("pageNumb", page);
//                    RequestDispatcher dispatcher //
                dispatcher = request.getServletContext().getRequestDispatcher("/student/function/list.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(FunctionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case "paging":
                //D??ng ????? th???c hi???n ph??n trang
                List<Function> listFunction = (ArrayList<Function>) session.getAttribute("listFunction");
                List<Team> listTeam = (ArrayList<Team>) session.getAttribute("listTeam");
                List<ClassSetting> listClassSetting = (ArrayList<ClassSetting>) session.getAttribute("listClassSetting");
                int pageNumb = Integer.parseInt(request.getParameter("pageNumb"));
                int pageChoose = Integer.parseInt(request.getParameter("pageChoose"));
                int startIndex = (pageChoose - 1) * 5;
                int endIndex = pageChoose * 5;
                if (endIndex >= listFunction.size()) {
                    endIndex = listFunction.size();
                }

                request.setAttribute("listFunction", listFunction);
                request.setAttribute("listTeam", listTeam);
                request.setAttribute("listClassSetting", listClassSetting);
                request.setAttribute("startIndex", startIndex);
                request.setAttribute("endIndex", endIndex);
                request.setAttribute("pageNumb", pageNumb);
//                RequestDispatcher dispatcher 
                dispatcher = request.getServletContext().getRequestDispatcher("/student/function/list.jsp");
                dispatcher.forward(request, response);
                break;
            case "edit":
                //L???y d??? li???u c???a Feature ???????c nh???p t??? jsp
                
                try {
                Function function = new Function();
                String submitStatus = request.getParameter("submitStatus");
                function.setFunctionId(Integer.parseInt(request.getParameter("functionId")));
                function.setTitle(request.getParameter("functionTitle"));
                function.setFeature(request.getParameter("functionFeature"));
                function.setPriority(Integer.parseInt(request.getParameter("priority")));
                function.setStatusId(Integer.parseInt(request.getParameter("status")));
                if (submitStatus.equals("Pending")) {
                    function.setSubmitStatus(1);
                } else if (submitStatus.equals("Committed")) {
                    function.setSubmitStatus(2);
                } else if (submitStatus.equals("Submitted")) {
                    function.setSubmitStatus(3);
                } else if (submitStatus.equals("Rejected")) {
                    function.setSubmitStatus(4);
                } else if (submitStatus.equals("Evaluated")) {
                    function.setSubmitStatus(5);
                };
                function.setDescription(request.getParameter("description"));

                int status;
                status = functionDao.updateFunction(function);
//                session.setAttribute("message", "message");
//                session.setMaxInactiveInterval(10);
                session.setAttribute("changeResult", status);
//                 request.setAttribute("functionId", function.getFunctionId());
//                 request.setAttribute("action", action);
//                RequestDispatcher dispatcher //
//                       dispatcher = request.getServletContext().getRequestDispatcher("/student/function/details.jsp");
//                dispatcher.forward(request, response);

                response.sendRedirect(contextPath + "/function");
            } catch (SQLException ex) {
                Logger.getLogger(FunctionController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FunctionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            ;
            break;
            case "add":
                try {
                //L???y d??? li???u c???a Feature ???????c nh???p t??? jsp
                Function function = new Function();
                function.setTeamId(1);
                function.setTitle(request.getParameter("functionTitle"));
                function.setFeature(request.getParameter("functionFeature"));
                function.setPriority(Integer.parseInt(request.getParameter("priority")));
                function.setIsClosed(false);
                function.setStatusId(Integer.parseInt(request.getParameter("status")));
                function.setSubmitStatus(1);
                function.setDescription(request.getParameter("description"));
                // Th???c hi???n th??m m???i 1 feature
                int status = functionDao.addFunction(function);
                session.setAttribute("changeResult", status);
                response.sendRedirect(contextPath + "/function");
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FunctionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case "change":
                try {
                int status = Integer.parseInt(request.getParameter("changeStatus"));
                int functionId = Integer.parseInt(request.getParameter("functionId"));
                int result = functionDao.changeStatusFunction(functionId, status);
                session.setAttribute("changeResult", result);
                response.sendRedirect(contextPath + "/function");
            } catch (SQLException ex) {
                Logger.getLogger(FunctionController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FunctionController.class.getName()).log(Level.SEVERE, null, ex);
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
