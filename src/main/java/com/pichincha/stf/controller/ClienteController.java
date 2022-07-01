/**
 * Clase: ClienteController.java
 * Fecha: 30 jun. 2022
 */
package com.pichincha.stf.controller;

import static org.hamcrest.CoreMatchers.containsString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pichincha.stf.entity.Cliente;
import com.pichincha.stf.entity.RespuestaTo;
import com.pichincha.stf.entity.to.ClienteTo;
import com.pichincha.stf.service.ClienteServicio;
import com.pichincha.stf.service.RespuestaServicio;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

/**
 *
 */

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

	@Autowired
	private ClienteServicio clienteServicio;

	@Autowired
	private RespuestaServicio respuestaServicio;

	@PostMapping("/guardar")
	public ResponseEntity<RespuestaTo> guardarCliente(@RequestBody ClienteTo clienteTo) {
		try {
			clienteServicio.guardarClienteDesdeTo(clienteTo);
			String mensaje = "Cliente guardado correctamente: ".concat(clienteTo.getCliente().getIdentificacion());
			log.info(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("OK", mensaje), HttpStatus.OK);

		} catch (CreditoAutomotrizException e) {
			String mensaje = "No se pudo guardar el cliente: ".concat(e.getMessage());
			log.info(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("ERR", mensaje), HttpStatus.OK);
		}

	}

	@PostMapping("/actualizar")
	public ResponseEntity<RespuestaTo> actualizarCliente(@RequestBody ClienteTo clienteTo) {
		String mensaje = "";
		try {
			clienteServicio.actualizarClienteDesdeTo(clienteTo);
			mensaje = "Cliente actualizado correctamente: ";
			log.info(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("OK", mensaje), HttpStatus.OK);

		} catch (CreditoAutomotrizException e) {
			mensaje = "No se pudo actualizar el cliente. Error: ".concat(e.getMessage());
			log.error(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("ERR", mensaje), HttpStatus.OK);
		}
	}

	@DeleteMapping("/eliminar")
	public ResponseEntity<RespuestaTo> elminarCliente(@RequestParam String identificacion) {
		Cliente cliente = clienteServicio.obtenerPorIdentificacion(identificacion);
		String mensaje = "";
		if (null == cliente) {
			mensaje = "No existe cliente con la identifiación: ".concat(identificacion);
			log.error(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("ERR", mensaje), HttpStatus.OK);
		} else {
			try {
				clienteServicio.eliminarCliente(cliente);
				mensaje = "Cliente eliminado correctamente. Identificacion: ".concat(identificacion);
				log.info(mensaje);
				return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("OK", mensaje),
						HttpStatus.OK);
			} catch (CreditoAutomotrizException e) {
				mensaje = "No se puede eliminar el cliente. Error: ".concat(e.getMessage());
				log.error(mensaje);
				return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("ERR", mensaje),
						HttpStatus.OK);
			}

		}

	}

}
