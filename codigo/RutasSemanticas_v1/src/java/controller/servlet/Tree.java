/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet;

import model.dao.TrackDaoImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.data.Track;

/**
 * Clase Tree, servlet que permite mostrar llos detalles
 * de las rutas seleccionadas en la plataforma web.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class Tree extends HttpServlet {

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
        
        // Redirección
        String page = "/pages/tree.jsp";
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(page);
        requestDispatcher.forward(request, response);
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
        
        // Obtención de los Id de las rutas seleccionadas.
        Map<String, String[]> requestMap = request.getParameterMap();
        HttpSession session = request.getSession(true);
        
        String[] id = requestMap.get("chkboxtrack");
        
        // Obtención de las rutas.
        TrackDaoImpl tDao = new TrackDaoImpl();
        ArrayList<Track> aTracks = new ArrayList();
        for (String id1 : id) {
            aTracks.add(tDao.getTrack(Double.parseDouble(id1)));
        }
       
        // Asignación de las rutas.
        session.setAttribute("aTracks", aTracks);
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Tree servlet";
    }// </editor-fold>

}
