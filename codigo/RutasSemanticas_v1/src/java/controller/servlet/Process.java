/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet;

import model.dao.ReadFromFileImpl;
import controller.servlet.utils.Identifiers;
import controller.servlet.utils.Path;
import controller.servlet.utils.ThreadName;
import model.dao.ExperimentDaoImpl;
import model.dao.PositionDaoImpl;
import model.dao.TrackDaoImpl;
import model.dao.UploadedFileDaoImpl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.data.Experiment;
import model.data.UploadedFile;

/**
 * Clase Index, servlet que permite mostrar la página de gestión del sistema.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class Process extends HttpServlet implements ThreadCompleteListener {

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
        String page = "/pages/processing.jsp";
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
        
        /* Obtención de todos los datos necesarios para
         * procesar el algoritmo.
         * Ficheros a analizar, valores seleccionados por el usuario, etc.
         * Se creará una primera prueba (experimento) en la Base de Datos
         * con la información disponible en este momento. Por último,
         * se lanzará el hilo que procesa el algoritmo.
         */
        
        
        Map<String, Object> selectedOptions = new HashMap();
        String path = this.getServletContext().getRealPath("");
        path += File.separator + Path.UPLOADEDFILES_FOLDER + File.separator;
        
        int areaSelect = -1;
        String areaName;
        String areaDescription;
        int existingArea;
        double maxDistanceValue = 0;
        boolean findStops = false;
        double variationOfMedian = -1;
        double maxStopDistance = -1;
        boolean findPois = false;
        double poiRadius = -1;
        double poiRadiusMult = -1;
        boolean storeData = false;
        String[] selectedFiles = new String[]{""};
        
        Map<String, String[]> requestMap = request.getParameterMap();
        
        // Opciones de área
        if(requestMap.get("areaselect") != null){
            for(String s : requestMap.get("areaselect")){
                areaSelect = Integer.parseInt(s);
                selectedOptions.put("areaSelect", areaSelect);
            }
            if(areaSelect == 1){
                for(String s : requestMap.get("areaname")){
                    areaName = s;
                    selectedOptions.put("areaName", areaName);
                }
                for(String s : requestMap.get("areadescription")){
                    areaDescription = s;
                    selectedOptions.put("areaDescription", areaDescription);
                }
            }
            if(areaSelect == 2){
                for(String s : requestMap.get("existingareas")){
                    existingArea = Integer.parseInt(s);
                    selectedOptions.put("existingAreas", existingArea);
                }
            }
        }
        
        // Opciones de distancia de rutas.
        if(requestMap.get("maxdistancevalue") != null){
            for(String s : requestMap.get("maxdistancevalue")){
                maxDistanceValue = Double.parseDouble(s);
                selectedOptions.put("maxDistanceValue", maxDistanceValue);
            }
        }
        
        // Opciones de variación de mediana.
        if(requestMap.get("varmedian") != null){
            for(String s : requestMap.get("varmedian")){
                variationOfMedian = Double.parseDouble(s);
                selectedOptions.put("variationOfMedian", variationOfMedian);
            }
        }
        
        
        // Opciones de paradas.
        if(requestMap.get("findstops") != null){
            findStops = true;
            selectedOptions.put("findStops", findStops);
            for(String s : requestMap.get("maxstopdistance")){
                maxStopDistance = Double.parseDouble(s);
                selectedOptions.put("maxStopDistance", maxStopDistance);
            }
        }else{
            selectedOptions.put("findStops", findStops);
        }
        
        // Opciones de PDIs.
        if(requestMap.get("findpois") != null){
            findPois = true;
            selectedOptions.put("findPois", findPois);
            for(String s : requestMap.get("radius")){
                poiRadius = Double.parseDouble(s);
                selectedOptions.put("poiRadius", poiRadius);
            }
            for(String s : requestMap.get("radiusmultiplier")){
                poiRadiusMult = Double.parseDouble(s);
                selectedOptions.put("poiRadiusMult", poiRadiusMult);
            }
        }else{
            selectedOptions.put("findPois", findPois);
        }
        
        // Opciones de almacenado de datos.
        if(requestMap.get("storedata") != null){
            storeData = true;
            selectedOptions.put("storeData", storeData);
        }else{
            selectedOptions.put("storeData", storeData);
        }
        
        UploadedFileDaoImpl uFD = new UploadedFileDaoImpl();
        ArrayList<UploadedFile> aFiles = new ArrayList();
        UploadedFile uF;
        // Ficheros a procesar.
        if(requestMap.get("files") != null){
            selectedFiles = requestMap.get("files");
            for(String fileName : selectedFiles){
                uF = new UploadedFile();
                uF.setId(uFD.getFileId(fileName));
                uF.setName(fileName);
                aFiles.add(uF);
            }
        }
        
        // Obtención de identificadores.
        Identifiers id = new Identifiers(new TrackDaoImpl().getMaxTrackId(), new PositionDaoImpl().getMaxPositionId());
        
        // Almacenado de la prueba con sus valores.
        ExperimentDaoImpl expDao = new ExperimentDaoImpl();
        Experiment exp = new Experiment();
        exp.setAnalyzedFiles(aFiles);
        exp.setAsignatedAreaType(areaSelect);
        exp.setTrackDistance(maxDistanceValue);
        exp.setFindStops(findStops);
        exp.setStopDistance(maxStopDistance);
        exp.setMedianVariation(variationOfMedian);
        exp.setFindPois(findPois);
        exp.setMaxRadius(poiRadius);
        exp.setRadiusMultiplier(poiRadiusMult);
        exp.setStoreInDB(storeData);
        exp.setExperimentDate(new java.sql.Date(new java.util.Date().getTime()));
        expDao.createExperiment(exp);
        
        // Lanzamiento del hilo del experimento.
        TrackDaoImpl tDao = new TrackDaoImpl();
        tDao.updateOldTracks();

        NotifyingThread nT = new ReadFromFileImpl(path, id, selectedFiles, selectedOptions);
        nT.addListener(this);
        nT.setName(ThreadName.TRACK_THREAD_NAME);
        nT.start();

        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Algorithm processing servlet";
    }// </editor-fold>

    /**
     * Método notifyOfThreadComplete, notificación de hilo completado.
     * @param thread el hilo completado.
     */
    @Override
    public void notifyOfThreadComplete(Thread thread) {
        
        String name = thread.getName();
        if(name.equals(ThreadName.TRACK_THREAD_NAME)){
            Logger.getLogger(Process.class.getName()).log(Level.INFO, "Thread {0} completed", thread.getName());
        }
        
    }
    
}
