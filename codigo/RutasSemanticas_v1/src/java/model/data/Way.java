package model.data;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Clase Way, permite representar los caminos que cuentan con Puntos De Interés.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class Way {
	
    /**
     * Identificador del camino
     */
    private double id;
    
    /**
     * Identificador de la región a la que pertenece el camino.
     */
    private double idRegion;

    /**
     * Lista de nodos del camino.
     */
    private ArrayList<BigDecimal> aNodes;

    /**
     * Etiquetas del camino.
     */
    private String tags;

    /**
     * Constructor de la clase.
     * @param id identificador del camino.
     * @param aNodes lista de nodos.
     * @param tags cadena de etiquetas del camino.
     */
    public Way(double id, ArrayList<BigDecimal> aNodes, String tags, double idRegion) {
        this.id = id;
        this.aNodes = aNodes;
        this.tags = tags;
        this.idRegion = idRegion;
    }

    /**
     * @return the id
     */
    public double getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(double id) {
        this.id = id;
    }

    /**
     * @return the aNodes
     */
    public ArrayList<BigDecimal> getaNodes() {
        return aNodes;
    }

    /**
     * @param aNodes the aNodes to set
     */
    public void setaNodes(ArrayList<BigDecimal> aNodes) {
        this.aNodes = aNodes;
    }

    /**
     * @return the tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * @return the idRegion
     */
    public double getIdRegion() {
        return idRegion;
    }

    /**
     * @param idRegion the idRegion to set
     */
    public void setIdRegion(double idRegion) {
        this.idRegion = idRegion;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Way{" + "id=" + id + ", idRegion=" + idRegion +
                ", aNodes=" + aNodes + ", tags=" + tags + '}';
    }
    
}
