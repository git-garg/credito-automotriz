/**
 * Clase: SolicitudCreditoController.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pichincha.stf.entity.RespuestaTo;
import com.pichincha.stf.entity.SolicitudCredito;
import com.pichincha.stf.entity.to.SolicitudCreditoTo;
import com.pichincha.stf.service.RespuestaServicio;
import com.pichincha.stf.service.SolicitudCreditoServicio;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

/**
 *
 */

@RestController
@RequestMapping("/solicitud/credito")
public class SolicitudCreditoController {

	private static final Logger log = LoggerFactory.getLogger(SolicitudCreditoController.class);

	@Autowired
	private SolicitudCreditoServicio solicitudCreditoServicio;

	@Autowired
	private RespuestaServicio respuestaServicio;

	@PostMapping("/guardar")
	public ResponseEntity<RespuestaTo> guardarSolicitudCredito(@RequestBody SolicitudCreditoTo solicitudCreditoTo) {
		String mensaje = "";

		try {
			SolicitudCredito solicitudCredito = solicitudCreditoServicio.guardar(solicitudCreditoTo);
			mensaje = "Solicituda guardada correctamente"
					.concat(" - Codigo: ".concat(solicitudCredito.getCodigoSolicitudCredito().toString()));
			log.info(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("OK", mensaje), HttpStatus.OK);
		} catch (CreditoAutomotrizException e) {
			mensaje = "No se pudo guardar la solicitud. Error: " + e.getMessage();
			log.error(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("ERR", mensaje), HttpStatus.OK);

		}
	}

}
