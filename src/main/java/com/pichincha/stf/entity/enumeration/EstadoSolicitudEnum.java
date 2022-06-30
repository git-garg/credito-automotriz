/**
 * Clase: EstadoSolicitud.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.entity.enumeration;

import java.util.Arrays;

/**
 *
 */
public enum EstadoSolicitudEnum {

	REGISTRADA("REG"), DESPACHADA("DES"), CANCELADA("PEN");

	private String abreviatura;

	private EstadoSolicitudEnum(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public static EstadoSolicitudEnum obtenerEstadoSolicitudPorAbreviatura(String abreviatura) {
		return Arrays.stream(EstadoSolicitudEnum.values())
				.filter(estadoSolicitud -> estadoSolicitud.getAbreviatura().equals(abreviatura)).findFirst()
				.orElse(null);
	}

}
