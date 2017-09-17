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
import model.data.Area;

/**
 * Clase AreaDaoImpl, permite realizar operaciones 
 * CRUD sobre las áreas.
 * @author David Moreno del Hoyo
 * @version 1.0
 */
public class AreaDaoImpl {
    
    /**
     * Conexión con la BD.
     */
    private final DBConnection dbConn;
    
    /**
     * Constructor de la clase.
     */
    public AreaDaoImpl(){
        dbConn = new DBConnection();
    }
    
    /**
     * Método getAreas, permite obtener todas las áreas almacenadas en la
     * Base de Datos del sistema.
     * @return aAreas áreas obtenidas.
     */
    public ArrayList<Area> getAreas(){
        
        Connection conn = dbConn.open();
        Area area;
        ArrayList<Area> aAreas = new ArrayList();
        Statement statement;
        ResultSet rs;
        String query = "SELECT tfm_area.id,"
                    + " nombre as name,"
                    + " descripcion as description,"
                    + " min_lat,"
                    + " max_lat,"
                    + " min_long,"
                    + " max_long,"
                    + " activa,"
                    + " tfm_area.fecha_modificacion as modificationDate,"
                    + " tfm_area.fecha_creacion as creationDate,"
                    + " count(tfm_ruta.id_ruta) as num_rutas"
                    + " FROM tfm_area LEFT JOIN tfm_ruta on tfm_area.id = tfm_ruta.id_area"
                    + " GROUP BY tfm_area.id"
                    + " ORDER BY num_rutas desc";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            conn.commit();
            while(rs.next()){
                area = new Area();
                area.setId(rs.getInt("id"));
                area.setName(rs.getString("name"));
                area.setDescription(rs.getString("description"));
                area.setMinLat(rs.getDouble("min_lat"));
                area.setMinLong(rs.getDouble("min_long"));
                area.setMaxLat(rs.getDouble("max_lat"));
                area.setMaxLong(rs.getDouble("max_long"));
                area.setActiva(rs.getBoolean("activa"));
                area.setFechaCreacion(rs.getDate("creationDate"));
                area.setFechaModificacion(rs.getDate("modificationDate"));
                area.setNumTracks(rs.getDouble("num_rutas"));
                aAreas.add(area);
            }
        } catch (SQLException sqle) {
                Logger.getLogger(AreaDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        dbConn.close(conn);
        return aAreas;
    }
    
    /**
     * Método getAreasWithTracks, permite obtener las áreas que contienen
     * rutas asignadas.
     * @return aAreas áreas obtenidas.
     */
    public ArrayList<Area> getAreasWithTracks(){
        
        Connection conn = dbConn.open();
        Area area;
        ArrayList<Area> aAreas = new ArrayList();
        Statement statement;
        ResultSet rs;
        String query = "SELECT tfm_area.id,"
                    + " nombre as name,"
                    + " descripcion as description,"
                    + " min_lat,"
                    + " max_lat,"
                    + " min_long,"
                    + " max_long,"
                    + " activa,"
                    + " tfm_area.fecha_modificacion as modificationDate,"               
                    + " tfm_area.fecha_creacion as creationDate,"
                    + " count(tfm_ruta.id_ruta) as num_rutas"
                    + " FROM tfm_area INNER JOIN tfm_ruta on tfm_area.id = tfm_ruta.id_area"
                    + " GROUP BY tfm_area.id"
                    + " ORDER BY num_rutas desc";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            conn.commit();
            while(rs.next()){
                area = new Area();
                area.setId(rs.getInt("id"));
                area.setName(rs.getString("name"));
                area.setDescription(rs.getString("description"));
                area.setMinLat(rs.getDouble("min_lat"));
                area.setMinLong(rs.getDouble("min_long"));
                area.setMaxLat(rs.getDouble("max_lat"));
                area.setMaxLong(rs.getDouble("max_long"));
                area.setActiva(rs.getBoolean("activa"));
                area.setFechaCreacion(rs.getDate("creationDate"));
                area.setFechaModificacion(rs.getDate("modificationDate"));
                area.setNumTracks(rs.getDouble("num_rutas"));
                aAreas.add(area);
            }
        } catch (SQLException sqle) {
                Logger.getLogger(AreaDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        dbConn.close(conn);
        return aAreas;
    }

    /**
     * Método getAreaById, permite obtener las áreas mediante su identificador.
     * @param areaId identificador de área.
     * @return area la área seleccionada.
     */
    public Area getAreaById(int areaId) {
        
        Connection conn = dbConn.open();
        Area area = new Area();
        Statement statement;
        ResultSet rs;
        String query = "SELECT id,"
                    + " nombre as name,"
                    + " descripcion as description,"
                    + " min_lat,"
                    + " max_lat,"
                    + " min_long,"
                    + " max_long,"
                    + " activa,"
                    + " tfm_area.fecha_modificacion as modificationDate,"
                    + " tfm_area.fecha_creacion as creationDate"
                    + " FROM tfm_area"
                    + " WHERE id = " + areaId;
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            conn.commit();
            if(rs.next()){
                area.setId(rs.getInt("id"));
                area.setName(rs.getString("name"));
                area.setDescription(rs.getString("description"));
                area.setMinLat(rs.getDouble("min_lat"));
                area.setMinLong(rs.getDouble("min_long"));
                area.setMaxLat(rs.getDouble("max_lat"));
                area.setMaxLong(rs.getDouble("max_long"));
                area.setActiva(rs.getBoolean("activa"));
                area.setFechaCreacion(rs.getDate("creationDate"));
                area.setFechaModificacion(rs.getDate("modificationDate"));
            }
        } catch (SQLException sqle) {
                Logger.getLogger(AreaDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        dbConn.close(conn);
        
        return area;
    }    

    /**
     * Método createArea, permite crear un área.
     * @param area area a insertar en la Base de Datos.
     * @return entero que identifica el área creada.
     */
    public int createArea(Area area) {

        Connection conn = dbConn.open();
        Statement statement;
        int result = -1;
        String insert = "INSERT INTO tfm_area (nombre, descripcion, min_lat, max_lat, min_long, max_long, activa ,fecha_creacion)"
                     + " VALUES ( '" + area.getName() + "', '"
                                     + area.getDescription() +"', "
                                     + area.getMinLat() + ", "
                                     + area.getMaxLat() + ", "
                                     + area.getMinLong()+ ", "
                                     + area.getMaxLong() + ", "
                                     + area.isActiva() + ", '"
                                     + area.getFechaCreacion() + "');";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            statement.execute(insert, Statement.RETURN_GENERATED_KEYS);
            conn.commit();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                result = rs.getInt(1);
            }
        } catch (SQLException sqle) {
                Logger.getLogger(AreaDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        dbConn.close(conn);
        return result;
    }

    /**
     * Método updateArea permite actualizar los valores de un área.
     * @param oldAreaId el identificador del área antiguo.
     * @param newArea la nueva área.
     * @return true si se actualiza de forma correcta.
     *         false si no.
     */
    public boolean updateArea(int oldAreaId, Area newArea) {
        
        Connection conn = dbConn.open();
        Statement statement;
        int result = 0;
        String update = "UPDATE tfm_area "
                     + " SET nombre = '" + newArea.getName() + "' ,"
                        + " descripcion = '" + newArea.getDescription() + "' ,"
                        + " min_lat = " + newArea.getMinLat() + ","
                        + " max_lat = " + newArea.getMaxLat() + ","
                        + " min_long = " + newArea.getMinLong() + ","
                        + " max_long = " + newArea.getMaxLong() + ","
                        + " fecha_modificacion = '" + newArea.getFechaModificacion()
                     + "' WHERE id = " + oldAreaId;
        
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            result = statement.executeUpdate(update);
            conn.commit();
        } catch (SQLException sqle) {
            Logger.getLogger(AreaDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        dbConn.close(conn);
        return result==1;
    }

    /**
     * Método deleteArea, permite eliminar un área por su identificador.
     * @param areaId el identificador del área.
     * @return true si el borrado es correcto.
     *         false si el borrado no es correcto.
     */
    public boolean deleteArea(int areaId) {
        
        Connection conn = dbConn.open();
        Statement statement;
        int result = 0;
        String delete = "DELETE FROM tfm_area" +
                       " WHERE id = " + areaId;
        
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            result = statement.executeUpdate(delete);
            conn.commit();
        } catch (SQLException sqle) {
            Logger.getLogger(AreaDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn);
        return result>0;
    }

    /**
     * Método deleteAllAreas, permite eliminar todas las áreas e inserta
     * el área por defecto.
     * @return true si el borrado es correcto.
     *         false si el borrado no es correcto.
     */
    public boolean deleteAllAreas() {
        
        Connection conn = dbConn.open();
        Statement statement;
        int result=0;
        String delete = "DELETE FROM tfm_area";
        String sequence = "ALTER SEQUENCE tfm_city_id_seq RESTART WITH 1;";
        String insertDefaul = "INSERT INTO tfm_area "
                            + "(nombre, descripcion, min_lat, min_long, max_lat,"
                            + " max_long, activa, fecha_creacion)"
                            + "VALUES"
                            + "('Desconocida', 'Área con rutas desconocidas',"
                            + " -90, -180, 90, 180, 'true', now());";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            result = statement.executeUpdate(delete);
            if(result > 0){
                statement.execute(sequence);
                statement.execute(insertDefaul);
                conn.commit();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(AreaDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn);
        return result>0;
        
    }
        
}
