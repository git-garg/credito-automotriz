/**
 * Clase: PatioServicio.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.service;

import com.pichincha.stf.entity.Patio;
import com.pichincha.stf.entity.to.PatioTo;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

/**
 *
 */
public interface PatioServicio {

	Long cargarPatios();

	Patio obtenerPorNumeroPuntoVenta(String numeroPuntoVenta);

	Patio guardarPatioAPatirDeTo(PatioTo patioTo) throws CreditoAutomotrizException;

	Patio actualizarPatioAPatirDeTo(PatioTo patioTo) throws CreditoAutomotrizException;

	void eliminarPatio(String numeroPuntoVenta) throws CreditoAutomotrizException;

}
