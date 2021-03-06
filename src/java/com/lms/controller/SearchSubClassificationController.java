/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lms.controller;

import com.lms.data.SubClassificationDao;
import com.lms.model.SubClassification;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet(name = "SearchSubClassificationController", urlPatterns = {"/SearchSubClassificationController"})
public class SearchSubClassificationController extends HttpServlet {

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
            out.println("<title>Servlet SearchSubClassificationController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchSubClassificationController at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);

        String firstFilterType = request.getParameter("firstFilterType");
        String firstFilterText = request.getParameter("firstFilterText");
        String secondFilterText = request.getParameter("secondFilterText");
        String btnViewAll = request.getParameter("btn");

        SubClassificationDao subClassificationDao = new SubClassificationDao();
        ArrayList<SubClassification> subClassificationList = new ArrayList<>();

        if ("ViewAllRecords".equals(btnViewAll)) {
            subClassificationList = subClassificationDao.getAllSubClassification();
        } else {
            if (!"".equals(secondFilterText)) {
                if (!"".equals(firstFilterText)) {
                    if (!"".equals(firstFilterType)) {
                        subClassificationList = subClassificationDao.searchSubClassification(firstFilterText, secondFilterText);
                    }
                    else{
                        subClassificationList = subClassificationDao.searchSubClassification(firstFilterText);
                    }
                } else if ("".equals(firstFilterText)) {
                    subClassificationList = subClassificationDao.searchSubClassification(secondFilterText);
                }
            } else if (("".equals(secondFilterText))) {
                if (!"".equals(firstFilterType)) {
                    subClassificationList = subClassificationDao.searchSubClassificationByMainClassificationName(firstFilterText);
                } else {
                    subClassificationList = subClassificationDao.searchSubClassification(firstFilterText);
                }
            }
        }

        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObject subClassificationJson = null;

        for (SubClassification subClassification : subClassificationList) {
            JsonObjectBuilder subClassificationBuilder = Json.createObjectBuilder();

            subClassificationJson = subClassificationBuilder
                    .add("SubClassificationId", subClassification.getSubClassificationId() != null ? subClassification.getSubClassificationId() : "")
                    .add("SubClassificationName", subClassification.getSubClassificationName() != null ? subClassification.getSubClassificationName() : "")
                    .add("MainClassificationId", subClassification.getMainClassificationId() != null ? subClassification.getMainClassificationId() : "")
                    .add("MainClassificationName", subClassification.getMainClassificationName() != null ? subClassification.getMainClassificationName() : "")
                    .build();
            arrayBuilder.add(subClassificationJson);
        }
        JsonObject root = rootBuilder.add("SubClassification", arrayBuilder).build();
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
