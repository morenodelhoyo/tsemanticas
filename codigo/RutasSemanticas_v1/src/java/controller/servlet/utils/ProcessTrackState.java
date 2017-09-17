/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet.utils;

/**
 * Interfaz ProcessTrackState, contiene identificadores de estado del proceso 
 * de rutas.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public interface ProcessTrackState {
    
    /**
     * Lectura de ficheros.
     */
    public final int FILE_READING = 1;
    
    /**
     * Procesado de rutas.
     */
    public final int TRACK_PROCESSING = 2;
    
    /**
     * Procesado de paradas.
     */
    public final int STOP_PROCESSING = 3;
    
    /**
     * Procesado de PDIs
     */
    public final int POI_PROCESSING = 4;
    
    /**
     * Guardado en DB.
     */
    public final int DB_SAVING = 5;
    
    /**
     * Proceso finalizado.
     */
    public final int PROCESS_ENDED = 6;
    
    
}
