/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.algorithm;

import controller.servlet.utils.Identifiers;
import controller.servlet.utils.ProcessTrackState;
import model.dao.AreaDaoImpl;
import model.dao.ExperimentDaoImpl;
import model.dao.ExperimentTrackDaoImpl;
import model.dao.TrackDaoImpl;
import model.dao.TrackProcessDaoImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.data.Area;
import model.data.AreaTrack;
import model.data.Position;
import model.data.Track;

/**
 * Clase ProcessTrack, permite procesar la/s ruta/s. Será posible
 * identificar varias rutas dentro de una única ruta dependiendo de la separación
 * temporal y espacial de las coordenadas geográficas que existan en el interior
 * de la ruta inicial.
 * 
 * @author David Moreno del Hoyo.
 * @version 1.0
 */
public class ProcessTrack {
    
    /**
     * Constante con los segundos de un día.
     */
    private final int DAY_TIME_SECONDS = 24*60*60;
    
    /**
     * Mapa de las opciones elegidas por el usuario para procesar los ficheros.
     */
    private final Map<String, Object> selectedOptions;
    
    /**
     * Rutas procedentes de la Base de Datos. En caso de no serlo, su valor
     * será null.
     */
    private final ArrayList<Track> aTracks;
    
    /**
     * Rutas generadas.
     */
    private final ArrayList<Track> generatedTracks;
        
    /**
     * Identificadores máximos de la Base de Datos.
     */
    private final Identifiers identifiers;
    
    /**
     * Contador de posiciones.
     */
    private double positionCounter;
    
    /**
     * Ruta recibida. En caso de no serlo, su valor será null.
     */
    private final Track track;
    
    /**
     * Contador de rutas.
     */
    private double trackCounter;
      
    /**
     * Latitud mínima que forma la ruta.
     */
    private double latMin = 90.0;

    /**
     * Latitud máxima que forma la ruta.
     */
    private double latMax = -90.0;

    /**
     * Longitud mínima que forma la ruta.
     */
    private double longMin = 180.0;

    /**
     * Longitud máxima que forma la ruta.
     */
    private double longMax = -180.0;
    
    /**
     * Construcor de la clase ProcessTrack.
     * @param aTracks
     *              rutas recibidas desde la Base de Datos.
     * @param identifiers
     *              identificadores máximos de la Base de Datos.
     * @param selectedOptions
     */
    public ProcessTrack(ArrayList<Track> aTracks, Identifiers identifiers,  Map<String, Object> selectedOptions) {
        this.aTracks = aTracks;
        this.identifiers = identifiers;
        this.selectedOptions = selectedOptions;
        generatedTracks = new ArrayList();
        track = null;
        trackCounter = identifiers.getTrackId();
        positionCounter = identifiers.getPositionId();
    }
    
    /**
     * Método process, permite procesar la/s ruta/s recibida/s.
     */
    public void process(){
        
        // Actualización del proceso.
        TrackProcessDaoImpl tPDao = new TrackProcessDaoImpl();
        tPDao.updateTrackProcess(ProcessTrackState.TRACK_PROCESSING);
        
        if(aTracks != null){
            aTracks.forEach((eachTrack) -> {
                processTrack(eachTrack);
            });
        }
        if(track != null){
            processTrack(track);
        }
        
        // Asignar un área a cada ruta.
        classify();
        
        // Una vez procesadas todas las rutas, se decidirá el siguiente paso.
        decide();
    }
    
