/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.data;

import java.util.ArrayList;
import java.util.Date;

/**
 * Clase Experiment, permite representar un experimento o prueba en el sistema.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class Experiment {
    
    /**
     * Identificador del experimento o prueba.
     */
    private double idExperiment;
    
    /**
     * Lista de ficheros analizados en la prueba.
     */
    private ArrayList<UploadedFile> analyzedFiles;
    
    /**
     * Tipo de asignación del área a la prueba.
     */
    private int asignatedAreaType;
    
    /**
     * Identificador del área seleccionada.
     */
    private double asignatedAreaId;
    
    /**
     * Nombre del área.
     */
    private String areaName;
    
    /**
     * Distancia máxima entre rutas. Si una ruta no cumple con los valorea
     * indicados por el usuario, el algoritmo de análisis de rutas podrá separar
     * dicha ruta en dos secciones.
     */
    private double trackDistance;
    
    /**
     * Indicación de búsqueda de paradas.
     */
    private boolean findStops;
    
    /**
     * Distancia máxima que dos posiciones pueden formar una parada.
     */
    private double stopDistance;
    
    /**
     * Valor de variación de mediana.
     */
    private double medianVariation;
    
    /**
     * Búsqueda de Puntos de Interés.
     */
    private boolean findPois;
    
    /**
     * Radio máximo de búsqueda.
     */
    private double maxRadius;
    
    /**
     * Multiplicador de radio.
     */
    private double radiusMultiplier;
    
    /**
     * Booleano que indica si la prueba ha sido almacenada en DB.
     */
    private boolean storeInDB;
    
    /**
     * Lista de rutas generadas por la prueba.
     */
    private ArrayList<Track> generatedTracks;
    
    /**
     * Identificadores de rutas.
     */
    private ArrayList<Double> tracksId;
    
    /**
     * Fecha en la que la prueba ha tenido lugar.
     */
    private Date experimentDate;
    
    /**
     * Indica si la prueba o experimento es nueva en el sistema.
     */
    private boolean newExperiment;
    
    /**
     * Indica si el usuario desea borrar la prueba.
     */
    private boolean temporary;
    
    
    /**
     * @return the idExperiment
     */
    public double getIdExperiment() {
        return idExperiment;
    }

    /**
     * @param idExperiment the idExperiment to set
     */
    public void setIdExperiment(double idExperiment) {
        this.idExperiment = idExperiment;
    }

    /**
     * @return the analyzedFiles
     */
    public ArrayList<UploadedFile> getAnalyzedFiles() {
        return analyzedFiles;
    }

    /**
     * @param analyzedFiles the analyzedFiles to set
     */
    public void setAnalyzedFiles(ArrayList<UploadedFile> analyzedFiles) {
        this.analyzedFiles = analyzedFiles;
    }

    /**
     * @return the asignatedAreaType
     */
    public int getAsignatedAreaType() {
        return asignatedAreaType;
    }

    /**
     * @param asignatedAreaType the asignatedAreaType to set
     */
    public void setAsignatedAreaType(int asignatedAreaType) {
        this.asignatedAreaType = asignatedAreaType;
    }

    /**
     * @return the asignatedAreaId
     */
    public double getAsignatedAreaId() {
        return asignatedAreaId;
    }

    /**
     * @param asignatedAreaId the asignatedAreaId to set
     */
    public void setAsignatedAreaId(double asignatedAreaId) {
        this.asignatedAreaId = asignatedAreaId;
    }

    /**
     * @return the areaName
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * @param areaName the areaName to set
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * @return the trackDistance
     */
    public double getTrackDistance() {
        return trackDistance;
    }

    /**
     * @param trackDistance the trackDistance to set
     */
    public void setTrackDistance(double trackDistance) {
        this.trackDistance = trackDistance;
    }

    /**
     * @return the findStops
     */
    public boolean isFindStops() {
        return findStops;
    }

    /**
     * @param findStops the findStops to set
     */
    public void setFindStops(boolean findStops) {
        this.findStops = findStops;
    }

    /**
     * @return the stopDistance
     */
    public double getStopDistance() {
        return stopDistance;
    }

    /**
     * @param stopDistance the stopDistance to set
     */
    public void setStopDistance(double stopDistance) {
        this.stopDistance = stopDistance;
    }

    /**
     * @return the medianVariation
     */
    public double getMedianVariation() {
        return medianVariation;
    }

    /**
     * @param medianVariation the medianVariation to set
     */
    public void setMedianVariation(double medianVariation) {
        this.medianVariation = medianVariation;
    }

    /**
     * @return the findPois
     */
    public boolean isFindPois() {
        return findPois;
    }

    /**
     * @param findPois the findPois to set
     */
    public void setFindPois(boolean findPois) {
        this.findPois = findPois;
    }

    /**
     * @return the maxRadius
     */
    public double getMaxRadius() {
        return maxRadius;
    }

    /**
     * @param maxRadius the maxRadius to set
     */
    public void setMaxRadius(double maxRadius) {
        this.maxRadius = maxRadius;
    }

    /**
     * @return the radiusMultiplier
     */
    public double getRadiusMultiplier() {
        return radiusMultiplier;
    }

    /**
     * @param radiusMultiplier the radiusMultiplier to set
     */
    public void setRadiusMultiplier(double radiusMultiplier) {
        this.radiusMultiplier = radiusMultiplier;
    }

    /**
     * @return the storeInDB
     */
    public boolean isStoreInDB() {
        return storeInDB;
    }

    /**
     * @param storeInDB the storeInDB to set
     */
    public void setStoreInDB(boolean storeInDB) {
        this.storeInDB = storeInDB;
    }

    /**
     * @return the generatedTracks
     */
    public ArrayList<Track> getGeneratedTracks() {
        return generatedTracks;
    }

    /**
     * @param generatedTracks the generatedTracks to set
     */
    public void setGeneratedTracks(ArrayList<Track> generatedTracks) {
        this.generatedTracks = generatedTracks;
    }

    /**
     * @return the tracksId
     */
    public ArrayList<Double> getTracksId() {
        return tracksId;
    }

    /**
     * @param tracksId the tracksId to set
     */
    public void setTracksId(ArrayList<Double> tracksId) {
        this.tracksId = tracksId;
    }

    /**
     * @return the experimentDate
     */
    public Date getExperimentDate() {
        return experimentDate;
    }

    /**
     * @param experimentDate the experimentDate to set
     */
    public void setExperimentDate(Date experimentDate) {
        this.experimentDate = experimentDate;
    }

    /**
     * @return new Experiment
     */
    public boolean isNewExperiment() {
        return newExperiment;
    }

    /**
     * @param newExperiment the newExperiment to set
     */
    public void setNewExperiment(boolean newExperiment) {
        this.newExperiment = newExperiment;
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Experiment{" + "idExperiment=" + idExperiment + ", analyzedFiles="
                + analyzedFiles + ", asignatedAreaType=" + asignatedAreaType + ", asignatedAreaId="
                + asignatedAreaId + ", areaName=" + areaName + ", trackDistance=" + trackDistance
                + ", findStops=" + findStops + ", stopDistance=" + stopDistance + ", medianVariation="
                + medianVariation + ", findPois=" + findPois + ", maxRadius=" + maxRadius + ", radiusMultiplier="
                + radiusMultiplier + ", storeInDB=" + storeInDB + ", generatedTracks=" + generatedTracks
                + ", tracksId=" + tracksId + ", experimentDate=" + experimentDate + ", newExperiment="
                + newExperiment + ", temporary=" + temporary + '}';
    }
    
    
}
