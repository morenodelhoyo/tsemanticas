package controller.algorithm;

import model.data.Position;

/**
 * Clase CoordinatesComparison, permite calcular distancias entre coordenadas geográficas
 * y validar si una ruta cumple con los criterios establecidos (distancia máxima entre puntos).
 * 
 * @author David Moreno del Hoyo.
 * @version 1.0
 */
public class CoordinatesComparison {
		
    /**
     * Radio de la Tierra.
     */
    static final double RADIUS = 6371e3;	

    /**
     * Método que calcula la distancia entre dos coordenadas geográficas. Se usa
     * la fórmula de Haversine.
     * @param p1 primera de las coordenadas.
     * @param p2 segunda coordenada.
     * @return distance la distancia calculada entre las dos coordenadas.
     */
    public double calculateDistance(Position p1, Position p2){
            return calculateDistance(p1.getLatitude(), p1.getLongitude(), p2.getLatitude(), p2.getLongitude());
    }

    /**
     * Método que calcula la distancia entre dos coordenadas geográficas. Se usa
     * la fórmula de Haversine.
     * @param lat1 latitud de la primera posición.
     * @param long1 longitud de la primera posición.
     * @param lat2 latitud de la segunda posición.
     * @param long2 longitud de la segunda posición.
     * @return distance distancia calculada.
     */
    public double calculateDistance(double lat1, double long1, double lat2, double long2){

            double latOneRadians = Math.toRadians(lat1);
            double latTwoRadians = Math.toRadians(lat2);
            double latDifference = Math.toRadians(lat2-lat1);
            double longDifference = Math.toRadians(long2-long1);

            double hav = Math.sin(latDifference/2) * Math.sin(latDifference/2) +
                    Math.cos(latOneRadians) * Math.cos(latTwoRadians) *
                    Math.sin(longDifference/2) * Math.sin(longDifference/2);

            double distance =  RADIUS * 2 * Math.atan2(Math.sqrt(hav), Math.sqrt(1-hav));

            return distance;
    }
	
    /**
     * Método que calcula la posición media entre dos posiciones.
     * @param lat1 latitud de la primera posición.
     * @param long1 longitud de la primera posición.
     * @param lat2 latitud de la segunda posición.
     * @param long2 longitud de la segunda posición.
     * @return Position, la posición media
     */
    public Position calculateMedipoint(double lat1, double long1, double lat2, double long2){
        double latitude = (lat1 + lat2) / 2;
        double longitude = (long1 + long2) / 2;
        return new Position(latitude, longitude);
    }

}
