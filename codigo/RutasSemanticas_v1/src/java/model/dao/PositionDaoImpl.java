/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import controller.connection.DBConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.data.Poi;
import model.data.Position;
import model.data.StopPoi;

/**
 *
 * @author rs
 */
public class PositionDaoImpl {
    
    /**
     * 
     */
    private final DBConnection dbConn;
    
    /**
     * 
     */
    public PositionDaoImpl(){
        dbConn = new DBConnection();
    }
    
    public ArrayList<Position> getPositionsByTrackId(double trackId){
        
        Connection conn = dbConn.open();
        StopPoiDaoImpl sPD = new StopPoiDaoImpl();
        ArrayList<Position> aPosition = new ArrayList();
        PoiDaoImpl pDao = new PoiDaoImpl();
        Position position;
        ResultSet rs;
        Statement statement;
        
        String queryPositions = "SELECT id_ruta as trackId,"
                                    + " id_posicion as positionId,"
                                    + " latitud as latitude,"
                                    + " longitud as longitude,"
                                    + " date_time,"
                                    + " altitud as altitude, "
                                    + " es_parada as isStop"
                            + " FROM tfm_posicion where id_ruta = " + trackId
                            + " ORDER BY date_time ASC;";
        
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(queryPositions);
            conn.commit();
            while(rs.next()){
                position = new Position();
                position.setPositionId(rs.getInt("positionId"));
                position.setTrackId(trackId);
                position.setLatitude(rs.getDouble("latitude"));
                position.setLongitude(rs.getDouble("longitude"));
                position.setAltitude(rs.getDouble("altitude"));
                position.setDate(rs.getTimestamp("date_time"));
                position.setListOfStopPois(sPD.getStopPoiByPosition(position));
                ArrayList<Poi> aPoi = new ArrayList();
                for(StopPoi sP : position.getListOfStopPois()){
                    if(sP.getIdPoi().compareTo(new BigDecimal(-1)) != 0){
                        aPoi.add(pDao.getPoiById(sP.getIdPoi(), position));
                    }
                }
                position.setListOfPois(aPoi);
                position.setStop(rs.getBoolean("isStop"));
                aPosition.add(position);
            }
        } catch (SQLException sqle) {
                Logger.getLogger(PositionDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        } 
        dbConn.close(conn);
        
        return aPosition;
    }

    public double createPosition(Position position) {
        
        StopPoiDaoImpl sPD = new StopPoiDaoImpl();
        Connection conn = dbConn.open();
        int result = -1;
        Statement statement;
        String positionInsert = "INSERT INTO tfm_posicion (id_ruta, latitud, longitud, date_time, altitud, es_parada)"
                             + " VALUES (" + position.getTrackId() + ", "
                                           + position.getLatitude() + ", "
                                           + position.getLongitude() + ", '"
                                           + position.getDate() + "', "
                                           + position.getAltitude() + ", "
                                           + position.isStop() + ");";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            result = statement.executeUpdate(positionInsert, Statement.RETURN_GENERATED_KEYS);
            conn.commit();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                result = rs.getInt(1);
                position.setPositionId(result);
                if(position.getListOfStopPois() != null && !position.getListOfStopPois().isEmpty()){
                    position.getListOfStopPois().forEach((sP) -> {
                        sPD.createStopPoi(sP);
                    });
                }
            }
            
            
        } catch (SQLException sqle) {
            Logger.getLogger(PositionDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }

        dbConn.close(conn);
        return result;
    }

    public boolean updateTheGeom(double trackId) {
        
        Connection conn = dbConn.open();
        Statement statement;
        int result;
        String update = "UPDATE tfm_posicion"
                     + " SET the_geom = ST_GeomFromText('POINT(' || longitud || ' ' || latitud || ')',4326) where id_ruta = " + trackId ;
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
    
    public double getMaxPositionId(){
        
        Connection conn = dbConn.open();
        ResultSet rs;
        Statement stmt;
        String query = "SELECT last_value as position_id"
                    + " FROM tfm_posicion_id_posicion_seq";
        double max = 0;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                max = (rs.getDouble("position_id") == 1) ? 1 : rs.getDouble("position_id")+1;
            }
        } catch (SQLException e) {
            Logger.getLogger(PositionDaoImpl.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        dbConn.close(conn);
        
        return max;
    }

}
