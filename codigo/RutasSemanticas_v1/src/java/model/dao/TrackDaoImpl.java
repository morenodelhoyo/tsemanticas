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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.data.Area;
import model.data.Track;

/**
 * Clase TrackDaoImpl, permite realizar operaciones CRUD sobre las rutas.
 * que relaciona posiciones con PDIs.
 * @author David Moreno del Hoyo
 * @version 1.0
 */
public class TrackDaoImpl {
        
    /**
     * Objeto de conexión contra la BD.
     */
    private final DBConnection dbConn;
    
    /**
     * Construcor de clase.
     */
    public TrackDaoImpl(){
        dbConn = new DBConnection();
    }

    /**
     * Método createTrack, permite hacer una inserción completa de una ruta.
     * @param track la ruta a insertar.
     * @return el id de la ruta generada.
     */
    public double createTrack(Track track) {
        
        Connection conn = dbConn.open();
        UsuarioDaoImpl uDao = new UsuarioDaoImpl();
        uDao.createUser(track.getUserid());
        
        int result = -1;
        Statement statement;
        
        String trackInsert = "INSERT INTO tfm_ruta (id_usuario,"
                                                 + "id_area,"
                                                 + "fecha_creacion,"
                                                 + "nuevo,"
                                                 + "es_temporal,"
                                                 + "fecha_comienzo,"
                                                 + "fecha_fin)"
                          + " VALUES ("
                                    + track.getUserid() + ", "
                                    + track.getArea().getId() + ", '"
                                    + new java.sql.Date(new Date().getTime()) + "'," 
                                    + "'true', '"
                                    + track.isTemporary() + "', '"
                                    + track.getInitDate() + "', '"
                                    + track.getEndDate() 
                          + "');";        
        
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            statement.executeUpdate(trackInsert, Statement.RETURN_GENERATED_KEYS);
            conn.commit();
            ResultSet rs = statement.getGeneratedKeys();
            PositionDaoImpl pDao = new PositionDaoImpl();
            if(rs.next()){
                result = rs.getInt(1);
                track.setTrackId(result);
                track.getaTrack().setTrackId(result);
                track.getListOfPositions().forEach((position) -> {
                    position.setTrackId(track.getTrackId());
                    pDao.createPosition(position);
                });
                AreaTrackDaoImpl aTDao = new AreaTrackDaoImpl();
                aTDao.createAreaTrack(track.getaTrack());
            }
            
            pDao.updateTheGeom(track.getTrackId());
            
            
        } catch (SQLException sqle) {
            Logger.getLogger(TrackDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }

        dbConn.close(conn); 
        return result;
    }

    /**
     * Método getTrack, permite obtener una ruta según su identificador.
     * @param trackId el identificador de la ruta.
     * @return track, la ruta a obtener.
     */
    public Track getTrack(double trackId) {
        
        Connection conn = dbConn.open();
        Area area;
        ResultSet rs;
        Statement statement;
        Track track = new Track();
        String queryTrack = "SELECT tfm_ruta.id_ruta as trackId,"
                                + " tfm_ruta.fecha_creacion as creationDate,"
                                + " tfm_ruta.fecha_comienzo as initDate,"
                                + " tfm_ruta.fecha_fin as endDate,"
                                + " tfm_usuario.id_usuario as userId,"
                                + " tfm_area.id as areaId,"
                                + " tfm_area.nombre as name,"
                                + " tfm_area.descripcion as description,"
                                + " tfm_area.min_lat as min_lat,"
                                + " tfm_area.min_long as min_long,"
                                + " tfm_area.max_lat as max_lat,"
                                + " tfm_area.max_long as max_long"
                        + " FROM tfm_ruta inner join tfm_usuario on tfm_ruta.id_usuario = tfm_usuario.id_usuario"
                                      + " inner join tfm_area on tfm_ruta.id_area = tfm_area.id"
                        + " WHERE id_ruta = " + trackId + ";";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(queryTrack);
            conn.commit();

            if(rs.next()){
                track.setUserid(rs.getInt("userId"));
                track.setTrackId(rs.getInt("trackId"));
                track.setFechaCreacion(rs.getDate("creationDate"));
                track.setInitDate(rs.getTimestamp("initDate"));
                track.setEndDate(rs.getTimestamp("endDate"));
                area = new Area();
                area.setId(rs.getInt("areaId"));
                area.setName(rs.getString("name"));
                area.setDescription(rs.getString("description"));
                area.setMinLat(rs.getDouble("min_lat"));
                area.setMaxLat(rs.getDouble("max_lat"));
                area.setMinLong(rs.getDouble("min_long"));
                area.setMaxLong(rs.getDouble("max_long"));
                track.setArea(area);
                track.setListOfPositions(new PositionDaoImpl().getPositionsByTrackId(trackId));
            } 
        } catch (SQLException sqle) {
                Logger.getLogger(TrackDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn); 
        return track;
    }
    
