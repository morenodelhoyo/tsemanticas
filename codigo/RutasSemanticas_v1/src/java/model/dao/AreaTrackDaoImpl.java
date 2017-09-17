/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import controller.connection.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.data.AreaTrack;

/**
 * Clase AreaTrackDaoImpl, permite realizar operaciones 
 * CRUD sobre las áreas generadas por las rutas.
 * @author David Moreno del Hoyo
 * @version 1.0
 */
public class AreaTrackDaoImpl {
    
    /**
     * Conexión contra la BD.
     */
    private final DBConnection dbConn;
    
    /**
     * Constructor de clase.
     */
    public AreaTrackDaoImpl(){
        dbConn = new DBConnection();
    }

    /**
     * Método createAreaTrack, permite crear el área circundante a la ruta.
     * @param areaTrack objeto de tipo AreaTrack.
     * @return true si se crea el área.
     *         false si no se crea.
     */
    public boolean createAreaTrack(AreaTrack areaTrack) {
        
        Connection conn = dbConn.open();
        Statement statement;
        String create = "INSERT INTO tfm_ruta_area"
                        + " (id_ruta,"
                         + " min_lat,"
                         + " min_long,"
                         + " max_lat,"
                         + " max_long)"
                         + " VALUES (" + areaTrack.getTrackId() + ", "
                                       + areaTrack.getMinLat() + ", "
                                       + areaTrack.getMinLong() + ", "
                                       + areaTrack.getMaxLat() + ", "
                                       + areaTrack.getMaxLong() +");";
        
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
    
}
