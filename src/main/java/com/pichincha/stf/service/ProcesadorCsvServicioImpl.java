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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.pichincha.stf.entity.Cliente;
import com.pichincha.stf.entity.EstadoCivilEnum;
import com.pichincha.stf.entity.SiNoEnum;
import com.pichincha.stf.respository.ClienteRepository;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;
import com.pichincha.stf.util.FechaUtil;
import com.pichincha.stf.util.Util;

/**
 * Clase que contiene los metodos para la lectura del archivo csv
 *
 */

@Service
public class ProcesadorCsvServicioImpl implements ProcesadorCsvServicio {

	@Autowired
	private ClienteRepository clienteRepository;

	private static final Logger log = LoggerFactory.getLogger(ProcesadorCsvServicioImpl.class);

	/**
	 * Carga los clientes a partir de un archivo csv
	 */
	@Override
	public void cargarClientes(String ruta, String archivo) throws IOException, URISyntaxException, CsvException {

		List<String[]> registrosArchivoCsv = obtenerRegistrosArchivoCsv(ruta, archivo);
		Collection<String[]> duplicados = obtenerDuplicados(registrosArchivoCsv);

		if (duplicados.isEmpty()) {

			registrosArchivoCsv.stream().forEach(registroCsv -> {
				try {
					clienteRepository.save(obtenerCliente(registroCsv));
				} catch (CreditoAutomotrizException e) {
					log.error("No se pudo crear el cliente con la identificacion: " + registroCsv[0] + " Error: "
							+ e.getMessage());
				}
			});

			log.info("Total registros ingresados: " + clienteRepository.findAll().size());
			clienteRepository.findAll().forEach(cliente -> log.info("Identificacion: " + cliente.getIdentificacion()));
		} else {
			log.error("Existen registros duplicados");
			duplicados.stream().forEach(duplicado -> log.error("Identificacion duplicada: " + duplicado[0]));
		}
	}

	/**
	 * obtiene los registros duplicados
	 * 
	 * @param registrosArchivoCsv
	 * @return
	 */
	private Collection<String[]> obtenerDuplicados(List<String[]> registrosArchivoCsv) {

		Map<String, String[]> mapa = new ConcurrentHashMap<String, String[]>();
		Map<String, String[]> duplicado = new ConcurrentHashMap<String, String[]>();
		registrosArchivoCsv.stream().forEach(registro -> {

			if (mapa.get(registro[0]) == null) {
				mapa.put(registro[0], registro);
			} else {
				duplicado.put(registro[0], registro);
			}
		});

		return duplicado.values();
	}

	/**
	 * Conviete un arreglo de String en objeto Cliente
	 * 
	 * @param registroCsv
	 * @return
	 * @throws CreditoAutomotrizException
	 */
	private Cliente obtenerCliente(String[] registroCsv) throws CreditoAutomotrizException {
		Cliente cliente = new Cliente();

		cliente.setIdentificacion(registroCsv[0]);
		cliente.setNombre(registroCsv[1]);
		cliente.setApellido(registroCsv[2]);
		cliente.setEdad(Util.obtenerIntegerDeString(registroCsv[3]));
		cliente.setDireccion(registroCsv[4]);
		cliente.setTelefono(registroCsv[5]);
		cliente.setFechaNacimiento(FechaUtil.obtenerFechaFormato(registroCsv[6], "dd/MM/yyyy"));
		cliente.setEstadoCivil(EstadoCivilEnum.getEstadoCivilEnumPorAbreviatura(registroCsv[7]));
		cliente.setIdentificacionConyugue(registroCsv[8]);
		cliente.setNombreConyugue(registroCsv[9]);
		cliente.setSujetoCredito(SiNoEnum.obtenerSiNOEnumPorAbreviatura(registroCsv[10]));

		return cliente;
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
	private List<String[]> obtenerRegistrosArchivoCsv(String ruta, String archivo)
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
