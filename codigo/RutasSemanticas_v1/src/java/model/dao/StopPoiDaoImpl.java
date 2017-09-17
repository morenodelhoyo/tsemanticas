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
import model.data.Position;
import model.data.StopPoi;

/**
 * Clase StopPoiDaoImpl, permite realizar operaciones CRUD sobre la tabla auxiliar
 * que relaciona posiciones con PDIs.
 * @author David Moreno del Hoyo
 * @version 1.0
 */
public class StopPoiDaoImpl {
    
    /**
     * 
     */
    private final DBConnection dbConn;
    
    /**
     * 
     */
    public StopPoiDaoImpl(){
        dbConn = new DBConnection();
    }
    
    /**
     * Método createStopPoi, permite crear una entrada en la tabla.
     * @param stopPoi el StopPoi a crear.
     * @return true si se crea.
     *         false si no.
     */
    public boolean createStopPoi(StopPoi stopPoi) {
        
        Connection conn = dbConn.open();
        Statement statement;
        String poiInsert = "INSERT INTO tfm_posicion_poi (id_posicion, id_nodo, distancia)"
                        + " VALUES (" + stopPoi.getIdPosition() + ", " + stopPoi.getIdPoi() + ", " + stopPoi.getDistance() + ");";
        
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            statement.executeUpdate(poiInsert);
            conn.commit();
        } catch (SQLException sqle) {
            Logger.getLogger(TrackDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn);
        return true;
    }

    /**
     * Método getStopPoiByPosition, permite obtener los PDIS ligados
     * a la posición pasada.
     * @param position la posición sobre la que buscar los PDIs.
     * @return aStopPoilista de StopPois encontrada.
     */
    public ArrayList<StopPoi> getStopPoiByPosition(Position position) {
        
        Connection conn = dbConn.open();
        StopPoi sP;
        ArrayList<StopPoi> aStopPoi = new ArrayList();
        ResultSet rs;
        Statement statement;
        String getStopPoi = "SELECT id_posicion_poi as positionPoiId,"
                                + " id_posicion as positionId,"
                                + " id_nodo as poiId,"
                                + " distancia as distance"
                         + " FROM tfm_posicion_poi"
                         + " WHERE id_posicion = " + position.getPositionId() + ";";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(getStopPoi);
            conn.commit();
            while(rs.next()){
                sP = new StopPoi();
                sP.setIdStopPoi(rs.getDouble("positionPoiId"));
                sP.setIdPosition(rs.getDouble("positionId"));
                sP.setIdPoi(rs.getBigDecimal("poiId"));
                sP.setDistance(rs.getDouble("distance"));
                aStopPoi.add(sP);
            }
            position.setListOfStopPois(aStopPoi);
        } catch (SQLException sqle) {
                Logger.getLogger(PositionDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        } 
        dbConn.close(conn);
        
        return aStopPoi;
    }

    /**
     * Método deleteAllStopPoi, permite borrar todos los datos que relacionan
     * poisiciones con PDIs.
     * @return true si se borran-
     *         false si no.
     */
    public boolean deleteAllStopPoi() {
        
        Connection conn = dbConn.open();
        Statement statement;
        int result;
        String query = "DELETE"
                    + " FROM tfm_posicion_poi;";
        String sequence = "ALTER SEQUENCE tfm_posicion_poi_id_posicion_poi_seq RESTART WITH 1;";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            result = statement.executeUpdate(query);
            if(result > 0){
                statement.executeUpdate(sequence);
                conn.commit();
            }
            
        } catch (SQLException sqle) {
            Logger.getLogger(TrackDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn); 
        return true;
    }

}
