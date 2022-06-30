/**
 * Clase: RespuestaServicioImpl.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.service.implementation;

import org.springframework.stereotype.Service;

import com.pichincha.stf.entity.RespuestaTo;
import com.pichincha.stf.service.RespuestaServicio;

/**
 *
 */

@Service
public class RespuestaServicioImpl implements RespuestaServicio {

	@Override
	public RespuestaTo obtenerRespuestaTo(String respuesta, String mensaje) {
		return new RespuestaTo(respuesta, mensaje);
	}

}
