/**
 * Clase: RespuestaServicio.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.service;

import com.pichincha.stf.entity.RespuestaTo;

/**
 *
 */
public interface RespuestaServicio {

	RespuestaTo obtenerRespuestaTo(String respuesta, String mensaje);

}
