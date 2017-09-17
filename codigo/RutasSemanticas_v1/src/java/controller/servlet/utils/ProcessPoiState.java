/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet.utils;

/**
 * Interfaz ProcessPoiState, contiene identificadores de estado del proceso 
 * de PDIs.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public interface ProcessPoiState {
    
    /**
     * Estado de lectura del fichero.
     */
    public final int FILE_READING = 1;
    
    /**
     * Estado de proceso de nodos.
     */
    public final int NODE_PROCESSING = 2;
    
    /**
     * Estado de proceso de caminos.
     */
    public final int WAY_PROCESSING = 3;
    
    /**
     * Estado finalizado.
     */
    public final int PROCESS_ENDED = 4;
    
}
