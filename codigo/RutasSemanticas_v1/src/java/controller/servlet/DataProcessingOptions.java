/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet;

import controller.servlet.utils.Path;
import java.io.File;
import model.dao.AreaDaoImpl;
import java.io.IOException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dao.UploadedFileDaoImpl;

/**
 * Clase DataProcessingOptions, servlet que permite obtener las opciones que
 * el usuario desea usar en el algoritmo de análisis de rutas.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class DataProcessingOptions extends HttpServlet {
    
    /**
     * 
     */
    String[] auxArray;

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
        if(auxArray == null || auxArray.length>0){
            String page = "/pages/dataprocessingoptions.jsp";
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(page);
            requestDispatcher.forward(request, response);
        }else{
            String path = this.getServletContext().getRealPath("");
            path += File.separator+Path.UPLOADEDFILES_FOLDER;
            File directory = new File(path);
            if(!directory.exists()){
                directory.mkdirs();   
            }
            UploadedFileDaoImpl uFD = new UploadedFileDaoImpl();        
            HttpSession session = request.getSession(true);
            session.setAttribute("numberOfCsvFiles", 0);
            session.setAttribute("numberOfNotCsvFiles", 0);
            session.setAttribute("error", false);
            session.setAttribute("mUF", uFD.getFiles());
            session.setAttribute("serverFolderFiles", directory.listFiles());
            
            String page = "/pages/upload.jsp";
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(page);
            requestDispatcher.forward(request, response);
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
        
        AreaDaoImpl aDao = new AreaDaoImpl();
        HttpSession session = request.getSession(true);
        session.setAttribute("areas", aDao.getAreas());
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
        
        //Obtención de todos los datos del formulario.
        
        AreaDaoImpl aDao = new AreaDaoImpl();
        Map<String, String[]> requestMap = request.getParameterMap();
        
        boolean expRepeat = false;
        
        for(String s : requestMap.get("exprepeat")){
            expRepeat = Boolean.parseBoolean(s);
        }
        
        HttpSession session = request.getSession(true);
        session.setAttribute("areas", aDao.getAreas());
        if(!expRepeat){
            session.setAttribute("notallfiles", true);
            session.setAttribute("selectedFiles", requestMap.get("chkbox"));
            session.setAttribute("trackdistance", 0.0);
            session.setAttribute("findstops", false);
            session.setAttribute("stopdistance", 0.0);
            session.setAttribute("medianvariation", 0.0);
            session.setAttribute("findpois", false);
            session.setAttribute("maxradius", 0.0);
            session.setAttribute("radiusmultiplier", 0.0);
            session.setAttribute("storeindb", false);
        }else{
            // Asignación de valores a las entradas de texto si se trata
            // de una repetición de una prueba.
            String[] aFiles = requestMap.get("files[]");
            String[] auxFiles = new String[aFiles.length];
            
            String path = this.getServletContext().getRealPath("");
            File directorio = new File(path+File.separator + Path.UPLOADEDFILES_FOLDER);
            int i=0;
            if(directorio.exists()){
                File[] files = directorio.listFiles();
                for(File f : files){
                    for(String s : aFiles){
                        if(f.getName().equals(s)){
                            auxFiles[i++] = s;
                        }
                    }
                }               
            }
            auxArray = new String[i];
            System.arraycopy(auxFiles, 0, auxArray, 0, i);
            
            boolean notallfiles = auxArray.length == auxFiles.length;
            
            session.setAttribute("selectedFiles", auxArray);
            session.setAttribute("notallfiles", notallfiles);
            
            for(String s : requestMap.get("trackdistance")){
                session.setAttribute("trackdistance", Double.parseDouble(s));
            }
            for(String s : requestMap.get("findstops")){
                session.setAttribute("findstops", Boolean.parseBoolean(s));
            }
            for(String s : requestMap.get("stopdistance")){
                session.setAttribute("stopdistance", Double.parseDouble(s));
            }
            for(String s : requestMap.get("medianvariation")){
                session.setAttribute("medianvariation", Double.parseDouble(s));
            }
            for(String s : requestMap.get("findpois")){
                session.setAttribute("findpois", Boolean.parseBoolean(s));
            }
            for(String s : requestMap.get("maxradius")){
                session.setAttribute("maxradius", Double.parseDouble(s));
            }
            for(String s : requestMap.get("radiusmultiplier")){
                session.setAttribute("radiusmultiplier", Double.parseDouble(s));
            }
            for(String s : requestMap.get("storeindb")){
                session.setAttribute("storeindb", Boolean.parseBoolean(s));
            }
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
        return "DataProcessingOptions servlet";
    }// </editor-fold>

}
