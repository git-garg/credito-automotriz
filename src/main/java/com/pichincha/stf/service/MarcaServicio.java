/**
 * Clase: MarcaServicio.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.service;

import java.io.IOException;
import java.net.URISyntaxException;

import com.opencsv.exceptions.CsvException;

/**
 * 
 */
public interface MarcaServicio {

	void cargarMarcas(String ruta, String arhivo) throws IOException, URISyntaxException, CsvException;

}
