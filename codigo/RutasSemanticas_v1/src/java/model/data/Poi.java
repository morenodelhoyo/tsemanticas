package model.data;

import java.math.BigDecimal;

/**
 * Clase Poi, permite representar una PDI en el sistema.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class Poi {
	
    /**
     * Identificador del PDI.
     */
    private BigDecimal id;
    
    /**
     * Región a la que pertenece el PDI.
     */
    private double regionId;

    /**
     * Latitud.
     */
    private double latitude;

    /**
     * Longitud.
     */
    private double longitude;
    
    /**
     * Nombre del PDI.
     */
    private String name;
    
    /**
     * Tipo del PDI.
     */
    private String amenity;

    /**
     * Distancia del PDI a la posición.
     */
    private double distance;

    /**
     * Constructor de clase.
     */
    public Poi(){}

    /**
     * Constructor de clase con parámetros.
     * @param id identificador del PDI.
     * @param latitude latitud.
     * @param longitude longitud.
     * @param name nombre del PDI.
     * @param amenity tipo del PDI.
     * @param regionId identificador de región.
     */
    public Poi(BigDecimal id, double latitude, double longitude, String name, String amenity, Double regionId) {
            this.id = id;
            this.latitude = latitude;
            this.longitude = longitude;
            this.name = name;
            this.amenity = amenity;
            this.regionId = regionId;
    }

    /**
     * @return the id
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * @return the regionId
     */
    public double getRegionId() {
        return regionId;
    }

    /**
     * @param regionId the regionId to set
     */
    public void setRegionId(double regionId) {
        this.regionId = regionId;
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
     * @return the amenity
     */
    public String getAmenity() {
        return amenity;
    }

    /**
     * @param amenity the amenity to set
     */
    public void setAmenity(String amenity) {
        this.amenity = amenity;
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Poi{" + "id=" + id + ", regionId=" + regionId + ", latitude="
                + latitude + ", longitude=" + longitude + ", name=" + name
                + ", amenity=" + amenity + ", distance=" + distance + '}';
    }
	
}
