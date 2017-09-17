package model.data;

import java.util.Date;

/**
 * Clase Area, permite representar un área en el sistema.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class Area {
	
    /**
     * Identificador del área.
     */
    private int id;

    /**
     * Nombre del área.
     */
    private String name;

    /**
     * Descripción del área.
     */
    private String description;

    /**
     * Latitud Mínima.
     */
    private double minLat;

    /**
     * Latitud máxima.
     */
    private double maxLat;

    /**
     * Longitud mínima.
     */
    private double minLong;

    /**
     * Longitud máxima.
     */
    private double maxLong;

    /**
     * Booleano que indica si el área se encuentra activa.
     */
    private boolean activa;
    
    /**
     * Númereo de rutas que contiene el área.
     */
    private double numTracks;
    
    /**
     * Fecha de creación del área.
     */
    private Date fechaCreacion;
    
    /**
     * Fecha de modificación del área.
     */
    private Date fechaModificacion;
    
    /**
     * Constructor por defecto.
     */
    public Area(){}
    
    /**
     * Constructor de clase con parámetros.
     * @param areaId identificador del área.
     */
    public Area(int id){
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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

    /**
     * @return the id
     */
    public int getId() {
            return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
            this.id = id;
    }

    /**
     * @return the activa
     */
    public boolean isActiva() {
        return activa;
    }

    /**
     * @param activa the activa to set
     */
    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    /**
     * @return the numTracks
     */
    public double getNumTracks() {
        return numTracks;
    }

    /**
     * @param numTracks the numTracks to set
     */
    public void setNumTracks(double numTracks) {
        this.numTracks = numTracks;
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
     * @return the fechaModificacion
     */
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * @param fechaModificacion the fechaModificacion to set
     */
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Area{" + "id=" + id + ", name=" + name + ", description="
                + description + ", min_lat=" + minLat + ", max_lat="
                + maxLat + ", min_long=" + minLong + ", max_long="
                + maxLong + ", activa=" + activa + ", numTracks="
                + numTracks + ", fechaCreacion=" + fechaCreacion
                + ", fechaModificacion=" + fechaModificacion + '}';
    }
    
}
