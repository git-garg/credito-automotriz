/**
 * Clase: SolicitudCreditoTo.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.entity.to;

import com.pichincha.stf.entity.SolicitudCredito;

import lombok.Data;

/**
 *
 */
@Data
public class SolicitudCreditoTo {

	private SolicitudCredito solicitudCredito;

	private String identificacionCliente;

	private String numeroPuntoVenta;

	private String placa;

	private String identificacionEjecutivo;

}
