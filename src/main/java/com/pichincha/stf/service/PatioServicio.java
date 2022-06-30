/**
 * Clase: PatioServicio.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.service;

import com.pichincha.stf.entity.Patio;

/**
 *
 */
public interface PatioServicio {
	
	Long cargarPatios();

	Patio obtenerPorNumeroPuntoVenta(String numeroPuntoVenta);
	
}
