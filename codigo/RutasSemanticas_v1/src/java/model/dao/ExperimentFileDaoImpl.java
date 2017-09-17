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
import model.data.UploadedFile;

/**
 * Clase ExperimentFileDaoImpl, permite realizar operaciones CRUD sobre los ficheros
 * que se han analizado en la prueba o experimento.
 * @author David Moreno del Hoyo
 * @version 1.0
 */
public class ExperimentFileDaoImpl {

    /**
     * Objeto de tipo DBConnection.
     */
    private final DBConnection dbConn;
    
    /**
     * Constructor de clase.
     */
    public ExperimentFileDaoImpl(){
        dbConn= new DBConnection();
    }
    
    /**
     * Método createExperimentFile, permite crear una nueva entrada en la
     * tabla correspondiente.
     * @param experimentId el identificador del experimento.
     * @param fileId el identificador del fichero.
     * @return true si se crea.
     *         false si no.
     */
    public boolean createExperimentFile(double experimentId, double fileId) {
        
        Connection conn = dbConn.open();
        Statement statement;
        String create = "INSERT INTO tfm_experimento_fichero"
                        + " (id_experimento,"
                        + " id_fichero)"
                        + " VALUES ("
                                    + experimentId + ","
                                    + fileId
                                + ");";
        
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

    /**
     * Método getExperimentFileName, permite obtener los ficheros analizados
     * en la prueba.
     * @param experimentId identificador del experimento.
     * @return aFile, el listado de ficheros obtenidos.
     */
    public ArrayList<UploadedFile> getExperimentFileNames(double experimentId) {
        
        ArrayList<UploadedFile> aFile = new ArrayList();
        UploadedFile uF;
        Connection conn = dbConn.open();
        ResultSet rs;
        Statement stmt;
        
        String getPOIsQuery = "SELECT tfm_fichero.id_fichero as fileId, nombre as name"
                            + " FROM tfm_fichero"
                            + " INNER JOIN"
                            + " tfm_experimento_fichero ON tfm_fichero.id_fichero = tfm_experimento_fichero.id_fichero"
                            + " WHERE id_experimento = "+ experimentId +";";
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(getPOIsQuery);
            while(rs.next()){
                uF = new UploadedFile();
                uF.setId(rs.getDouble("fileId"));
                uF.setName(rs.getString("name"));
                aFile.add(uF);
            }
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(PoiDaoImpl.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        dbConn.close(conn);
        
        return aFile;
    }
    
}
