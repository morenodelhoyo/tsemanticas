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

/**
 * Clase ExperimentTrackDaoImpl, permite realizar operaciones CRUD sobre las rutas
 * realizadas al lanzar un experimento.
 * @author David Moreno del Hoyo
 * @version 1.0
 */
public class ExperimentTrackDaoImpl {

    /**
     * Objeto de tipo DBConnection.
     */
    private final DBConnection dbConn;
    
    /**
     * Constructor de clase.
     */
    public ExperimentTrackDaoImpl(){
        dbConn= new DBConnection();
    }
    
    /**
     * Método createExperimentTrack, permite crear una entrada en la tabla con
     * las rutas analizadas en la prueba.
     * @param experimentId identificador de la prueba.
     * @param trackId identificadaor de la ruta.
     * @return true si se crea.
     *         false si no.
     */
    public boolean createExperimentTrack(double experimentId, double trackId) {
        
        Connection conn = dbConn.open();
        Statement statement;
        String create = "INSERT INTO tfm_experimento_ruta"
                        + " (id_experimento,"
                        + " id_ruta)"
                        + " VALUES ("
                                    + experimentId + ","
                                    + trackId
                                + ");";
        
        boolean created = false;
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            statement.executeUpdate(create);            
            conn.commit();
            created = true;
        } catch (SQLException sqle) {
            Logger.getLogger(PositionDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn);
        
        return created;
    }

    /**
     * Método getTrackId, permite obtener los identificadores de rutas realizadas
     * en un experimento o prueba.
     * @param experimentId el identificador del experimento.
     * @return aTracksId lista de pruebas.
     */
    public ArrayList<Double> getTrackId(double experimentId) {
        
        ArrayList<Double> aTracksId = new ArrayList();
        Connection conn = dbConn.open();
        ResultSet rs;
        Statement stmt;
        
        String getPOIsQuery = "SELECT id_ruta as trackId"
                           + " FROM tfm_experimento_ruta"
                           + " WHERE id_experimento = " + experimentId +";";
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(getPOIsQuery);
            while(rs.next()){
                aTracksId.add(rs.getDouble("trackId"));
            }
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(PoiDaoImpl.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        dbConn.close(conn);
        
        return aTracksId;
    }

}
