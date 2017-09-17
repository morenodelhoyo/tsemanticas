package controller.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase DBConnection, permite establecer una conexión contra la Base de Datos.
 * 
 * @author David Moreno del Hoyo.
 * @version 1.0
 */
public final class DBConnection {
    
    /**
     * Objeto de tipo Connection.
     */
    private Connection conn = null;
	
    /**
     * Método que permite obtener la conexión contra la Base de Datos.
     * @return 
     */
    public Connection open() {
        
        try {
            Class.forName("org.postgresql.Driver");
            Properties props = new Properties();
            props.setProperty("user", DBConfiguration.USERNAME);
            props.setProperty("password", DBConfiguration.PASSWORD);
            conn = DriverManager.getConnection(DBConfiguration.URL+DBConfiguration.DBNAME, props);
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return conn;
    }
	
    /**
     * Método que permite cerrar la conexión contra la Base de Datos.
     * @param conn conexión a cerrar.
     */
    public void close(Connection conn){
        try {
            conn.close();
        } catch (SQLException sqle) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, sqle.getMessage());
        }
    }

}
