package model.data;

import java.math.BigDecimal;

/**
 * Clase StopPoi, permite representar una parada con PDIs en la ruta.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class StopPoi {
    
    
    /**
     * Identificador de la parada con PDIs.
     */
    private double idStopPoi;
    
    /**
     * Identificador de la posición.
     */
    private double idPosition;

    /**
     * Identificador del PDI.
     */
    private BigDecimal idPoi;
    
    /**
     * Valor de la distancia del PDI a la posición.
     */
    private double distance;

    /**
     * Constructor de clase.
     */
    public StopPoi(){}

    /**
     * Constructor de clase con parámetros.
     * @param idStopPoi identificador de la parada con PDI.
     * @param idPoi identificador del PDI.
     * @param idPosition identificador de la posición.
     * @param distance distancia calculada.
     */
    public StopPoi(double idStopPoi, double idPosition, BigDecimal idPoi, double distance) {
        this.idStopPoi = idStopPoi;
        this.idPoi = idPoi;
        this.idPosition = idPosition;
        this.distance = distance;
    }

    /**
     * @return the idStopPoi
     */
    public double getIdStopPoi() {
        return idStopPoi;
    }

    /**
     * @param idStopPoi the idStopPoi to set
     */
    public void setIdStopPoi(double idStopPoi) {
        this.idStopPoi = idStopPoi;
    }
    
    /**
     * @return the idPoi
     */
    public BigDecimal getIdPoi() {
        return idPoi;
    }

    /**
     * @param idPoi the id_poi to set
     */
    public void setIdPoi(BigDecimal idPoi) {
        this.idPoi = idPoi;
    }

    /**
     * @return the idPosition
     */
    public double getIdPosition() {
        return idPosition;
    }

    /**
     * @param idPosition the idPosition to set
     */
    public void setIdPosition(double idPosition) {
        this.idPosition = idPosition;
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
        return "StopPoi{" + "idStopPoi=" + idStopPoi + ", idPosition="
                + idPosition + ", idPoi=" + idPoi + ", distance="
                + distance + '}';
    }
    
}
