package controller.connection;


/**
 * Clase DBConfiguration, con los valores de configuración del sistema.
 * @author David Moreno del Hoyo
 * @version 1.0
 */
public class DBConfiguration {

    /**
     * API usada para la conexión contra la Base de Datos.
     */
    public static final String API = "jdbc";

    /**
     * SGBD usado.
     */
    public static final String DBMS = "postgresql";

    /**
     * Servidor donde corre el SGBD.
     */
    public static final String SERVER = "localhost";

    /**
     * Puerto de conexión.
     */
    public static final int PORT = 5432;

    /**
     * Cadena de conexión.
     */
    public static final String URL = API+":"+DBMS+"://"+SERVER+":"+PORT+"/";

    /**
     * Nombre de la Base de Datos.
     */
    public static final String DBNAME = "rutassemanticas";

    /**
     * Usuario de la Base de Datos.
     */
    public static final String USERNAME = "rs";

    /**
     * Contraseña del usuario.
     */
    public static final String PASSWORD = "rs";
}
