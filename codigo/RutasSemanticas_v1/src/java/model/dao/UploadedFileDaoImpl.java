/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import controller.connection.DBConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.data.UploadedFile;

/**
 * Clase UploadedFileDaoImpl, permite realizar operaciones CRUD
 * sobre los ficheros que han sido subidos al servidor.
 * @author David Moreno del Hoyo
 * @version 1.0
 */
public class UploadedFileDaoImpl {
    
        
    /**
     * Objeto de tipo DBConnection, permite conectar contra la BD.
     */
    private final DBConnection dbConn;
    
    /**
     * Constructor de clase.
     */
    public UploadedFileDaoImpl(){
        dbConn = new DBConnection();
    }

    /**
     * Método getFiles, permite obtener los ficheros subidos al servidor.
     * @return mUF, el mapa con los ficheros seleccionados.
     */
    public Map<String, UploadedFile> getFiles() {

        Connection conn = dbConn.open();
        UploadedFile uF;
        Map<String, UploadedFile> mUF = new HashMap();
        
        ResultSet rs;
        Statement statement;
        String query = "SELECT id_fichero,"
                    + " nombre as name,"
                    + " fecha_subida as uploadedDate,"
                    + " procesado as processed,"
                    + " fecha_procesado as processedDate"
                    + " FROM tfm_fichero;";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()){
                uF = new UploadedFile();
                uF.setId(rs.getDouble("id_fichero"));
                uF.setName(rs.getString("name"));
                uF.setUploadedDate(rs.getDate("uploadedDate"));
                uF.setIsProcessed(rs.getBoolean("processed"));
                if(uF.isIsProcessed()){
                    uF.setProcessedDate(rs.getDate("processedDate"));
                }
                mUF.put(uF.getName(), uF);
            }
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(UsuarioDaoImpl.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        
        dbConn.close(conn);
        return mUF;
    }

    /**
     * Método createFile, permite crear una entrada en la tabla de ficheros.
     * @param uploadedFile el fichero a insertar.
     * @return true si se crea.
     *         false si no.
     */
    public boolean createFile(UploadedFile uploadedFile) {
        
        Connection conn = dbConn.open();
        String fileInsert = "INSERT INTO tfm_fichero (nombre, fecha_subida)"
                         + " VALUES (?,?)"
                         + " ON CONFLICT (nombre) DO"
                         + " UPDATE SET fecha_subida = '" + new java.sql.Date(new java.util.Date().getTime()) +"',"
                                + " procesado = false,"
                                + " fecha_procesado = NULL;";

        try {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(fileInsert);
            preparedStatement.setString(1, uploadedFile.getName());
            preparedStatement.setDate(2, (Date) uploadedFile.getUploadedDate());
            preparedStatement.executeUpdate();
            conn.commit();
        } catch (SQLException sqle) {
            Logger.getLogger(WayDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        dbConn.close(conn);
        
        return true;
    }

    /**
     * Método updateFile, permite actualizar la tabla de ficheros.
     * @param fileName el fichero a actualizar.
     * @return true si se actualiza.
     *         false si no.
     */
    public boolean updateFile(String fileName) {
        
        Connection conn = dbConn.open();
        Statement statement;
        int result = 0;
        String update = "UPDATE tfm_fichero"
                     + " SET procesado = true, fecha_procesado = '" + new java.sql.Date(new java.util.Date().getTime()) + "'"
                     + " WHERE nombre = '" + fileName + "'";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            result = statement.executeUpdate(update);
            conn.commit();
        } catch (SQLException sqle) {
            Logger.getLogger(PositionDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn);
        return result == 1;

    }

    /**
     * Método getFileId, permite obtener el id del fichero a partir
     * de su nombre.
     * @param fileName el fichero a buscar.
     * @return result, el id del fichero.
     */
    public double getFileId(String fileName) {
        
        Connection conn = dbConn.open();
        double result = -1;
        ResultSet rs;
        Statement statement;
        String query = "SELECT id_fichero"
                    + " FROM tfm_fichero"
                    + " WHERE nombre = '"+ fileName + "';";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()){
                result = rs.getDouble("id_fichero");
            }
        } catch (SQLException e) {
            Logger.getLogger(UsuarioDaoImpl.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        
        dbConn.close(conn);
        return result;
    }

    /**
     * Método deleteAll, permite eliminar todas las entradas de la tabla.
     * @return true si se eliminan.
     *         false si no.
     */
    public boolean deleteAll() {
        
        Connection conn = dbConn.open();
        Statement statement;
        int result = 0;
        String delete = "DELETE FROM tfm_fichero";
        String sequence = "ALTER SEQUENCE tfm_fichero_id_fichero_seq RESTART WITH 1";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            result = statement.executeUpdate(delete);
            if(result > 0){
                statement.execute(sequence);
                conn.commit();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(AreaDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        
        dbConn.close(conn);
        return result > 0;
        
    }
    
}
