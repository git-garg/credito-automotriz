/**
 * Clase: ProcesadorCsvServicioImpl.java
 * Fecha: 27 jun. 2022
 */
package com.pichincha.stf.service;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

/**
 * Clase que contiene los metodos para la lectura del archivo csv
 *
 */

@Service
public class ProcesadorCsvServicioImpl implements ProcesadorCsvServicio {

	/**
	 * obtiene los registros duplicados
	 * 
	 * @param registrosArchivoCsv
	 * @return
	 */
	@Override
	public Collection<String[]> obtenerDuplicados(List<String[]> registrosArchivoCsv, int posicionColumnaCsv) {

		Map<String, String[]> mapa = new ConcurrentHashMap<String, String[]>();
		Map<String, String[]> duplicado = new ConcurrentHashMap<String, String[]>();
		registrosArchivoCsv.stream().forEach(registro -> {

			if (mapa.get(registro[posicionColumnaCsv]) == null) {
				mapa.put(registro[posicionColumnaCsv], registro);
			} else {
				duplicado.put(registro[posicionColumnaCsv], registro);
			}
		});

		return duplicado.values();
	}

	/**
	 * Obtiene una lista de arreglo de String del archivo scv
	 * 
	 * @param ruta
	 * @param archivo
	 * @return
	 * @throws IOException
	 * @throws CsvException
	 * @throws URISyntaxException
	 */
	@Override
	public List<String[]> obtenerRegistrosArchivoCsv(String ruta, String archivo)
			throws IOException, CsvException, URISyntaxException {

		Reader reader = Files
				.newBufferedReader(Paths.get(ClassLoader.getSystemResource(ruta.concat("/").concat(archivo)).toURI()));

		CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
		CSVReader lectorCsv = new CSVReaderBuilder(reader).withSkipLines(1).withCSVParser(parser).build();

		List<String[]> registrosCsv = new ArrayList<String[]>();
		registrosCsv = lectorCsv.readAll();
		reader.close();
		lectorCsv.close();

		return registrosCsv;
	}

}
