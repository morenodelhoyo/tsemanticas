package model.data;

import java.util.ArrayList;
import java.util.Date;

/**
 * Clase Track, permite representar una ruta con todos sus elementos.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class Track {

    /**
     * Identificador de usuario que ha realizado la ruta.
     */
    private int userid;

    /**
     * Identificador de la ruta.
     */
    private double trackId;

    /**
     * Lista de posiciones de la ruta.
     */
    private ArrayList<Position> listOfPositions;

    /**
     * Objeto de tipo Area que permite representar un área concreta.
     */
    private Area area;
    
    /**
     * Fecha de creación de la ruta en el sistema.
     */
    private Date fechaCreacion;
    
    /**
     * Booleano que indica si la ruta es nueva en el sistema.
     */
    private boolean newTrack;
    
    /**
     * Booleano que indica si la ruta ha de ser temporal.
     */
    private boolean temporary;
    
    /**
     * Área que genera las posiciones de la ruta.
     */
    private AreaTrack aTrack;
    
    /**
     * Fecha de inicio de la ruta.
     */
    private Date initDate;
    
    /**
     * Fecha de fin de la ruta.
     */
    private Date endDate;
    
    /**
     * Número de posiciones de la ruta.
     */
    private double nPositions;
    
    /**
     * Constructor por defecto.
     */
    public Track(){}
    
    /**
     * Constructor de la clase con parámetros.
     * @param userId identificador de usuario de la ruta.
     * @param aPositions lista de posiciones.
     * @param trackId identificador de la ruta.
     */
    public Track(int userId, ArrayList<Position> aPositions, double trackId){
        this.listOfPositions = aPositions;
        this.userid = userId;
        this.trackId = trackId;
    }

    /**
     * @return the userid
     */
    public int getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(int userid) {
        this.userid = userid;
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
     * @return the listOfPositions
     */
    public ArrayList<Position> getListOfPositions() {
        return listOfPositions;
    }

    /**
     * @param listOfPositions the listOfLocations to set
     */
    public void setListOfPositions(ArrayList<Position> listOfPositions) {
        this.listOfPositions = listOfPositions;
    }

    /**
     * @return the area
     */
    public Area getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(Area area) {
        this.area = area;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    /**
     * 
     * @return 
     */
    public boolean isNewTrack() {
        return newTrack;
    }

    /**
     * 
     * @param newTrack 
     */
    public void setNewTrack(boolean newTrack) {
        this.newTrack = newTrack;
    }

    /**
     * @return the temporary
     */
    public boolean isTemporary() {
        return temporary;
    }

    /**
     * @param temporary the temporary to set
     */
    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }
    
    /**
     * @return the aTrack
     */
    public AreaTrack getaTrack() {
        return aTrack;
    }

    /**
     * @param aTrack the aTrack to set
     */
    public void setaTrack(AreaTrack aTrack) {
        this.aTrack = aTrack;
    }

    /**
     * @return the initDate
     */
    public Date getInitDate() {
        return initDate;
    }

    /**
     * @param initDate the initDate to set
     */
    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    /**
     * @return the nPositions
     */
    public double getnPositions() {
        return nPositions;
    }

    /**
     * 
     * @param nPositions 
     */
    public void setnPositions(double nPositions) {
        this.nPositions = nPositions;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Track{" + "userid=" + userid + ", trackId="
                + trackId + ", listOfPositions=" + listOfPositions
                + ", area=" + area + ", fechaCreacion=" + fechaCreacion
                + ", newTrack=" + newTrack + ", temporary=" + temporary
                + ", aTrack=" + aTrack + ", initDate=" + initDate + ", endDate="
                + endDate + ", nPositions=" + nPositions + '}';
    }
    
    
    
}
