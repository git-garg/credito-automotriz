/**
 * Clase: Util.java
 * Fecha: 28 jun. 2022
 */
package com.pichincha.stf.util;

import com.pichincha.stf.service.exception.CreditoAutomotrizException;

/**
 * Contiene metodos utilitarios
 *
 */
public class Util {

	public static Integer obtenerIntegerDeString(String cadena) throws CreditoAutomotrizException {
		try {
			return Integer.parseInt(cadena);
		} catch (NumberFormatException e) {
			throw new CreditoAutomotrizException("No se puede convertir: " + cadena, e);
		}

	}
}
