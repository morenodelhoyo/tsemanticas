/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet.utils;

/**
 * Interfaz ThreadName, contiene los nombres de los hilos que se lanzan 
 * al ejecutar el algoritmo de análisis o en el proceso de ficheros de PDIs.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public interface ThreadName {
    
    public final String TRACK_THREAD_NAME = "PTT";
    
    public final String AMENITIES_THREAD_NAME = "PPT";
    
}
