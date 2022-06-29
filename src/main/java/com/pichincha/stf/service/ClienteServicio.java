/**
 * Clase: ClienteServicio.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.service;

import java.io.IOException;
import java.net.URISyntaxException;

import com.opencsv.exceptions.CsvException;
import com.pichincha.stf.service.exception.ClienteException;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

/**
 *
 */
public interface ClienteServicio {

	void cargarClientes(String ruta, String archivo)
			throws IOException, URISyntaxException, CsvException, ClienteException, CreditoAutomotrizException;

}
