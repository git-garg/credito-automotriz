/**
 * Clase: ClienteException.java
 * Fecha: 28 jun. 2022
 * Usuario: GABRIEL
 */
package com.pichincha.stf.service.exception;

/**
 * Clase para el manejo de excepciones de cliente
 *
 */
public class ClienteException extends Exception {

	private static final long serialVersionUID = -4451743092234270197L;

	public ClienteException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}

	public ClienteException(String mensaje) {
		super(mensaje);
	}

	public ClienteException(Throwable causa) {
		super(causa);
	}

}
