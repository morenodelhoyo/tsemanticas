/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet;

import com.google.gson.Gson;
import controller.servlet.utils.ThreadName;
import model.dao.PoiProcessDaoImpl;
import model.dao.TrackProcessDaoImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Clase GetProcessState, servlet que permite obtener el estado de los procesos.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class GetProcessState extends HttpServlet {

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
        
        // Obtención del proceso que comprobar.
        String processType = request.getParameter("thread");

        int status = 0;
        
        // Proceso de análisis de rutas.
        if(processType.equals(ThreadName.TRACK_THREAD_NAME)){
            TrackProcessDaoImpl tPDao = new TrackProcessDaoImpl();
            status = tPDao.getTrackProcess();
        }
        
        // Proceso de PDIs
        if(processType.equals(ThreadName.AMENITIES_THREAD_NAME)){
            PoiProcessDaoImpl pPDao = new PoiProcessDaoImpl();
            status = pPDao.getPoiProcess();
        }
        
        // Respuesta.
        String json = new Gson().toJson(status);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "GetProcessState servlet";
    }// </editor-fold>

}
