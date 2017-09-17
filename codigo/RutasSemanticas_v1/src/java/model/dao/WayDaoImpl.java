/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import controller.connection.DBConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.data.Way;

/**
 * Clase WayDaoImpl, permite realizar operaciones CRUD sobre los caminos definidos
 * por los nodos o PDIs.
 * @author David Moreno del Hoyo
 * @version 1.0
 */
public class WayDaoImpl {
    
    /**
     * Objeto de tipo DBConnection pra establecer la conexión contra la BD.
     */
    private final DBConnection dbConn;
    
    /**
     * Constructor de clase.
     */
    public WayDaoImpl(){
        dbConn = new DBConnection();
    }
    
    /**
     * Método insertWay, permite insertar un camino en la BD.
     * @param way el camino a insertar.
     * @return true si se crea.
     *         false si no.
     */
    public boolean insertWay(Way way) {
        
        Connection conn = dbConn.open();
        way.getaNodes().forEach((nodeId) -> {
            String wayInsert = "INSERT INTO tfm_caminos (id, node, tags, id_region)"
                            + " VALUES (?,?,?,?);";        
            try {
                conn.setAutoCommit(false);
                PreparedStatement preparedStatement = conn.prepareStatement(wayInsert);
                preparedStatement.setDouble(1, way.getId());
                preparedStatement.setBigDecimal(2, nodeId);
                preparedStatement.setString(3, way.getTags());
                preparedStatement.setDouble(4, way.getIdRegion());
                preparedStatement.executeUpdate();
                conn.commit();
            } catch (SQLException sqle) {
                Logger.getLogger(WayDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
            }
        });
        
        dbConn.close(conn);
        return true;
    }
    
    /**
     * Método getNumberOfWaysByRegion, permite obtener el número de caminos
     * por cada región existente.
     * @param regionId el identificador de la región.
     * @return número de caminos.
     */
    public BigDecimal getNumberOfWaysByRegion(double regionId){
        
        Connection conn = dbConn.open();
        Statement statement;
        ResultSet rs;
        BigDecimal result = new BigDecimal("0");
        String query = "SELECT count(distinct(id)) as numberOfWays"
                    + " FROM tfm_caminos"
                    + " WHERE id_region = " + regionId + ";";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            conn.commit();
            while(rs.next()){
                result = rs.getBigDecimal("numberOfWays");
            }
        } catch (SQLException sqle) {
                Logger.getLogger(AreaDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        dbConn.close(conn);
        
        return result;
    } 
    
    
    
    public String getAmenityOfPoi(BigDecimal nodeId){
        
        Connection conn = dbConn.open();
        Statement statement;
        ResultSet rs;
        String result = "";
        String query = "SELECT tags"
                    + " FROM tfm_caminos"
                    + " WHERE node = " + nodeId + ""
                    + " limit 1;";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            conn.commit();
            while(rs.next()){
                result = rs.getString("tags");
            }
        } catch (SQLException sqle) {
                Logger.getLogger(AreaDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        dbConn.close(conn);
        
        return result;
    } 

    /**
     * Método deleteAllWays, permite eliminar todos los caminos del sistema.
     * @return true si se eliminan.
     *         false si no.
     */
    public boolean deleteAllWays() {
        
        Connection conn = dbConn.open();
        Statement statement;
        String query = "DELETE"
                    + " FROM tfm_caminos;";
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
