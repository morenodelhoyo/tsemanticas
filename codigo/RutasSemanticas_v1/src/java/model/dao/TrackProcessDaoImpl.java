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
 * Clase TrackProcessDaoImpl, permite realizar comprobar el estado del hilo
 * de ejecución del proceso de rutas.
 * @author David Moreno del Hoyo
 * @version 1.0
 */
public class TrackProcessDaoImpl {
    
    /**
     * Conexión con la BD.
     */
    private final DBConnection dbConn;
    
    /**
     * Constructor de clase.
     */
    public TrackProcessDaoImpl(){
        dbConn = new DBConnection();
    }
        
    /**
     * Método updateTrackProcess, permite actualizar el estado del hilo de 
     * proceso de rutas.
     * @param state el estado.
     * @return true si se actualiza.
     *         false si no.
     */
    public boolean updateTrackProcess(int state) {
        
        Connection conn = dbConn.open();
        
        String query = "SELECT estado FROM tfm_proceso_ruta";
        
        String insert = "INSERT INTO tfm_proceso_ruta (estado) VALUES ("+state+");";
        
        String update = "UPDATE tfm_proceso_ruta"
                     + " SET estado = " + state
                     + " WHERE id_proceso_ruta = 1";
        
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
     * Método getTrackProcess, permite obtener el estado del proceso del hilo.
     * @return result, el estado del hilo.
     */
    public int getTrackProcess() {
        
        Connection conn = dbConn.open();
        Statement statement;
        ResultSet rs;
        int result = -1;
        
        String query = "SELECT estado as state"
                    + " FROM tfm_proceso_ruta";
        
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
