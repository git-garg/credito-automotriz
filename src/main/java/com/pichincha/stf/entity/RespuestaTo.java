/**
 * Clase: RespuestaTo.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.entity;

import lombok.Data;

/**
 *
 */
@Data
public class RespuestaTo {

	private String respuesta;
	private String mensaje;

	public RespuestaTo(String respuesta, String mensaje) {
		super();
		this.respuesta = respuesta;
		this.mensaje = mensaje;
	}

}
