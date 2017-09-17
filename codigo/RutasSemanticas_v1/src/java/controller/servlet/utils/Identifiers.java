/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet.utils;

/**
 * Clase Identifiers, permite obtener los últimos identificadores de las tablas
 * relevantes para el análisis de datos.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class Identifiers {
    
    /**
     * Indentificador de ruta.
     */
    private double trackId;
    
    /**
     * Identificador de posición.
     */
    private double positionId;
    
    /**
     * Constructor de clase con parámetros.
     * @param trackId identificador de la ruta.
     * @param positionId identificador de la posición.
     */
    public Identifiers(double trackId, double positionId){
        this.trackId = trackId;
        this.positionId = positionId;
    }

    /**
     * @return the trackId
     */
    public double getTrackId() {
        return trackId;
    }

    /**
     * @param trackId the trackId to set
     */
    public void setTrackId(double trackId) {
        this.trackId = trackId;
    }

    /**
     * @return the positionId
     */
    public double getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(double positionId) {
        this.positionId = positionId;
    }

    @Override
    public String toString() {
        return "Identifiers{" + "trackId=" + trackId
                + ", positionId=" + positionId + '}';
    }
    
}
