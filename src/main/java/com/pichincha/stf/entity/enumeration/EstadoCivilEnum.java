/**
 * Clase: EstadoCivilEnum.java
 * Fecha: 27 jun. 2022
 */
package com.pichincha.stf.entity.enumeration;

import java.util.Arrays;

/**
 * Enumeracion para estado civil
 *
 */

public enum EstadoCivilEnum {

	SOLTERO("SOL"), CASADO("CAS"), DIVORCIADO("DIV"), UNION_LIBRE("UNL"), VIUDO("VIU"), UNION_DE_HECHO("UDH");

	private String abreviatura;

	private EstadoCivilEnum(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public static EstadoCivilEnum getEstadoCivilEnumPorAbreviatura(String abreviatura) {
		return Arrays.stream(EstadoCivilEnum.values())
				.filter(estadoCivil -> estadoCivil.getAbreviatura().equals(abreviatura)).findFirst().orElse(null);
	}

}
