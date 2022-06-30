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
import com.pichincha.stf.entity.Vehiculo;
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
			Vehiculo vehiculo = vehiculoServicio.guardar(vehiculoTo);
			mensaje = "Vehículo guardado correctamente".concat(" - Placa: ".concat(vehiculo.getPlaca()));
			log.info(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("OK", mensaje), HttpStatus.OK);
		} catch (CreditoAutomotrizException e) {
			mensaje = "No se pudo guardar el vehíulo. Error: " + e.getMessage();
			log.error(mensaje);
			return new ResponseEntity<RespuestaTo>(respuestaServicio.obtenerRespuestaTo("ERR", mensaje), HttpStatus.OK);

		}
	}

}
