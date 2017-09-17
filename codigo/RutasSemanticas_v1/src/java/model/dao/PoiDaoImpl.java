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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.data.Poi;
import model.data.Position;

/**
 * Clase PoiDaoImpl, permite realizar operaciones CRUD sobre los PDIs.
 * @author David Moreno del Hoyo
 * @version 1.0
 */
public class PoiDaoImpl {
    
    /**
     * Conexión contra la BD.
     */
    private final DBConnection dbConn;
    
    /**
     * Construcor de clase.
     */
    public PoiDaoImpl(){
        dbConn= new DBConnection();
    }

    /**
     * Método getNearestPois, permite obtener los PDIs cercanos.
     * @param position la posición sobre la que buscarlos.
     * @param radius el radio al que buscar.
     * @return aPOI, lista de PDIs encontrados-
     */
    public ArrayList<Poi> getNearestPois(Position position, double radius) {

        Connection conn = dbConn.open();
        Poi poi;
        ArrayList<Poi> aPOI = new ArrayList();
        ResultSet rs;
        Statement stmt;
        WayDaoImpl wDao = new WayDaoImpl();
        
        String getPOIsQuery = "SELECT id,"
                                  + " latitud AS latitude,"
                                  + " longitud AS longitude,"
                                  + " nombre as name,"
                                  + " amenity,"
                                  + " ST_Distance(the_geom, ref_the_geom) AS distance"
                           + " FROM tfm_nodos"
                           + " CROSS JOIN"
                           + " (SELECT ST_MakePoint("+ position.getLongitude() + ", " + position.getLatitude() +")::geography AS ref_the_geom) AS crs_join"
                           + " WHERE ST_DWithin(the_geom, ref_the_geom, " + radius + ")"
                           + " ORDER BY ST_Distance(the_geom, ref_the_geom);";
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(getPOIsQuery);
            while(rs.next()){
                poi = new Poi();
                poi.setId(rs.getBigDecimal("id"));
                poi.setLatitude(rs.getDouble("latitude"));
                poi.setLongitude(rs.getDouble("longitude"));
                poi.setName(rs.getString("name"));
                String amenity = rs.getString("amenity");
                if(amenity.equals("")){
                    amenity = wDao.getAmenityOfPoi(poi.getId());
                }
                poi.setAmenity(amenity);
                poi.setDistance(rs.getDouble("distance"));
                aPOI.add(poi);
            }
        } catch (SQLException e) {
            Logger.getLogger(PoiDaoImpl.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        dbConn.close(conn);
        return aPOI;
    }

    /**
     * Método getPoiById, permite obtener un PDI mediante su id.
     * @param poiId identificador del poi.
     * @param position posición sobre la que buscarlo.
     * @return poi, el poi encontrado.
     */
    public Poi getPoiById(BigDecimal poiId, Position position) {
        
        Connection conn = dbConn.open();
        Poi poi = new Poi();
        ResultSet rs;
        Statement stmt;
        WayDaoImpl wDao = new WayDaoImpl();
        
        String getPOIsQuery = "SELECT id,"
                                  + " latitud AS latitude,"
                                  + " longitud AS longitude,"
                                  + " nombre as name,"
                                  + " amenity,"
                                  + " ST_Distance(the_geom, ref_the_geom) AS distance"
                           + " FROM tfm_nodos"
                           + " CROSS JOIN"
                           + " (SELECT ST_MakePoint("+ position.getLongitude() + ", " + position.getLatitude() +")::geography AS ref_the_geom) AS crs_join"
                           + " WHERE id = " + poiId + ";";
                           
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(getPOIsQuery);
            while(rs.next()){
                poi = new Poi();
                poi.setId(rs.getBigDecimal("id"));
                poi.setLatitude(rs.getDouble("latitude"));
                poi.setLongitude(rs.getDouble("longitude"));
                poi.setName(rs.getString("name"));
                String amenity = rs.getString("amenity");
                if(amenity == null || amenity.equals("")){
                    amenity = wDao.getAmenityOfPoi(poi.getId());
                }
                poi.setAmenity(amenity);
                poi.setDistance(rs.getDouble("distance"));
            }
        } catch (SQLException e) {
            Logger.getLogger(PoiDaoImpl.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        dbConn.close(conn);
        return poi;
    }
    
    /**
     * Método deleteAllPois, permite eliminar todos los PDIs del sistema.
     * @return true si se eliminan.
     *         false si no.
     */
    public boolean deleteAllPois() {
        
        Connection conn = dbConn.open();
        Statement statement;
        String query = "DELETE"
                    + " FROM tfm_nodos;";
        String insertDefault = "INSERT INTO tfm_nodos (id) VALUES (-1);";
        
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            statement.execute(query);
            statement.executeUpdate(insertDefault);
            conn.commit();
        } catch (SQLException sqle) {
            Logger.getLogger(TrackDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn); 
        return true;
    }

    /**
     * Método insertPoi, permite insertar un PDI.
     * @param poi el PDI a insertar.
     * @return true si se crea.
     *         false si no.
     */
    public boolean insertPoi(Poi poi){
        
        insertName(poi.getAmenity());
        
        Connection conn = dbConn.open();
        String amenityInsert = "INSERT INTO tfm_nodos (id, latitud, longitud, nombre, amenity, id_region)"
                            + " VALUES (?,?,?,?,?, ?) ON CONFLICT (id) DO NOTHING;";
        
        try {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(amenityInsert);
            preparedStatement.setBigDecimal(1, poi.getId());
            preparedStatement.setDouble(2, poi.getLatitude());
            preparedStatement.setDouble(3, poi.getLongitude());
            preparedStatement.setString(4, poi.getName());
            preparedStatement.setString(5, poi.getAmenity());
            preparedStatement.setDouble(6, poi.getRegionId());
            preparedStatement.executeUpdate();
            conn.commit();
        } catch (SQLException sqle) {
            Logger.getLogger(PoiDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        dbConn.close(conn);
        return true;
    }
    
    /**
     * Método updateTheGeom, permite actualizar los valores de geometría de 
     * los PDIs.
     * @return true si se actualizan.
     *         false si no.
     */
    public boolean updateTheGeom() {
        
        Connection conn = dbConn.open();
        Statement statement;
        int result;
        String update = "UPDATE tfm_nodos"
                     + " SET the_geom = ST_GeomFromText('POINT(' || longitud || ' ' || latitud || ')',4326);";
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
     * Método getNumberOfPoisByRegion, permite obtener el número de nodos por
     * región.
     * @param regionId el identificador de la región.
     * @return número total de nodos.
     */
    public BigDecimal getNumberOfPoisByRegion(double regionId){
        
        Connection conn = dbConn.open();
        Statement statement;
        ResultSet rs;
        String query = "SELECT count(id) as numberOfAmenities"
                    + " FROM tfm_nodos"
                    + " WHERE id_region = " + regionId + ";";
        BigDecimal result = new BigDecimal("0");
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            conn.commit();
            while(rs.next()){
                result = rs.getBigDecimal("numberOfAmenities");
            }
        } catch (SQLException sqle) {
                Logger.getLogger(AreaDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        dbConn.close(conn);
        
        return result;
    }

    /**
     * Método insertName, permite insertar el tipo del nodo en una tabla auxiliar.
     * @param name el nombre o tipo del nodo.
     * @return true si se crea.
     *         false si no.
     */
    public boolean insertName(String name) {
        
        Connection conn = dbConn.open();
        
        name = name.toLowerCase();
        
        String amenityInsert = "INSERT INTO tfm_nombre_nodo (nombre)"
                            + " VALUES (?) ON CONFLICT (nombre) DO NOTHING;";
        
        try {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(amenityInsert);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            conn.commit();
        } catch (SQLException sqle) {
            Logger.getLogger(PoiDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn);
        return true;
        
    }
    
}
