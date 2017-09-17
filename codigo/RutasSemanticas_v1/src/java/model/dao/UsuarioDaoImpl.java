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
 * Clase UsuarioDaoImpl, permite realizar operaciones CRUD sobre los usuarios.
 * @author David Moreno del Hoyo
 * @version 1.0
 */
public class UsuarioDaoImpl {
    
    /**
     * Objeto de tipo DBConnection.
     */
    private final DBConnection dbConn;
    
    /**
     * Constructor de clase.
     */
    public UsuarioDaoImpl(){
        dbConn = new DBConnection();
    }

    /**
     * Método createUser, permite crear un usuario.
     * @param userId identificdor del usuario a crear.
     * @return true si se crea.
     *         false si no.
     */
    public double createUser(double userId) {
        
        Connection conn = dbConn.open();
        int result = -1;
        Statement statement;
        String userInsert = "INSERT INTO tfm_usuario (id_usuario)"
                         + " VALUES (" + userId + ") ON CONFLICT DO NOTHING;";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            statement.execute(userInsert, Statement.RETURN_GENERATED_KEYS);
            conn.commit();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                result = rs.getInt(1);
            }
        } catch (SQLException sqle) {
                Logger.getLogger(UsuarioDaoImpl.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
        dbConn.close(conn);
        return result;
    }

    /**
     * Método deleteUsers, permite eliminar los usuarios del sistema.
     * @return true si se borran.
     *         false si no.
     */
    public boolean deleteUsers() {
        
        Connection conn = dbConn.open();
        Statement statement;
        String query = "DELETE"
                    + " FROM tfm_usuario;";
        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            Logger.getLogger(UsuarioDaoImpl.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        dbConn.close(conn);
        
        return true;
    }

}
