/*
 * Creado el 09-nov-2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.utiles;

import java.math.BigDecimal;

/**
 * @author Programa8
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class MathUtil {
	/**
	 * Metodo estático para redondear un double a una precision decimal dada.
	 * @param x Número a redondear 
	 * @param prec Precision dada
	 * @return Numero redondeado
	 * @throws RuntimeException Si la precision es negativa
	 */
	public static double cutDouble(double x, int prec, boolean redondeo) {
		if (prec < 0) {
			throw new RuntimeException("Precisión negativa");
		}
		BigDecimal bd = new BigDecimal(Double.toString(x));
		if (redondeo)
			bd = bd.setScale(prec, BigDecimal.ROUND_HALF_UP);
		else 
			bd = bd.setScale(prec, BigDecimal.ROUND_DOWN);
			
		double result = bd.doubleValue();
		// Chequeamos si hay problemas de redondeo alrededor de la precision
		// solicitada
		double error = 5 - (x - result) * StrictMath.pow(10, prec + 1);
		if ((error < 0.0001) && (error > 0)) {
			result += 1 / StrictMath.pow(10, prec);
		}
		return result;
	}
	
	public static double truncDouble(double x){
		return cutDouble(x, 2, false);
	}
	
	/**
	 * Método estático para redondear un double a 2 decimales
	 * @param x Número a redondear
	 * @return Numero redondeado
	 */
	public static double roundDouble(double x) {
		return cutDouble(x, 2, true);
	}
	
	public static double stableMultiply(double x, double y) {
		BigDecimal bx = new BigDecimal(Double.toString(x));
		BigDecimal by = new BigDecimal(Double.toString(y));
		bx = bx.multiply(by);
		return bx.doubleValue();
	}
	
	public static double stableDivide(double x, double y) {
		BigDecimal bx = new BigDecimal(Double.toString(x));
		BigDecimal by = new BigDecimal(Double.toString(y));
		bx = bx.divide(by, BigDecimal.ROUND_HALF_UP);
		return bx.doubleValue();
	}
	
    /**
     * Suma el contenido de un array de longs
     *
     * @param x
     * @return int
     */
    public static long sum(long[] x) {
        long sum = 0;
        for (int i = 0; i < x.length; i++) {
            long j = x[i];
            sum = +j;
        }
        return sum;
    }

    /**
     * Suma el contenido de un array de int
     *
     * @param x
     * @return int
     */
    public static long sum(int[] x) {
        long sum = 0;
        for (int i = 0; i < x.length; i++) {
            int j = x[i];
            sum = +j;
        }
        return sum;
    }

}
