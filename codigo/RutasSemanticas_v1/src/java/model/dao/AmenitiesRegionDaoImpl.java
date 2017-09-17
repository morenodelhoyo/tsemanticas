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
import model.data.Region;

/**
 * Clase AmenitiesRegionDaoImpl, permite realizar operaciones 
 * CRUD sobre los Puntos De Interés.
 * @author David Moreno del Hoyo
 * @version 1.0
 */
public class AmenitiesRegionDaoImpl {

    /**
     * Objeto de conexión con la Base de Datos.
     */
    private final DBConnection dbConn;
    
    /**
     * Construcor de la clase.
     */
    public AmenitiesRegionDaoImpl(){
        dbConn = new DBConnection();
    }

    /**
     * Método create, permite insertar un nueva nueva región en el sistema.
     * @param name nombre de la región.
     * @return result valor del índice primario de la tabla.
     */
    public double create(String name) {
        
        Statement statement;
        double result = -1;
        Connection conn = dbConn.open();
        String insert = "INSERT INTO tfm_region_pois (nombre)"
                     + " VALUES ('" + name + "');";
        
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            result = statement.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);
            conn.commit();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                result = rs.getInt(1);
            }
        } catch (SQLException sqle) {
            Logger.getLogger(AmenitiesRegionDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        dbConn.close(conn);
        return result;
        
    }

    /**
     * Método delete, permite eliminar una región dentro del sistema.
     * @param id identificador de la región que se elimina.
     * @return true si el borrado es correcto.
     *         false si el borrado es incorrecto.
     */
    public boolean delete(double id) {
        
        Connection conn = dbConn.open();
        Statement statement;
        String delete = "DELETE FROM tfm_region_pois"
                     + " WHERE id_region = " + id + " ;";
        int result = 0;
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            result = statement.executeUpdate(delete);
            conn.commit();
        } catch (SQLException sqle) {
            Logger.getLogger(AmenitiesRegionDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn);
        return result>0;
    }

    /**
     * Método getRegion, permite obtener una región completa desde la Base de Datos.
     * @return aRegios, array con las regiones del sistema.
     */
    public ArrayList<Region> getRegions() {
        
        PoiDaoImpl pDao = new PoiDaoImpl();
        WayDaoImpl wDao = new WayDaoImpl();
        ArrayList<Region> aRegions = new ArrayList();
        Region r;
        Statement statement;
        ResultSet rs;
        Connection conn = dbConn.open();
        
        String select = "SELECT id_region as regionId,"
                            + " nombre as name"
                     + " FROM tfm_region_pois;";
        
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(select);
            conn.commit();
            while(rs.next()){
                r = new Region();                
                r.setRegionId(rs.getInt("regionId"));
                r.setName(rs.getString("name"));
                r.setNumberOfNodes(pDao.getNumberOfPoisByRegion(r.getRegionId()));
                r.setNumberOfWays(wDao.getNumberOfWaysByRegion(r.getRegionId()));
                aRegions.add(r);
            }
        } catch (SQLException sqle) {
            Logger.getLogger(AmenitiesRegionDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        dbConn.close(conn);
        
        return aRegions;
    }

    /**
     * Método deleteAll, permite eliminar todas las regiones disponibles.
     * @return true si el borrado es correcto.
     *         false si el borrado es incorrecto.
     */
    public boolean deleteAll() {
        
        Connection conn = dbConn.open();
        Statement statement;
        int result = 0;
        String delete = "DELETE FROM tfm_region_pois;";
        String sequence = "ALTER SEQUENCE tfm_region_amenities_id_region_seq RESTART WITH 1;";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            result = statement.executeUpdate(delete);
            if(result > 0){
                statement.execute(sequence);
                conn.commit();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(AmenitiesRegionDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn);
        return result>0;
    }

}