    /**
     * Método getPartialTracksByArea, permite obtener una porción de la ruta
     * a partir del id del área al que pertenece.
     * @param areaId el id del area.
     * @return aTrack, la lista de rutas.
     */
    public ArrayList<Track> getPartialTracksByArea(double areaId) {
        
        Connection conn = dbConn.open();
        ArrayList<Track> aTrack = new ArrayList();
        Area area;
        Track track;
        Statement statement;
        ResultSet rs;
        
        String query = "SELECT tfm_ruta.id_ruta as rutaId,"
                    + " tfm_ruta.fecha_creacion as creationDate,"
                    + " tfm_ruta.fecha_comienzo as initDate,"
                    + " tfm_ruta.fecha_fin as endDate,"
                    + " count(id_posicion) as positions,"
                    + " tfm_area.nombre as areaName,"
                    + " tfm_area.id as areaId"
              + " FROM tfm_ruta"
              + " INNER JOIN tfm_posicion on tfm_ruta.id_ruta = tfm_posicion.id_ruta "
              + " INNER JOIN tfm_area on tfm_area.id = tfm_ruta.id_area "
              + " WHERE tfm_area.id = " + areaId 
              + " GROUP BY tfm_ruta.id_ruta, tfm_area.nombre, tfm_area.id"
              + " ORDER BY tfm_ruta.fecha_creacion asc";
        
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            conn.commit();
            while(rs.next()){
                track = new Track();
                area = new Area();
                track.setTrackId(rs.getDouble("rutaId"));
                track.setFechaCreacion(rs.getTimestamp("creationDate"));
                track.setInitDate(rs.getTimestamp("initDate"));
                track.setEndDate(rs.getTimestamp("endDate"));
                track.setnPositions(rs.getDouble("positions"));
                area.setId(rs.getInt("areaId"));
                area.setName(rs.getString("areaName"));
                track.setArea(area);
                aTrack.add(track);
            }
        } catch (SQLException sqle) {
                Logger.getLogger(TrackDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        dbConn.close(conn);
        return aTrack;
    }
    
    /**
     * Método getPartialTracksOrderedByDate, permite obtener rutas 
     * ordenadas por fecha.
     * @param maxNumberOfTracks el número máximo de rutas a obtener.
     * @return aTrack, las rutas obtenidas.
     */
    public ArrayList<Track> getPartialTracksOrderedByDate(double maxNumberOfTracks) {
        
        Connection conn = dbConn.open();
        ArrayList<Track> aTrack = new ArrayList();
        Track track;
        Area area;
        Statement statement;
        ResultSet rs;
        String query;
        
        query = "SELECT tfm_ruta.id_ruta as rutaId,"
                    + " tfm_ruta.fecha_creacion as creationDate,"
                    + " tfm_ruta.fecha_comienzo as initDate,"
                    + " tfm_ruta.fecha_fin as endDate,"
                    + " count(id_posicion) as positions,"
                    + " tfm_area.nombre as areaName,"
                    + " tfm_area.id as areaId"
              + " FROM tfm_ruta"
              + " INNER JOIN tfm_posicion on tfm_ruta.id_ruta = tfm_posicion.id_ruta "
              + " INNER JOIN tfm_area on tfm_area.id = tfm_ruta.id_area "
              + " GROUP BY tfm_ruta.id_ruta, tfm_area.nombre, tfm_area.id"
              + " ORDER BY tfm_ruta.fecha_creacion asc";
        
        if(maxNumberOfTracks == -1){
            query += ";";
        }else{
            query += " LIMIT " + maxNumberOfTracks + ";";
        }
        
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            conn.commit();
            while(rs.next()){
                track = new Track();
                area = new Area();
                track.setTrackId(rs.getDouble("rutaId"));
                track.setFechaCreacion(rs.getTimestamp("creationDate"));
                track.setInitDate(rs.getTimestamp("initDate"));
                track.setEndDate(rs.getTimestamp("endDate"));
                track.setnPositions(rs.getDouble("positions"));
                area.setId(rs.getInt("areaId"));
                area.setName(rs.getString("areaName"));
                track.setArea(area);
                aTrack.add(track);
            }
        } catch (SQLException sqle) {
                Logger.getLogger(TrackDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn);        
        return aTrack;
    }

    /**
     * Método deleteTracks, permite eliminar todas las rutas del sistema.
     * @return true si se eliminan.
     *         false si no.
     */
    public boolean deleteTracks() {
        
        Connection conn = dbConn.open();
        Statement statement;
        int result;
        String delete = "DELETE"
                     + " FROM tfm_ruta;";
        
        String sequence = "ALTER SEQUENCE tfm_ruta_id_ruta_seq RESTART WITH 1;";
        String sequence_position = "ALTER SEQUENCE tfm_posicion_id_posicion_seq RESTART WITH 1;";
        String sequence_position_poi = "ALTER SEQUENCE tfm_posicion_poi_id_posicion_poi_seq RESTART WITH 1;";
        boolean deleted = false;
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            result = statement.executeUpdate(delete);
            if(result > 0){
                statement.execute(sequence);
                statement.execute(sequence_position);
                statement.execute(sequence_position_poi);
                conn.commit();
                deleted = true;
            }
            
        } catch (SQLException sqle) {
            Logger.getLogger(TrackDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn); 
        return deleted;
    }
    
    /**
     * Método getMaxTrackId, permite obtener el máximo id de las rutas del 
     * sistema.
     * @return el identificador.
     */
    public double getMaxTrackId(){
       
        Connection conn = dbConn.open();
        ResultSet rs;
        Statement stmt;
        String query = "SELECT last_value as track_id"
                    + " FROM tfm_ruta_id_ruta_seq";
        
        double result = 0;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                result = (rs.getDouble("track_id") == 1) ? 1 : rs.getDouble("track_id")+1;
            }
        } catch (SQLException e) {
            Logger.getLogger(TrackDaoImpl.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        dbConn.close(conn); 
        
        return result;
    }
    
    /**
     * Método updateTrack, permite actualizar el área de la ruta.
     * @param areaId el área de la ruta a actualizar.
     * @return true si se modifica.
     *         false si no.
     */
    public boolean updateTrack(double areaId) {
        
        
        Connection conn = dbConn.open();
        Statement statement;
        String update = "UPDATE tfm_ruta"
                     + " SET id_area = 1"
                     + " WHERE id_area = " + (int)areaId + ";";
        boolean updated = false;
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            statement.executeUpdate(update);
            conn.commit();
            updated = true;
        } catch (SQLException sqle) {
                Logger.getLogger(TrackDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn);
        return updated;
        
    }

    /**
     * Método updateOldTracks, permita actualizar las rutas antiguas al generarse
     * nuevas rutas en el sistema.
     * @return true si se actualizan.
     *         false si no.
     */
    public boolean updateOldTracks() {
        
        Connection conn = dbConn.open();
        Statement statement;
        String update = "UPDATE tfm_ruta"
                     + " SET nuevo = 'false'"
                     + " WHERE nuevo = 'true';";
        boolean updated = false;
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            statement.executeUpdate(update);
            conn.commit();
            updated = true;
        } catch (SQLException sqle) {
                Logger.getLogger(TrackDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn);
        return updated;
        
    }

   /**
    * Método deleteNewTracks, permite eliminar las rutas nuevas al detenerse una
    * ejecución de una prueba,
    * @return true si se eliminan.
    *         false si no.
    */
    public boolean deleteNewTracks() {
        
        Connection conn = dbConn.open();
        Statement statement;
        String query = "DELETE"
                    + " FROM tfm_ruta"
                    + " WHERE nuevo = 'true';";
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
    
    /**
     * Método deleteTemporaryTracks, permite eliminar las rutas que el usuario
     * decide no guardar en el sistema.
     * @return true si se borran.
     *         false si no.
     */
    public boolean deleteTemporaryTracks() {

        Connection conn = dbConn.open();
        Statement statement;
        String delete = "DELETE FROM tfm_ruta"
                     + " WHERE es_temporal = 'true'";
        
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            statement.executeUpdate(delete);
            conn.commit();
        } catch (SQLException sqle) {
            Logger.getLogger(TrackDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn); 
        return true;
    }

    /**
     * Metodo getPartialTracksInsideArea, permite obtener una porción de las rutas
     * que se incluyen en un área descrita por sus coordenadas.
     * @param minLat la latitud mínima.
     * @param minLong la longitud mínima.
     * @param maxLat la latitud máxima.
     * @param maxLong la longitud máxima.
     * @return aTrack, la lista de rutas obtenidas.
     */
    public ArrayList<Track> getPartialTracksInsideArea(double minLat, double minLong, double maxLat, double maxLong) {
        
        Connection conn = dbConn.open();
        ArrayList<Track> aTrack = new ArrayList();
        Track track;
        Area area;
        Statement statement;
        ResultSet rs;
        
        String query = "SELECT tfm_ruta.id_ruta as rutaId,"
                    + " tfm_ruta.fecha_creacion as creationDate,"
                    + " tfm_ruta.fecha_comienzo as initDate,"
                    + " tfm_ruta.fecha_fin as endDate,"
                    + " count(id_posicion) as positions,"
                    + " tfm_area.nombre as areaName,"
                    + " tfm_area.id as areaId"
              + " FROM tfm_ruta"
              + " INNER JOIN tfm_posicion on tfm_ruta.id_ruta = tfm_posicion.id_ruta "
              + " INNER JOIN tfm_area on tfm_area.id = tfm_ruta.id_area "
              + " INNER JOIN tfm_ruta_area on tfm_ruta_area.id_ruta = tfm_ruta.id_ruta"
              + " WHERE tfm_ruta_area.min_lat > " + minLat 
              + " and tfm_ruta_area.max_lat < " + maxLat
              + " and tfm_ruta_area.min_long > " + minLong
              + " and tfm_ruta_area.max_long < " + maxLong
              + " GROUP BY tfm_ruta.id_ruta, tfm_area.nombre, tfm_area.id"
              + " ORDER BY tfm_ruta.fecha_creacion asc";
        
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            conn.commit();
            while(rs.next()){
                track = new Track();
                area = new Area();
                track.setTrackId(rs.getDouble("rutaId"));
                track.setFechaCreacion(rs.getTimestamp("creationDate"));
                track.setInitDate(rs.getTimestamp("initDate"));
                track.setEndDate(rs.getTimestamp("endDate"));
                track.setnPositions(rs.getDouble("positions"));
                area.setId(rs.getInt("areaId"));
                area.setName(rs.getString("areaName"));
                track.setArea(area);
                aTrack.add(track);
            }
        } catch (SQLException sqle) {
                Logger.getLogger(TrackDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn);
        return aTrack;
    }
    
}
