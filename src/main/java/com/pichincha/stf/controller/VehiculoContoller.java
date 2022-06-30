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
import com.pichincha.stf.entity.Vehiculo;
import com.pichincha.stf.entity.enumeration.EstadoVehiculoEnum;
import com.pichincha.stf.entity.to.VehiculoTo;
import com.pichincha.stf.service.RespuestaServicio;
import com.pichincha.stf.service.VehiculoServicio;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

@RestController
@RequestMapping("/vehiculo")
public class VehiculoContoller {

	private static final Logger log = LoggerFactory.getLogger(VehiculoContoller.class);

	@Autowired
	private VehiculoServicio vehiculoServicio;

	@Autowired
	private RespuestaServicio respuestaServicio;

	@PostMapping("/guardar")
	public ResponseEntity<RespuestaTo> guardarVehiculo(@RequestBody VehiculoTo vehiculoTo) {

		String mensaje = "";

		try {
			Vehiculo vehiculo = vehiculoServicio.guardarAPartirDeTo(vehiculoTo);
			mensaje = "Vehículo guardado correctamente".concat(" - Placa: ".concat(vehiculo.getPlaca()));
			log.info(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("OK", mensaje), HttpStatus.OK);
		} catch (CreditoAutomotrizException e) {
			mensaje = "No se pudo guardar el vehíulo. Error: " + e.getMessage();
			log.error(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("ERR", mensaje), HttpStatus.OK);

		}
	}

	@DeleteMapping("/eliminar")
	public ResponseEntity<RespuestaTo> eliminarCliente(@RequestParam String placa) {
		String mensaje = "";
		Vehiculo vehiculo = vehiculoServicio.obtenerPorPlacaEstado(placa, EstadoVehiculoEnum.DISPONIBLE);
		if (null == vehiculo) {
			mensaje = "No existe vehículo o esta comprometido. Placa: ".concat(" - Placa: ".concat(placa));
			log.info(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("OK", mensaje), HttpStatus.OK);
		} else {
			vehiculoServicio.eliminarVahiculo(vehiculo);
			mensaje = "Vehículo eliminado correctamente".concat(" - Placa: ".concat(vehiculo.getPlaca()));
			log.info(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("OK", mensaje), HttpStatus.OK);
		}
	}

	@PostMapping("/actualizar")
	public ResponseEntity<RespuestaTo> actualizarCliente(@RequestBody VehiculoTo vehiculoTo) {
		String mensaje = "";

		try {
			vehiculoServicio.actualizarAPartirDeTo(vehiculoTo);
			mensaje = "Vehículo actualizado correctamente"
					.concat(" - Placa: ".concat(vehiculoTo.getVehiculo().getPlaca()));
			log.info(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("OK", mensaje), HttpStatus.OK);
		} catch (CreditoAutomotrizException e) {
			mensaje = "No se pudo actualizar el vehíulo. Placa: ".concat(vehiculoTo.getVehiculo().getPlaca())
					.concat(". Error: ").concat(e.getMessage());
			log.info(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("ERR", mensaje), HttpStatus.OK);
		}
	}

}