    /**
     * Método processTrack, permite procesar la ruta pasada. Se comprobarán
     * si existe alguna ruta adicional a partir de la inicial.
     * @param eachTrack
     *              la ruta sobre la que se verificará la existencia de rutas
     *              adicionales.
     */
    public void processTrack(Track eachTrack){
        
        /**
         * Lista de diferencias temporales.
         */
        ArrayList<Double> aDiferencias;
        
        /**
         * Lista auxiliar para las diferencias temporales y cálculo de meidana.
         */
        ArrayList<Double> aux;

        /**
         * Lista de posiciones de la ruta.
         */
        ArrayList<Position> aPositions = eachTrack.getListOfPositions();
        
        /**
         * Lista de posiciones que forman una nueva ruta.
         */
        ArrayList<Position> aTrack = new ArrayList();
        
        /**
         * Objeto de tipo CoordinatesComparison que permite comparar
         * dos posiciones de una ruta.
         */
        CoordinatesComparison coordinatesComparison = new CoordinatesComparison();
        
        /**
         * Objeto de tipo DateComparison que permite comparar dos fechas.
         */
        DateComparison dC = new DateComparison();
        
        /** 
         * Variable booleana que indicará si hay cambios significativos en los
         * valores de diferencias temporales calculados.
         */
        boolean isNecessary;
        
        // valor de una diferencia de tiempo.
        double diferencia;
        
        // distancia en metros entre dos coordenadas geográficas.
        double meters;
        
        // valor de la mediana.
        double valueOfMedian = 0;
        
        // distancia máxima elegida por el usuario.
        double maxDistanceValue = (Double)selectedOptions.get("maxDistanceValue");
        
        if(aPositions.size() > 1){
            for(Position position: aPositions){
                latMin = (position.getLatitude() <= latMin) ? position.getLatitude() : latMin;
                latMax = (position.getLatitude() >= latMax) ? position.getLatitude() : latMax;
                longMin = (position.getLongitude() <= longMin) ? position.getLongitude() : longMin;
                longMax = (position.getLongitude() >= longMax) ? position.getLongitude() : longMax;
            }
        }
        
        while(!aPositions.isEmpty()){
            /* No se tomarán en cuenta las rutas de una sola posición ya
             * que no conducen a un análisis correcto.
             */
            if(aPositions.size()>1){
                // En caso de existir más de un elemento.
                aDiferencias = new ArrayList();
                // Calcular la diferencia de tiempos.
                calculateTimeDifferences(aPositions, aDiferencias);                
                
                // Si quedan dos elementos (única cifra de diferencias) y se tiene una mediana anterior:
                if (aDiferencias.size() == 1 && valueOfMedian != 0){
                    meters = coordinatesComparison.calculateDistance(aPositions.get(0), aPositions.get(1));
                    if((valueOfMedian > aDiferencias.get(0) && meters <= maxDistanceValue) ||
                       (valueOfMedian < aDiferencias.get(0) && meters <= maxDistanceValue) ||
                       (valueOfMedian < aDiferencias.get(0) && meters > maxDistanceValue)){
                        aTrack.addAll(aPositions);
                        aPositions.clear();
                        break;
                    } else {
                        // Este caso genera dos rutas de un elemento. No es relevante.
                        aPositions.clear();
                    }
                }
                if (aDiferencias.size() == 1 && valueOfMedian == 0){
                    /* Calculando la distancia entre los puntos se determinará si pertenecen
                     * a la misma ruta o a una diferente ya que no se dispone de una diferencia de tiempo
                     * anterior. */
                    meters = coordinatesComparison.calculateDistance(aPositions.get(0), aPositions.get(1));
                    if(meters < maxDistanceValue){
                        aTrack.addAll(aPositions);
                        aPositions.clear();
                        break;
                    } else {
                        // Este caso genera dos rutas de un elemento. No es relevante.
                        aPositions.clear();
                    }
                }
                // Ordenar las diferencias de tiempos obtenidos
                aux = new ArrayList(aDiferencias);
                Collections.sort(aux);
                /* En caso de que existan tres posiciones (dos valores de diferencias):
                 * Necesario ya que la mediana siempre será válida (no habrá un valor tan
                 * superior como el necesario a la mediana calculada y se tomaría
                 * como una misma ruta).
                 */
                if(aDiferencias.size() == 2 && valueOfMedian != 0){
                    /* Si los tiempos son menores que la mediana, el punto 
                     * intermedio forma parte de la ruta.
                     */
                    if(aDiferencias.get(0) <= valueOfMedian && aDiferencias.get(1)<=valueOfMedian){
                        aTrack.addAll(aPositions);
                        aPositions.clear();
                        break;
                    }
                    double distance_ab = coordinatesComparison.calculateDistance(aPositions.get(0), aPositions.get(1));
                    double distance_bc = coordinatesComparison.calculateDistance(aPositions.get(1), aPositions.get(2));
                    // Las posiciones pertenecen a una misma ruta.
                    if((aDiferencias.get(0) > valueOfMedian && distance_ab <= maxDistanceValue) && (aDiferencias.get(1) <= valueOfMedian && distance_bc <= maxDistanceValue) ||
                       (aDiferencias.get(0) > valueOfMedian && distance_ab <= maxDistanceValue) && (aDiferencias.get(1) <= valueOfMedian && distance_bc > maxDistanceValue)){
                        aTrack.addAll(aPositions);
                        aPositions.clear();
                        break;
                    }
                    // Las posiciones pertenecen a una misma ruta.
                    if((aDiferencias.get(0) < valueOfMedian && distance_ab <= maxDistanceValue) && (aDiferencias.get(1) >= valueOfMedian && distance_bc <= maxDistanceValue) ||
                       (aDiferencias.get(0) <= valueOfMedian && distance_ab > maxDistanceValue) && (aDiferencias.get(1) >= valueOfMedian && distance_bc <= maxDistanceValue)){
                        aTrack.addAll(aPositions);
                        aPositions.clear();
                        break;
                    }
                    // Las posiciones pertenecen a una misma ruta.
                    if((aDiferencias.get(0) > valueOfMedian && coordinatesComparison.calculateDistance(aPositions.get(0), aPositions.get(1)) <= maxDistanceValue) &&
                       (aDiferencias.get(1) > valueOfMedian && coordinatesComparison.calculateDistance(aPositions.get(1), aPositions.get(2)) <= maxDistanceValue)){
                        aTrack.addAll(aPositions);
                        aPositions.clear();
                       break;
                    }
                    // El resto de casos generan dos rutas independientemente del resultado de las mediciones.
                    aTrack.add(aPositions.remove(0));
                    aTrack.add(aPositions.get(0));
                    
                    setPositionsId(aTrack);
                    
                    
                    
                    
                    Track newTrack = new Track(eachTrack.getUserid(), aTrack, trackCounter);
                    generatedTracks.add(newTrack);
                    trackCounter ++;
                    
                    aTrack = new ArrayList();
                    aTrack.addAll(aPositions);
                    aPositions.clear();
                    break;
                } else if(aDiferencias.size() == 2 && valueOfMedian == 0) {
                    /* En el caso de no contar con una mediana previa se considerará solamente
                     * la diferencia de espacio entre las posiciones.
                     */
                    double distance_ab = coordinatesComparison.calculateDistance(aPositions.get(0), aPositions.get(1));
                    double distance_bc = coordinatesComparison.calculateDistance(aPositions.get(1), aPositions.get(2));
                    if(distance_ab <= maxDistanceValue && distance_bc <= maxDistanceValue){
                        aTrack.addAll(aPositions);
                        aPositions.clear();
                        break;
                    }
                    if((distance_ab <= maxDistanceValue &&  distance_bc > maxDistanceValue) ||
                       (distance_ab > maxDistanceValue &&  distance_bc <= maxDistanceValue) ||
                       (distance_ab > maxDistanceValue &&  distance_bc > maxDistanceValue)){
                        aTrack.add(aPositions.remove(0));
                        aTrack.add(aPositions.get(0));
                        
                        setPositionsId(aTrack);
                        Track newTrack = new Track(eachTrack.getUserid(), aTrack, trackCounter);                        
                        generatedTracks.add(newTrack);
                        trackCounter ++;

                        aTrack = new ArrayList();
                        aTrack.addAll(aPositions);
                        aPositions.clear();
                        break;
                    }
                }

                // Calcular la mediana
                valueOfMedian = calculateMedian(aux, aPositions);
                // Comprobar si las diferencias de tiempos obtenidas son mayores a la mediana.
                isNecessary = validateDifferences(valueOfMedian, aDiferencias);
                
                // En caso de que haya valores diferentes.
                if(isNecessary){
                    // Lista de posiciones auxiliar para nuevas rutas.
                    ArrayList<Position> auxPositions = new ArrayList();
                    for(int i=0; i<aPositions.size()-1;i++){
                        diferencia = dC.calculateDifference(aPositions.get(i).getDate(), aPositions.get(i+1).getDate())/1000;
                        if(diferencia <= valueOfMedian && diferencia <= DAY_TIME_SECONDS){
                            if(aPositions.size() == 2){
                                auxPositions.add(aPositions.remove(i));
                                auxPositions.add(aPositions.remove(i));
                            }else{
                                auxPositions.add(aPositions.remove(i));
                                i--;
                            }
                        }else{
                            meters = coordinatesComparison.calculateDistance(aPositions.get(i), aPositions.get(i+1));
                            if(meters < maxDistanceValue){
                                if(aPositions.size() == 2){
                                    auxPositions.add(aPositions.remove(i));
                                    auxPositions.add(aPositions.remove(i));
                                }else{
                                    auxPositions.add(aPositions.remove(i));
                                    i--;
                                }
                            }else{
                                // Se genera una nueva ruta.
                                auxPositions.add(aPositions.remove(i));
                                i--;
                                aTrack.addAll(auxPositions);
                                if(aTrack.size() > 1){
                                    setPositionsId(aTrack);
                                    Track newTrack = new Track(eachTrack.getUserid(), aTrack, trackCounter);                                    
                                    generatedTracks.add(newTrack);
                                    trackCounter ++;
                                }
                                aTrack = new ArrayList();
                                auxPositions = new ArrayList();
                                break;
                            }
                        }
                    }
                    aTrack.addAll(auxPositions);
                }else{
                    // Si todos los tiempos son similares al de la mediana, añadir la ruta completa.
                    aTrack.addAll(aPositions);
                    aPositions.clear();
                }
            }else{
                aPositions.clear();
                break;
            } // fin aPositions > 1
        } // fin while
        if(!aTrack.isEmpty() && aTrack.size() > 1){
            setPositionsId(aTrack);
            Track newTrack = new Track(eachTrack.getUserid(), aTrack, trackCounter);
            generatedTracks.add(newTrack);            
            trackCounter ++;
        }
    } // end method
    
