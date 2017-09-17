/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet;

import controller.servlet.utils.Path;
import controller.servlet.utils.ThreadName;
import model.dao.AmenitiesRegionDaoImpl;
import model.dao.AreaDaoImpl;
import model.dao.ExperimentDaoImpl;
import model.dao.PoiDaoImpl;
import model.dao.StopPoiDaoImpl;
import model.dao.TrackDaoImpl;
import model.dao.UploadedFileDaoImpl;
import model.dao.UploadedPoiFileDaoImpl;
import model.dao.UsuarioDaoImpl;
import model.dao.WayDaoImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;

/**
 * Clase AllDataDelete, servlet que permite eliminar todos los datos.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class AllDataDelete extends HttpServlet {

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
        String page = "/pages/alldatadelete.jsp";
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
        
        String deleteType = request.getParameter("type");
        
        stopThreads();
        
        // Dependiendo de la petición se efecturará un borrado u otro.
        switch(deleteType){
            case "all":
                
                ExperimentDaoImpl eDao = new ExperimentDaoImpl();
                eDao.deleteAllExperiments();
                
                TrackDaoImpl tDao = new TrackDaoImpl();
                tDao.deleteTracks();
                
                AreaDaoImpl aDao = new AreaDaoImpl();
                aDao.deleteAllAreas();
                
                StopPoiDaoImpl sPDao = new StopPoiDaoImpl();
                sPDao.deleteAllStopPoi();
                
                UploadedFileDaoImpl uFDao = new UploadedFileDaoImpl();
                uFDao.deleteAll();
                
                UploadedPoiFileDaoImpl uPFDao = new UploadedPoiFileDaoImpl();
                uPFDao.deleteAll();
                
                UsuarioDaoImpl uDao = new UsuarioDaoImpl();
                uDao.deleteUsers();
                
                deleteServerFiles();
                
                break;
            case "pois":
                
                sPDao = new StopPoiDaoImpl();
                sPDao.deleteAllStopPoi();
                
                PoiDaoImpl pDao = new PoiDaoImpl();
                pDao.deleteAllPois();
                
                WayDaoImpl wDao = new WayDaoImpl();
                wDao.deleteAllWays();
                
                AmenitiesRegionDaoImpl rDao = new AmenitiesRegionDaoImpl();
                rDao.deleteAll();
                
                break;
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
        return "AllDataDelete servlet";
    }// </editor-fold>
    
    /**
     * Método deleteServerFiles, permite eliminar los ficheros del servidor.
     */
    private void deleteServerFiles(){
        
        // Búsqueda y borrado de los ficheros en las carpetas del servidor.
        String path = this.getServletContext().getRealPath("");
        path += File.separator+Path.UPLOADEDFILES_FOLDER;
        File directory = new File(path);
        if(directory.exists()){
            try {
                FileUtils.cleanDirectory(directory);
                try {
                    Files.delete(Paths.get(path));
                } catch (IOException ex) {
                    Logger.getLogger(AllDataDelete.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(AllDataDelete.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        path = this.getServletContext().getRealPath("");
        path += File.separator+Path.POIS_FOLDER;
        directory = new File(path);
        if(directory.exists()){
            try {
                FileUtils.cleanDirectory(directory);
                try {
                    Files.delete(Paths.get(path));
                } catch (IOException ex) {
                    Logger.getLogger(AllDataDelete.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(AllDataDelete.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Método stopThreads, permite parar los hilos en ejecución.
     */
    private void stopThreads(){
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
        for(Thread thread : threadArray){
            if(thread.getName().equals(ThreadName.TRACK_THREAD_NAME) ||
                    thread.getName().equals(ThreadName.AMENITIES_THREAD_NAME)){
                thread.interrupt();
                thread.stop();
            }
        }
    }

}
