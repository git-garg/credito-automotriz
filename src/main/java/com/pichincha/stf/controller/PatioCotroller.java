/**
 * Clase: PatioCotroller.java
 * Fecha: 1 jul. 2022
 */
package com.pichincha.stf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pichincha.stf.entity.Patio;
import com.pichincha.stf.entity.RespuestaTo;
import com.pichincha.stf.entity.to.PatioTo;
import com.pichincha.stf.service.PatioServicio;
import com.pichincha.stf.service.RespuestaServicio;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

/**
 *
 */

@RestController
@RequestMapping("/patio")
public class PatioCotroller {

	private static final Logger log = LoggerFactory.getLogger(PatioCotroller.class);

	@Autowired
	private PatioServicio patioServicio;

	@Autowired
	private RespuestaServicio respuestaServicio;

	@PostMapping("/guardar")
	public ResponseEntity<RespuestaTo> guardarPatio(@RequestBody PatioTo vehiculoTo) {
		try {
			Patio patio = patioServicio.guardarPatioAPatirDeTo(vehiculoTo);
			String mensaje = "El patio se ha guardado correctamente: Número de punto de venta: "
					.concat(patio.getNumeroPuntoVenta());
			log.info(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("OK", mensaje), HttpStatus.OK);
		} catch (CreditoAutomotrizException e) {
			String mensaje = "No se puedo guardar el patio: ".concat(e.getMessage());
			log.info(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("ERR", mensaje), HttpStatus.OK);
		}
	}

	@PostMapping("/actualizar")
	public ResponseEntity<RespuestaTo> actualizarPatio(@RequestBody PatioTo vehiculoTo) {
		try {
			Patio patio = patioServicio.actualizarPatioAPatirDeTo(vehiculoTo);
			String mensaje = "El patio se ha actualizado correctamente: Número de punto de venta: "
					.concat(patio.getNumeroPuntoVenta());
			log.info(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("OK", mensaje), HttpStatus.OK);
		} catch (CreditoAutomotrizException e) {
			String mensaje = "No se pudo actualizar el patio. ".concat(e.getMessage());
			log.error(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("ERR", mensaje), HttpStatus.OK);
		}
	}

	@DeleteMapping("/eliminar")
	public ResponseEntity<RespuestaTo> eliminarPatio(@RequestParam String numeroPuntoVenta) {
		try {
			patioServicio.eliminarPatio(numeroPuntoVenta);
			String mensaje = "Patio eliminado correctamente: ".concat(numeroPuntoVenta);
			log.info(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("OK", mensaje), HttpStatus.OK);
		} catch (CreditoAutomotrizException e) {
			String mensaje = "No se pudo eliminar el patio: ".concat(e.getMessage());
			log.error(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("ERR", mensaje), HttpStatus.OK);
		}
	}

	@GetMapping("/buscar/{numeroPuntoVenta}")
	public ResponseEntity<PatioTo> bucarPatio(@PathVariable String numeroPuntoVenta) {
		Patio patio = patioServicio.obtenerPorNumeroPuntoVenta(numeroPuntoVenta);
		PatioTo patioTo = new PatioTo();
		patioTo.setPatio(patio);
		return new ResponseEntity(patioTo, HttpStatus.OK);
	}

}
