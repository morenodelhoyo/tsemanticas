package model.data;

/**
 * Clase Usuario, permite representar los usuarios que realizan rutas.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public class Usuario {

    /**
     * Identificador del usuario.
     */
    private double userId;

    /**
     * Constructor de clase.
     * @param userId identificador del usuario.
     */
    public Usuario(double userId){
        this.userId = userId;
    }

    /**
     * @return the userId
     */
    public double getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
            this.userId = userId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
            return "Usuario [userId=" + userId + "]";
    }
}
