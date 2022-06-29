/**
 * Clase: ProcesadorCsvServicio.java
 * Fecha: 27 jun. 2022
 * Usuario: GABRIEL
 */
package com.pichincha.stf.service;

import java.io.IOException;
import java.net.URISyntaxException;

import com.opencsv.exceptions.CsvException;
import com.pichincha.stf.service.exception.ClienteException;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

/**
 * 
 * Interface que define los metodos para la lectura del archivo csv
 *
 */
public interface ProcesadorCsvServicio {

	void cargarClientes()
			throws IOException, URISyntaxException, CsvException, ClienteException, CreditoAutomotrizException;

}
