/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.algorithm;

import controller.servlet.utils.ProcessTrackState;
import model.dao.ExperimentDaoImpl;
import model.dao.ExperimentTrackDaoImpl;
import model.dao.PoiDaoImpl;
import model.dao.TrackDaoImpl;
import model.dao.TrackProcessDaoImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.data.Poi;
import model.data.Position;
import model.data.StopPoi;
import model.data.Track;

/**
 * Clase ProcessPOI, clase que permite obtener y procesar los POI de las
 * paradas detectadas en cada ruta.
 * @author David Moreno del Hoyo.
 */
public class ProcessPOI {
    
    /**
     * Mapa de rutas a procesar ordenadas por área.
     */
    private final ArrayList<Track> aTracks;
    
    /**
     * Opciones seleccionadas por el usuario.
     */
    private final Map<String, Object> selectedOptions;
    
    /**
     * Objeto de tipo PoiDaoImpl.
     */
    private final PoiDaoImpl pDI;
    
    /**
     * Constructor de clase.
     * @param aTracks
     *          Lista de rutas a procesar.
     * @param selectedOptions
     *          Opciones seleccionadas por el usuario.
     */
    public ProcessPOI(ArrayList<Track> aTracks, Map<String, Object> selectedOptions){
        this.aTracks = aTracks;
        this.selectedOptions = selectedOptions;
        pDI = new PoiDaoImpl();
    }
    
    /**
     * Método process, permite procesar cada una de las rutas.
     */
    public void process(){
        
        TrackProcessDaoImpl tPDao = new TrackProcessDaoImpl();
        tPDao.updateTrackProcess(ProcessTrackState.POI_PROCESSING);
        
        if(aTracks != null){
            for(Track eachTrack : aTracks){
                processStopTrack(eachTrack);
            }
        }
        // Toma de decisión después de procesar todos los POI.
        decide();
        
    } // end method
    
    
    /**
     * Método processStopTrack, permite procesar cada una de las rutas.
     * @param track
     *          ruta cuyas paradas serán procesadas.
     */
    private void processStopTrack(Track track){
        
        // Lista de posiciones de la ruta.
        ArrayList<Position> aPosition = track.getListOfPositions();
        
        // lista de puntos de interés.
        ArrayList<Poi> aPoi;
        
        // Lista de posiciones con parada.
        ArrayList<StopPoi> aStopPoi;
        
        // Objeto de tipo StopPoi para asignar a la posición.
        StopPoi st;
        
        // Procesado de todas las posiciones.
        for(int i=0; i<aPosition.size(); i++){
            aStopPoi = new ArrayList();
            // Comprobación para primera, la última posición y si es parada.
            if(i == 0 || i== aPosition.size()-1 || aPosition.get(i).isStop()){
                // Obtención de los PDIs cercanos.
                aPoi = new ArrayList(pDI.getNearestPois(aPosition.get(i), (Double)selectedOptions.get("poiRadius")));
                // Asignación de los PDIs a la posición.
                for(Poi poi : aPoi){
                    st = new StopPoi();
                    st.setIdPoi(poi.getId());
                    st.setIdPosition(aPosition.get(i).getPositionId());
                    st.setDistance(poi.getDistance());
                    aStopPoi.add(st);
                }
                
                if(aPoi.isEmpty() && (i == 0 || i == aPosition.size() -1)){
                    st = new StopPoi();
                    st.setIdPoi(new BigDecimal("-1"));
                    st.setIdPosition(aPosition.get(i).getPositionId());
                    st.setDistance(-1);
                    aStopPoi.add(st);
                }
                aPosition.get(i).setListOfStopPois(aStopPoi);
            }
        }
    } // end method

    
    /**
     * Método decide, permite tomar una decisión dependiendo
     * de las opciones que haya seleccionado el usuario.
     */
    private void decide(){

        // Obtención de los valores de las opciones del usuario.
        boolean saveInDB = (Boolean)selectedOptions.get("storeData");
        
        if(saveInDB){
            Logger.getLogger(ProcessPOI.class.getName()).log(Level.INFO, "Saving in DB.");
            TrackProcessDaoImpl tPDao = new TrackProcessDaoImpl();
            tPDao.updateTrackProcess(ProcessTrackState.DB_SAVING);
        }
        
        // Almacenado del experimento en la BD.
        TrackDaoImpl tDao = new TrackDaoImpl();
        for(Track eachTrack : aTracks){
            eachTrack.setTemporary(!saveInDB);
            tDao.createTrack(eachTrack);
        }
        
        // Creación del experimento en la BD.
        ExperimentDaoImpl eD = new ExperimentDaoImpl();
        double lastExpId = eD.getLastExperimentId();
        ExperimentTrackDaoImpl eRD = new ExperimentTrackDaoImpl();
        
        aTracks.forEach((eachTrack) -> {
            eRD.createExperimentTrack(lastExpId, eachTrack.getTrackId());
        });
        
        Logger.getLogger(ProcessPOI.class.getName()).log(Level.INFO, "Proceso de búsqueda de POIs finalizado.");

    } // end method      
    
} // end class
