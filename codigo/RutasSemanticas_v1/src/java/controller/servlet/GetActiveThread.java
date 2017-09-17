/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Clase GetActiveThreads, servlet que permite obtener los hilos activos.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class GetActiveThread extends HttpServlet {

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
        
        boolean cancel = Boolean.parseBoolean(request.getParameter("cancelThread"));
        String t = request.getParameter("threads");
        String[] threads = t.split(",");
        Boolean[] threadsActive = new Boolean[threads.length];
        Arrays.fill(threadsActive, Boolean.FALSE);        
        
        // Petición de los hilos activos de Java.
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
        boolean isInterrupted = false;
        
        // Búsqueda de los hilos lanzados por la plataforma web.
        for(int i=0; i<threads.length; i++){
            if(!cancel){
                for(Thread thread : threadArray){
                    if(thread.getName().equals(threads[i])){
                        threadsActive[i] = thread.isAlive();
                    }
                }
            }else{
                // Parada del hilo en caso de solicitud del usuario.
                for(Thread thread : threadArray){
                    if(thread.getName().equals(threads[i])){
                        if(thread.isAlive()){
                            thread.interrupt();
                            thread.stop();
                            isInterrupted = true;
                        }
                    }
                }
            }
        }
        
        
        if(!isInterrupted){
            String json = new Gson().toJson(threadsActive);
            response.setContentType("application/json");
            response.getWriter().write(json);
        }else{
            String json = new Gson().toJson("interrupted");
            response.setContentType("application/json");
            response.getWriter().write(json);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "GetActiveThreads servlet";
    }// </editor-fold>

}
