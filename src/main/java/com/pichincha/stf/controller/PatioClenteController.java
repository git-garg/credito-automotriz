package com.pichincha.stf.controller;

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

import com.pichincha.stf.entity.RespuestaTo;
import com.pichincha.stf.entity.to.ClientePatioTo;
import com.pichincha.stf.service.PatioClienteServicio;
import com.pichincha.stf.service.RespuestaServicio;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

@RestController
@RequestMapping("/cliente-patio/")
public class PatioClenteController {

	private static final Logger log = LoggerFactory.getLogger(PatioClenteController.class);

	@Autowired
	private PatioClienteServicio patioClienteServicio;

	@Autowired
	private RespuestaServicio respuestaServicio;

	@PostMapping("/guardar")
	public ResponseEntity<RespuestaTo> guardarPatioCliente(@RequestBody ClientePatioTo clientePatioTo) {
		String identificacion = clientePatioTo.getIdentificacion();
		String numeroPuntoVenta = clientePatioTo.getNumeroPuntoVenta();
		try {
			patioClienteServicio.guardarPatioCliente(numeroPuntoVenta, identificacion);
			String mensaje = "Se ha realizado la asignación correctamente. Patio: "
					.concat(numeroPuntoVenta.concat(". Cliente: ".concat(identificacion)));
			log.info(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("OK", mensaje), HttpStatus.OK);
		} catch (CreditoAutomotrizException e) {
			String mensaje = "No se pudo realizar la asignación correctamente. Patio: ".concat(numeroPuntoVenta
					.concat(". Cliente: ".concat(identificacion)).concat(" Error: ").concat(e.getMessage()));
			log.error(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("ERR", mensaje), HttpStatus.OK);
		}
	}

	@PostMapping("/actualizar")
	public ResponseEntity<RespuestaTo> actualizarPatioCliente(@RequestBody ClientePatioTo clientePatioTo) {

		String identificacion = clientePatioTo.getIdentificacion();
		String numeroPuntoVenta = clientePatioTo.getNumeroPuntoVenta();

		String nuevaIdentificacion = clientePatioTo.getNuevaIdentificacion();
		String nuevoNumeroPuntoVenta = clientePatioTo.getNuevoNumeroPuntoVenta();

		try {
			patioClienteServicio.actualizarPatioCliente(numeroPuntoVenta, identificacion, nuevoNumeroPuntoVenta,
					nuevaIdentificacion);
			String mensaje = "Se ha actualizado la asignación correctamente. Patio: "
					.concat(numeroPuntoVenta.concat(". Cliente: ".concat(identificacion)));

			log.info(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("OK", mensaje), HttpStatus.OK);
		} catch (CreditoAutomotrizException e) {
			String mensaje = "Se pudo actualizar la asignación correctamente. Patio: "
					.concat(nuevoNumeroPuntoVenta.concat(". Cliente: ".concat(nuevaIdentificacion)))
					.concat(" Error: ".concat(e.getMessage()));
			log.error(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("ERR", mensaje), HttpStatus.OK);
		}

	}

	@DeleteMapping("/eliminar")
	public ResponseEntity<RespuestaTo> eliminarPatioCliente(@RequestParam String numeroPuntoVenta,
			@RequestParam String identificacion) {
		try {
			patioClienteServicio.eliminarPatioCliente(numeroPuntoVenta, identificacion);
			String mensaje = "La asignacion se ha eliminado correctamente. Clinete: ".concat(identificacion)
					.concat(". Número de punto de venta: ").concat(numeroPuntoVenta);
			log.info(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("OK", mensaje), HttpStatus.OK);
		} catch (CreditoAutomotrizException e) {
			String mensaje = "No se ha podido eliminar la asignacion. ".concat(e.getMessage());
			log.error(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("ERR", mensaje), HttpStatus.OK);
		}
	}

}
