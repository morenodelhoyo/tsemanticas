/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet;

import model.dao.AreaDaoImpl;
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
 * Clase Results, servlet que permite mostrar la página de resultados de la 
 * plataforma web.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class Results extends HttpServlet {

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
        String page = "/pages/results.jsp";
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
        
        // Asignación de valores iniciales.
        HttpSession session = request.getSession(true);
        TrackDaoImpl tDao = new TrackDaoImpl();
        AreaDaoImpl aDao = new AreaDaoImpl();
        
        session.setAttribute("maxlat", 0.0);
        session.setAttribute("minlat", 0.0);
        session.setAttribute("maxlong", 0.0);
        session.setAttribute("minlong", 0.0);
        
        session.setAttribute("aTracks", tDao.getPartialTracksOrderedByDate(10));
        session.setAttribute("aAreas", aDao.getAreasWithTracks());
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
    
        // Asignación de valores iniciales.
        HttpSession session = request.getSession(true);
        Map<String, String[]> requestMap = request.getParameterMap();
        TrackDaoImpl tDao = new TrackDaoImpl();
        AreaDaoImpl aDao = new AreaDaoImpl();
        
        session.setAttribute("maxlat", 0.0);
        session.setAttribute("minlat", 0.0);
        session.setAttribute("maxlong", 0.0);
        session.setAttribute("minlong", 0.0);
        
        // Obtención de las rutas pertenecientes a las áreas seleccionadas.
        if(requestMap.get("chkbox") != null){
            ArrayList<Track> aTracks = new ArrayList();
            String[] chkboxes = requestMap.get("chkbox");
            for(String s : chkboxes){
                session.setAttribute("aAreas", aDao.getAreasWithTracks());
                aTracks.addAll(tDao.getPartialTracksByArea(Double.parseDouble(s)));
            }
            
            // Asignación de valores.
            session.setAttribute("aTracks", aTracks);
        }
        
        // Obtención de las áreas según los datos de coordenadas introducidos.
        if(requestMap.get("maxlat") != null &&
           requestMap.get("minlat") != null &&
           requestMap.get("maxlong") != null &&
           requestMap.get("minlong") != null){
            
            double maxLat = 0.0, minLat = 0.0, maxLong = 0.0, minLong = 0.0;
            for(String s : requestMap.get("maxlat")){
                maxLat = Double.parseDouble(s);
            }
            for(String s : requestMap.get("minlat")){
                minLat = Double.parseDouble(s);
            }
            for(String s : requestMap.get("maxlong")){
                maxLong = Double.parseDouble(s);
            }
            for(String s : requestMap.get("minlong")){
                minLong = Double.parseDouble(s);
            }
            
            // Asignaciónde valores.
            session.setAttribute("maxlat", maxLat);
            session.setAttribute("minlat", minLat);
            session.setAttribute("maxlong", maxLong);
            session.setAttribute("minlong", minLong);
            
            session.setAttribute("aAreas", aDao.getAreasWithTracks());
            ArrayList<Track> aTracks = new ArrayList();
            aTracks.addAll(tDao.getPartialTracksInsideArea(minLat, minLong, maxLat, maxLong));
            session.setAttribute("aTracks", aTracks);
        }
        
        
        processRequest(request, response);
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Results servlet";
    }// </editor-fold>

}
