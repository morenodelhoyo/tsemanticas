/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet.utils;

/**
 * Interfaz Path, contiene cadenas con las extensiones de ficheros y nombres
 * de carpetas del sistema.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public interface Path {
    
    /**
     * Extensión zip.
     */
    public final String ZIP_EXTENSION = "zip";
    
    /**
     * Extensión csv.
     */
    public final String CSV_EXTENSION = "csv";
    
    /**
     * Carpeta de ficheros de datos.
     */
    public final String UPLOADEDFILES_FOLDER = "uploadedfiles";
    
    /**
     * Carpeta de ficheros de PDIs.
     */
    public final String POIS_FOLDER = "pois";
    
}
