/**
 * Clase: EstadoVehiculoEnum.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.entity.enumeration;

import java.util.Arrays;

/**
 *
 */
public enum EstadoVehiculoEnum {

	DISPONIBLE("DIS"), COMPROMETIDO("COM"), VENDIDO("VEN");

	public String getAbreviatura() {
		return abreviatura;
	}

	private String abreviatura;

	private EstadoVehiculoEnum(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public static EstadoVehiculoEnum obtenerEstadoSolicitudPorAbreviatura(String abreviatura) {
		return Arrays.stream(EstadoVehiculoEnum.values())
				.filter(estadoVehiculo -> estadoVehiculo.getAbreviatura().equals(abreviatura)).findFirst().orElse(null);
	}

}
