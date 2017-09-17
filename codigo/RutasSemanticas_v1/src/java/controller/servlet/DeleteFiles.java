/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet;

import controller.servlet.utils.Path;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 * Clase DeleteFiles, servlet que permite gestionar los PDIs
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class DeleteFiles extends HttpServlet {

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
        
        // Obtención de los ficheros a borrar.
        String jsonFiles = request.getParameter("files");        
        
        JSONObject obj = new JSONObject(jsonFiles);
        Map<String, Object> mapOfJson = obj.toMap();
        Object files = mapOfJson.get("files");
        Object object[] = new Object[1];
        
        if(files instanceof String){
            object[0] = files;
        }
        if(files instanceof List){
            object = ((List) files).toArray();
        }
        List<Object>  aFiles = new ArrayList();
        aFiles.addAll(Arrays.asList(object));
                
        String path = this.getServletContext().getRealPath("");
        path += File.separator + Path.UPLOADEDFILES_FOLDER + File.separator;
        File directory = new File(path);
        
        //Borrado de los ficheros.
        aFiles.forEach((o) -> {
            for(File f : directory.listFiles()){
                if(f.getName().equals(o)){
                    f.delete();
                }
            }
        });
        
        PrintWriter out = response.getWriter();
        out.println("done");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "DeleteFiles servlet";
    }// </editor-fold>

}
