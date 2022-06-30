/**
 * Clase: SiNoEnum.java
 * Fecha: 27 jun. 2022
 */
package com.pichincha.stf.entity.enumeration;

import java.util.Arrays;

/**
 * Enumeracion para valores de si, no
 *
 */
public enum SiNoEnum {

	SI("S"), NO("N");

	private String abreviatura;

	private SiNoEnum(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public static SiNoEnum obtenerSiNOEnumPorAbreviatura(String abreviatura) {
		return Arrays.stream(SiNoEnum.values()).filter(siNo -> siNo.getAbreviatura().equals(abreviatura)).findFirst()
				.orElse(null);
	}

}
