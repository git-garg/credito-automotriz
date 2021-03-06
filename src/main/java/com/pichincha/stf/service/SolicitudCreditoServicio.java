/**
 * Clase: SolicitudCreditoServicio.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.service;

import java.time.LocalDate;
import java.util.List;

import com.pichincha.stf.entity.Cliente;
import com.pichincha.stf.entity.SolicitudCredito;
import com.pichincha.stf.entity.to.SolicitudCreditoTo;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

/**
 *
 */
public interface SolicitudCreditoServicio {

	SolicitudCredito guardar(SolicitudCreditoTo solicitudCreditoTo) throws CreditoAutomotrizException;

	SolicitudCredito obtenerSolicituPorClienteFecha(String identificacion, LocalDate fechaElaboracion);

	List<SolicitudCredito> obtenerSolicitudesPorCliente(Cliente cliente);

	List<SolicitudCredito> obtenerSolicitudPorNumeroPuntoVentaIdentificacion(String numeroPuntoVenta,
			String identificacion);

}
