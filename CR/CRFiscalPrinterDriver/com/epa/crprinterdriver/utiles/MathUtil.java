package com.epa.crprinterdriver.utiles;

import java.math.BigDecimal;

public class MathUtil {
	 public static double cutDouble(double x, int prec, boolean redondeo) {
        int type = redondeo ? BigDecimal.ROUND_HALF_UP : BigDecimal.ROUND_DOWN;
        return cutDouble(x, prec, type);
    }

	public static double cutDouble(double x, int prec, int roundType) {
        if (prec < 0) {
            throw new RuntimeException("Precisión negativa");
        }
        BigDecimal bd = new BigDecimal(Double.toString(x));
        bd = bd.setScale(prec, roundType);
        double result = bd.doubleValue();
        // Chequeamos si hay problemas de redondeo alrededor de la precision
        // solicitada
        double error = 5 - (x - result) * StrictMath.pow(10, prec + 1);
        if ((error < 0.0001) && (error > 0)) {
            result += 1 / StrictMath.pow(10, prec);
        }
        return result;

    }
}
