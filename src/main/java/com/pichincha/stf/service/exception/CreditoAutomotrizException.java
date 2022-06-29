/**
 * Clase: CreditoAutomotrizException.java
 * Fecha: 28 jun. 2022
 */
package com.pichincha.stf.service.exception;

/**
 * Clase para el manejo de excepciones de cliente
 *
 */
public class CreditoAutomotrizException extends Exception {

	private static final long serialVersionUID = -2505315993644985890L;

	public CreditoAutomotrizException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}

	public CreditoAutomotrizException(String mensaje) {
		super(mensaje);
	}

	public CreditoAutomotrizException(Throwable causa) {
		super(causa);
	}

}
