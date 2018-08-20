/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lms.controller;

import com.lms.data.BookClassificationDao;
import com.lms.model.BookClassification;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Natsu
 */
@WebServlet(name = "GetBookClassificationBySubClassificationId", urlPatterns = {"/GetBookClassificationBySubClassificationId"})
public class GetBookClassificationBySubClassificationId extends HttpServlet {

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
            out.println("<title>Servlet GetBookClassificationBySubClassificationId</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetBookClassificationBySubClassificationId at " + request.getContextPath() + "</h1>");
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        BookClassificationDao bookClassificationDao = new BookClassificationDao();
        String subClassificationId = request.getParameter("subClassificationId");
        List<BookClassification> bookClassificationList = bookClassificationDao.checkSubClassificationIdExisit(subClassificationId);
        
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        
        JsonObjectBuilder rootbuilder = Json.createObjectBuilder();
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObject mainClassificationJson = null;
        
        for(BookClassification bookClassification : bookClassificationList){
            JsonObjectBuilder mainClassificationBuilder = Json.createObjectBuilder();
            
            mainClassificationJson = mainClassificationBuilder
                    .add("subClassificationId", bookClassification.getSubClassificationId())
                    .add("bookId", bookClassification.getBookId())
                    .build();
            arrayBuilder.add(mainClassificationJson);
        }
        
        JsonObject root = rootbuilder.add("BookClassification", arrayBuilder).build();
        
        writer.print(root);
        
        writer.flush();
        writer.close();
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
