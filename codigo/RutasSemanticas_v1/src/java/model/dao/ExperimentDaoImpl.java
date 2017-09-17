/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import controller.connection.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.data.Experiment;
import model.data.UploadedFile;

/**
 * Clase ExperimentDaoImpl, permite realizar operaciones CRUD sobre las pruebas
 * o experimentos realizados.
 * @author David Moreno del Hoyo
 * @version 1.0
 */
public class ExperimentDaoImpl {
    
    /**
     * Conexión contra la BD.
     */
    private final DBConnection dbConn;
    
    /**
     * Constructor de clase.
     */
    public ExperimentDaoImpl(){
        dbConn = new DBConnection();
    }

    /**
     * Método createExperiment, permite crear una nueva prueba o experimento
     * sobre la Base de Datos.
     * @param experiment el experimento a crear.
     * @return result el identificador del experimento creado.
     */
    public double createExperiment(Experiment experiment) {
        
        updateOldExperiments();
        
        
        StopPoiDaoImpl sPD = new StopPoiDaoImpl();
        Connection conn = dbConn.open();
        double result = -1;
        Statement statement;
        String create = "INSERT INTO tfm_experimento"
                        + " (tipo_asignacion_area,"
                         + " buscar_paradas,"
                         + " distancia_parada,"
                         + " variacion_mediana,"
                         + " buscar_pois,"
                         + " radio_maximo,"
                         + " mult_radio,"
                         + " almacenado_db,"
                         + " fecha_experimento,"
                         + " nuevo,"
                         + " es_temporal,"
                         + " distancia_ruta)"
                         + " VALUES ("
                                    + experiment.getAsignatedAreaType() + ","
                                    + experiment.isFindStops() + ","
                                    + experiment.getStopDistance() + ","
                                    + experiment.getMedianVariation() + ","
                                    + experiment.isFindPois() + ","
                                    + experiment.getMaxRadius() + ","
                                    + experiment.getRadiusMultiplier() + ","
                                    + experiment.isStoreInDB() + ",'"
                                    + experiment.getExperimentDate() + "',"
                                    + "'true','"
                                    + experiment.isTemporary() + "',"
                                    + experiment.getTrackDistance()
                                + ");";
        
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            result = statement.executeUpdate(create, Statement.RETURN_GENERATED_KEYS);
            conn.commit();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                result = rs.getDouble(1);
                experiment.setIdExperiment(result);
                ExperimentFileDaoImpl eFD = new ExperimentFileDaoImpl();
                experiment.getAnalyzedFiles().forEach((uF) -> {
                    eFD.createExperimentFile(experiment.getIdExperiment(), uF.getId());
                });
            }
            conn.commit();
            
        } catch (SQLException sqle) {
            Logger.getLogger(PositionDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }

        dbConn.close(conn);
        return result;
    }

    /**
     * Método getExperiments, permite obtener los experimentos almacenados.
     * @return aExp la lista de experimentos.
     */
    public ArrayList<Experiment> getExperiments() {
        
        Connection conn = dbConn.open();
        Statement statement;
        ExperimentFileDaoImpl eFD = new ExperimentFileDaoImpl();
        ExperimentTrackDaoImpl eTD = new ExperimentTrackDaoImpl();
        
        ArrayList<Experiment> aExp = new ArrayList();
        Experiment exp;
        ResultSet rs;
        
        String query = "SELECT id_experimento as experiment_id,"
                           + " tipo_asignacion_area as asignation_area_type,"
                           + " buscar_paradas as find_stops,"
                           + " distancia_parada as stop_distance,"
                           + " variacion_mediana as median_variation,"
                           + " buscar_pois as find_pois,"
                           + " radio_maximo as max_radius,"
                           + " mult_radio as radius_multiplier,"
                           + " almacenado_db as stored_db,"
                           + " fecha_experimento as experiment_date,"
                           + " id_area_asignada,"
                           + " nombre as areaName,"
                           + " distancia_ruta as trackDistance"
                    + " FROM tfm_experimento"
                    + " INNER JOIN tfm_area"
                    + " ON tfm_experimento.id_area_asignada = tfm_area.id;";
        
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()){
                exp = new Experiment();
                exp.setIdExperiment(rs.getInt("experiment_id"));
                exp.setAsignatedAreaType(rs.getInt("asignation_area_type"));
                exp.setFindStops(rs.getBoolean("find_stops"));
                exp.setStopDistance(rs.getDouble("stop_distance"));
                exp.setMedianVariation(rs.getDouble("median_variation"));
                exp.setFindPois(rs.getBoolean("find_pois"));
                exp.setMaxRadius(rs.getDouble("max_radius"));
                exp.setRadiusMultiplier(rs.getDouble("radius_multiplier"));
                exp.setStoreInDB(rs.getBoolean("stored_db"));
                exp.setExperimentDate(rs.getDate("experiment_date"));
                exp.setAreaName(rs.getString("areaName"));
                exp.setTrackDistance(rs.getDouble("trackDistance"));
                ArrayList<UploadedFile> analyzedFiles = eFD.getExperimentFileNames(exp.getIdExperiment());
                exp.setAnalyzedFiles(analyzedFiles);
                ArrayList<Double> createdTracks = eTD.getTrackId(exp.getIdExperiment());
                exp.setTracksId(createdTracks);
                aExp.add(exp);
            }
            conn.commit();
        } catch (SQLException sqle) {
            Logger.getLogger(PositionDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        return aExp;
    }
    
    /**
     * Método updateExperiment, permite actualizar el experimento a medida
     * que la prueba avanza.
     * @param expId identificador
     * @param areaId identificador de área.
     * @return true si se actualiza.
     *         false si no.
     */
    public boolean updateExperiment(double expId, double areaId){
        
        Connection conn = dbConn.open();
        Statement statement;
        int result;
        String update = "UPDATE tfm_experimento"
                     + " SET id_area_asignada = " + areaId
                     + " WHERE id_experimento = " + expId + ";";
        
        boolean updated = false;
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            result = statement.executeUpdate(update);
            conn.commit();
            if(result == 1){
                updated = true;
            }
        } catch (SQLException sqle) {
            Logger.getLogger(PositionDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn);
        
        return updated;
        
    }

    /**
     * Método getLastExperimentId, permite obtener el último identificador
     * de experimentos realizados.
     * @return identificador obtenido. 
     */
    public double getLastExperimentId() {
        
        Connection conn = dbConn.open();
        ResultSet rs;
        Statement stmt;
        String query = "SELECT last_value as experimentId"
                    + " FROM tfm_experimento_id_experimento_seq";
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                return rs.getDouble("experimentId");
            }
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(TrackDaoImpl.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        dbConn.close(conn);
        
        return -1;
        
    }

    /**
     * Método deleteExperiment, permite borrar un único experimento.
     * @param expId identificador del mismo.
     * @return true si se elimina.
     *         false en caso contrario.
     */
    public boolean deleteExperiment(double expId) {
        
        Connection conn = dbConn.open();
        Statement stmt;
        String delete = "DELETE FROM tfm_experimento"
                     + " WHERE id_experimento = " + expId;
        
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.executeUpdate(delete);
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(PoiDaoImpl.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        dbConn.close(conn);
        return true;
        
    }

    /**
     * Método deleteAllExperiments, permite eliminar todos los experimentos.
     * @return true si se borran.
     *         false si no.
     */
    public boolean deleteAllExperiments() {
        
        Connection conn = dbConn.open();
        int result;
        Statement stmt;
        String delete = "DELETE FROM tfm_experimento";
        String sequence = "ALTER SEQUENCE tfm_experimento_id_experimento_seq RESTART WITH 1;";
        
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            result = stmt.executeUpdate(delete);
            if(result > 0){
                stmt.execute(sequence);
                conn.commit();
                return true;
            }
            
        } catch (SQLException e) {
            Logger.getLogger(PoiDaoImpl.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        dbConn.close(conn);
        return false;
    }

    /**
     * Método updateOldExperiments, permite actualizar los experimentos marcados
     * como nuevos cuando se insertan nuevos experimentos.
     * @return true si se actualizan.
     *         false si no.
     */
    public boolean updateOldExperiments() {
        
        Connection conn = dbConn.open();
        Statement statement;
        String update = "UPDATE tfm_experimento"
                     + " SET nuevo = 'false'"
                     + " WHERE nuevo = 'true';";

        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            statement.executeUpdate(update);
            conn.commit();
            return true;
        } catch (SQLException sqle) {
                Logger.getLogger(TrackDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn);
        return false;
    }

    /**
     * Método deleteNewExperiments, método que permite eliminar los experimentos
     * creados pero que han de ser eliminados por detenerse el hilo de proceso.
     * @return true si se borran.
     *         false si no.
     */
    public boolean deleteNewExperiments() {
        
        Connection conn = dbConn.open();
        Statement statement;
        String query = "DELETE"
                    + " FROM tfm_experimento"
                    + " WHERE nuevo = 'true';";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            conn.commit();
        } catch (SQLException sqle) {
            Logger.getLogger(TrackDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn); 
        return true;
    }

    
    /**
     * Método deleteTemporaryExperiments, permite eliminar las pruebas temporales que
     * se han creado y el usuario no desea almacenar.
     * @return true si se eliminan.
     *         false en caso contrario.
     */
    public boolean deleteTemporaryExperiments() {
        
        Connection conn = dbConn.open();
        Statement statement;
        String query = "DELETE"
                    + " FROM tfm_experimento"
                    + " WHERE es_temporal = 'true';";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            conn.commit();
        } catch (SQLException sqle) {
            Logger.getLogger(TrackDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn); 
        return true;
    }
    
}
