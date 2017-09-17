/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import controller.algorithm.DataCleaning;
import controller.algorithm.ProcessTrack;
import controller.servlet.utils.Identifiers;
import controller.servlet.utils.ProcessTrackState;
import controller.servlet.NotifyingThread;
import java.io.BufferedReader;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import model.data.Position;
import model.data.Track;

/**
 * Clase ReadFromFileImpl, esta clase permite leer uno o varios fichero/s en 
 * ormato CSV que contenga una o varias rutas realizada por uno o varios
 * usuario/s.
 * @author David Moreno del Hoyo
 */
public class ReadFromFileImpl extends NotifyingThread {
    
    /**
     * Lista de rutas leídas desde los ficheros procesados.
     */
    private ArrayList<Track> aTracksFromFiles;
    
    /**
     * Ruta hacia la carpeta donde se encuentran los ficheros a procesar.
     */
    private final String folder;
    
    /**
     * Identificadores máximos de la Base de datos.
     */
    private final Identifiers identifiers;
    
    /**
     * Rutas seleccionadas.
     */
    private final String[] selectedFiles;
    
    /**
     * Mapa de las opciones elegidas por el usuario para procesar los ficheros.
     */
    private final Map<String, Object> selectedOptions;
    
    /**
     * Ruta procesada desde el fichero.
     */
    private Track track;
    
    /**
     * Objeto de tipo DataCleaning.
     */
    private DataCleaning dC;

    
    /**
     * Constructor de la clase ReadFromFile.
     * @param folder
     *          la carpeta donde se encuentran los ficheros.
     * @param identifiers
     *          los identificadores máximos de las tablas de la Base de Datos.
     * @param selectedFiles
     *          la ruta hacia la carpeta o el fichero seleccionado.
     * @param selectedOptions
     *          opciones que ha seleccionado el usuario para procesar los ficheros.
     */
    public ReadFromFileImpl(String folder, Identifiers identifiers, String[] selectedFiles, Map<String, Object> selectedOptions){
        this.folder = folder;
        this.selectedFiles = selectedFiles;
        this.identifiers = identifiers;
        this.selectedOptions = selectedOptions;
    }
    
    /**
     * Método process, permite procesar la ruta seleccionada. Se buscarán todos los
     * ficheros que la carpeta incluya y se compararán con los seleccionados para
     * procesar solo los indicados.
     */
    public void process(){
        
        TrackProcessDaoImpl tPDao = new TrackProcessDaoImpl();
        tPDao.updateTrackProcess(ProcessTrackState.FILE_READING);
        
        // Contador de ficheros
        aTracksFromFiles = new ArrayList();
        dC = new DataCleaning();
        int fileCounter = 1;
        // Objeto de tipo File con el path inicial.
        File f = new File(folder);
        if(f.isDirectory()){
            File locationFiles[] = f.listFiles();
            for(File file : locationFiles){
                for(String fileName : selectedFiles){
                    if(file.getName().equals(fileName)){
                        Logger.getLogger(ReadFromFileImpl.class.getName()).log(Level.INFO, "Processing file [{0}], number [{1}] of [{2}]", new Object[]{fileName, fileCounter, selectedFiles.length});
                        read(file, -1);
                        fileCounter ++;
                    }
                }
            }
            Logger.getLogger(ReadFromFileImpl.class.getName()).log(Level.INFO, "Files processed: [{0}]. Tracks read: [{1}]", new Object[]{--fileCounter, aTracksFromFiles.size()});
            updateFilesState();
            decide();
        } else {
            Logger.getLogger(ReadFromFileImpl.class.getName()).log(Level.SEVERE, "Error retrieving files.");
        }
    } // end method
    
    /**
     * Método read, permite leer un fichero concreto y transformarlo a una ruta.
     * @param file
     *          el fichero obtenido desde el path.
     * @param userId
     *          el identificador de usuario, en caso de no tenerlo, será -1.
     */
    private void read(File file, int userId){
        
        // Asignación del identificador de usuario a la ruta.
        track = new Track();        
        track.setUserid(userId);
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            ArrayList<Position> aPosition = new ArrayList();
            // Comienzo de la lectura del fichero.
            String line = br.readLine();
            while (line != null) {
                // Separación de los datos leídos por cada línea.
                String[] lineRead = line.split(",");
                // Instancia de un nuevo objeto de tipo Position y asignación de valores.
                Position position = new Position();
                position.setLatitude(Double.parseDouble(lineRead[2]));
                position.setLongitude(Double.parseDouble(lineRead[3]));
                position.setAltitude(Double.parseDouble(lineRead[5]));
                position.setAreaId(-1);
                try {
                    position.setDate(new SimpleDateFormat("yyyy-MM-dd H:m:s").parse(lineRead[7]+" "+lineRead[8]));
                } catch (ParseException e) {
                    Logger.getLogger(ReadFromFileImpl.class.getName()).log(Level.SEVERE, e.getMessage());
                }
                
                // Se añade la coordenada si sus datos de localización son correctos.
                if(dC.isAValidPosition(position)){
                    aPosition.add(position);
                }

                line = br.readLine();
            }
            // Asignación de las distintas posiciones geográficas a la ruta.
            track.setListOfPositions(aPosition);
        } catch (FileNotFoundException fnfe) {
                Logger.getLogger(ReadFromFileImpl.class.getName()).log(Level.SEVERE,fnfe.getMessage());
        } catch (IOException ioe) {
                Logger.getLogger(ReadFromFileImpl.class.getName()).log(Level.SEVERE,ioe.getMessage());
        }
        
        // Añadir la ruta al array de rutas.
        aTracksFromFiles.add(track);
        Logger.getLogger(ReadFromFileImpl.class.getName()).log(Level.INFO, "File: {0} processed.", file.getName());
        
    } // end method
    
    
    private void updateFilesState(){
        
        UploadedFileDaoImpl uFD = new UploadedFileDaoImpl();
        
        for(String s : selectedFiles){
            uFD.updateFile(s);
        }
        
    }
    
    /**
     * Método decide, permite tomar una decisión dependiendo
     * de las opciones que haya seleccionado el usuario.
     */
    private void decide(){
        
        // En este caso se procesarán las rutas leídas. Después se tomarán en cuenta
        // las opciones seleccionadas por el usuario.
        new ProcessTrack(aTracksFromFiles, identifiers, selectedOptions).process();
        
    } // end method

    @Override
    public void doRun() {
        
        // Procesado de todos los ficheros seleccionados por el usuario.
        process();
        
        Logger.getLogger(ReadFromFileImpl.class.getName()).log(Level.INFO, "Proceso de lectura de ficheros finalizado.");
        
        TrackProcessDaoImpl tPDao = new TrackProcessDaoImpl();
        tPDao.updateTrackProcess(ProcessTrackState.PROCESS_ENDED);
        
    }
    
} // end class
