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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.data.UploadedFile;

/**
 * Clase UploadedPoiFileDaoImpl, permite realizar operaciones CRUD
 * sobre los ficheros de PDIs subidos.
 * @author David Moreno del Hoyo
 * @version 1.0
 */
public class UploadedPoiFileDaoImpl {
    
    /**
     * Objeto de tipo DBConnection para conextarse con la BD.
     */
    private final DBConnection dbConn;
    
    /**
     * Constructor de clase.
     */
    public UploadedPoiFileDaoImpl(){
        dbConn = new DBConnection();
    }

    /**
     * Método createFile, permite crear una entrada sobre la tabla correspondiente.
     * @param uploadedFile el fichero a crear.
     * @return true si se crea.
     *         false si no.
     */
    public boolean createFile(UploadedFile uploadedFile) {
        
        Connection conn = dbConn.open();
        String fileInsert = "INSERT INTO tfm_fichero_poi (nombre, fecha_procesado)"
                         + " VALUES (?,?)"
                         + " ON CONFLICT (nombre) DO"
                         + " UPDATE SET fecha_procesado = '" + new java.sql.Date(new java.util.Date().getTime()) +"';";

        try {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(fileInsert);
            preparedStatement.setString(1, uploadedFile.getName());
            preparedStatement.setDate(2, (Date) uploadedFile.getProcessedDate());
            preparedStatement.executeUpdate();
            conn.commit();
        } catch (SQLException sqle) {
            Logger.getLogger(WayDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        dbConn.close(conn);
        
        return true;
    }

    /**
     * Método deleteAll, permite eliminar todos los ficheros almacenados
     * en la tabla correspondiente.
     * @return true si se borran.
     *         false si no.
     */
    public boolean deleteAll() {
        
        Connection conn = dbConn.open();
        Statement statement;
        int result=0;
        String delete = "DELETE FROM tfm_fichero_poi;";
        String sequence = "ALTER SEQUENCE tfm_fichero_poi_id_fichero_seq RESTART WITH 1";
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
        return result>0;
    }
    
}
