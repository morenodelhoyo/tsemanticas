/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.data;

/**
 * Clase AreaTrack, permite representar un área conformada por las posiciones de una ruta
 * concreta.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class AreaTrack {
    
    /**
     * Identificador del área.
     */
    private double trackId;
    
    /**
     * Latitud mínima.
     */
    private double minLat;
    
    /**
     * Longitud mínima.
     */
    private double minLong;
    
    /**
     * Latitud máxima.
     */
    private double maxLat;
    
    /**
     * Longitud máxima.
     */
    private double maxLong;

    /**
     * Constructor de clase con parámetros.
     * @param minLat latitud mínima
     * @param minLong longitud mínima.
     * @param maxLat latitud máxima.
     * @param maxLong longitud máxima.
     */
    public AreaTrack(double minLat, double minLong, double maxLat, double maxLong) {
        this.minLat = minLat;
        this.minLong = minLong;
        this.maxLat = maxLat;
        this.maxLong = maxLong;
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
     * @return the minLat
     */
    public double getMinLat() {
        return minLat;
    }

    /**
     * @param minLat the minLat to set
     */
    public void setMinLat(double minLat) {
        this.minLat = minLat;
    }

    /**
     * @return the minLong
     */
    public double getMinLong() {
        return minLong;
    }

    /**
     * @param minLong the minLong to set
     */
    public void setMinLong(double minLong) {
        this.minLong = minLong;
    }

    /**
     * @return the maxLat
     */
    public double getMaxLat() {
        return maxLat;
    }

    /**
     * @param maxLat the maxLat to set
     */
    public void setMaxLat(double maxLat) {
        this.maxLat = maxLat;
    }

    /**
     * @return the maxLong
     */
    public double getMaxLong() {
        return maxLong;
    }

    /**
     * @param maxLong the maxLong to set
     */
    public void setMaxLong(double maxLong) {
        this.maxLong = maxLong;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AreaTrack{" + "trackId=" + trackId + ", minLat=" + minLat
        + ", minLong=" + minLong + ", maxLat=" + maxLat + ", maxLong=" + maxLong + '}';
    }
    
}