    /**
     * Método classify asigna un área a las rutas dependiendo de la opción
     * que haya elegido el usuario.
     */
    private void classify(){
        
        AreaDaoImpl aDao = new AreaDaoImpl();
        Area a = new Area();
        // tipo 1: se asignará a un área nueva.
        // tipo 2: se asignará al área indicada.
        int areaClassificationType = (Integer) selectedOptions.get("areaSelect");
        if(areaClassificationType == 1){
            a = new Area();
            a.setActiva(true);
            a.setName((String) selectedOptions.get("areaName"));
            a.setDescription((String) selectedOptions.get("areaDescription"));
            a.setMinLat(latMin);
            a.setMaxLat(latMax);
            a.setMinLong(longMin);
            a.setMaxLong(longMax);
            a.setFechaCreacion(new java.sql.Date(new Date().getTime()));
            int areaId = aDao.createArea(a);
            a.setId(areaId);
        }
        
        if(areaClassificationType == 2){
            a = new AreaDaoImpl().getAreaById((int)selectedOptions.get("existingAreas"));
        }
        
        // Búsqueda de los límites de la ruta.
        double lMin = 90;
        double lMax = -90;
        double loMin = 180;
        double loMax = -180;
        
        for(Track t : generatedTracks){
            for(Position position: t.getListOfPositions()){
                lMin = (position.getLatitude() <= lMin) ? position.getLatitude() : lMin;
                lMax = (position.getLatitude() >= lMax) ? position.getLatitude() : lMax;
                loMin = (position.getLongitude() <= loMin) ? position.getLongitude() : loMin;
                loMax = (position.getLongitude() >= loMax) ? position.getLongitude() : loMax;
            }
            t.setaTrack(new AreaTrack(lMin, longMin, latMax, longMax));
            t.setArea(a);
            t.setInitDate(t.getListOfPositions().get(0).getDate());
            t.setEndDate(t.getListOfPositions().get(t.getListOfPositions().size()-1).getDate());
        }
        
    }
    
