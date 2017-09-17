package controller.algorithm;

import java.util.Date;

/**
 * Clase DateComparison, permite calcular la diferencia entre dos fechas.
 * @author David Moreno del Hoyo.
 * @version 1.0
 */
public class DateComparison {
	
	/**
	 * Método calculateDifference calcula la diferencia entre fechas.
	 * @param d1
	 * @param d2
	 * @return
	 */
	public double calculateDifference(Date d1, Date d2){
		return Math.abs(d2.getTime()-d1.getTime());
	}

}
