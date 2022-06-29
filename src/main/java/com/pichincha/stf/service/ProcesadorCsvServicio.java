/**
 * Clase: ProcesadorCsvServicio.java
 * Fecha: 27 jun. 2022
 */
package com.pichincha.stf.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import com.opencsv.exceptions.CsvException;

/**
 * 
 * Interface que define los metodos para la lectura del archivo csv
 *
 */
public interface ProcesadorCsvServicio {

	

	Collection<String[]> obtenerDuplicados(List<String[]> registrosArchivoCsv, int posicionColumnaCsv);

	List<String[]> obtenerRegistrosArchivoCsv(String ruta, String archivo)
			throws IOException, CsvException, URISyntaxException;
}