    /**
     * Método decide, permite tomar una decisión dependiendo
     * de las opciones que haya seleccionado el usuario.
     */
    private void decide(){
        
        identifiers.setTrackId(-- trackCounter);
        identifiers.setPositionId(positionCounter);
        
        // Actualización del experimento.
        ExperimentDaoImpl eD = new ExperimentDaoImpl();
        double lastExpId = eD.getLastExperimentId();
        eD.updateExperiment(lastExpId, generatedTracks.get(0).getArea().getId());
        
        // Si se desean buscar paradas.
        if(selectedOptions.get("findStops").equals(true)){
            Logger.getLogger(ProcessTrack.class.getName()).log(Level.INFO, "Processing stops.");
            new ProcessStop(generatedTracks, selectedOptions).process();
        } else {
            // En caso contrario.
            boolean saveInDB = (Boolean)selectedOptions.get("storeData");
            if(saveInDB){
                Logger.getLogger(ProcessPOI.class.getName()).log(Level.INFO, "Saving in DB.");
                TrackProcessDaoImpl tPDao = new TrackProcessDaoImpl();
                tPDao.updateTrackProcess(ProcessTrackState.DB_SAVING);
            }
            
            // Almacenado de la ruta.
            TrackDaoImpl tDao = new TrackDaoImpl();
            generatedTracks.forEach((eachTrack) -> {
                eachTrack.setTemporary(!saveInDB);
                tDao.createTrack(eachTrack);
            });
            
            // Creación del experimento.
            ExperimentTrackDaoImpl eRD = new ExperimentTrackDaoImpl();
            generatedTracks.forEach((t) -> {
                eRD.createExperimentTrack(lastExpId, t.getTrackId());
            });
        }
        
        Logger.getLogger(ProcessTrack.class.getName()).log(Level.INFO, "Proceso de búsqueda de rutas finalizado.");
    } // end method
    
    
    /**
    * Método validateDifferences, método que comprueba las diferencias obtenidas de las localizaciones 
    * recorridas.
    * @param valueOfMedian valor de la mediana con la que comparar.
    * @return isNecessary, true si hay diferencias apreciables entre los valores y la mediana.
    *                      false si no las hay.
    */
   private boolean validateDifferences(double valueOfMedian, ArrayList<Double> aDiferencias){

        boolean isNecessary = true;

        for(int i=0; i<aDiferencias.size(); i++){
             if(aDiferencias.get(i)>valueOfMedian){
                 isNecessary = true;
                 break;
             }else{
                 isNecessary = false;
             }
        }
        return isNecessary;
   } // end method
	
