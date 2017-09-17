/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.data;

import java.util.Date;

/**
 * Clase UploadedFile, permite representar ficheros de datos subidos
 * al servidor.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class UploadedFile {
    
    /**
     * Identificador del fichero.
     */
    private double id;
    
    /**
     * Nombre del fichero.
     */
    private String name;
    
    /**
     * Fecha de subida.
     */
    private Date uploadedDate;
    
    /**
     * Variable booleana que indica si el fichero ha sido procesado.
     */
    private boolean isProcessed;
    
    /**
     * Fecha de procesado.
     */
    private Date processedDate;

    /**
     * Constructor por defecto.
     */
    public UploadedFile(){}
    
    /**
     * Constructor de clase con parámetros.
     * @param id identificador del fichero.
     * @param name nombre del fichero.
     * @param uploadedDate fecha de subida.
     * @param isProcessed procesado, true/false.
     * @param processedDate  fecha de procesado.
     */
    public UploadedFile(double id, String name, Date uploadedDate, boolean isProcessed, Date processedDate) {
        this.id = id;
        this.name = name;
        this.uploadedDate = uploadedDate;
        this.isProcessed = isProcessed;
        this.processedDate = processedDate;
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
     * @return the uploadedDate
     */
    public Date getUploadedDate() {
        return uploadedDate;
    }

    /**
     * @param uploadedDate the uploadedDate to set
     */
    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    /**
     * @return the isProcessed
     */
    public boolean isIsProcessed() {
        return isProcessed;
    }

    /**
     * @param isProcessed the isProcessed to set
     */
    public void setIsProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    /**
     * @return the processedDate
     */
    public Date getProcessedDate() {
        return processedDate;
    }

    /**
     * @param processedDate the processedDate to set
     */
    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }

    /**
     * 
     * @return id
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "UploadedFile{" + "id=" + id + ", name=" + name 
                + ", uploadedDate=" + uploadedDate + ", isProcessed="
                + isProcessed + ", processedDate=" + processedDate + '}';
    }
    
}
