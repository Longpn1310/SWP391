/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package class_eval;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HaiLong
 */
@WebServlet(name = "ClassEvalController", urlPatterns = {"/class-eval"})
public class ClassEvalController extends HttpServlet {

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
        ArrayList<ClassEval> list = new ClassEvalDao().getAll();

        HashMap<String, ClassEval> map = new HashMap<>();
        for (ClassEval ce : list) {
            ClassEval e = map.get(ce.getRole());
            if (e == null) {
                e = ce;
                
            }
            switch (ce.getMilestoneId()) {
                    case 1:
                        e.iter1 = ce.getTotal();
                        break;
                    case 2:
                        e.iter2 = ce.getTotal();
                        break;
                    case 3:
                        e.iter3 = ce.getTotal();
                        break;
                    case 4:
                        e.iter4 = ce.getTotal();
                        break;
                    case 5:
                        e.iter5 = ce.getTotal();
                        break;
                }
            map.put(ce.getRole(), e);
        }
        list = new ArrayList<>();
        for(String key: map.keySet()) {
            list.add(map.get(key));
        }
        request.setAttribute("list", list);
        request.getRequestDispatcher("./trainer/class_eval/class_eval.jsp").forward(request, response);
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        processRequest(request, response);
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
