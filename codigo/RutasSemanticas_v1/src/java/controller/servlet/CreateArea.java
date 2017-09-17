/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet;

import model.dao.AreaDaoImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.data.Area;

/**
 * Clase CreateArea, servlet que permite crear un nuevo área.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class CreateArea extends HttpServlet {

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
        
        // Obtención de los valores.
        Area area = new Area();
        area.setName(request.getParameter("areaname"));
        area.setDescription(request.getParameter("areadescription"));
        area.setMaxLat(Double.parseDouble(request.getParameter("latmax")));
        area.setMinLat(Double.parseDouble(request.getParameter("latmin")));
        area.setMaxLong(Double.parseDouble(request.getParameter("longmax")));
        area.setMinLong(Double.parseDouble(request.getParameter("longmin")));
        area.setActiva(true);
        area.setFechaCreacion(new java.sql.Date(new java.util.Date().getTime()));
        
        // Creación del área.
        AreaDaoImpl aDao = new AreaDaoImpl();
        aDao.createArea(area);
        
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "CreateArea servlet";
    }// </editor-fold>

}
