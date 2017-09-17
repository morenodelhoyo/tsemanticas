/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.algorithm;

import controller.servlet.utils.ProcessTrackState;
import model.dao.ExperimentDaoImpl;
import model.dao.ExperimentTrackDaoImpl;
import model.dao.TrackDaoImpl;
import model.dao.TrackProcessDaoImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.data.Position;
import model.data.Track;


/**
 * Clase Processtop, permite procesar las paradas de una o varias rutas. En esta
 * clase se procesará cada ruta y se buscarán las posibles paradas incluidas en la
 * cada una. Al final, se asignará la lista de paradas a la ruta. 
 * @author David Moreno del Hoyo
 * @version 1.0
 */
public class ProcessStop {
    
    /**
     * Lista de rutas sobre las que buscar paradas ordenadas por área.
     */
    private final ArrayList<Track> aTracks;
    
    /**
     * Opciones seleccionadas por el usuario.
     */
    private final Map<String, Object> selectedOptions;
    
    /**
     * Constructor de clase.
     * @param aTracks
     *          Lista de rutas a procesar ordenadas por área.
     * @param selectedOptions
     *          Mapa de las opciones seleccionadas por el usuario.
     */
    public ProcessStop(ArrayList<Track> aTracks, Map<String, Object> selectedOptions){
        this.aTracks = aTracks;
        this.selectedOptions = selectedOptions;
    }    
    
    /**
     * Método process, permite procesar la lista de rutas.
     */
    public void process(){
        
        // Actualización del estado.
        TrackProcessDaoImpl tPDao = new TrackProcessDaoImpl();
        tPDao.updateTrackProcess(ProcessTrackState.STOP_PROCESSING);
        
        if(aTracks != null){
            aTracks.forEach((eachTrack) -> {
                processStopTrack(eachTrack);
            });
        }
        // Toma de decisión una vez finalizado el proceso.
        decide();
        
    } // end method
    
    /**
     * Método processStopTrack, permite procesar las paradas de cada ruta.
     * @param eachTrack
     *              La ruta a procesar.
     */
    private void processStopTrack(Track eachTrack){
        
        /**
         * Lista de diferencias temporales.
         */
        ArrayList<Double> aDiferencias;
        
        /**
         * Lista auxiliar para las diferencias temporales y cálculo de mediana.
         */
        ArrayList<Double> aux;
        
        /**
         * Lista de posiciones obtenidas de la ruta.
         */
        ArrayList<Position> aPositions = eachTrack.getListOfPositions();
        
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

        double maxStopDistance = (Double)selectedOptions.get("maxStopDistance");
        
        // Posiciones de la ruta que forman parte de una parada.
        int[] pQSonParada;
        
        if(aPositions.size()>2){
            pQSonParada = new int[3];
            // Recorrer las posiciones de la ruta.
            aDiferencias = new ArrayList();
            // Calcular la diferencia de tiempos.
            calculateTimeDifferences(aPositions, aDiferencias);

            /* En caso de que existan tres posiciones (dos valores de diferencias):
             * Necesario ya que la mediana siempre será válida (no habrá un valor tan
             * superior como el necesario a la mediana calculada y se tomaría
             * como una misma ruta).
             */
            if(aDiferencias.size() == 2 && valueOfMedian != 0){
                pQSonParada = new int[3];
                double distance_ab = coordinatesComparison.calculateDistance(aPositions.get(0), aPositions.get(1));
                double distance_bc = coordinatesComparison.calculateDistance(aPositions.get(1), aPositions.get(2));
                // Se genera una parada en la primera mitad de la ruta.
                if((aDiferencias.get(0) > valueOfMedian && distance_ab <= maxStopDistance) && (aDiferencias.get(1) <= valueOfMedian && distance_bc <= maxStopDistance) ||
                   (aDiferencias.get(0) > valueOfMedian && distance_ab <= maxStopDistance) && (aDiferencias.get(1) <= valueOfMedian && distance_bc > maxStopDistance)){
                    pQSonParada[0] = 1;
                    pQSonParada[1] = 1;
                    pQSonParada[2] = 0;
                    processPositionsOfTrack(eachTrack, pQSonParada);
                }
                // Se genera una parada en la segunda mitad de la ruta.
                if((aDiferencias.get(0) < valueOfMedian && distance_ab <= maxStopDistance) && (aDiferencias.get(1) >= valueOfMedian && distance_bc <= maxStopDistance) ||
                   (aDiferencias.get(0) <= valueOfMedian && distance_ab > maxStopDistance) && (aDiferencias.get(1) >= valueOfMedian && distance_bc <= maxStopDistance)){
                    pQSonParada[0] = 0;
                    pQSonParada[1] = 1;
                    pQSonParada[2] = 1;
                    processPositionsOfTrack(eachTrack, pQSonParada);
                }
                // Los tiempos son mayores pero ambas distancias menores, son dos paradas en la misma ruta.
                if((aDiferencias.get(0) > valueOfMedian && coordinatesComparison.calculateDistance(aPositions.get(0), aPositions.get(1)) <= maxStopDistance) &&
                   (aDiferencias.get(1) > valueOfMedian && coordinatesComparison.calculateDistance(aPositions.get(1), aPositions.get(2)) <= maxStopDistance)){
                    pQSonParada[0] = 1;
                    pQSonParada[1] = 1;
                    pQSonParada[2] = 1;
                   processPositionsOfTrack(eachTrack, pQSonParada);
                }
                // La ruta no tiene paradas.
                processPositionsOfTrack(eachTrack, pQSonParada);
            } else if(aDiferencias.size() == 2 && valueOfMedian == 0) {
                /* En el caso de no contar con una mediana previa se considerará solamente
                 * la diferencia de espacio entre las posiciones.
                 */
                double distance_ab = coordinatesComparison.calculateDistance(aPositions.get(0), aPositions.get(1));
                double distance_bc = coordinatesComparison.calculateDistance(aPositions.get(1), aPositions.get(2));
                // La ruta es única sin paradas.
                if(distance_ab <= maxStopDistance && distance_bc <= maxStopDistance){
                    pQSonParada = new int[3];
                    processPositionsOfTrack(eachTrack, pQSonParada);
                }
            }

            aux = new ArrayList(aDiferencias);
            Collections.sort(aux);
            // Calcular la mediana
            valueOfMedian = calculateMedian(aux, aPositions);            
            
            // Comprobar si las diferencias de tiempos obtenidas son mayores a la mediana.
            isNecessary = validateDifferences(valueOfMedian, aDiferencias);
            
            // En caso de que haya valores diferentes.
            if(isNecessary){
                pQSonParada = new int[aPositions.size()];
                for(int i=0; i<aPositions.size()-1;i++){
                    diferencia = dC.calculateDifference(aPositions.get(i).getDate(), aPositions.get(i+1).getDate())/1000;
                    if(diferencia > valueOfMedian){
                        meters = coordinatesComparison.calculateDistance(aPositions.get(i), aPositions.get(i+1));                        
                        if(meters < maxStopDistance){
                            pQSonParada[i] = 1;
                            pQSonParada[i+1] = 1;
                        }
                    }
                }
            }else{
                // Si todos los tiempos son similares al de la mediana, no hay paradas.
                pQSonParada = new int[aPositions.size()];
            }
            processPositionsOfTrack(eachTrack, pQSonParada);
        }else{
            // Si la ruta tiene dos posiciones, no hay parada intermedia.
            pQSonParada = new int[2];
            processPositionsOfTrack(eachTrack, pQSonParada);
        }
        
    } // end method
    
