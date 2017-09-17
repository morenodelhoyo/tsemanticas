/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet;

import controller.servlet.utils.Path;
import model.dao.UploadedFileDaoImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
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
 * Clase Upload, servlet que permite subir y obtener los ficheros de datos
 * para ser analizados.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
@MultipartConfig(fileSizeThreshold=1024*1024, maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class Upload extends HttpServlet {
    
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
        
        // Obtención de la ruta a la carpeta de ficheros.
        String path = this.getServletContext().getRealPath("");
        path += File.separator+Path.UPLOADEDFILES_FOLDER;
        File directory = new File(path);
        if(!directory.exists()){
            directory.mkdirs();   
        }
        HttpSession session = request.getSession(true);
        
        UploadedFileDaoImpl uFD = new UploadedFileDaoImpl();
        
        // Asiganción de valores.
        session.setAttribute("mUF", uFD.getFiles());
        session.setAttribute("serverFolderFiles", directory.listFiles());
        
        // Redirección.
        String page = "/pages/upload.jsp";
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
        
        
        // Obtención de los ficheros existentes.
        UploadedFileDaoImpl uFD = new UploadedFileDaoImpl();        
        
        HttpSession session = request.getSession(true);
        session.setAttribute("numberOfCsvFiles", 0);
        session.setAttribute("numberOfNotCsvFiles", 0);
        session.setAttribute("error", false);
        session.setAttribute("mUF", uFD.getFiles());
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
        
        // Subida de los ficheros al sistema.
        
        UploadedFile uF;
        UploadedFileDaoImpl uFD = new UploadedFileDaoImpl();
        
        int numberOfCsvFiles = 0;
        int numberOfNotCsvFiles = 0;
        
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
                    String ruta = path + File.separator + Path.UPLOADEDFILES_FOLDER + File.separator + fileName;
                    File directorio = new File(path+File.separator + Path.UPLOADEDFILES_FOLDER);
                    if(!directorio.exists()){
                        directorio.mkdirs();
                    }
                    
                    // Se sube el fichero en caso de ser zip o csv.
                    if(extension.equals(Path.CSV_EXTENSION) || extension.equals(Path.ZIP_EXTENSION)){
                        try (FileOutputStream os = new FileOutputStream(ruta)) {
                            os.write(b);
                        }
                        if(extension.equals(Path.CSV_EXTENSION)){
                            numberOfCsvFiles ++;
                        }
                    }else{
                        numberOfNotCsvFiles ++;
                    }
                    
                    // Extracción de los ficheros incluidos en el zip.
                    if(extension.equals(Path.ZIP_EXTENSION)){
                        ZipInputStream zis = new ZipInputStream(new FileInputStream(directorio + File.separator + fileName));
                        ZipEntry eachFile;
                        while (null != (eachFile=zis.getNextEntry()) ){
                            File newFile = new File(directorio + File.separator + eachFile.getName());
                            // Se guardan los csv.
                            if(FilenameUtils.getExtension(directorio + File.separator + eachFile.getName()).equals(Path.CSV_EXTENSION)){
                                numberOfCsvFiles ++;
                                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                                    int len;
                                    byte[] buffer = new byte[1024];
                                    while ((len = zis.read(buffer)) > 0) {
                                        fos.write(buffer, 0, len);
                                    }
                                }
                                zis.closeEntry();
                                // Insertar en BD
                                uF = new UploadedFile(0, eachFile.getName(), new java.sql.Date(new Date().getTime()), false, null);
                                uFD.createFile(uF);
                            } else {
                                numberOfNotCsvFiles ++;
                            }
                        }
                        File fileToDelete = new File(path + File.separator + Path.UPLOADEDFILES_FOLDER + File.separator + fileName);
                        fileToDelete.delete();
                    } else {
                        // Insertar en BD
                        uF = new UploadedFile(0, fileName, new java.sql.Date(new Date().getTime()), false, null);
                        uFD.createFile(uF);
                    }
                }
            }
        }
        
        // Asignación de valores.
        HttpSession session = request.getSession(true);
        session.setAttribute("numberOfCsvFiles", numberOfCsvFiles);
        session.setAttribute("numberOfNotCsvFiles", numberOfNotCsvFiles);
        
        if(numberOfCsvFiles == 0 && numberOfNotCsvFiles == 0){
            session.setAttribute("error", true);
        } else {
            session.setAttribute("error", false);
        }

        
        
        processRequest(request, response);
    }
    
    /**
    * Método obtenerNombreFichero, permite obtener el nombre del fichero que
    * se almacena en la parte que nos pasan por parametro
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
        return "Upload files Servlet";
    }// </editor-fold>

}
