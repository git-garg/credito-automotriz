/**
 * Clase: ProcesadorCsvServicioImpl.java
 * Fecha: 27 jun. 2022
 * Usuario: GABRIEL
 */
package com.pichincha.stf.service;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

	@Override
	public String obtenerRegistrosArchivCsv(String ruta, String archivo)
			throws IOException, URISyntaxException, CsvException {
		Reader reader = Files
				.newBufferedReader(Paths.get(ClassLoader.getSystemResource(ruta.concat("/").concat(archivo)).toURI()));

		return this.leerTodosLosRegistros(reader).toString();
	}

	@Override
	public void cargarClientes() {
		// TODO Auto-generated method stub

	}

	public List<String[]> leerTodosLosRegistros(Reader reader) throws IOException, CsvException {

		CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();

		CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).withCSVParser(parser).build();

		List<String[]> list = new ArrayList<String[]>();
		list = csvReader.readAll();
		reader.close();
		csvReader.close();
		return list;
	}

}
