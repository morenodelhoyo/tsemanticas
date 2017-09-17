/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.data;

import java.math.BigDecimal;

/**
 * Clase Region, permite representar una región que contiene PDIs.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class Region {
    
    /**
     * Identificador de la región
     */
    private int regionId;
    
    /**
     * Nombre de la región
     */
    private String name;
    
    /**
     * Número de nodos.
     */
    private BigDecimal numberOfNodes;
    
    /**
     * Número de caminos.
     */
    private BigDecimal numberOfWays;
    
    /**
     * Constructor de clase.
     */
    public Region(){}

    /**
     * @return name
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
     * @return regionId the regionId
     */
    public int getRegionId() {
        return regionId;
    }

    /**
     * 
     * @param regionId the regionId to set
     */
    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
    
    /**
     * @return the numberOfNodes
     */
    public BigDecimal getNumberOfNodes() {
        return numberOfNodes;
    }

    /**
     * @param numberOfNodes the numberOfNodes to set
     */
    public void setNumberOfNodes(BigDecimal numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
    }

    /**
     * @return the numberOfWays
     */
    public BigDecimal getNumberOfWays() {
        return numberOfWays;
    }

    /**
     * @param numberOfWays the numberOfWays to set
     */
    public void setNumberOfWays(BigDecimal numberOfWays) {
        this.numberOfWays = numberOfWays;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Region{" + "regionId=" + regionId + ", name=" + name
                + ", numberOfNodes=" + numberOfNodes + ", numberOfWays="
                + numberOfWays + '}';
    }    
    
}