    /**
     * Método processPositionsOfTrack, este método permite generar las
     * paradas a partir de los resultados obtenidos en el método processStopTrack.
     * @see processStopTrack.
     * @param track
     *          ruta a cuyas posiciones se van a procesar.
     * @param stops 
     *          vector de paradas.
     */
    private void processPositionsOfTrack(Track track, int[] stops){
        
        // Lista de posiciones de la ruta.
        ArrayList<Position> aPosition = track.getListOfPositions();
        
        for(int i=0; i<stops.length; i++){
            if(stops[i] == 1){
                aPosition.get(i).setStop(true);
            }
        }
        
    } // end method.
    
    /**
     * Méotdo decide, permite tomar una decisión dependiendo
     * de las opciones que haya seleccionado el usuario.
     */
    private void decide(){
        
        // Si se desea buscar PDIs
        if(selectedOptions.get("findPois").equals(true)){
            Logger.getLogger(ProcessStop.class.getName()).log(Level.INFO, "Processing POIs.");
            new ProcessPOI(aTracks, selectedOptions).process();
        } else {
            // En caso contrario
            boolean saveInDB = (Boolean)selectedOptions.get("storeData");
            if(saveInDB){
                Logger.getLogger(ProcessPOI.class.getName()).log(Level.INFO, "Saving in DB.");
                TrackProcessDaoImpl tPDao = new TrackProcessDaoImpl();
                tPDao.updateTrackProcess(ProcessTrackState.DB_SAVING);
            }
            
            // Creación de la ruta en la BD.
            TrackDaoImpl tDao = new TrackDaoImpl();
            aTracks.forEach((eachTrack) -> {
                eachTrack.setTemporary(!saveInDB);
                tDao.createTrack(eachTrack);
            });
            
            // Creación del experimento en la BD.
            ExperimentDaoImpl eD = new ExperimentDaoImpl();
            double lastExpId = eD.getLastExperimentId();
            ExperimentTrackDaoImpl eRD = new ExperimentTrackDaoImpl();
            aTracks.forEach((t) -> {
                eRD.createExperimentTrack(lastExpId, t.getTrackId());
            });
        }
        
        Logger.getLogger(ProcessStop.class.getName()).log(Level.INFO, "Proceso de búsqueda de paradas finalizado.");
        
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
        return valueOfMedian*(Double)selectedOptions.get("variationOfMedian");
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
    
} // end class