    /**
     * Método calculateMedian, permite calcular la mediana.
     * @return valueOfMedian, es el valor obtenido.
     */
    private double calculateMedian(ArrayList<Double> aux, ArrayList<Position> aPositions) {

        double valueOfMedian;

        if(aux.size()%2==0){
            double first = aux.get(aPositions.size()/2-1);
            double second = aux.get(aPositions.size()/2); 
            valueOfMedian = (first+second) / 2;
        }else{
            if(aux.size() == 1){
                valueOfMedian = aux.get(0);
            }else{
                valueOfMedian = aux.get((aPositions.size()/2)-1);
            }
        }
        aux.clear();
        valueOfMedian = valueOfMedian*(Double)selectedOptions.get("variationOfMedian");
        return valueOfMedian;
    } // end method
    
    /**
     * Método calculateDifferences, permite calcular la diferencia de tiempo
     * entre dos objetos de tipo Date.
     */
    private void calculateTimeDifferences(ArrayList<Position> aPositions, ArrayList<Double> aDiferencias){

        DateComparison dC = new DateComparison();

        for(int i=0; i<aPositions.size()-1; i++){
            aDiferencias.add(dC.calculateDifference(aPositions.get(i).getDate(), aPositions.get(i+1).getDate()) / 1000);
        }
    } // end method
    
    /**
     * Método setPositionsId permite asignar los identificadores de las posiciones a la ruta.
     * @param aPosition lista de posiciones.
     */
    private void setPositionsId(ArrayList<Position> aPosition){
        
        for(Position position : aPosition){
            position.setTrackId(trackCounter);
            position.setPositionId(positionCounter);
            positionCounter ++;
        }
        
    } // end method
    
} // end class
