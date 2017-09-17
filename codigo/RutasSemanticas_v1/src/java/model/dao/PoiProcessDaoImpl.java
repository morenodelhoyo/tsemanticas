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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase PoiProcessDaoImpl, obtener el estado de el hilo de ejecución de PDIs.
 * @author David Moreno del Hoyo
 * @version 1.0
 */
public class PoiProcessDaoImpl {
    
    /**
     * Conexión contra la BD.
     */
    private final DBConnection dbConn;
    
    /**
     * Construcor de clase.
     */
    public PoiProcessDaoImpl(){
        dbConn = new DBConnection();
    }

    /**
     * Método updatePoiProcess, permite actualizar el estado de procesado
     * del hilo en la tabla.
     * @param state estado del hilo.
     * @return true si se actualiza.
     *         false si no.
     */
    public boolean updatePoiProcess(int state) {
               Connection conn = dbConn.open();
        
        String query = "SELECT estado FROM tfm_proceso_poi";
        
        String insert = "INSERT INTO tfm_proceso_poi (estado) VALUES ("+state+");";
        
        String update = "UPDATE tfm_proceso_poi"
                     + " SET estado = " + state
                     + " WHERE id_proceso_poi = 1";
        
        Statement statement;
        boolean updated = false;

        try {
            conn.setAutoCommit(false);
            
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            conn.commit();
            if(rs.next()){
                statement = conn.createStatement();
                statement.executeUpdate(update);
            }else{
                statement = conn.createStatement();
                statement.executeUpdate(insert);
            }
            
            conn.commit();
            updated = true;
        } catch (SQLException sqle) {
            Logger.getLogger(TrackProcessDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn);
        return updated;
    }

    /**
     * Método getPoiProcess, permite obtener el estado del hilo.
     * @return result, el estado.
     */
    public int getPoiProcess() {
        
        Connection conn = dbConn.open();
        Statement statement;
        ResultSet rs;
        int result = -1;
        
        String query = "SELECT estado as state"
                    + " FROM tfm_proceso_poi";
        
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            conn.commit();
            while(rs.next()){
                result = rs.getInt("state");
            }
        } catch (SQLException sqle) {
            Logger.getLogger(TrackProcessDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn); 
        return result;
    }
    
}
