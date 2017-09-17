/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.algorithm;

import model.data.Position;

/**
 * Clase DataCleaning, permite eliminar los puntos que no sean correctos.
 * @author David Moreno del Hoyo.
 * @version 1.0
 */
public class DataCleaning {
    
    /**
     * Método isAValidPosition comprueba que la posición sea válida.
     * @param position la posición a validar.
     * @return true si es válida, false si no lo es.
     */
    public boolean isAValidPosition(Position position){
        
        return (position.getLatitude() >= -90.0 && position.getLatitude() <= 90.0) &&
                (position.getLongitude() >= -180.0 && position.getLongitude() <= 180.0);
        
    }
    
}
