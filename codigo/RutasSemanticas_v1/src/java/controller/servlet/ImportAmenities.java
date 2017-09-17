/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet;

import controller.algorithm.AmenityProcessing;
import controller.servlet.utils.Path;
import controller.servlet.utils.ThreadName;
import model.dao.UploadedPoiFileDaoImpl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.data.UploadedFile;
import org.apache.commons.io.FilenameUtils;

/**
 * Clase ImportAmenities, servlet que permite importar PDIs al sistema.
 * Permite la subida de un fichero y posterior procesado del mismo.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
@MultipartConfig(fileSizeThreshold=1024*1024, maxFileSize=1024*1024*1024, maxRequestSize=1073782441*2)

public class ImportAmenities extends HttpServlet implements ThreadCompleteListener {

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
        String page = "/pages/importingamenities.jsp";
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
        
        // Obtención de la región
        String regionName = request.getParameter("regionname");      
        
        boolean isXML = false;
        // Procesado del fichero para subirlo al servidor.
        for (Part part : request.getParts()) {
            if (part.getName().equals("file")){
                try (InputStream is = request.getPart(part.getName()).getInputStream()) {
                    int i = is.available();
                    byte[] b = new byte[i];
                    
                    if(b.length == 0){
                        break;
                    }
                    
                    is.read(b);
                    String fileName = obtenerNombreFichero(part);
                    String extension = FilenameUtils.getExtension(fileName);
                    String path = this.getServletContext().getRealPath("");
                    String ruta = path + File.separator + Path.POIS_FOLDER + File.separator + fileName;
                    File directory = new File(path+File.separator+Path.POIS_FOLDER);
                    fileName = FilenameUtils.getBaseName(fileName);
                    
                    if(!directory.exists()){
                        directory.mkdirs();
                    }
                    
                    try (FileOutputStream os = new FileOutputStream(ruta)) {
                        os.write(b);
                    }
                    
                    // Comprobación de que sea un fichero xml.
                    if(extension.equals("xml")){
                        isXML = true;
                    }else{
                        break;
                    }
                    
                    if(isXML){
                        // Crear entrada en la tabla de ficheros de PDIs.
                        UploadedPoiFileDaoImpl uPFDao = new UploadedPoiFileDaoImpl();
                        UploadedFile uFile = new UploadedFile();
                        uFile.setName(fileName);
                        uFile.setProcessedDate(new java.sql.Date(new java.util.Date().getTime()));
                        uPFDao.createFile(uFile);

                        // Almacenado de los datos en la Base de Datos.
                        NotifyingThread nT = new AmenityProcessing(ruta, regionName);
                        nT.addListener(this);
                        nT.setName(ThreadName.AMENITIES_THREAD_NAME);
                        nT.start();
                    } 
                }
            }
        }
        
        HttpSession session = request.getSession(true);
        session.setAttribute("isXML", isXML);
        
        if(!isXML){
            session.setAttribute("error", true);
            String page = "/pages/amenitiesmanage.jsp";
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(page);
            requestDispatcher.forward(request, response);
        } else {
            session.setAttribute("error", false);
            processRequest(request, response);
        }
        
        
    }
    
    /**
    * Obtenemos el nombre del fichero que se almacena en la parte que nos pasan por parametro
    * 
    * @param part Parte del formulario
    * @return string Nombre del fichero o nullo si no se encuentra el nombre
    */
    private String obtenerNombreFichero(Part part) {
        String cabecera = part.getHeader("content-disposition");
        for (String cd : cabecera.split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "ImportAmenities servlet";
    }// </editor-fold>

    /**
     * Notificación de fin del hilo lanzado.
     * @param thread el hilo que termina.
     */
    @Override
    public void notifyOfThreadComplete(Thread thread) {
        Logger.getLogger(ImportAmenities.class.getName()).log(Level.INFO, "{0} ended successfully", thread.getName());
        
    }

}
