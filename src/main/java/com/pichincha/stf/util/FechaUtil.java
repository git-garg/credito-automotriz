/**
 * Clase: FechaUtil.java
 * Fecha: 28 jun. 2022
 */
package com.pichincha.stf.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import com.pichincha.stf.service.exception.CreditoAutomotrizException;

/**
 * Clase utilitaria para manejo de fechas
 *
 */
public class FechaUtil {

	public static LocalDate obtenerFechaFormato(String fecha, String formato) throws CreditoAutomotrizException {

		DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(formato, Locale.US);
		LocalDate date = LocalDate.parse(fecha, formatoFecha);

		try {
			return date;
		} catch (DateTimeParseException e) {
			throw new CreditoAutomotrizException("No se pudo convertir la fecha: " + fecha, e);
		}
	}

}
