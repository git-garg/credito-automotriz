/**
 * Clase: EstadoCivilEnum.java
 * Fecha: 27 jun. 2022
 * Usuario: GABRIEL
 */
package com.pichincha.stf.entity;

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

}
