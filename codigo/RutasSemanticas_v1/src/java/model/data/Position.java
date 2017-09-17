package model.data;

import java.util.ArrayList;
import java.util.Date;

/**
 * Clase Position, permite definir objetos como coordenadas geográficas mediante
 * posiciones de Laititud y Longitud.
 * 
 * @author David Moreno del Hoyo
 * @version 1.0
 * 
 */
public class Position {
    
    /**
     * Identificador de posición.
     */
    private double positionId;
	
    /**
     * Identificador de la coordenada.
     */
    private double trackId;

    /**
     * Longitud
     */
    private double longitude;

    /**
     * Latitud.
     */
    private double latitude;

    /**
     * Altitud.
     */
    private double altitude;

    /**
     * Fecha.
     */
    private Date date;

    /**
     * Identificador de área.
     */
    private int areaId;
    
    /**
     * Indicador de parada, true si es parada, false si no.
     */
    private boolean stop;
    
    /**
     * Lista de posiciones con parada.
     */
    private ArrayList<StopPoi> listOfStopPois;
    
    /**
     * Lista de  PDIs asociados a la posición.
     */
    private ArrayList<Poi> listOfPois;
    
    /**
     * Constructor de clase.
     */
    public Position(){}
    
    /**
     * Constructor con parámetros.
     * @param latitude longitud de la posición.
     * @param longitude latitud de la posición. 
     */
    public Position(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return the positionId
     */
    public double getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the idPosition to set
     */
    public void setPositionId(double positionId) {
        this.positionId = positionId;
    }   
    
    /**
     * @return the track_id
     */
    public double getTrackId() {
        return trackId;
    }

    /**
     * @param trackId the track_id to set
     */
    public void setTrackId(double trackId) {
        this.trackId = trackId;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
            return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
            this.longitude = longitude;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
            return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
            this.latitude = latitude;
    }

    /**
     * @return the date
     */
    public Date getDate() {
            return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
            this.date = date;
    }


    /**
     * @return the altitude
     */
    public double getAltitude() {
            return altitude;
    }

    /**
     * @param altitude the altitude to set
     */
    public void setAltitude(double altitude) {
            this.altitude = altitude;
    }

    /**
     * @return the areaId
     */
    public int getAreaId() {
        return areaId;
    }

    /**
     * @param areaId the areaId to set
     */
    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    /**
     * @return the stop
     */
    public boolean isStop() {
        return stop;
    }

    /**
     * @param stop the stop to set
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }

    /**
     * @return the listOfStopPois
     */
    public ArrayList<StopPoi> getListOfStopPois() {
        return listOfStopPois;
    }

    /**
     * @param listOfStopPois the listOfStopPois to set
     */
    public void setListOfStopPois(ArrayList<StopPoi> listOfStopPois) {
        this.listOfStopPois = listOfStopPois;
    }

    /**
     * @return the listOfPois
     */
    public ArrayList<Poi> getListOfPois() {
        return listOfPois;
    }

    /**
     * @param listOfPois the listOfPois to set
     */
    public void setListOfPois(ArrayList<Poi> listOfPois) {
        this.listOfPois = listOfPois;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Position{" + "positionId=" + positionId
                + ", trackId=" + trackId + ", longitude="
                + longitude + ", latitude=" + latitude + ", altitude="
                + altitude + ", date=" + date + ", areaId=" + areaId
                + ", stop=" + stop + ", listOfStopPois=" + listOfStopPois
                + ", listOfPois=" + listOfPois + '}';
    }
    
}
