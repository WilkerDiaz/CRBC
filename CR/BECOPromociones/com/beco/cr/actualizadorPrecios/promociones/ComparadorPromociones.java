package com.beco.cr.actualizadorPrecios.promociones;

import java.util.Comparator;


public class ComparadorPromociones implements Comparator<Object> {

	/**
	 * Compara promociones por prioridad
	 */
	public int compare(Object promo1, Object promo2) {
		PromocionExt primera = (PromocionExt) promo1;
		PromocionExt segunda = (PromocionExt) promo2;
		if(primera.getPrioridad()>segunda.getPrioridad())
			return 1;
		else if(primera.getPrioridad()<segunda.getPrioridad())
			return -1;
		else
			//Agregada comparación por codigo de promocion
			//jperez
			if(primera.getCodPromocion()>segunda.getCodPromocion())
				return 1;
			else if(primera.getCodPromocion()<segunda.getCodPromocion())
				return -1;
			return 0;
	}

}
